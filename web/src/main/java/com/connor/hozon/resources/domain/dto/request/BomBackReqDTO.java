package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;
import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/20
 * @Description: bom撤销前端参数
 */
@Data
public class BomBackReqDTO extends BaseDTO {
    private static final long serialVersionUID = 2159855122488633063L;
    /**
     * puids
     */
    private String puids;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 类型 1生产 6财务
     */
    Integer type;

    /**
     * 表单id
     */
    private Long orderId;
}
