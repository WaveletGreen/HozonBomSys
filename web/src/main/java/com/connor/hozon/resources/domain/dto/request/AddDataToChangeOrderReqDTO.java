package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;
import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description: 设变模块 添加数据到变更表单
 */
@Data
public class AddDataToChangeOrderReqDTO extends BaseDTO {
    private static final long serialVersionUID = -8754762380503005076L;
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

    private String[] puidString;

    private String url;
}
