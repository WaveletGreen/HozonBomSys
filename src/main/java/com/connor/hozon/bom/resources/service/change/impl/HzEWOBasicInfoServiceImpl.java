package com.connor.hozon.bom.resources.service.change.impl;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEWOBasicInfoReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEWOBasicInfoRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOBasicInfoDAO;
import com.connor.hozon.bom.resources.mybatis.change.impl.HzEWOBasicInfoDAOImpl;
import com.connor.hozon.bom.resources.query.HzEWOBasicInfoQuery;
import com.connor.hozon.bom.resources.service.change.HzEWOBasicInfoService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzEWOBasicInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/8
 * @Description:
 */
@Service("HzEWOBasicInfoService")
public class HzEWOBasicInfoServiceImpl implements HzEWOBasicInfoService {

    @Autowired
    private HzEWOBasicInfoDAO hzEWOBasicInfoDAO;

    @Override
    public OperateResultMessageRespDTO updateHzEWOBasicInfo(UpdateHzEWOBasicInfoReqDTO reqDTO) {
        try {
            //只有发起人才可以更改ewo表单信息
            User user = UserInfo.getUser();
            HzEWOBasicInfoQuery hzEWOBasicInfoQuery = new HzEWOBasicInfoQuery();
            hzEWOBasicInfoQuery.setId(reqDTO.getId());
            List<HzEWOBasicInfo> infos = hzEWOBasicInfoDAO.findHzEWOBasicInfoList(hzEWOBasicInfoQuery);
            if(ListUtil.isNotEmpty(infos)){
                HzEWOBasicInfo info = infos.get(0);
                if(info == null || !Long.valueOf(user.getId()).equals(info.getOriginatorId())){
                    OperateResultMessageRespDTO operateResultMessageRespDTO = new OperateResultMessageRespDTO();
                    operateResultMessageRespDTO.setErrMsg("只有流程发起人才能更改表单信息！");
                    operateResultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                    return operateResultMessageRespDTO;
                }
            }
            HzEWOBasicInfo hzEWOBasicInfo = new HzEWOBasicInfo();
            hzEWOBasicInfo.setChangeDesc(reqDTO.getChangeDesc());
            hzEWOBasicInfo.setChangeType(reqDTO.getChangeType());
            hzEWOBasicInfo.setCostAssumeDept(reqDTO.getCostAssumeDept());
            hzEWOBasicInfo.setEffectTime(reqDTO.getEffectTime());
            hzEWOBasicInfo.setFinishTime(reqDTO.getFinishTime());
            hzEWOBasicInfo.setFlowStatus(reqDTO.getFlowStatus());
            hzEWOBasicInfo.setPlatform(reqDTO.getPlatform());
            hzEWOBasicInfo.setProjectCode(reqDTO.getProjectCode());
            hzEWOBasicInfo.setProjectStage(reqDTO.getProjectStage());
            hzEWOBasicInfo.setPublicType(reqDTO.getPublicType());
            hzEWOBasicInfo.setReasonCode(reqDTO.getReasonCode());
            hzEWOBasicInfo.setReasonDesc(reqDTO.getReasonDesc());
            hzEWOBasicInfo.setRelationEngineer(reqDTO.getRelationEngineer());
            hzEWOBasicInfo.setRelationEngineerDept(reqDTO.getRelationEngineerDept());
            hzEWOBasicInfo.setRelationEwoNo(reqDTO.getRelationEwoNo());
            hzEWOBasicInfo.setRelationItemId(reqDTO.getRelationItemId());
            hzEWOBasicInfo.setRelationItemName(reqDTO.getRelationItemName());
            hzEWOBasicInfo.setTel(reqDTO.getTel());
            hzEWOBasicInfo.setTitle(reqDTO.getTitle());
            hzEWOBasicInfo.setVehicleCode(reqDTO.getVehicleCode());
            int i = hzEWOBasicInfoDAO.update(hzEWOBasicInfo);
            if(i<0){
                return OperateResultMessageRespDTO.getFailResult();
            }
            return OperateResultMessageRespDTO.getSuccessResult();
        }catch (Exception e){
            return OperateResultMessageRespDTO.getFailResult();
        }
    }

