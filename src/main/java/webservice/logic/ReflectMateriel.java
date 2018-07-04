package webservice.logic;

import sql.pojo.project.HzMaterielRecord;

public class ReflectMateriel {
    private MasterMaterial mm;

    public ReflectMateriel(HzMaterielRecord hzMaterielRecord)  {
        mm=new MasterMaterial();
        mm.setFactory(hzMaterielRecord.getpFactoryPuid());//工厂id
        mm.setMaterialCode(hzMaterielRecord.getpMaterielCode()+"");//物料编码
        //mm.setMaterialType(hzMaterielRecord.getpMaterielType());//物料类型()
        mm.setMaterialName(hzMaterielRecord.getpMaterielDesc()+"");//物料中文描述
        mm.setMaterialEnglishName(hzMaterielRecord.getpMaterielDescEn());//物料描述 英文
        mm.setUnit(hzMaterielRecord.getpBasicUnitMeasure());//基本单位
        mm.setVertureFlag(hzMaterielRecord.getpInventedPart()+"");//虚拟件标识
        mm.setDoubleAttribute(hzMaterielRecord.getpSpareMaterial());//备件&原材料双属性标示
        mm.setPerVIN(hzMaterielRecord.getpVinPerNo());//VIN前置号
        mm.setColorFlag(hzMaterielRecord.getpColorPart()+"");//颜色件标识
        mm.setGrossWeight(hzMaterielRecord.getpHeight());//毛重
        mm.setInAndOutFlag(hzMaterielRecord.getpInOutSideFlag()+"");//内外饰标识
        mm.setThreeCFlag(hzMaterielRecord.getP3cPartFlag()+"");//3C件标识
        //mm.setMRPController(hzMaterielRecord.getpMrpController());//MRP控制者
        mm.setPartImportance(hzMaterielRecord.getpPartImportantDegree());//零件重要度
        mm.setBulkFlag(hzMaterielRecord.getpLoosePartFlag()+"");//散件标志

    }


    public MasterMaterial getMm() {
        return mm;
    }
}
