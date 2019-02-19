package com.connor.hozon.bom.resources.domain.dto.request;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description: 设变模块 添加数据到变更表单
 */
@Data
public class AddDataToChangeOrderReqDTO extends BaseDTO {
    /**
     * 数据ids  中间用英文逗号隔开
     */
    private String puids;

    /**
     * 表单id
     */
    private Long orderId;

    /**
     * 审核人id
     */
    private Long auditorId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     *  类型 1 生产 6 财务
     */
    private Integer type;
}
