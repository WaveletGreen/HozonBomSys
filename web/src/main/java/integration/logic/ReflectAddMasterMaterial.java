package integration.logic;

import sql.pojo.cfg.derivative.HzCfg0ModelFeature;
import cn.net.connor.hozon.dao.pojo.bom.materiel.HzMaterielRecord;

public class ReflectAddMasterMaterial {

    private HzMaterielRecord record;

    public ReflectAddMasterMaterial() {

    }

    public ReflectAddMasterMaterial(HzCfg0ModelFeature hcfm) {
        record = new HzMaterielRecord();
        //物料代码
        record.setpMaterielCode(hcfm.getMaterialCode());
        // 物料类型
        record.setpMaterielType(hcfm.getMaterielType());
        //工厂
        record.setpMaterielWerk(hcfm.getFactoryCode());
        record.setpFactoryPuid(hcfm.getFactoryCode());
        //物料中文描述
        record.setpMaterielDesc(hcfm.getMaterielDesc());
        //物料描述 英文
        record.setpMaterielDescEn(hcfm.getMaterielEnDesc());
        //基本计量单位
        record.setpBasicUnitMeasure(hcfm.getBasicCountUnit());
        //虚拟件标识(1 ,0 )
        if (hcfm.getVirtualFlag() != null) {
            record.setpInventedPart(hcfm.getVirtualFlag());
        }
        //备件&原材料双属性标示
//        record.setpSpareMaterial(hcfm.getDoubleAttribute());
        //VIN前置号
        record.setpVinPerNo(hcfm.getVinCode());
        //颜色件标识
        if (hcfm.getColor() != null) {
            record.setpColorPart(hcfm.getColor());
        }
        //毛重
        record.setpHeight(hcfm.getGrossWeight());
        //内外饰标识
        if (hcfm.getLabelFlag() != null) {
            record.setpInOutSideFlag(hcfm.getLabelFlag());
        }
        //3C件标识
        if (hcfm.getRulesFlag() != null) {
            record.setP3cPartFlag(hcfm.getRulesFlag());
        }
        //MRP控制者
        record.setpMrpController(hcfm.getMrpController());
        //零件重要度
//        record.setpPartImportantDegree(hcfm.getPartImportance());
        //是否已发送过SAP
        record.setSendSapFlag(hcfm.getIsSent());
    }

    public HzMaterielRecord getRecord() {
        return record;
    }

    public void setRecord(HzMaterielRecord record) {
        this.record = record;
    }
}
