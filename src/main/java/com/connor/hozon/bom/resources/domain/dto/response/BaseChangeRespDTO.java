package com.connor.hozon.bom.resources.domain.dto.response;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:变更基本返回值
 * 状态（A U D） 变更类型
 */
@Data
public abstract class BaseChangeRespDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = -1940522775687050322L;
    /**
     * 变更类型（A D U）
     */
    private String changeType;
    /**
     * 生效时间
     */
    private String effectTime;
}
