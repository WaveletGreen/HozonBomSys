package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzMaterielCfgService;
import com.connor.hozon.bom.resources.controller.BaseController;
import integration.base.feature.ZPPTCO002;
import integration.base.productAttributes.ZPPTCI007;
import integration.base.productAttributes.ZPPTCO007;
import integration.logic.Features;
import integration.logic.VehicleBom;
import integration.option.ActionFlagOption;
import integration.service.impl.cfg2.TransCfgService;
import integration.service.impl.produceAttr7.TransProductAttrService;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.cfg.HzMaterielCfgBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("snMaterielCfgService")
public class SynMaterielCfgService{
    @Autowired
    TransCfgService transCfgService;
    @Autowired
    IHzMaterielCfgService hzMaterielCfgService;
    @Autowired
    TransProductAttrService transProductAttrService;
    private final static Logger logger = LoggerFactory.getLogger(SynMaterielCfgService.class);

    public JSONObject addFeature(List<HzMaterielCfgBean> records) throws Exception {
        return execute(records, ActionFlagOption.ADD);
    }

    private JSONObject execute(List<HzMaterielCfgBean> records, ActionFlagOption option) throws Exception{
        //需要更新的数据，更新特性属性
        List<HzMaterielCfgBean> needToUpdateStatus = new ArrayList<>();
        Map<String, List<HzMaterielCfgBean>> _mapCoach = new HashMap<>();
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
        Map<String, Map<String, HzMaterielCfgBean>> coach = new HashMap<>();
        Map<String, String> packNumOfFeature = new HashMap<>();
        String packnum = UUIDHelper.generateUpperUid();

        int index;
        for (HzMaterielCfgBean record : records) {
                String fpuid = record.getPuid();
                //没有父层的puid
                if (!packNumOfFeature.containsKey(fpuid)) {
                    //添加父层puid和包号的对应关系
                    packNumOfFeature.put(fpuid, packnum);
                    packnum = UUIDHelper.generateUpperUid();
                }
            //收录包号对应的特性
            index = 1;
            List<VehicleBom> vehicleBomList = VehicleBom.getVehicleBom(fpuid,hzMaterielCfgService);
            String packNum=UUIDHelper.generateUpperUid();

            for(VehicleBom vehicleBom : vehicleBomList){
                //有没有包号，没有则添加包号
                if (!coach.containsKey(packNumOfFeature.get(fpuid))) {
                    Map<String, HzMaterielCfgBean> _m = new HashMap<>();
                    _m.put(String.valueOf(index), record);
                    coach.put(packNumOfFeature.get(fpuid), _m);
                } else {
                    index += coach.get(packNumOfFeature.get(fpuid)).size();
                }
                //数据包号
                vehicleBom.setPackNo(packNumOfFeature.get(fpuid));
                //行号
                vehicleBom.setItem(String.valueOf(index));

                //动作描述代码
                if (option == ActionFlagOption.ADD) {
                    //没有发送过，添加发送
                    if (null == record.getIsSent() || record.getIsSent() == 0) {
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

                //            featuresList.add(features);
                coach.get(packNumOfFeature.get(fpuid)).put(vehicleBom.getItem(), record);

            }

        }

        transProductAttrService.execute();
//        List<ZPPTCO002> list = transCfgService.getOut().getItem();
        List<ZPPTCO007> list=transProductAttrService.getOut().getItem();
//        try {
//            if (list != null && li6st.size() > 0) {
//                for (ZPPTCO007 _l : list) {
//                    total++;
//                    if (_l == null) {
//                        totalOfUnknown++;
//                        continue;
//                    }
//                    IntegrateMsgDTO dto = new IntegrateMsgDTO();
//                    HzMaterielCfgBean record = coach.get(_l.getPPACKNO()).get(_l.getPZITEM());
//                    dto.setItemId(record.getObjectName());
//                    dto.setMsg(_l.getPMESSAGE());
//                    dto.setPuid(record.getPuid());
//                    if ("S".equalsIgnoreCase(_l.getPTYPE())) {
//                        success.add(dto);
//                        totalOfSuccess++;
//                        needToUpdateStatus.add(record);
//                    } else {
//                        fail.add(dto);
//                        totalOfFail++;
//                    }
//                }
//                Map<String, Object> _map = new HashMap<>();
//                //设定需要更新特性值已发送,不用设定相关性值已发送
//                _map.put("isSent", 1);
//                _map.put("list", needToUpdateStatus);
//                if (needToUpdateStatus.size() > 0) {
//                    hzMaterielCfgService.doUpdateIsSent(_map);
//                }
//            }
//        } catch (Exception e) {
//            logger.error("发送特性到ERP失败", e);
//        }

        result.put("success", success);
        result.put("fail", fail);
        result.put("total", total);
        result.put("totalOfSuccess", totalOfSuccess);
        result.put("totalOfFail", totalOfFail);
        result.put("totalOfOutOfParent", totalOfOutOfParent);
        result.put("totalOfUnknown", totalOfUnknown);
        result.put("_forDelete", _forDelete);

        return  result;
    }
}
