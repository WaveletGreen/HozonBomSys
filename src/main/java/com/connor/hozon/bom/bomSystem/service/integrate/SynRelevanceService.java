package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelGroupDao;
import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynRelevanceService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import integration.base.relevance.ZPPTCO004;
import integration.logic.Correlate;
import integration.option.ActionFlagOption;
import integration.option.CorrelateTypeOption;
import integration.service.impl.feature4.TransOptionsService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0Record;

import java.util.*;

@Configuration
public class SynRelevanceService implements ISynRelevanceService {
    @Autowired
    TransOptionsService transOptionsService;

    @Autowired
    HzCfg0Service hzCfg0Service;
    /**
     * 模型族dao层
     */
    @Autowired
    HzCfg0ModelGroupDao hzCfg0ModelGroupDao;//            hzCfg0ModelGroupDao.selectByMainUid(cfg0MainPuid);


    /**
     * 一开始同步所有数据到ERP，不实现
     *
     * @param projectPuid
     * @return
     */
    @Override
    public JSONObject synAllByProjectPuid(String projectPuid) throws Exception {
        List<HzCfg0Record> list = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, new QueryBase());
        List<HzRelevanceBean> beans = new ArrayList<>();
        if (list != null && list.size() > 0) {
            sortData(list, beans);
            return execute(beans, ActionFlagOption.ADD, CorrelateTypeOption.CorrelateType_1);
        } else {
            return null;
        }

    }

    /**
     * 添加相关性
     *
     * @param relevance
     * @return
     */
    @Override
    public JSONObject addRelevance(List<HzRelevanceBean> relevance) throws Exception {
        return execute(relevance, ActionFlagOption.ADD, CorrelateTypeOption.CorrelateType_1);
    }

    /**
     * 更新相关性，只能更新描述
     *
     * @param relevance
     * @return
     */
    @Override
    public JSONObject updateRelevance(HzRelevanceBean relevance) throws Exception {
        //设置可用
        return execute(Collections.singletonList(relevance), ActionFlagOption.UPDATE, CorrelateTypeOption.CorrelateType_1);
    }

    /**
     * 删除相关性，传给SAP时设置ZKNART为3
     *
     * @param relevance
     * @return
     */
    @Override
    public JSONObject deleteRelevance(List<HzRelevanceBean> relevance) throws Exception {
        return execute(relevance, ActionFlagOption.DELETE, CorrelateTypeOption.CorrelateType_3);
    }

    /**
     * 整理数据，将特性转化成相关性
     *
     * @param list  特性列表
     * @param beans 相关性结果集
     */
    public void sortData(List<HzCfg0Record> list, List<HzRelevanceBean> beans) {
        list.forEach(cfg0Record -> {
            HzRelevanceBean bean = new HzRelevanceBean();
            //相关性代码
            bean.setRelevanceCode(cfg0Record.getpCfg0Relevance());
            //相关性
            bean.setRelevance(cfg0Record.getpCfg0FamilyName() + "-" + cfg0Record.getpCfg0ObjectId());
            //相关性描述
            bean.setRelevanceDesc(cfg0Record.getpCfg0FamilyDesc() + "-" + cfg0Record.getpCfg0Desc());
            //创建时间
            bean.setCreateDate(cfg0Record.getCreateDate());
            //修改时间
            bean.setModifyDate(cfg0Record.getLastModifyDate());
            //相关性是否已发送
            bean.setIsRelevanceSended(cfg0Record.getIsRelevanceSent());
            //存入puid
            bean.setPuid(cfg0Record.getPuid());
            //添加进缓存
            beans.add(bean);
        });
    }


    public JSONObject execute(List<HzRelevanceBean> features, ActionFlagOption option, CorrelateTypeOption correlateTypeOption) throws Exception {
        /**
         * 清除缓存
         */
        transOptionsService.setClearInputEachTime(true);
        transOptionsService.getInput().getItem().clear();

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

        List<HzRelevanceBean> toSend = new ArrayList<>();
        //需要更新的数据，更新特性属性
        List<HzRelevanceBean> needToUpdateStatus = new ArrayList<>();
        Map<String, HzRelevanceBean> _mapCoach = new HashMap<>();
        JSONObject result = new JSONObject();
        for (HzRelevanceBean bean : features) {
            Correlate correlate = new Correlate(bean);
            String packnum = UUIDHelper.generateUpperUid();
            //包号
            correlate.setPackNo(packnum);
            //动作描述代码
            if (option == ActionFlagOption.ADD) {
                if (null == bean.getIsRelevanceSended() || 0 == bean.getIsRelevanceSended()) {
                    correlate.setActionFlag(option);
                } else {
                    //更新
                    correlate.setActionFlag(ActionFlagOption.UPDATE);
                }
            }
            //不排除删除状态
            else {
                //没有发送过，则不执行更新状态
                if (null == bean.getIsRelevanceSended() || 0 == bean.getIsRelevanceSended()) {
                    continue;
                } else {
                    correlate.setActionFlag(option);
                }
            }
            //相关性类型
            correlate.setCorrelateType(correlateTypeOption);
            _mapCoach.put(packnum, bean);
            transOptionsService.getInput().getItem().add(correlate.getZpptci004());
        }
        //发送
        if (!SynMaterielService.debug && transOptionsService.getInput().getItem().size() > 0) {
            transOptionsService.execute();
        }
        List<ZPPTCO004> list = transOptionsService.getOut().getItem();
        if (list != null && list.size() > 0 && _mapCoach.size() > 0) {
            for (ZPPTCO004 _l : list) {
                total++;
                if (_l == null) {
                    totalOfUnknown++;
                    continue;
                }
                if (null == _l.getPPACKNO()) {
                    totalOfUnknown++;
                    continue;
                }
                IntegrateMsgDTO dto = new IntegrateMsgDTO();
                HzRelevanceBean record = _mapCoach.get(_l.getPPACKNO());
                dto.setItemId(record.getRelevanceCode());
                dto.setMsg(_l.getPMESSAGE());
                dto.setPuid(record.getPuid());
                if ("S".equalsIgnoreCase(_l.getPTYPE())) {
                    success.add(dto);
                    totalOfSuccess++;
                    needToUpdateStatus.add(record);
                } else {
                    fail.add(dto);
                    totalOfFail++;
                }
            }
        }
        Map<String, Object> _map = new HashMap<>();
        //设定需要更新特性值已发送,不用设定相关性值已发送
        _map.put("isRelevanceSent", 1);
        _map.put("list", needToUpdateStatus);
        if (needToUpdateStatus != null && needToUpdateStatus.size() > 0)
            hzCfg0Service.doUpdateByBatch(_map);

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

}
