package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.resources.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.service.work.HzWorkProcessService;
import com.connor.hozon.bom.resources.service.work.impl.HzWorkProcessServiceImpl;
import integration.base.processRoute.ZPPTCO006;
import integration.logic.ProcessRoute;
import integration.logic.VehicleBom;
import integration.option.ActionFlagOption;
import integration.service.impl.cfg2.TransCfgService;
import integration.service.impl.processRoute6.TransProcessRouteService;
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

@Service("synProcessRouteService")
public class SynProcessRouteService {
    @Autowired
    private HzWorkProcessService hzWorkProcessService;
    @Autowired
    TransCfgService transCfgService;
    @Autowired
    TransProcessRouteService transProcessRouteService;
    private final static Logger logger = LoggerFactory.getLogger(SynMaterielCfgService.class);

    public JSONObject addRouting(List<String> materielIds, String projectId){
        return execute(materielIds, projectId, ActionFlagOption.ADD);
    }
    public JSONObject deleteRouting(List<String> materielIds, String projectId){
        return execute(materielIds, projectId, ActionFlagOption.DELETE);
    }
    public JSONObject updateRouting(List<String> materielIds, String projectId){
        return execute(materielIds, projectId, ActionFlagOption.UPDATE);
    }

    private JSONObject execute(List<String> materielIds, String projectId, ActionFlagOption option) {
        transProcessRouteService.setClearInputEachTime(true);
        transProcessRouteService.getInput().getItem().clear();

        //需要更新的数据，更新特性属性
        List<HzWorkProcessRespDTO> needToUpdateStatus = new ArrayList<>();
        Map<String, List<ProcessRoute>> _mapCoach = new HashMap<>();
        JSONObject result =  new JSONObject();
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

        if (materielIds.size()==0|| materielIds==null||projectId.isEmpty()) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }
        Map<String, Map<String, HzWorkProcessRespDTO>> coach = new HashMap<>();
        Map<String, String> packNumOfFeature = new HashMap<>();
        String packnum = UUIDHelper.generateUpperUid();

        int index;
        for(String materielId : materielIds){
            HzWorkProcessRespDTO respDTO = hzWorkProcessService.findHzWorkProcess(materielId, projectId);
            String fpuid = respDTO.getPuid();
            if (!packNumOfFeature.containsKey(fpuid)) {
                //添加父层puid和包号的对应关系
                packNumOfFeature.put(fpuid, packnum);
                packnum = UUIDHelper.generateUpperUid();
            }
            index = 1;
            ProcessRoute processRoute = new ProcessRoute();
            if (!coach.containsKey(packNumOfFeature.get(fpuid))) {
                Map<String, HzWorkProcessRespDTO> _m = new HashMap<>();
                _m.put(String.valueOf(index), respDTO);
                coach.put(packNumOfFeature.get(fpuid), _m);
            } else {
                index += coach.get(packNumOfFeature.get(fpuid)).size();
            }
            //数据包号
            processRoute.setPackNo(packNumOfFeature.get(respDTO.getPuid()));
            //行号
            processRoute.setLineNum(String.valueOf(index));

            if (option == ActionFlagOption.ADD) {
                //没有发送过，添加发送
                if(respDTO.getIsSent()==null||respDTO.getIsSent()==0){
                    processRoute.setActionFlag(option);
                }
                //有发送过，执行更新
                else {
                    continue;
                }
            }
            //执行更新或删除
            else {
                processRoute.setActionFlag(option);
            }

            processRoute.setFactory(respDTO.getFactoryCode());
            processRoute.setMaterialCode((String)respDTO.getpMaterielCode());
            if(respDTO.getpCount()==null){
                processRoute.setBasedAmount("1");
            }else {
                processRoute.setBasedAmount(String.valueOf(respDTO.getpCount()));
            }
            if(respDTO.getPurpose()==null){
                processRoute.setUse("1");
            }else{
                processRoute.setUse(respDTO.getPurpose());
            }
            if(respDTO.getState()==null){
                processRoute.setStatus("4");
            }else{
                processRoute.setStatus(respDTO.getState());
            }
            processRoute.setProcessNumber(respDTO.getpProcedureCode());
            processRoute.setWorkCenter(respDTO.getpWorkCode());
            processRoute.setControlCode(respDTO.getControlCode());
            processRoute.setProcessDescription(respDTO.getpProcedureDesc());

            coach.get(packNumOfFeature.get(fpuid)).put(processRoute.getLineNum(), respDTO);
            transProcessRouteService.getInput().getItem().add(processRoute.getZpptci006());
        }
        if(!SynMaterielService.debug){
            transProcessRouteService.execute();
        }
        List<ZPPTCO006> list = transProcessRouteService.getOut().getItem();

        try {
            if (list != null && list.size() > 0) {
                for (ZPPTCO006 _l : list) {
                    total++;
                    if (_l == null) {
                        totalOfUnknown++;
                        continue;
                    }
                    IntegrateMsgDTO dto = new IntegrateMsgDTO();
                    HzWorkProcessRespDTO respDTO = coach.get(_l.getPPACKNO()).get(_l.getPZITEM());
                    dto.setItemId(respDTO.getPuid().toUpperCase());
                    dto.setMsg(_l.getPMESSAGE());
                    dto.setPuid(respDTO.getPuid());
                    if ("S".equalsIgnoreCase(_l.getPTYPE())) {
                        success.add(dto);
                        totalOfSuccess++;
                        needToUpdateStatus.add(respDTO);
                    } else {
                        fail.add(dto);
                        totalOfFail++;
                    }
                }
                Map<String, Object> _map = new HashMap<>();
                //设定需要更新特性值已发送,不用设定相关性值已发送
                _map.put("isFeatureSent", 1);
                _map.put("list", needToUpdateStatus);
                if (needToUpdateStatus.size() > 0) {
                    hzWorkProcessService.doUpdateByBatch(_map);
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
