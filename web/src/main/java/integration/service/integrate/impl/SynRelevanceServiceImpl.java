/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.service.integrate.impl;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzCfg0ModelGroupDao;
import cn.net.connor.hozon.dao.dao.configuration.relevance.HzRelevanceBasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.relevance.HzRelevanceBasic;
import cn.net.connor.hozon.services.response.configuration.relevance.HzRelevanceResponseDTO;
import cn.net.connor.hozon.services.response.integration.IntegrateMsgResponseDTO;
import cn.net.connor.hozon.common.util.UUIDHelper;
import com.connor.hozon.service.configuration.feature.impl.FeatureValueServiceImpl;
import integration.base.relevance.ZPPTCO004;
import integration.logic.Correlate;
import integration.option.ActionFlagOption;
import integration.option.CorrelateTypeOption;
import integration.service.impl.feature4.TransOptionsService;
import integration.service.integrate.SynRelevanceService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public class SynRelevanceServiceImpl implements SynRelevanceService {
    @Autowired
    TransOptionsService transOptionsService;

    @Autowired
    FeatureValueServiceImpl hzFeatureServiceImpl;
    /**
     * 模型族dao层
     */
    @Autowired
    HzCfg0ModelGroupDao hzCfg0ModelGroupDao;//            hzCfg0ModelGroupDao.selectByMainUid(cfg0MainPuid);


    @Autowired
    HzRelevanceBasicDao hzRelevanceBasicDao;

//    /**
//     * 一开始同步所有数据到ERP，不实现
//     *
//     * @param projectPuid
//     * @return
//     */
//    @Override
//    public JSONObject synAllByProjectPuid(String projectPuid) throws Exception {
//        List<HzFeatureValue> list = hzFeatureServiceImpl.selectFeatureListByProjectId(projectPuid, new HzFeatureQuery());
//        List<HzRelevanceResponseDTO> beans = new ArrayList<>();
//        if (list != null && list.size() > 0) {
//            sortData(list, beans);
//            return execute(beans, ActionFlagOption.ADD, CorrelateTypeOption.CorrelateType_1);
//        } else {
//            return null;
//        }
//
//    }

//    /**
//     * 添加相关性
//     *
//     * @param relevance
//     * @return
//     */
//    @Override
//    public JSONObject addRelevance(List<HzRelevanceResponseDTO> relevance) throws Exception {
//        return execute(relevance, ActionFlagOption.ADD, CorrelateTypeOption.CorrelateType_1);
//    }
//
//    /**
//     * 更新相关性，只能更新描述
//     *
//     * @param relevance
//     * @return
//     */
//    @Override
//    public JSONObject updateRelevance(HzRelevanceResponseDTO relevance) throws Exception {
//        //设置可用
//        return execute(Collections.singletonList(relevance), ActionFlagOption.UPDATE, CorrelateTypeOption.CorrelateType_1);
//    }
//
//    /**
//     * 删除相关性，传给SAP时设置ZKNART为3
//     *
//     * @param relevance
//     * @return
//     */
//    @Override
//    public JSONObject deleteRelevance(List<HzRelevanceResponseDTO> relevance) throws Exception {
//        return execute(relevance, ActionFlagOption.DELETE, CorrelateTypeOption.CorrelateType_3);
//    }

//    /**
//     * 整理数据，将特性转化成相关性
//     *
//     * @param list  特性列表
//     * @param beans 相关性结果集
//     */
//    public void sortData(List<HzFeatureValue> list, List<HzRelevanceResponseDTO> beans) {
//        list.forEach(cfg0Record -> {
//            HzRelevanceResponseDTO bean = new HzRelevanceResponseDTO();
//            //相关性代码
//            bean.setRelevanceCode(cfg0Record.getpCfg0Relevance());
//            //相关性
//            bean.setRelevance(cfg0Record.getpCfg0FamilyName() + "-" + cfg0Record.getFeatureValueCode());
//            //相关性描述
//            bean.setRelevanceDesc(cfg0Record.getpCfg0FamilyDesc() + "-" + cfg0Record.getpCfg0Desc());
//            //创建时间
//            bean.setCreateDate(cfg0Record.getCreateDate());
//            //修改时间
//            bean.setModifyDate(cfg0Record.getLastModifyDate());
//            //相关性是否已发送
//            bean.setIsRelevanceSent(cfg0Record.getIsRelevanceSent());
//            //存入puid
//            bean.setId(cfg0Record.getId());
//            //添加进缓存
//            beans.add(bean);
//        });
//    }
//
//
//    public JSONObject execute(List<HzRelevanceResponseDTO> features, ActionFlagOption option, CorrelateTypeOption correlateTypeOption) throws Exception {
//        /**
//         * 清除缓存
//         */
//        transOptionsService.setClearInputEachTime(true);
//        transOptionsService.getInput().getItem().clear();
//
//        /**
//         * 成功项
//         */
//        List<IntegrateMsgResponseDTO> success = new ArrayList<>();
//        /**
//         * 失败项
//         */
//        List<IntegrateMsgResponseDTO> fail = new ArrayList<>();
//
//        /***
//         * 计数
//         */
//        int total = 0;
//        int totalOfSuccess = 0;
//        int totalOfFail = 0;
//        int totalOfOutOfParent = 0;
//        int totalOfUnknown = 0;
//
//        List<HzRelevanceResponseDTO> toSend = new ArrayList<>();
//        //需要更新的数据，更新特性属性
//        List<HzRelevanceResponseDTO> needToUpdateStatus = new ArrayList<>();
//        Map<String, HzRelevanceResponseDTO> _mapCoach = new HashMap<>();
//        JSONObject result = new JSONObject();
//        for (HzRelevanceResponseDTO bean : features) {
//            Correlate correlate = new Correlate(bean);
//            String packnum = UUIDHelper.generateUpperUid();
//            //包号
//            correlate.setPackNo(packnum);
//            //动作描述代码
//            if (option == ActionFlagOption.ADD) {
//                if (null == bean.getIsRelevanceSent() || 0 == bean.getIsRelevanceSent()) {
//                    correlate.setActionFlag(option);
//                } else {
//                    //更新
//                    correlate.setActionFlag(ActionFlagOption.UPDATE);
//                }
//            }
//            //不排除删除状态
//            else {
//                //没有发送过，则不执行更新状态
//                if (null == bean.getIsRelevanceSent() || 0 == bean.getIsRelevanceSent()) {
//                    continue;
//                } else {
//                    correlate.setActionFlag(option);
//                }
//            }
//            //相关性类型
//            correlate.setCorrelateType(correlateTypeOption);
//            _mapCoach.put(packnum, bean);
//            transOptionsService.getInput().getItem().add(correlate.getZpptci004());
//        }
//        //发送
//        if (!SynMaterielService.debug && transOptionsService.getInput().getItem().size() > 0) {
//            transOptionsService.execute();
//        }
//        List<ZPPTCO004> list = transOptionsService.getOut().getItem();
//        if (list != null && list.size() > 0 && _mapCoach.size() > 0) {
//            for (ZPPTCO004 _l : list) {
//                total++;
//                if (_l == null) {
//                    totalOfUnknown++;
//                    continue;
//                }
//                if (null == _l.getPPACKNO()) {
//                    totalOfUnknown++;
//                    continue;
//                }
//                IntegrateMsgResponseDTO dto = new IntegrateMsgResponseDTO();
//                HzRelevanceResponseDTO record = _mapCoach.get(_l.getPPACKNO());
//                dto.setItemId(record.getRelevanceCode());
//                dto.setMsg(_l.getPMESSAGE());
//                dto.setId(record.getId());
//                if ("S".equalsIgnoreCase(_l.getPTYPE())) {
//                    success.add(dto);
//                    totalOfSuccess++;
//                    needToUpdateStatus.add(record);
//                } else {
//                    fail.add(dto);
//                    totalOfFail++;
//                }
//            }
//        }
//        Map<String, Object> _map = new HashMap<>();
//        //设定需要更新特性值已发送,不用设定相关性值已发送
//        _map.put("isRelevanceSent", 1);
//        _map.put("list", needToUpdateStatus);
//        if (needToUpdateStatus != null && needToUpdateStatus.size() > 0)
//            hzFeatureServiceImpl.updateByBatch(_map);
//
//        result.put("status", true);
//        result.put("success", success);
//        result.put("fail", fail);
//        result.put("total", total);
//        result.put("totalOfSuccess", totalOfSuccess);
//        result.put("totalOfFail", totalOfFail);
//        result.put("totalOfOutOfParent", totalOfOutOfParent);
//        result.put("totalOfUnknown", totalOfUnknown);
//        return result;
//    }


    @Override
    public JSONObject addRelevance(List<HzRelevanceBasic> relevance) throws Exception {
        return execute2(relevance, ActionFlagOption.ADD, CorrelateTypeOption.CorrelateType_1);
    }

    @Override
    public JSONObject updateRelevance(List<HzRelevanceBasic> relevance) throws Exception {
        return execute2(relevance, ActionFlagOption.UPDATE, CorrelateTypeOption.CorrelateType_1);
    }

    @Override
    public JSONObject deleteRelevance(List<HzRelevanceBasic> relevance) throws Exception {
        return execute2(relevance, ActionFlagOption.DELETE, CorrelateTypeOption.CorrelateType_3);
    }








    public JSONObject execute2(List<HzRelevanceBasic> hzRelevanceBasics, ActionFlagOption option, CorrelateTypeOption correlateTypeOption) throws Exception {
        /**
                  * 清除缓存
                  */
        transOptionsService.setClearInputEachTime(true);
        transOptionsService.getInput().getItem().clear();
        transOptionsService.getOut().getItem().clear();
        /**
         * 成功项
         */
        List<IntegrateMsgResponseDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgResponseDTO> fail = new ArrayList<>();

        /***
         * 计数
         */
        int total = 0;
        int totalOfSuccess = 0;
        int totalOfFail = 0;
        int totalOfOutOfParent = 0;
        int totalOfUnknown = 0;

//        int lineNum = 1;
        List<HzRelevanceResponseDTO> toSend = new ArrayList<>();
        //需要更新的数据，更新特性属性
        List<HzRelevanceBasic> needToUpdateStatus = new ArrayList<>();
        Map<String, HzRelevanceBasic> _mapCoach = new HashMap<>();
        JSONObject result = new JSONObject();
        for (HzRelevanceBasic bean : hzRelevanceBasics) {
            Correlate correlate = new Correlate(bean);
            String packnum = UUIDHelper.generateUpperUid();
            //包号
            correlate.setPackNo(packnum);
            //行号
//            correlate.setLineNum(String.valueOf(lineNum));
//            lineNum++;
            //动作描述代码
            if (option == ActionFlagOption.ADD) {
                if (null == bean.getIsSent() || 0 == bean.getIsSent()) {
                    correlate.setActionFlag(option);
                } else {
                    //更新
                    correlate.setActionFlag(ActionFlagOption.UPDATE);
                }
            }
            //不排除删除状态
            else {
                //没有发送过，则不执行更新状态
                if (null == bean.getIsSent() || 0 == bean.getIsSent()) {
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
        if (!SynMaterielServiceImpl.debug && transOptionsService.getInput().getItem().size() > 0) {
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
                IntegrateMsgResponseDTO dto = new IntegrateMsgResponseDTO();
                HzRelevanceBasic record = _mapCoach.get(_l.getPPACKNO());
                dto.setItemId(record.getRbRelevanceCode());
                dto.setMsg(_l.getPMESSAGE());
                dto.setPuid(record.getId().toString());
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
        if (needToUpdateStatus != null && needToUpdateStatus.size() > 0) {
            hzRelevanceBasicDao.doUpdateIsSent(_map);
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
