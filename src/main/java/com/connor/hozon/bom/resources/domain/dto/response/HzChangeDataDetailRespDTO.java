package com.connor.hozon.bom.resources.domain.dto.response;

import lombok.Data;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Data
public class HzChangeDataDetailRespDTO<T> {
    /**
     * 变更对象
     */
    T module;

    /**
     * 变更单标题 把状态、变更类型也加进来（A U D）
     */
    Map<String,Object> title;


}
