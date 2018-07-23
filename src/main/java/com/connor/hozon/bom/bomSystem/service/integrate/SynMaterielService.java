package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynMaterielService;
import com.connor.hozon.bom.resources.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
import integration.base.masterMaterial.ZPPTCO001;
import integration.logic.ReflectMateriel;
import integration.option.ActionFlagOption;
import integration.service.impl.masterMaterial1.TransMasterMaterialService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;

import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkStringIsEmpty;

/**
 * 同步主数据
 */
@Service("synMaterielService")
public class SynMaterielService implements ISynMaterielService {
    public static boolean debug = true;
    /**
     * 发送服务
     */
    @Autowired
    TransMasterMaterialService transMasterMaterialService;

    /***
     * sql语句in的容量
     */
    private final static int capacity = 1000;
    /**
     * 物料服务
     */
    @Autowired
    HzMaterielDAO hzMaterielDAO;
    /**
     * 工厂服务
     */
    @Autowired
    HzFactoryDAO hzFactoryDAO;


    /**
     * 更新操作
     *
     * @param dtos
     * @return
     */
    @Override
    public JSONObject updateOrAddByUids(List<EditHzMaterielReqDTO> dtos) {
        return updateOrDelete(dtos, ActionFlagOption.UPDATE_EMPTY);
    }

    /**
     * 删除操作
     *
     * @param dtos
     * @return
     */
    @Override
    public JSONObject deleteByPuids(List<EditHzMaterielReqDTO> dtos) {
        return updateOrDelete(dtos, ActionFlagOption.DELETE);
    }

    /**
     * 发送全部
     *
     * @param projectPuid 006567
     * @return
     */
    @Override
    public JSONObject synAllByProjectPuid(String projectPuid) {
        JSONObject result;
        if (projectPuid == null || "".equalsIgnoreCase(projectPuid)) {
            result = new JSONObject();
            result.put("status", false);
            result.put("msg", "查无数据");
            return result;
        }
        transMasterMaterialService.setClearInputEachTime(true);
        HzMaterielQuery query = new HzMaterielQuery();
        query.setProjectId(projectPuid);
        List<HzMaterielRecord> src = hzMaterielDAO.findHzMaterielForList(query);
        List<HzMaterielRecord> sorted = new ArrayList<>();
        src.stream().filter(_s -> _s.getpMaterielType() != null).forEach(_s -> sorted.add(_s));
        result = execute(sorted, ActionFlagOption.ADD);
        return result;
    }

    /**
     * @param dtos
     * @param option 操作标识符，更新可传
     * @return
     */
    private JSONObject updateOrDelete(List<EditHzMaterielReqDTO> dtos, ActionFlagOption option) {

        JSONObject result;
        if (dtos == null || dtos.isEmpty()) {
            result = new JSONObject();
            result.put("status", false);
            result.put("msg", "查无数据");
            return result;
        }

        //每一次调用都清空缓存
        transMasterMaterialService.setClearInputEachTime(true);
        HzMaterielQuery query = new HzMaterielQuery();

        List<HzMaterielRecord> sorted = new ArrayList<>();
        List<String> puids = new ArrayList<>();

        dtos.forEach(dto -> puids.add(dto.getPuid()));

        for (String puid : puids) {
            //拒绝为空
            if (puid == null || "".equalsIgnoreCase(puid))
                continue;
            query.setPuid(puid);
            HzMaterielRecord record = hzMaterielDAO.getHzMaterielRecord(query);
            //拒绝为空
            if (record == null)
                continue;
            sorted.add(record);
        }
        result = execute(sorted, option);
        return result;
    }


