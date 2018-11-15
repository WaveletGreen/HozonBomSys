package com.connor.hozon.bom.resources.domain.dto.response;

import lombok.Data;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Data
public class HzChangeDataRespDTO {
    /**
     * 超链接
     */
    private String hyperLinkName;
//    /**
//     * 申请人
//     */
//    private String applicant;
//    /**
//     * 审核人
//     */
//    private String auditor;
//    /**
//     * 申请时间
//     */
//    private Date applicantTime;

    /**
     * 表单id
     */
    private Long orderId;

//    /**
//     * 审核人id
//     */
//    private Long auditorId;
}
