package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzEWOBasicInfoReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzEWOBasicInfoRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOBasicInfoQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOBasicInfoDAO;
import com.connor.hozon.bom.resources.service.change.HzEWOBasicInfoService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOBasicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/8
 * @Description:
 */
@Service("hzEWOBasicInfoService")
public class HzEWOBasicInfoServiceImpl implements HzEWOBasicInfoService {

    @Autowired
    private HzEWOBasicInfoDAO hzEWOBasicInfoDAO;

    @Override
    public WriteResultRespDTO updateHzEWOBasicInfo(UpdateHzEWOBasicInfoReqDTO reqDTO) {
        try {
            //只有发起人才可以更改ewo表单信息
            User user = UserInfo.getUser();
            HzEWOBasicInfoQuery hzEWOBasicInfoQuery = new HzEWOBasicInfoQuery();
            hzEWOBasicInfoQuery.setId(reqDTO.getId());
            List<HzEWOBasicInfo> infos = hzEWOBasicInfoDAO.findHzEWOBasicInfoList(hzEWOBasicInfoQuery);
            if(ListUtil.isNotEmpty(infos)){
                HzEWOBasicInfo info = infos.get(0);
                if(info == null || !Long.valueOf(user.getId()).equals(info.getOriginatorId())){
                    WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
                    writeResultRespDTO.setErrMsg("只有流程发起人才能更改表单信息！");
                    writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                    return writeResultRespDTO;
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
                return WriteResultRespDTO.getFailResult();
            }
            return WriteResultRespDTO.getSuccessResult();
        }catch (Exception e){
            return WriteResultRespDTO.getFailResult();
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
                    respDTO.setOriginator(hzEWOBasicInfo.getOriginator());
                    respDTO.setChangeDesc(hzEWOBasicInfo.getChangeDesc());
                    respDTO.setReasonDesc(hzEWOBasicInfo.getReasonDesc());
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

}
