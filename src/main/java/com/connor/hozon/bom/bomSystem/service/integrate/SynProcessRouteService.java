package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.resources.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.service.work.HzWorkProcessService;
import integration.logic.VehicleBom;
import integration.option.ActionFlagOption;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzMaterielCfgBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("synProcessRouteService")
public class SynProcessRouteService {
    @Autowired
    private HzWorkProcessService hzWorkProcessService;
    private final static Logger logger = LoggerFactory.getLogger(SynMaterielCfgService.class);

    public JSONObject addFeature(List<HzWorkProcessRespDTO> respDTO) throws Exception {
        return execute(respDTO, ActionFlagOption.ADD);
    }

    private JSONObject execute(List<HzWorkProcessRespDTO> respDTOs, ActionFlagOption add) {
        //需要更新的数据，更新特性属性
        List<HzWorkProcessRespDTO> needToUpdateStatus = new ArrayList<>();
        Map<String, List<HzWorkProcessRespDTO>> _mapCoach = new HashMap<>();
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

        if (respDTOs == null || respDTOs.size() <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }
        Map<String, Map<String, HzWorkProcessRespDTO>> coach = new HashMap<>();
        Map<String, String> packNumOfFeature = new HashMap<>();
        String packnum = UUIDHelper.generateUpperUid();

        int index;
        for (HzWorkProcessRespDTO respDTO : respDTOs) {
            String fpuid = respDTO.getPuid();
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








        return result;
    }
}
