package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynMaterielService;
import com.connor.hozon.bom.resources.dto.request.UpdateHzMaterielReqDTO;
import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;
import webservice.base.helper.UUIDHelper;
import webservice.base.masterMaterial.ZPPTCO001;
import webservice.logic.ReflectMateriel;
import webservice.option.ActionFlagOption;
import webservice.service.impl.masterMaterial1.TransMasterMaterialService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同步主数据
 */
@Service("synMaterielService")
public class SynMaterielService implements ISynMaterielService {
    /**
     * 发送服务
     */
    @Autowired
    TransMasterMaterialService transMasterMaterialService;
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
    public JSONObject updateByPuids(List<UpdateHzMaterielReqDTO> dtos) {
        return updateOrDelete(dtos, ActionFlagOption.UPDATE_EMPTY);
    }

    /**
     * 删除操作
     *
     * @param dtos
     * @return
     */
    @Override
    public JSONObject deleteByPuids(List<UpdateHzMaterielReqDTO> dtos) {
        return updateOrDelete(dtos, ActionFlagOption.DELETE);
    }

    /**
     * 发送全部
     *
     * @param projectPuid
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
        List<HzMaterielRecord> sorted = hzMaterielDAO.findHzMaterielForList(query);
        result = execute(sorted, ActionFlagOption.ADD);
        return result;
    }

    /**
     * @param dtos
     * @param option 操作标识符，更新可传
     * @return
     */
    private JSONObject updateOrDelete(List<UpdateHzMaterielReqDTO> dtos, ActionFlagOption option) {
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
        StringBuilder sbs = new StringBuilder();
        StringBuilder sbf = new StringBuilder();
        JSONObject result = new JSONObject();

        Map<String, HzMaterielRecord> _mapCoach = new HashMap<>();
        Map<String, String> _factoryCoach = new HashMap<>();

        sbs.append("更新成功项:<br/>");
        sbf.append("更新失败项:<br/>");

        boolean hasFail = false;

        for (HzMaterielRecord record : sorted) {
            ReflectMateriel reflectMateriel = new ReflectMateriel(record);
            /////////////////////////////////////////////////////手动设置一些必填参数////////////////////////////////////////////////////
            //设置包号
            String packNo = UUIDHelper.generateUpperUid();
            reflectMateriel.setPackNo(packNo);
            //设置行号
            reflectMateriel.setLineNum(packNo.substring(0, 5));

            //更新操作
            reflectMateriel.setActionFlagOption(option);
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
        transMasterMaterialService.execute();
        List<ZPPTCO001> resultPool = transMasterMaterialService.getOut().getItem();

        for (ZPPTCO001 zpptco001 : resultPool) {
            if ("S".equalsIgnoreCase(zpptco001.getTYPE())) {
                sbs.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(zpptco001.getGUID()).getpMaterielCode() + "<br/>");
            } else {
                sbf.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(zpptco001.getGUID()).getpMaterielCode() + "(" + zpptco001.getMESSAGE() + ")<br/>");
                hasFail = true;
            }
        }
        if (hasFail) {
            result.put("msg", sbs.append("<br/>" + sbf).toString());
        } else {
            result.put("msg", sbs.toString());
        }
        result.put("status", true);
        return result;
    }

}
