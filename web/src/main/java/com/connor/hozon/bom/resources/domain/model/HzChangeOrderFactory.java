package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzChangeOrderReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.sys.entity.User;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
public class HzChangeOrderFactory {
    public static HzChangeOrderRecord reqDTOChangeRecord(EditHzChangeOrderReqDTO reqDTO){
        HzChangeOrderRecord record = new HzChangeOrderRecord();
        User user = UserInfo.getUser();
        record.setChangeNo(reqDTO.getChangeNo());
        record.setChangeType(reqDTO.getChangeType());
        record.setCreateNo(reqDTO.getCreateNo());
        record.setId(reqDTO.getId());
        record.setMarketType("上市前".equals(reqDTO.getMarketType())?1:0);
        record.setProjectId(reqDTO.getProjectId());
        record.setProjectStage(reqDTO.getProjectStage());
        record.setRemark(reqDTO.getRemark());
        record.setTel(reqDTO.getTel());
        record.setCreateId(Long.valueOf(user.getId()));
        record.setUpdateId(Long.valueOf(user.getId()));
        record.setCreateName(user.getUsername());
        record.setUpdateName(user.getUsername());
        record.setRelationChangeNo(reqDTO.getRelationChangeNo());
        record.setDeptId(user.getGroupId());
        record.setFromTc(0);
        record.setCreateNo(user.getUserNo());
        return  record;
    }


    // 不要用 1==record.getXXX() 可能会报NPE
    public static HzChangeOrderRespDTO changeOrderRecordToRespDTO(HzChangeOrderRecord record){
        HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
        respDTO.setChangeNo(record.getChangeNo());
        respDTO.setChangeType(record.getChangeType());
        respDTO.setCreateName(Integer.valueOf(1).equals(record.getFromTc())?record.getCreateNameTC():record.getCreateName());
        respDTO.setDeptName(Integer.valueOf(1).equals(record.getFromTc())?record.getDeptNameTC():record.getDeptName());
        respDTO.setCreateNo(record.getCreateNo());
        respDTO.setCreateTime(DateUtil.formatTimestampDate(record.getCreateTime()));
        respDTO.setId(record.getId());
        respDTO.setMarketType(Integer.valueOf(1).equals(record.getMarketType())?"上市前":"上市后");
        //流程发起人
        if(record.getOriginator()!=null)
            respDTO.setOriginator(record.getOriginator());
        //else
            //respDTO.setOriginator(record.getCreateNameTC());
        //respDTO.setOriginTime(DateUtil.formatTimestampDate(record.getOriginTime()));
        if(record.getApplicantTime()==null)
            respDTO.setOriginTime(DateUtil.formatTimestampDate(record.getOriginTime()));
        else
            respDTO.setOriginTime(DateUtil.formatTimestampDate(record.getApplicantTime()));//申请时间
        respDTO.setAuditTime(DateUtil.formatTimestampDate(record.getAuditTime()));//审核时间
        respDTO.setProjectStage(record.getProjectStage());
        respDTO.setRelationChangeNo(record.getRelationChangeNo());
        respDTO.setRemark(record.getRemark());
        respDTO.setOrderResource(record.getFromTc());
        respDTO.setState(record.getState());
        respDTO.setStatus(record.getState());
        respDTO.setTel(record.getTel());
        respDTO.setIsFromTc(record.getFromTc());
        if(Integer.valueOf(0).equals(record.getFromTc())){
            respDTO.setSource("BOM");
        }else{
            respDTO.setSource("TC");
        }
        respDTO.setProjectName(record.getProjectName());
        if(record.getAuditRecordId()!=null)
            respDTO.setAuditId(Long.parseLong(record.getAuditRecordId()));
        if(record.getAuditId()!=null)
            respDTO.setAuditId(Long.parseLong(record.getAuditId()));
        if(record.getChangeAccepter()!=null)
            respDTO.setChangeAccepter(record.getChangeAccepter());

        respDTO.setAuditor(record.getAuditor());
        return respDTO;
    }


}
