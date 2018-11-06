/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.resources.domain.dto.response.HzWorkProcessRespDTO;
import com.connor.hozon.bom.resources.service.work.HzWorkProcessService;
import integration.base.processRoute.ZPPTCO006;
import integration.logic.ProcessRoute;
import integration.logic.VehicleBom;
import integration.option.ActionFlagOption;

import integration.service.impl.processRoute6.TransProcessRouteService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
@Service("synProcessRouteService")
public class SynProcessRouteService {
    @Autowired
    private HzWorkProcessService hzWorkProcessService;
    @Autowired
    TransProcessRouteService transProcessRouteService;
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(SynProcessRouteService.class);

    /**
     * 新增
     * @param materielIds  物料的puid  表P_MATERIEL_CODE 的主键 PUID
     * @param projectId    项目Id      表P_MATERIEL_CODE 的 P_PERTAIN_TO_PROJECT_PUID
     * @return
     */
    public JSONObject addRouting(String[] materielIds, String projectId){
        return execute(materielIds, projectId, ActionFlagOption.ADD);
    }

    /**
     * 删除
     * @param materielIds   物料的puid  表P_MATERIEL_CODE 的主键 PUID
     * @param projectId     项目Id      表P_MATERIEL_CODE 的 P_PERTAIN_TO_PROJECT_PUID
     * @return
     */
    public JSONObject deleteRouting(String[] materielIds, String projectId){
        return execute(materielIds, projectId, ActionFlagOption.DELETE);
    }

    /**
     * 修改
     * @param materielIds   物料的puid  表P_MATERIEL_CODE 的主键 PUID
     * @param projectId     项目Id      表P_MATERIEL_CODE 的 P_PERTAIN_TO_PROJECT_PUID
     * @return
     */
    public JSONObject updateRouting(String[] materielIds, String projectId){
        return execute(materielIds, projectId, ActionFlagOption.UPDATE);
    }

    /**
     * 核心方法
     * @param materielIds   物料的puid
     * @param projectId     项目Id
     * @param option        动作标志
     * @return
     */
    private JSONObject execute(String[] materielIds, String projectId, ActionFlagOption option) {
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

        if (materielIds.length==0|| materielIds==null||projectId.isEmpty()) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }
        //上传数据Map
        Map<String, Map<String, HzWorkProcessRespDTO>> coach = new HashMap<>();
        //包号为Key，puid为Value
        Map<String, String> packNumOfFeature = new HashMap<>();
        String packnum = UUIDHelper.generateUpperUid();
        //层级
        int index;
        for(String materielId : materielIds){
            //查找到该id下的工艺路线对象
            HzWorkProcessRespDTO respDTO = hzWorkProcessService.findHzWorkProcess(materielId, projectId);
            String fpuid = respDTO.getPuid();
            //是否有该puid，没有则加入
            if (!packNumOfFeature.containsKey(fpuid)) {
                //添加父层puid和包号的对应关系
                packNumOfFeature.put(fpuid, packnum);
                packnum = UUIDHelper.generateUpperUid();
            }
            index = 1;
            ProcessRoute processRoute = new ProcessRoute();
            //是否有该包号，没有则加并将传输数据传入传输Map中，有则层级加一
            if (!coach.containsKey(packNumOfFeature.get(fpuid))) {
                Map<String, HzWorkProcessRespDTO> _m = new HashMap<>();
                _m.put(String.valueOf(index), respDTO);
                coach.put(packNumOfFeature.get(fpuid), _m);
            } else {
                index ++;
            }
            //数据包号
            processRoute.setPackNo(packNumOfFeature.get(respDTO.getPuid()));
            //行号
            processRoute.setLineNum(String.valueOf(index));
            //动作标志为Add时，如果以发送过，则进入下个循环，没发送过则加A
            if (option == ActionFlagOption.ADD) {
                //没有发送过，添加发送
                if(respDTO.getIsSent()==null||respDTO.getIsSent()==0){
                    processRoute.setActionFlag(option);
                }
                //有发送过，执行更新
                else if(respDTO.getIsSent()==1){
                    processRoute.setActionFlag(ActionFlagOption.UPDATE);
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


            processRoute.setWorkNumber1(BigDecimal.valueOf(Long.valueOf(respDTO.getpDirectLabor())));
            processRoute.setWorkNumber2(BigDecimal.valueOf(Long.valueOf(respDTO.getpIndirectLabor())));
            processRoute.setWorkNumber3(BigDecimal.valueOf(Long.valueOf(respDTO.getpMachineLabor())));
            processRoute.setWorkNumber4(BigDecimal.valueOf(Long.valueOf(respDTO.getpBurn())));
            processRoute.setWorkNumber5(BigDecimal.valueOf(Long.valueOf(respDTO.getpMachineMaterialLabor())));
            processRoute.setWorkNumber6(BigDecimal.valueOf(Long.valueOf(respDTO.getpOtherCost())));


            coach.get(packNumOfFeature.get(fpuid)).put(processRoute.getLineNum(), respDTO);
            transProcessRouteService.getInput().getItem().add(processRoute.getZpptci006());
            supProcess(processRoute);
        }
        if(!SynMaterielService.debug){
            transProcessRouteService.execute();//回传2条output数据了！！！！
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
                Map<String, Object> _map = new HashMap<String, Object>();
                //设定需要更新特性值已发送,不用设定相关性值已发送
                if(ActionFlagOption.ADD.equals(option)){
                    _map.put("isFeatureSent", 1);
                    _map.put("list", needToUpdateStatus);
                }else {
                    _map.put("isFeatureSent", 0);
                    _map.put("list", needToUpdateStatus);

                }
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



    private void supProcess(ProcessRoute processRoute){
        for(int i=0;i<1;i++){
            ProcessRoute processRoute1 = new ProcessRoute();
            processRoute1.setPackNo(processRoute.getPackNo());
            processRoute1.setLineNum(String.valueOf(i+2));
            processRoute1.setActionFlag(processRoute.getActionFlag());
            processRoute1.setFactory(processRoute.getFactory());
            processRoute1.setMaterialCode(processRoute.getMaterialCode());
            processRoute1.setBasedAmount(processRoute.getBasedAmount());
            processRoute1.setEffectiveDate(processRoute.getEffectiveDate());
            processRoute1.setUse(processRoute.getUse());
            processRoute1.setStatus(processRoute.getStatus());
            processRoute1.setProcessNumber("00"+(i+2)+"0");
            processRoute1.setWorkCenter("T1BP-Q");
            processRoute1.setControlCode("ZHZ3");
            processRoute1.setProcessDescription("焊装-数量维度");
            processRoute1.setWorkNumber1(processRoute.getWorkNumber1());
            processRoute1.setWorkNumber2(processRoute.getWorkNumber2());
            processRoute1.setWorkNumber3(processRoute.getWorkNumber3());
            processRoute1.setWorkNumber4(processRoute.getWorkNumber4());
            processRoute1.setWorkNumber5(processRoute.getWorkNumber5());
            processRoute1.setWorkNumber6(processRoute.getWorkNumber6());
            transProcessRouteService.getInput().getItem().add(processRoute1.getZpptci006());
        }
    }

}
