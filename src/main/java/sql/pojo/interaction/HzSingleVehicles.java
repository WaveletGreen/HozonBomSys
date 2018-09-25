package sql.pojo.interaction;

import lombok.Data;

@Data
public class HzSingleVehicles {
    /**
     * 主键
     */
    private Long id;
    /**
     * 项目外键
     */
    private String svlProjectUid;
    /**
     * 配置物料特性表的单车配置外键
     */
    private Long svlDmbId;

    /**
     * 配置物料特性主数据
     */
    private String svlCfgMaterialUid;
    /**
     * 物料编号
     */
    private String svlMaterialCode;
    /**
     * 基本信息
     */
    private String svlMaterialBasicInfo;
    /**
     * 内饰颜色代码
     */
    private String svlInnerColorCode;
    /**
     * 内饰颜色名称
     */
    private String svlInnerColorName;
    /**
     * 颜色代码，工程师手动填
     */
    private String svlColorCode;
    /**
     * 颜色名称
     */
    private String svlColorName;
    /**
     * 电池型号
     */
    private String svlBatteryCode;
    /**
     * 电机型号
     */
    private String svlMotorCode;
    /**
     * 保留字段
     */
    private String svlReserved1;
    /**
     * 保留字段
     */
    private String svlReserved2;
    /**
     * 保留字段
     */
    private String svlReserved3;
    /**
     * 保留字段
     */
    private String svlReserved4;
    /**
     * 保留字段
     */
    private String svlReserved5;
    /**
     * 保留字段
     */
    private String svlReserved6;
    /**
     * 保留字段
     */
    private String svlReserved7;
    /**
     * 保留字段
     */
    private String svlReserved8;
    /**
     * 保留字段
     */
    private String svlReserved9;
    /**
     * 保留字段
     */
    private String svlReserved10;
    /**
     * 保留字段
     */
    private String svlReserved11;
    /**
     * 保留字段
     */
    private String svlReserved12;
    /**
     * 保留字段
     */
    private String svlReserved13;
    /**
     * 保留字段
     */
    private String svlReserved14;
    /**
     * 保留字段
     */
    private String svlReserved15;
    /**
     * 品牌代码
     */
    private String brandCode;
    /**
     * 中文品牌
     */
    private String brandName;
    /**
     * 平台代码
     */
    private String platformCode;
    /**
     * 平台名称
     */
    private String platformName;
    /**
     * 车型代码
     */
    private String vehicleCode;
    /**
     * 车型名称
     */
    private String vehicleName;

}