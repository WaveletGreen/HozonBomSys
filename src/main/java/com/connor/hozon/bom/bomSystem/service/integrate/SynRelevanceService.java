package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynRelevanceService;
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

    /**
     * 一开始同步所有数据到ERP，不实现
     *
     * @param projectPuid
     * @return
     */
    @Override
    public JSONObject synAllByProjectPuid(String projectPuid) {
        return null;
    }

    /**
     * 添加相关性
     *
     * @param relevance
     * @return
     */
    @Override
    public JSONObject addRelevance(List<HzRelevanceBean> relevance) throws Exception {
        return null;
    }

    /**
     * 更新相关性，只能更新描述
     *
     * @param relevancePuid
     * @return
     */
    @Override
    public JSONObject updateRelevance(String relevancePuid) {
        return null;
    }

    /**
     * 删除相关性，传给SAP时设置ZKNART为3
     *
     * @param features
     * @return
     */
    @Override
    public JSONObject deleteRelevance(List<HzRelevanceBean> features) throws Exception {
        return null;
    }

    public JSONObject execute(List<HzRelevanceBean> features, ActionFlagOption option) throws Exception {
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
        features.forEach(bean -> {
            Correlate correlate = new Correlate();
            String packnum = UUIDHelper.generateUpperUid();
            correlate.setPackNo(packnum);
            correlate.setLineNum(bean.getPuid().substring(0, 5));
            //动作描述代码
            correlate.setActionFlag(ActionFlagOption.ADD);
            //相关性
            correlate.setCorrelate(bean.getRelevance());
            //相关性描述
            correlate.setCorrelateDescript(bean.getRelevanceDesc());
            //相关性状态
            correlate.setCorrelateState("5");
            //创建日期
            correlate.setCreateDate(new Date());
            //相关性类型
            correlate.setCorrelateType(CorrelateTypeOption.CorrelateType_1);
            //相关性代码
            correlate.setCorrelateCode(bean.getRelevanceCode());
            _mapCoach.put(packnum, bean);
            transOptionsService.getInput().getItem().add(correlate.getZpptci004());
        });
        //发送
        if (!SynMaterielService.debug)
            transOptionsService.execute();
        List<ZPPTCO004> list = transOptionsService.getOut().getItem();
        if (list != null && list.size() > 0) {
            result.put("status", true);
            for (ZPPTCO004 _l : list) {
                total++;
                if (_l == null) {
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
