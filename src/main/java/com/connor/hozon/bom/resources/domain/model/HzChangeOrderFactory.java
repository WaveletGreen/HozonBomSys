package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzChangeOrderReqDTO;
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
        record.setChangeNo(reqDTO.getCreateNo());
        record.setChangeType(reqDTO.getChangeType());
        record.setCreateNo(reqDTO.getCreateNo());
        record.setHasRelatedChange("Y".equalsIgnoreCase(reqDTO.getHasRelatedChange())?1:0);
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
        return  record;
    }
}
