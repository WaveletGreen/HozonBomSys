package integration.logic;

import sql.pojo.cfg.HzCfg0ModelFeature;
import sql.pojo.project.HzMaterielRecord;

public class ReflectAddMasterMaterial {

    private HzMaterielRecord record;

    public ReflectAddMasterMaterial(){

    }

    public ReflectAddMasterMaterial(HzCfg0ModelFeature hcfm){
        record = new HzMaterielRecord();
        //物料代码
            record.setpMaterielCode(hcfm.getMaterialCode());
        // 物料类型
            record.setpMaterielType(hcfm.getMaterialCode());
        //工厂
            record.setpMaterielWerk(hcfm.getFactoryCode());
        //物料中文描述
            record.setpMaterielDesc(hcfm.getpFeatureCnDesc());
        //物料描述 英文
            record.setpMaterielDescEn(hcfm.getpFeatureSingleVehicleCode());
        //基本计量单位
            record.setpBasicUnitMeasure(hcfm.getBasicCountUnit());
        //虚拟件标识(1 ,0 )
        if(hcfm.getVirtualFlag()!=null){
            record.setpInventedPart(Integer.valueOf(hcfm.getVirtualFlag()));
        }
        //备件&原材料双属性标示
//        record.setpSpareMaterial(hcfm.getDoubleAttribute());
        //VIN前置号
            record.setpVinPerNo(hcfm.getVinCode());
        //颜色件标识
        if(hcfm.getColor()!=null){
            record.setpColorPart(Integer.valueOf(hcfm.getColor()));
        }
        //毛重
            record.setpHeight(hcfm.getGrossWeight());
        //内外饰标识
        if(hcfm.getLabelFlag()!=null){
            record.setpInOutSideFlag(Integer.valueOf(hcfm.getLabelFlag()));
        }
        //3C件标识
        if(hcfm.getRulesFlag()!=null){
            record.setP3cPartFlag(Integer.valueOf(hcfm.getRulesFlag()));
        }
        //MRP控制者
            record.setpMrpController(hcfm.getMrpController());
        //零件重要度
//        record.setpPartImportantDegree(hcfm.getPartImportance());

    }

    public HzMaterielRecord getRecord() {
        return record;
    }

    public void setRecord(HzMaterielRecord record) {
        this.record = record;
    }
}
