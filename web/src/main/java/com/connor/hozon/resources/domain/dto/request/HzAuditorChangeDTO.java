package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;
import lombok.Data;

@Data
public class HzAuditorChangeDTO extends BaseDTO {
    /**
     * 主键id
     */
    private Long id;
    private String auditorid;
    private String auditResult;
    private String orderId;

    private String changeNo;

    public Integer getAuditResult() {
        if(auditResult==null){
            return null;
        }
        switch (auditResult){
            case "完成":return 1;
            case "取消":return 0;
            default:return null;
        }
    }

}
