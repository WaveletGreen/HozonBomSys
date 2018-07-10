//package com.connor.hozon.bom;
//
//import webservice.Author;
//import webservice.base.bom.TABLEOFZPPTCI005;
//import webservice.base.bom.TABLEOFZPPTCO005;
//import webservice.base.bom.ZPPTCI005;
//import webservice.base.bom.ZPPTCO005;
//import webservice.base.cfg.*;
//import webservice.base.classify.*;
//import webservice.base.maindatas.*;
//import webservice.base.options.TABLEOFZPPTCI004;
//import webservice.base.options.TABLEOFZPPTCO004;
//import webservice.base.options.ZPPTCI004;
//import webservice.base.options.ZPPTCO004;
//import webservice.base.processRoute.TABLEOFZPPTCI006;
//import webservice.base.processRoute.TABLEOFZPPTCO006;
//import webservice.base.processRoute.ZPPTCI006;
//import webservice.base.processRoute.ZPPTCO006;
//import webservice.base.productAttributes.TABLEOFZPPTCI007;
//import webservice.base.productAttributes.TABLEOFZPPTCO007;
//import webservice.base.productAttributes.ZPPTCI007;
//import webservice.base.productAttributes.ZPPTCO007;
//
//import javax.xml.ws.Holder;
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class Test1 extends Author {
//    Author author = new Author();
//    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//    public static void main(String[] args) {
////        testMainDatas();
////        testCfg();
////        testClassify();
////        testService();
//
//        //testMasterData();//001-物料主数据接口
//        //testFeature();//002-特性/特性值接口
//        testCorrelation();//004-相关性接口
//        testBom();//005-BOM接口：要添加更改编号
//        //testVehicleMaterial();//007-整车物料属性接口：（如属性CAB的值指定为CAB1时，传输数据为A、整车属性、CAB、CAB1）
//
//
//        //testConfigurableMaterial();//003-可配置物料分配特性接口：index 0 ,size 0(zpptcsap003.zppTCSAP003(input, output)方法？？？)
//        //testProcessRoute();//006-工艺路线接口:创建工序时一致性检查失败
//    }
//
//    private static void testProcessRoute() {
//        Holder<TABLEOFZPPTCI006> input = new Holder<TABLEOFZPPTCI006>();
//        TABLEOFZPPTCI006 tableofzpptci006 = new TABLEOFZPPTCI006();
//        ZPPTCI006 zpptci006 = new ZPPTCI006();
//        zpptci006.setZPACKNO("S00Adgdg");//数据包号
//        zpptci006.setZITEM("FGF");//行号
//        zpptci006.setZACTIONID("A");//动作描述代码
//        zpptci006.setZWERKS("1001");//工厂
//        zpptci006.setZMATNR("S00-5000020");//物料编码S7150AABAAA0001
//        zpptci006.setZBASED("1");//基本数量
//        zpptci006.setZDATE(sdf.format(new Date()));//有效日期
//        zpptci006.setZUSE("1");//用途
//        zpptci006.setZSTA("4");//状态
//        zpptci006.setZNUMBER("0010");//工序序号
//        zpptci006.setZWORK("T1BP");//工作中心
//        zpptci006.setZCON("ZHZ2");//控制码
//        zpptci006.setZROUT("合众焊装主线");//工序描述:S00车型2018款 白色 短续航 舒适型
//        zpptci006.setZDATA1(new BigDecimal("1000"));//作业数量1
//        tableofzpptci006.getItem().add(zpptci006);
//        input.value = tableofzpptci006;//
//
//        Holder<TABLEOFZPPTCO006> output = new Holder<TABLEOFZPPTCO006>();
//        TABLEOFZPPTCO006 tableofzpptco006 = new TABLEOFZPPTCO006();
//        ZPPTCO006 zpptco006 = new ZPPTCO006();
//        tableofzpptco006.getItem().add(zpptco006);
//        output.value = tableofzpptco006;//
//        IProcessRouteService iProcessRouteService = new ProcessRouteService();
//        boolean success = iProcessRouteService.add(input,output);
//        if (success)
//            System.out.println("发送成功");
//        else
//            System.out.println("发送失败");
//        System.out.println(output.value.getItem().get(0).getTYPE());
//    }
//
//    private static void testBom() {
//        Holder<TABLEOFZPPTCI005> input = new Holder<TABLEOFZPPTCI005>();
//        TABLEOFZPPTCI005 tableofzpptci005 = new TABLEOFZPPTCI005();
//        ZPPTCI005 zpptci005 = new ZPPTCI005();
//        zpptci005.setZPACKNO("KLFDS");//数据包号
//        zpptci005.setZITEM("DSFD");//行号
//        zpptci005.setZACTIONID("A");//动作描述代码
//        zpptci005.setZAENNR("01");//更改编号
//        //zpptci005.setZAETXT("");//工程更改号描述
//        //zpptci005.setZPCNNO("");//TC系统更改号
//        zpptci005.setZWERKS("1001");//工厂
//        zpptci005.setZUSE("1");//BOM类型---1or6
//        zpptci005.setZMATNR("S00-5000010");//表头物料编码
//        zpptci005.setZBASEQ("1");//基本数据
//        zpptci005.setZSORTF("0010");//BOM序号
//        zpptci005.setZMATNRC("S00-5000000AABA");//组件物料编码
//        zpptci005.setZMENGE("1");//数量
//        zpptci005.setZMEINS("EA");//单位
//        zpptci005.setZLOCATION("BJ01");//发料库存地点
//        //zpptci005.setZKNBLK("");//相关性（选配条件）
//        //zpptci005.setZSUBPR("");//采购件下级件标识
//        zpptci005.setZZPWZ("总装");//装配位置
//        zpptci005.setZWORKS("dfd");//使用车间
//        zpptci005.setZSTATION("1");//工位
//        //zpptci005.setZGUID("");//事物标识
//        tableofzpptci005.getItem().add(zpptci005);
//        input.value = tableofzpptci005;//
//
//        Holder<TABLEOFZPPTCO005> output = new Holder<TABLEOFZPPTCO005>();
//        TABLEOFZPPTCO005 tableofzpptco005 = new TABLEOFZPPTCO005();
//        ZPPTCO005 zpptco005 = new ZPPTCO005();
//        tableofzpptco005.getItem().add(zpptco005);
//        output.value = tableofzpptco005;//
//
//        IBomService iBomService = new BomService();
//        boolean success = iBomService.add(input,output);
//        if (success)
//            System.out.println("发送成功");
//        else
//            System.out.println("发送失败");
//        System.out.println(output.value.getItem().get(0).getTYPE());
//    }
//
//    private static void testMasterData() {
//        Holder<TABLEOFZPPTCI001> input = new Holder<TABLEOFZPPTCI001>();
//        TABLEOFZPPTCI001 tableofzpptci001 = new TABLEOFZPPTCI001();
//        ZPPTCI001 zpptci001 = new ZPPTCI001();
//        //zpptci001.setRESERVE01("dsada");
//        zpptci001.setGUID("8ca372e3-27d0-4080-a8c9-3bc1114d");//数据包号
//        zpptci001.setZITEM("SDSaas");//行号
//        zpptci001.setZWERKS("1001");//工厂
//        zpptci001.setZMATNR("S001SADA1231345");//物料编码
//        zpptci001.setZACTIONID("A");//动作描述代码
//        zpptci001.setZMAKTX("物料中文描述");//物料中文描述
//        zpptci001.setZMEINS("EA");//基本计量单位
//        zpptci001.setZMTART("A006");//物料类型
//        zpptci001.setZMRPC("Z01");//MRP控制者
//        zpptci001.setZBESKZ("E");//采购类型
//        zpptci001.setZMATKL("SADasD");//虚拟机标识
//        tableofzpptci001.getItem().add(zpptci001);
//        input.value = tableofzpptci001;// .getItem().add(zpptci001);
//
//        Holder<TABLEOFZPPTCO001> output = new Holder<TABLEOFZPPTCO001>();
//        TABLEOFZPPTCO001 tableofzpptco001 = new TABLEOFZPPTCO001();
//        ZPPTCO001 zpptco001 = new ZPPTCO001();
//        tableofzpptco001.getItem().add(zpptco001);
//        output.value = tableofzpptco001;//
//        IMasterDataService iMasterDataService = new MasterDataService();
//        boolean success = iMasterDataService.add(input,output);
//        //boolean success = iMasterDataService.delete(input,output);
//        //boolean success = iMasterDataService.update(input,output);
//        if (success)
//            System.out.println("发送成功");
//        else
//            System.out.println("发送失败");
//        System.out.println(output.value.getItem().get(0).getTYPE());
//    }
//
//    private static void testVehicleMaterial() {
//        Holder<TABLEOFZPPTCI007> input = new Holder<TABLEOFZPPTCI007>();
//        TABLEOFZPPTCI007 tableofzpptci007 = new TABLEOFZPPTCI007();
//        ZPPTCI007 zpptci007 = new ZPPTCI007();
//        zpptci007.setZPACKNO("S001dfd");//数据包号
//        zpptci007.setZITEM("VGA");//行号
//        zpptci007.setZACTIONID("A");//动作描述代码
//        zpptci007.setZWERKS("1001");//工厂
//        zpptci007.setZMATNR("S00DB12B00A");//整车编码
//        zpptci007.setZATNAM("SD_09");//属性编码(特性编码)
//        zpptci007.setZATWRT("SD_09");//属性值(特性编码值)
//        tableofzpptci007.getItem().add(zpptci007);
//        input.value = tableofzpptci007;
//        Holder<TABLEOFZPPTCO007> output = new Holder<TABLEOFZPPTCO007>();
//        TABLEOFZPPTCO007 tableofzpptco007 = new TABLEOFZPPTCO007();
//        ZPPTCO007 zpptco007 = new ZPPTCO007();
//        tableofzpptco007.getItem().add(zpptco007);
//        output.value = tableofzpptco007;
//
//        IVehicleMaterialService iVehicleMaterialService = new VehicleMaterialService();
//        boolean success = iVehicleMaterialService.add(input,output);
//        if (success)
//            System.out.println("发送成功");
//        else
//            System.out.println("发送失败");
//        System.out.println(output.value.getItem().get(0).getTYPE());
//    }
//
//    private static void testCorrelation() {
//        Holder<TABLEOFZPPTCI004> input = new Holder<TABLEOFZPPTCI004>();
//        TABLEOFZPPTCI004 tableofzpptci004 = new TABLEOFZPPTCI004();
//        ZPPTCI004 zpptci004 = new ZPPTCI004();
//        zpptci004.setZPACKNO("SFGD");//数据包号
//        zpptci004.setZITEM("ADF");//行号
//        zpptci004.setZACTIONID("D");//动作描述代码
//        zpptci004.setZKNNAM("HZYQCS-1AA00-CA");//相关性
//        zpptci004.setZKNKTX("油漆车身总成-高配&带迎宾灯（侧围带孔）-白");//相关性描述
//        zpptci004.setZKNART("1");//相关性类型(1：发布可执行;3：锁定不执行)
//        zpptci004.setZKNSTA("5");//相关性状态(默认：5)
//        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        //sdf.format(new Date());
//        zpptci004.setZKNEDT(sdf.format(new Date()));//创建日期
//        zpptci004.setZKNADT(sdf.format(new Date()));//修改日期
//        zpptci004.setZKNNAMCODE("$ROOT.HZYQCS = '1AA00' AND $ROOT.HZCSYS = 'BA'");//相关性代码
//        tableofzpptci004.getItem().add(zpptci004);
//        input.value = tableofzpptci004;
//        Holder<TABLEOFZPPTCO004> output = new Holder<TABLEOFZPPTCO004>();
//        TABLEOFZPPTCO004 tableofzpptco004 = new TABLEOFZPPTCO004();
//        ZPPTCO004 zpptco004 = new ZPPTCO004();
//        tableofzpptco004.getItem().add(zpptco004);
//        output.value = tableofzpptco004;
//        ICorrelationService iCorrelationService = new CorrelationService();
//        //boolean success = iCorrelationService.add(input,output);
//        //boolean success = iCorrelationService.delete(input,output);
//        boolean success = iCorrelationService.update(input,output);
//        if(success){
//            System.out.println("发送成功");
//        }
//        else{
//            System.out.println("发送失败");
//        }
//    }
//
//    private static void testConfigurableMaterial() {//003
//        Holder<TABLEOFZPPTCI003> input = new Holder<TABLEOFZPPTCI003>();
//        TABLEOFZPPTCI003 tableofzpptci003 = new TABLEOFZPPTCI003();
//        ZPPTCI003 zpptci003 = new ZPPTCI003();
//        zpptci003.setZPACKNO("S001");//数据包号
//        zpptci003.setZITEM("SDS");//超级物料
//        zpptci003.setZACTIONID("A");//动作描述代码
//        zpptci003.setZMATNR("S001SADA1231345");//可配置物料编码(整车产品)
//        zpptci003.setZATNAM("HZCSYS");//特性编码
//        zpptci003.setZATWRT("1AA00");//特性值编码
//        zpptci003.setZCLASS("");//模型族-分类
//        tableofzpptci003.getItem().add(zpptci003);
//        input.value = tableofzpptci003;
//
//        Holder<TABLEOFZPPTCO003> output = new Holder<TABLEOFZPPTCO003>();
//        TABLEOFZPPTCO003 tableofzpptco003 = new TABLEOFZPPTCO003();
//        ZPPTCO003 zpptco003 = new ZPPTCO003();
//        tableofzpptco003.getItem().add(zpptco003);
//        output.value = tableofzpptco003;
////        System.out.println(output.value.getItem().get(0).getTYPE());
//        IConfigurableMaterialService iConfigurableMaterialService = new ConfigurableMaterialService();
//        boolean success = iConfigurableMaterialService.add(input,output);
//        //boolean success = iConfigurableMaterialService.delete(input,output);
//        //boolean success = iConfigurableMaterialService.deal(input,output);
//        if(success){
//            System.out.println("发送成功");
//        }
//        else{
//            System.out.println("发送失败");
//        }
//    }
//
//    private static void testFeature() {//002
//        Holder<TABLEOFZPPTCI002> input = new Holder<TABLEOFZPPTCI002>();
//        TABLEOFZPPTCI002 tableofzpptci002 = new TABLEOFZPPTCI002();
//        ZPPTCI002 zpptci002 = new ZPPTCI002();
//        zpptci002.setZPACKNO("GHG");// 数据包号
//        zpptci002.setZITEM("ASDS");// 行号
//        zpptci002.setZACTIONID("A");// 动作
//        zpptci002.setZATNAM("AL001");//特性编码
//        zpptci002.setZATBEZ("特性描述");//特性描述
//        zpptci002.setZATWRT("1AA00");//特性值编码
//        zpptci002.setZATWTB("特性值描述");//特性值描述
//        tableofzpptci002.getItem().add(zpptci002);
//        input.value = tableofzpptci002;
//
//        Holder<TABLEOFZPPTCO002> output = new Holder<TABLEOFZPPTCO002>();
//        TABLEOFZPPTCO002 tableofzpptco002 = new TABLEOFZPPTCO002();
//        ZPPTCO002 zpptco002 = new ZPPTCO002();
//        tableofzpptco002.getItem().add(zpptco002);
//        output.value = tableofzpptco002;
//
//        IFeatureService iFeatureService = new FeatureService();
//
//        boolean success = iFeatureService.add(input,output);
//        //boolean success=iFeatureService.delete(input,output);
//        //boolean success=iFeatureService.update(input,output);
//        //boolean success=iFeatureService.updateByDelete(input,output);
//        if(success){
//            System.out.println("发送成功");
//        }
//        else{
//            System.out.println("发送失败");
//        }
//        System.out.println(output.value.getItem().get(0).getTYPE());//S
//
//    }
//
//    private static void testService() {
//        Holder<TABLEOFZPPTCI005> input = new Holder<TABLEOFZPPTCI005>();
//        TABLEOFZPPTCI005 tableofzpptci005 = new TABLEOFZPPTCI005();
//        ZPPTCI005 zpptci005 = new ZPPTCI005();
//        zpptci005.setRESERVE01("dsada");
//
//        zpptci005.setZMATNR("S001SADA1231345");
//        zpptci005.setZACTIONID("A");
//        zpptci005.setZMEINS("EA");
//        tableofzpptci005.getItem().add(zpptci005);
//
//        input.value = tableofzpptci005;//
//
//        Holder<TABLEOFZPPTCO005> output = new Holder<TABLEOFZPPTCO005>();
//        TABLEOFZPPTCO005 tableofzpptco005 = new TABLEOFZPPTCO005();
//        ZPPTCO005 zpptco005 = new ZPPTCO005();
//        output.value = tableofzpptco005;
//
//        IBomService iBomService=new BomService();
//
//        boolean success=iBomService.add(input,output);
//        if(success){
//            System.out.println("发送成功");
//        }
//        else{
//            System.out.println("发送失败");
//        }
//    }
//
//    private static void testClassify() {
//        ZPPTCSAP003_Service service = new ZPPTCSAP003_Service();
//        ZPPTCSAP003 zpptcsap003 = service.getZPPTCSAP003();
//        Holder<TABLEOFZPPTCI003> input = new Holder<TABLEOFZPPTCI003>();
//        TABLEOFZPPTCI003 tableofzpptci003 = new TABLEOFZPPTCI003();
//        ZPPTCI003 zpptci003 = new ZPPTCI003();
//        zpptci003.setZATWRT("");
//
//
//        tableofzpptci003.getItem().add(zpptci003);
//        input.value = tableofzpptci003;
//
//        Holder<TABLEOFZPPTCO003> output = new Holder<TABLEOFZPPTCO003>();
//        zpptcsap003.zppTCSAP003(input, output);
//    }
//
//    /**
//     * 测试配置信息传输
//     */
//    private static void testCfg() {
//        ZPPTCSAP002_Service service = new ZPPTCSAP002_Service();
//        ZPPTCSAP002 zpptcsap002 = service.getZPPTCSAP002();
//        Holder<TABLEOFZPPTCI002> input = new Holder<TABLEOFZPPTCI002>();
//        TABLEOFZPPTCI002 tableofzpptci002 = new TABLEOFZPPTCI002();
//        ZPPTCI002 zpptci002 = new ZPPTCI002();
//        zpptci002.setZPACKNO("123456");// 数据包号
//        zpptci002.setZITEM("123dsa");// 行号
//        zpptci002.setZACTIONID("A");// 动作
//        zpptci002.setZATNAM("AL001");
//        zpptci002.setZATBEZ("特性描述");
//        zpptci002.setZATWRT("AA00");
//        zpptci002.setZATWTB("特性值描述");
//        tableofzpptci002.getItem().add(zpptci002);
//        input.value = tableofzpptci002;
//
//        Holder<TABLEOFZPPTCO002> output = new Holder<TABLEOFZPPTCO002>();
//        TABLEOFZPPTCO002 tableofzpptco002 = new TABLEOFZPPTCO002();
//        ZPPTCO002 zpptco002 = new ZPPTCO002();
//        tableofzpptco002.getItem().add(zpptco002);
//        output.value = tableofzpptco002;
//
//        zpptcsap002.zppTCSAP002(input, output);
//        System.out.println(output.value.getItem().get(0).getTYPE());
//    }
//
//    /**
//     * 测试主数据传输
//     */
//    private static void testMainDatas() {
//        ZPPTCSAP001_Service service = new ZPPTCSAP001_Service();
//        ZPPTCSAP001 zpptcsap001 = service.getZPPTCSAP001();
//        Holder<TABLEOFZPPTCI001> input = new Holder<TABLEOFZPPTCI001>();
//
//        TABLEOFZPPTCI001 tableofzpptci001 = new TABLEOFZPPTCI001();
//        ZPPTCI001 zpptci001 = new ZPPTCI001();
//        zpptci001.setRESERVE01("dsada");
//        zpptci001.setGUID("S001");
//        zpptci001.setZMATNR("S001SADA1231345");
//        zpptci001.setZACTIONID("A");
//        zpptci001.setZMAKTX("物料中文描述");
//        zpptci001.setZMEINS("EA");
//        zpptci001.setZMTART("A006");
//        zpptci001.setZMRPC("Z01");
//        zpptci001.setZBESKZ("E");
//        zpptci001.setZMATKL("SADasD");
//        tableofzpptci001.getItem().add(zpptci001);
//
//        input.value = tableofzpptci001;// .getItem().add(zpptci001);
//
//        Holder<TABLEOFZPPTCO001> output = new Holder<TABLEOFZPPTCO001>();
//        TABLEOFZPPTCO001 tableofzpptco001 = new TABLEOFZPPTCO001();
//        ZPPTCO001 zpptco001 = new ZPPTCO001();
//        output.value = tableofzpptco001;// .getItem().add(zpptco001);
//
//        zpptcsap001.zppTCSAP001(input, output);
//        System.out.println(output.value.getItem().get(0).getMESSAGE());
//        System.out.println(output.value.getItem().get(0).getTYPE());
//    }
//
//}
