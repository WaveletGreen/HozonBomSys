package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzChangeOrderReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzChangeOrderRespDTO;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.sys.entity.User;
import sql.pojo.change.HzChangeOrderRecord;

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


    public static HzChangeOrderRespDTO changeOrderRecordToRespDTO(HzChangeOrderRecord record){
        HzChangeOrderRespDTO respDTO = new HzChangeOrderRespDTO();
        respDTO.setChangeNo(record.getChangeNo());
        respDTO.setChangeType(record.getChangeType());
        respDTO.setCreateName(record.getCreateName());
        respDTO.setCreateNo(record.getCreateNo());
        respDTO.setCreateTime(DateUtil.formatTimestampDate(record.getCreateTime()));
        respDTO.setId(record.getId());
        respDTO.setMarketType(Integer.valueOf(1).equals(record.getMarketType())?"上市前":"上市后");
        respDTO.setOriginator(record.getOriginator());
        respDTO.setOriginTime(DateUtil.formatTimestampDate(record.getOriginTime()));
        respDTO.setProjectStage(record.getProjectStage());
        respDTO.setRelationChangeNo(record.getRelationChangeNo());
        respDTO.setRemark(record.getRemark());
        Integer state = record.getState();
        if(Integer.valueOf(1).equals(state)){
            respDTO.setState("已完成");
        }else if(Integer.valueOf(2).equals(state)){
            respDTO.setState("进行中");
        }else {
            respDTO.setState("已取消");
        }
        respDTO.setTel(record.getTel());
        respDTO.setDeptName(record.getDeptName());
        return respDTO;
    }


}
