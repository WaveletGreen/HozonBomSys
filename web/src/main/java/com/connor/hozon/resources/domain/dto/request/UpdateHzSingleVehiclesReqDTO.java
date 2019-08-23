package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;
import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/9/26
 * @Description:
 */
@Data
public class UpdateHzSingleVehiclesReqDTO extends BaseDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 内饰颜色代码
     */
    private String svlInnerColorCode;
    /**
     * 内饰颜色名称
     */
    private String svlInnerColorName;

    /**
     * 电池型号
     */
    private String svlBatteryCode;
    /**
     * 电机型号
     */
    private String svlMotorCode;

}
