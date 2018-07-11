package com.connor.hozon.bom;

import integration.Author;
import integration.base.bom.TABLEOFZPPTCI005;
import integration.base.bom.TABLEOFZPPTCO005;
import integration.base.bom.ZPPTCI005;
import integration.base.bom.ZPPTCO005;
import integration.service.impl.bom5.TransBomService;

import javax.xml.ws.Holder;

public class Test extends Author {

    public static void main(String[] args) {
//        MasterMaterial masterMaterial = new MasterMaterial();
//
//        String guid = UUID.randomUUID().toString().replaceAll("-", "");
//
//        HzMbomRecordDAO mbomRecordDAO = new HzMbomRecordDAOImpl();
//
//        HzMbomRecord mbomRecord = mbomRecordDAO.findHzMbomByeBomPuid("Q_YpLyl446t5TA");
//
//        if (mbomRecord != null) {
//            String guid3 = UUID.randomUUID().toString().replaceAll("-", "");
//            MasterMaterial masterMaterial2 = new MasterMaterial();
//        }
//
//
//        masterMaterial.setGUID(guid);
//        masterMaterial.setLineNum("S00123");
//
//        masterMaterial.setActionFlag(ActionFlagOption.ADD);
//
//
//        masterMaterial.setFactory("1001");
//        masterMaterial.setMaterialCode("S00-15212SDA");
//        masterMaterial.setMaterialName("普通物料");
//        masterMaterial.setMRPController(MRPControlOption.MRP_ASSEMBLY);
//        masterMaterial.setUnit("EA");
//        masterMaterial.setPurchaseType(PurchaseOption.PURCHASE_EMPTY);
//        masterMaterial.setMaterialType(MaterialOption.MATERIAL_ACCESSORIES);
//        masterMaterial.setVertureFlag("50");
//
//        TransMasterMaterialService service = new TransMasterMaterialService();
//        service.getInput().getItem().add(masterMaterial.getZpptci001());
//        service.execute();
//        service.getOut();
//        System.out.println(service.getOut().getItem().get(0).getTYPE());
//        System.out.println();
//
//
//        ReflectBom bom = new ReflectBom(new HzMBomToERPBean());
//        String guid2 = UUID.randomUUID().toString().replaceAll("-", "");
//
//        bom.setPackNo(guid2);
//        bom.setLineNum("1");
//
//        bom.setActionFlag(ActionFlagOption.ADD);
//        bom.setHeadOfBomLine("S00-5000010");
//        bom.setChildOfBomLine("S00-5000010AAB");
//        bom.setAssemblyPoint("C");
//        bom.setFactory("1001");
//        bom.setNumber("1");
//        bom.setOrderOfBomLine("2");
//        bom.setStation("A01");
//        bom.setUseWorkshop("车间1");
//        bom.setUnit("EA");
//        bom.setModifyCode("S001");
//        bom.setBomType(BomOption.BOM_TYPE_PRODUCTION);
//        bom.setStockLocation("CP04");
//
//        TransBomService bomService = new TransBomService();
//        bomService.getInput().getItem().add(bom.getZpptci005());
//        bomService.execute();
//        bomService.getOut();
//        System.out.println(bomService.getOut().getItem().get(0).getTYPE());
//        System.out.println();

        Holder<TABLEOFZPPTCI005> input = new Holder<TABLEOFZPPTCI005>();
        TABLEOFZPPTCI005 tableofzpptci005 = new TABLEOFZPPTCI005();
        ZPPTCI005 zpptci005 = new ZPPTCI005();
        zpptci005.setPPACKNO("KLFDS");//数据包号
        zpptci005.setPITEM("DSFD");//行号
        zpptci005.setPACTIONID("A");//动作描述代码
        zpptci005.setPAENNR("01");//更改编号
        //zpptci005.setPAETXT("");//工程更改号描述
        //zpptci005.setPPCNNO("");//TC系统更改号
        zpptci005.setPWERKS("1001");//工厂
        zpptci005.setPUSE("1");//BOM类型---1or6
        zpptci005.setPMATNR("S00-5000010");//表头物料编码
        zpptci005.setPBASEQ("1");//基本数据
        zpptci005.setPSORTF("0010");//BOM序号
        zpptci005.setPMATNRC("S00-5000000AABA");//组件物料编码
        zpptci005.setPMENGE("1");//数量
        zpptci005.setPMEINS("EA");//单位
        zpptci005.setPLOCATION("BJ01");//发料库存地点
        //zpptci005.setPKNBLK("");//相关性（选配条件）
        //zpptci005.setPSUBPR("");//采购件下级件标识
        zpptci005.setPZPWZ("总装");//装配位置
        zpptci005.setPWORKS("dfd");//使用车间
        zpptci005.setPSTATION("1");//工位
        //zpptci005.setZGUID("");//事物标识
        tableofzpptci005.getItem().add(zpptci005);
        input.value = tableofzpptci005;//

        Holder<TABLEOFZPPTCO005> output = new Holder<TABLEOFZPPTCO005>();
        TABLEOFZPPTCO005 tableofzpptco005 = new TABLEOFZPPTCO005();
        ZPPTCO005 zpptco005 = new ZPPTCO005();
        tableofzpptco005.getItem().add(zpptco005);
        output.value = tableofzpptco005;//

        TransBomService bomService=new TransBomService();
        bomService.getInput().getItem().add(zpptci005);
        bomService.execute();
        //boolean success = iMasterDataService.delete(input,output);
        //boolean success = iMasterDataService.update(input,output);
        System.out.println(output.value.getItem().get(0).getPTYPE());


        System.out.println();

    }
}