    @Override
    public HzEWOBasicInfoRespDTO findHzEWOBasicInfo(HzEWOBasicInfoQuery query) {
        HzEWOBasicInfoRespDTO respDTO = new HzEWOBasicInfoRespDTO();
        try {
            List<HzEWOBasicInfo> hzEWOBasicInfoList = hzEWOBasicInfoDAO.findHzEWOBasicInfoList(query);
            if(ListUtil.isNotEmpty(hzEWOBasicInfoList)){
                HzEWOBasicInfo hzEWOBasicInfo = hzEWOBasicInfoList.get(0);
                respDTO.setChangeDesc(hzEWOBasicInfo.getChangeDesc());
                respDTO.setChangeType(hzEWOBasicInfo.getChangeType());
                respDTO.setCostAssumeDept(hzEWOBasicInfo.getCostAssumeDept());
                respDTO.setDept(hzEWOBasicInfo.getDept());
                respDTO.setEffectTime(DateUtil.formatTimestampDate(hzEWOBasicInfo.getEffectTime()));
                respDTO.setEwoNo(hzEWOBasicInfo.getEwoNo());
                respDTO.setFinishTime(DateUtil.formatTimestampDate(hzEWOBasicInfo.getFinishTime()));
                respDTO.setFlowStatus(hzEWOBasicInfo.getFlowStatus());
                respDTO.setFormCreateTime(DateUtil.formatTimestampDate(hzEWOBasicInfo.getFormCreateTime()));
                respDTO.setOriginator(hzEWOBasicInfo.getOriginator());
                respDTO.setId(hzEWOBasicInfo.getId());
                respDTO.setPlatform(hzEWOBasicInfo.getPlatform());
                respDTO.setProjectCode(hzEWOBasicInfo.getProjectCode());
                respDTO.setProjectStage(hzEWOBasicInfo.getProjectStage());
                respDTO.setPublicType(hzEWOBasicInfo.getChangeType());
                respDTO.setReasonCode(hzEWOBasicInfo.getReasonCode());
                respDTO.setReasonDesc(hzEWOBasicInfo.getReasonDesc());
                respDTO.setRelationEngineer(hzEWOBasicInfo.getRelationEngineer());
                respDTO.setRelationEngineerDept(hzEWOBasicInfo.getRelationEngineerDept());
                respDTO.setRelationEwoNo(hzEWOBasicInfo.getRelationEwoNo());
                respDTO.setRelationItemId(hzEWOBasicInfo.getRelationItemId());
                respDTO.setRelationItemName(hzEWOBasicInfo.getRelationItemName());
                respDTO.setTel(hzEWOBasicInfo.getTel());
                respDTO.setTitle(hzEWOBasicInfo.getTitle());
                respDTO.setVehicleCode(hzEWOBasicInfo.getVehicleCode());
                respDTO.setProjectId(hzEWOBasicInfo.getProjectId());
            }
            return respDTO;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<HzEWOBasicInfoRespDTO> findHzEWOList(HzEWOBasicInfoQuery query) {
        List<HzEWOBasicInfoRespDTO> ewoBasicInfoList = new ArrayList<>();
        try{
            List<HzEWOBasicInfo> infos = hzEWOBasicInfoDAO.findHzEWOBasicInfoList(query);
            if(ListUtil.isNotEmpty(infos)){
                infos.forEach(hzEWOBasicInfo -> {
                    HzEWOBasicInfoRespDTO respDTO = new HzEWOBasicInfoRespDTO();
                    respDTO.setProjectId(hzEWOBasicInfo.getProjectId());
                    respDTO.setEwoNo(hzEWOBasicInfo.getEwoNo());
                    respDTO.setId(hzEWOBasicInfo.getId());
                    respDTO.setFormCreateTime(DateUtil.formatTimestampDate(hzEWOBasicInfo.getFormCreateTime()));
                    ewoBasicInfoList.add(respDTO);
                });

                return ewoBasicInfoList;
            }
        }catch (Exception e){
            return null;
        }
        return null;
    }


    public static void main(String[] a){
        HzEWOBasicInfoDAO hzEWOBasicInfoDAO = new HzEWOBasicInfoDAOImpl();
//        HzEWOBasicInfo hzEWOBasicInfo = new HzEWOBasicInfo();
//        hzEWOBasicInfo.setEwoNo("20180000312");
////        hzEWOBasicInfo.setOriginator("haozt");
////        hzEWOBasicInfo.setOriginatorId(24L);
////        hzEWOBasicInfo.setFormCreateTime(new Date());
//        hzEWOBasicInfo.setDept("测试部");
//        hzEWOBasicInfo.setChangeDesc("《传奇》是一首很特别的歌，没有明显的起伏却情感饱满，好似一个人在低低地诉说自己的心事。《传奇》一开始的“无意中多看了你一眼”是铺陈，中间用了四个排比句表达思念，也让思念更加立体，具有空间感，最后引出相爱的两个人终生相守的信念。"
//                );
//        hzEWOBasicInfo.setId(1L);
//        hzEWOBasicInfo.setTel("12345566");
//        hzEWOBasicInfo.setVehicleCode("EP10");
//        hzEWOBasicInfo.setRelationItemName("林飞");
//        int i  = hzEWOBasicInfoDAO.update(hzEWOBasicInfo);
//            HzEWOBasicInfoQuery query = new HzEWOBasicInfoQuery();
//            query.setId(1L);
//            HzEWOBasicInfo info = hzEWOBasicInfoDAO.findHzEWOBasicInfo(query);
//            String date = DateUtil.formatTimestampDate(info.getFormCreateTime());
//        System.out.println(JSON.toJSONString(info));
    }

}