    /**
     * 执行操作
     *
     * @param sorted
     * @param option
     * @return
     */
    private JSONObject execute(List<HzMaterielRecord> sorted, ActionFlagOption option) {
        transMasterMaterialService.setClearInputEachTime(true);
        if (debug)
            transMasterMaterialService.getInput().getItem().clear();
        /**
         * 成功项
         */
        List<IntegrateMsgDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgDTO> fail = new ArrayList<>();

        /***
         * 计数
         */
        int total = 0;
        int totalOfSuccess = 0;
        int totalOfFail = 0;
        int totalOfOutOfParent = 0;
        int totalOfUnknown = 0;

        JSONObject result = new JSONObject();

        Map<String, HzMaterielRecord> _mapCoach = new HashMap<>();
        Map<String, String> _factoryCoach = new HashMap<>();

        List<HzMaterielRecord> toUpdate = new ArrayList<>();
        List<String> puids = new ArrayList<>();
        Set<String> materialCode = new HashSet<>();
        //没有过滤部门层
        for (HzMaterielRecord record : sorted) {
            //过滤重复的零件号
            if (materialCode.contains(record.getpMaterielCode())) {
                continue;
            } else {
                materialCode.add(record.getpMaterielCode());
            }
            if (!validate(record)) {
                totalOfUnknown++;
                continue;
            }
            ReflectMateriel reflectMateriel = new ReflectMateriel(record);
            /////////////////////////////////////////////////////手动设置一些必填参数////////////////////////////////////////////////////
            //设置包号
            String packNo = UUIDHelper.generateUpperUid();
            reflectMateriel.setPackNo(packNo);
            //            //设置行号
            //            reflectMateriel.setLineNum(packNo.substring(0, 5));

            //已经发送过了，设置为更新
            if (record.getSendSapFlag() != null && record.getSendSapFlag() == 1) {
                //更新操作或删除操作
                if (option == ActionFlagOption.DELETE || option == ActionFlagOption.UPDATE_EMPTY) {
                    reflectMateriel.setActionFlagOption(option);
                }
                //默认执行更新操作
                else {
                    reflectMateriel.setActionFlagOption(ActionFlagOption.UPDATE_EMPTY);
                }
            }
            //没有发送过，执行新增操作
            else {
                //更新操作或新增操作
                if (option == ActionFlagOption.UPDATE_EMPTY || option == ActionFlagOption.ADD) {
                    reflectMateriel.setActionFlagOption(ActionFlagOption.ADD);
                }
                //没有，不允许删除
                else {
                    totalOfUnknown++;
                    continue;
                }
            }
            if (!_factoryCoach.containsKey(record.getpFactoryPuid())) {
                HzFactory factory = hzFactoryDAO.findFactory(record.getpFactoryPuid(), null);
                _factoryCoach.put(record.getpFactoryPuid(), factory.getpFactoryCode());
            }
            //设置工厂
            reflectMateriel.setFactory(_factoryCoach.get(record.getpFactoryPuid()));
            reflectMateriel.setMRPAndPurchase(record.getpMrpController(), record.getResource(), "默认公告");
            /////////////////////////////////////////////////////手动设置一些必填参数////////////////////////////////////////////////////
            transMasterMaterialService.getInput().getItem().add(reflectMateriel.getMm().getZpptci001());
            //加入缓存
            _mapCoach.put(packNo, record);
        }
        _mapCoach.forEach((k, v) ->
                System.out.println(v.getpMaterielCode())
        );
        //执行
        if (debug) {
            return result;
        } else {
            transMasterMaterialService.execute();
        }
        List<ZPPTCO001> resultPool = transMasterMaterialService.getOut().getItem();
        if (resultPool != null)
            for (ZPPTCO001 zpptco001 : resultPool) {
                total++;
                if (zpptco001 == null || checkStringIsEmpty(zpptco001.getPGUID())) {
                    totalOfUnknown++;
                    continue;
                }
                HzMaterielRecord record = _mapCoach.get(zpptco001.getPGUID());
                IntegrateMsgDTO msgDTO = new IntegrateMsgDTO();
                msgDTO.setMsg(zpptco001.getPMESSAGE());
                msgDTO.setItemId(record.getpMaterielCode());
                msgDTO.setPuid(record.getPuid());

                if ("S".equalsIgnoreCase(zpptco001.getPTYPE())) {
                    success.add(msgDTO);
                    totalOfSuccess++;
                    toUpdate.add(record);
                } else {
                    fail.add(msgDTO);
                    totalOfFail++;
                }
            }

        /**
         * 更新信息
         */
        toUpdate.forEach(to -> puids.add(to.getpMaterielCode()));
        splitListThenUpdate(puids);

        result.put("status", true);
        result.put("success", success);
        result.put("fail", fail);

        result.put("total", total);
        result.put("totalOfSuccess", totalOfSuccess);
        result.put("totalOfFail", totalOfFail);
        result.put("totalOfOutOfParent", totalOfOutOfParent);
        result.put("totalOfUnknown", totalOfUnknown);
        return result;
    }

    /**
     * 拆分list，然后查询数据库
     *
     * @param list uid集合
     * @return
     */
    public int splitListThenUpdate(List<String> list) {
        int intCount = list.size() / capacity;
        int index = 0;
        int endIndex;
        int result = 0;

        if (intCount == 0) {
            return executeByType(list);
        } else if (intCount > 0) {
            endIndex = index + capacity;
            while (true) {
                //第一次查0-1000，以后都从1000-2000...查
                result = executeByType(list.subList(index, endIndex));
                //向后截列表
                index += capacity;
                //加1000
                endIndex = index + capacity;
                if (endIndex >= list.size()) {
                    endIndex = list.size();
                    result = executeByType(list.subList(index, endIndex));
                    break;
                }
                if (result < capacity) {
                    result = -1;
                    break;
                }
            }
        }
        return result;
    }

    private int executeByType(List<String> list) {
        return hzMaterielDAO.updateByBatch(list);
    }

    private boolean validate(HzMaterielRecord record) {
        if (null == record.getpMaterielDesc() || "".equalsIgnoreCase(record.getpMaterielDesc()) || null == record.getpFactoryPuid() || "".equals(record.getpFactoryPuid())) {
            return false;
        } else {
            return true;
        }
    }
}
