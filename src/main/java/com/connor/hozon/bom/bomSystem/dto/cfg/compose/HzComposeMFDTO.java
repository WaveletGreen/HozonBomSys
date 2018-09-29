package com.connor.hozon.bom.bomSystem.dto.cfg.compose;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 配置物料特性传输对象，用于接收前端回传的配置物料
 * @Date: Created in 2018/8/30 14:15
 * @Modified By:
 */
@Data
public class HzComposeMFDTO {
    /**
     * 项目ID
     */
    private String projectUid;
    /**
     * 车型模型ID
     */
    private String modelUid;
    /**
     * 颜色车型ID
     */
    private String colorModel;
    /**
     * 工厂，默认是1001,作为物料数据传到SAP,一下的字段都是需要传到SAP的字段
     */
    private String factoryCode;
    /**
     * 物料编码
     */
    private String materialCode;
    /**
     * 旧物料编码
     */
    private String oldMaterielCode;
    /**
     * 物料中文描述
     */
    private String materielDesc;
    /**
     * 物料英文描述
     */
    private String materielEnDesc;
    /**
     * 基本计量单位
     */
    private String basicCountUnit;
    /**
     * 物料类型
     */
    private String materielType;
    /**
     * 虚拟件标识
     */
    private Integer virtualFlag;
    /**
     * 采购类型
     */
    private String purchaseType;
    /**
     * MRP控制者，衍生物料都是整车
     */
    private String mrpController;
    /**
     * 公告号，（只有MRP=整车才有，否则传空置）
     */
    private String bulletinNum;
    /**
     * vin前置码
     */
    private String vinCode;
    /**
     * 颜色件标识
     */
    private Integer color;
    /**
     * 毛重
     */
    private String grossWeight;
    /**
     * 内外饰标识
     */
    private Integer labelFlag;
    /**
     * 3C件标识
     */
    private Integer rulesFlag;
    /**
     * 删除标识
     */
    private Integer deleteFlag;
    /**
     * 重要度
     */
    private Integer importance;
    /**
     * 超级物料号
     */
    private String superMateriel;

    private String pCfg0ModelBasicDetail;

    public String getpCfg0ModelBasicDetail() {
        return pCfg0ModelBasicDetail;
    }

    public void setpCfg0ModelBasicDetail(String pCfg0ModelBasicDetail) {
        this.pCfg0ModelBasicDetail = pCfg0ModelBasicDetail;
    }
}
