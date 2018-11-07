/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzMaterielCfgService;
import integration.base.productAttributes.ZPPTCO007;
import integration.logic.VehicleBom;
import integration.option.ActionFlagOption;
import integration.service.impl.produceAttr7.TransProductAttrService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.derivative.HzMaterielCfgBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("snMaterielCfgService")
public class SynMaterielCfgService {
    @Autowired
    IHzMaterielCfgService hzMaterielCfgService;
    @Autowired
    TransProductAttrService transProductAttrService;
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(SynMaterielCfgService.class);

    /**
     * 新增
     *
     * @param records 仅需对其puid属性赋值，puid为表 Hz_Cfg0_Model_Record 的主键 PUID
     * @return
     */
    public JSONObject addMaterielCfg(List<HzMaterielCfgBean> records) {
        return execute(records, ActionFlagOption.ADD);
    }

    /**
     * 删除
     *
     * @param records 仅需对其puid属性赋值，puid为表 Hz_Cfg0_Model_Record 的主键 PUID
     * @return
     */
    public JSONObject deleteMaterielCfg(List<HzMaterielCfgBean> records) {
        return execute(records, ActionFlagOption.DELETE);
    }

    /**
     * 修改
     *
     * @param records 仅需对其puid属性赋值，puid为表 Hz_Cfg0_Model_Record 的主键 PUID
     * @return
     */
    public JSONObject updataMaterielCfg(List<HzMaterielCfgBean> records) {
        deleteMaterielCfg(records);
        return addMaterielCfg(records);
    }

    /**
     * 核心方法
     *
     * @param records 仅需对其puid属性赋值，puid为表 Hz_Cfg0_Model_Record 的主键 PUID
     * @param option  动作标志
     * @return
     */
    private JSONObject execute(List<HzMaterielCfgBean> records, ActionFlagOption option) {
        transProductAttrService.setClearInputEachTime(true);
        transProductAttrService.getInput().getItem().clear();
        //需要更新的数据，更新特性属性
        List<HzMaterielCfgBean> needToUpdateStatus = new ArrayList<>();
        JSONObject result = new JSONObject();
        /**
         * 成功项
         */
        List<IntegrateMsgDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgDTO> fail = new ArrayList<>();

        List<String> _forDelete = new ArrayList<>();
        /***
         * 计数
         */
        int total = 0;
        int totalOfSuccess = 0;
        int totalOfFail = 0;
        int totalOfOutOfParent = 0;
        int totalOfUnknown = 0;

        if (records == null || records.size() <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }
        //上传数据Map
        Map<String, Map<String, HzMaterielCfgBean>> coach = new HashMap<>();
        //包号为Key，puid为Value
        Map<String, String> packNumOfFeature = new HashMap<>();
        //包号
        String packnum = UUIDHelper.generateUpperUid();
        //层级
        int index;
        for (HzMaterielCfgBean record : records) {
            String fpuid = record.getPuid();
            //没有父层的puid
            if (!packNumOfFeature.containsKey(fpuid)) {
                //添加父层puid和包号的对应关系
                packnum = UUIDHelper.generateUpperUid();
                packNumOfFeature.put(fpuid, packnum);
            }
            //收录包号对应的特性
            index = 1;
            List<VehicleBom> vehicleBomList = VehicleBom.getVehicleBom(fpuid, hzMaterielCfgService);

            for (VehicleBom vehicleBom : vehicleBomList) {
                //有没有包号，没有则添加包号
                if (!coach.containsKey(packNumOfFeature.get(fpuid))) {
                    Map<String, HzMaterielCfgBean> _m = new HashMap<>();
                    _m.put(String.valueOf(index), record);
                    coach.put(packNumOfFeature.get(fpuid), _m);
                } else {
                    index++;
                    coach.get(packNumOfFeature.get(fpuid)).put(String.valueOf(index), record);
                }
                //数据包号
                vehicleBom.setPackNo(packNumOfFeature.get(fpuid));
                //行号
                vehicleBom.setItem(String.valueOf(index));

                //动作描述代码
                if (option == ActionFlagOption.ADD) {
                    //没有发送过，添加发送
                    if (null == vehicleBom.getIsSent() || vehicleBom.getIsSent() == 0) {
                        vehicleBom.setActionFlag(option);
                    }
                    //有发送过，执行更新
                    else {
                        continue;
                    }
                }
                //执行更新或删除
                else {
                    vehicleBom.setActionFlag(option);
                }

                transProductAttrService.getInput().getItem().add(vehicleBom.getZpptci007());
                coach.get(packNumOfFeature.get(fpuid)).put(vehicleBom.getItem(), record);

            }

        }
        if (!SynMaterielService.debug) {
            if (transProductAttrService.getInput().getItem().size() > 0) {
                transProductAttrService.execute();
            }
        }
        List<ZPPTCO007> list = transProductAttrService.getOut().getItem();
        try {
            if (list != null && list.size() > 0) {
                for (ZPPTCO007 _l : list) {
                    total++;
                    if (_l == null || _l.getPPACKNO() == null) {
                        totalOfUnknown++;
                        continue;
                    }
                    IntegrateMsgDTO dto = new IntegrateMsgDTO();
                    HzMaterielCfgBean record = coach.get(_l.getPPACKNO()).get(_l.getPZITEM());
                    dto.setItemId(_l.getPZITEM());
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
                Map<String, Object> _map = new HashMap<>();
                //设定需要更新特性值已发送,不用设定相关性值已发送
                if(option == ActionFlagOption.ADD){
                    _map.put("isSent", 1);
                }else if(option == ActionFlagOption.DELETE){
                    _map.put("isSent", 0);
                }
                _map.put("list", needToUpdateStatus);
                if (needToUpdateStatus.size() > 0) {
                    hzMaterielCfgService.doUpdateIsSent(_map);
                }
            }
        } catch (Exception e) {
            logger.error("发送特性到ERP失败", e);
        }

        result.put("success", success);
        result.put("fail", fail);
        result.put("total", total);
        result.put("totalOfSuccess", totalOfSuccess);
        result.put("totalOfFail", totalOfFail);
        result.put("totalOfOutOfParent", totalOfOutOfParent);
        result.put("totalOfUnknown", totalOfUnknown);
        result.put("_forDelete", _forDelete);

        return result;
    }
}
