package com.connor.hozon.bom.resources.domain.dto.request;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/20
 * @Description: bom撤销前端参数
 */
@Data
public class BomBackReqDTO {
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
}