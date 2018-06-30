package com.connor.hozon.bom;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sql.pojo.bom.HzBomLineRecord;
import webservice.Author;
import webservice.base.bom.ZPPTCI005;
import webservice.base.masterMaterial.*;
import webservice.service.impl.bom5.TransBomService;
import webservice.service.impl.masterMaterial1.TransMasterMaterialService;

import javax.xml.ws.Holder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MatserMaterielTest extends Author {
    @Autowired
    HzEbomRecordDAO hzEbomRecordDAO;
    @Autowired
    HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    TransMasterMaterialService service;

    @Autowired
    TransBomService bomService;

    @Test
    public void testBom() {
        Map<String, String> cond = new HashMap<>();
        cond.put("projectId", "514762CB57204113BFAC56A5740AF1F8");//projectpuid
        cond.put("puid", "8ca372e3-27d0-4080-a8c9-3bc1114dea6f");//PUID
        HzBomLineRecord hzBomLineRecord = hzBomLineRecordDao.findBomLineByPuid(cond);

        if (hzBomLineRecord == null) {
            System.out.println("没有找到对象");
            return;
        }

        Map<String, String> uuidRecord = new HashMap<>();

        String puid = hzBomLineRecord.getPuid().replaceAll("-", "");
        String tempPuid = UUID.randomUUID().toString().replaceAll("-", "");
        uuidRecord.put(tempPuid, puid);


        ZPPTCI005 zpptci005 = new ZPPTCI005();
        zpptci005.setZPACKNO(tempPuid);//数据包号.Test，包好最长32，已确认
        zpptci005.setZITEM(hzBomLineRecord.getPuid().substring(0, 5));//行号,Test，最长6位，已确认

        zpptci005.setZACTIONID("A");//动作描述代码
        zpptci005.setZAENNR("02");//更改编号
        zpptci005.setZAETXT("");//工程更改号描述
        zpptci005.setZPCNNO("");//TC系统更改号
        zpptci005.setZWERKS("1001");//工厂
        zpptci005.setZUSE("1");//BOM类型---1or6
        zpptci005.setZMATNR(hzBomLineRecord.getLineID());//表头物料编码，可以与主数据的MATNR一样
        /*都是1吗？*/
        zpptci005.setZBASEQ("1");//基本数据，
        zpptci005.setZSORTF(hzBomLineRecord.getOrderNum().toString());//BOM序号

        zpptci005.setZMATNRC(hzBomLineRecord.getLineID());//组件物料编码

        zpptci005.setZMENGE("1");//数量
        zpptci005.setZMEINS("EA");//单位
        /**        填CP04却返回null，库存地址应该传什么*/
        zpptci005.setZLOCATION("CP04");//发料库存地点             //
        zpptci005.setZKNBLK("");//相关性（选配条件）
        zpptci005.setZSUBPR("");//采购件下级件标识
        zpptci005.setZZPWZ("总装");//装配位置
        zpptci005.setZWORKS("");//使用车间
        zpptci005.setZSTATION("");//工位
        zpptci005.setZGUID("");//事物标识

        bomService.getInput().getItem().add(zpptci005);
        bomService.execute();
        bomService.getOut().getItem().get(0).getMESSAGE();
        System.out.println();
    }

    @Test
    public void main2() throws CloneNotSupportedException {
        Map<String, String> cond = new HashMap<>();
        cond.put("projectId", "514762CB57204113BFAC56A5740AF1F8");//projectpuid
        cond.put("puid", "8ca372e3-27d0-4080-a8c9-3bc1114dea6f");//PUID
        HzBomLineRecord hzBomLineRecord = hzBomLineRecordDao.findBomLineByPuid(cond);

        if (hzBomLineRecord == null) {
            System.out.println("没有找到对象");
            return;
        }

        ZPPTCI001 zpptci001 = new ZPPTCI001();
        ZPPTCI001 zpptci002;


        Map<String, String> uuidRecord = new HashMap<>();

        String puid = hzBomLineRecord.getPuid().replaceAll("-", "");
        String tempPuid = UUID.randomUUID().toString().replaceAll("-", "");
        uuidRecord.put(tempPuid, puid);
        //设置数据
        //最长32？
        zpptci001.setGUID(tempPuid);
        //最长6位？
        zpptci001.setZITEM(hzBomLineRecord.getPuid().substring(0, 5));//行号，最长6位
        zpptci001.setZWERKS("1001");//工厂
        zpptci001.setZMATNR(hzBomLineRecord.getLineID());//物料编码
        zpptci001.setZACTIONID("A");//动作描述代码A/D/U
        zpptci001.setZMAKTX(hzBomLineRecord.getpBomLinePartName());//物料中文描述
        zpptci001.setZMEINS("EA");//基本计量单位
        zpptci001.setZMTART("A006");//物料类型
        zpptci001.setZMRPC("Z01");//MRP控制者
        zpptci001.setZBESKZ("E");//采购类型
        zpptci001.setZMATKL(hzBomLineRecord.getIs2Y() == 1 ? "Y" : "N");//虚拟机标识

        service.getInput().getItem().add(zpptci001);
        service.execute();

//        zpptci002 = (ZPPTCI001) zpptci001.clone();
//        zpptci002.setGUID("QERTYUI");
//        service.getInput().getItem().add(zpptci001);
//        service.getInput().getItem().add(zpptci002);
//        service.execute();

        System.out.println(service.getOut().getItem().get(0).getTYPE());
        System.out.println();

    }

    //    @Test
    public void main() throws CloneNotSupportedException {
        Map<String, String> cond = new HashMap<>();
        cond.put("projectId", "514762CB57204113BFAC56A5740AF1F8");//projectpuid
        cond.put("puid", "8ca372e3-27d0-4080-a8c9-3bc1114dea6f");//PUID
        HzBomLineRecord hzBomLineRecord = hzBomLineRecordDao.findBomLineByPuid(cond);

        if (hzBomLineRecord == null) {
            System.out.println("没有找到对象");
            return;
        }

        ZPPTCI001 zpptci001 = new ZPPTCI001();
        ZPPTCI001 zpptci002;
        ZPPTCO001 zpptco001 = new ZPPTCO001();

        //服务
        ZPPTCSAP001_Service service = new ZPPTCSAP001_Service();
        ZPPTCSAP001 zpptcsap001 = service.getZPPTCSAP001();
        //输入参数
        Holder<TABLEOFZPPTCI001> input = new Holder<>();
        TABLEOFZPPTCI001 tableofzpptci001 = new TABLEOFZPPTCI001();
        //输出参数
        Holder<TABLEOFZPPTCO001> output = new Holder<>();
        TABLEOFZPPTCO001 tableofzpptco001 = new TABLEOFZPPTCO001();
        tableofzpptco001.getItem().add(zpptco001);
        output.value = tableofzpptco001;
        //设置数据
        //最长32？
        zpptci001.setGUID(hzBomLineRecord.getPuid().replaceAll("-", ""));
        //最长6位？
        zpptci001.setZITEM(hzBomLineRecord.getPuid().substring(0, 5));//行号，最长6位
        zpptci001.setZWERKS("1001");//工厂
        zpptci001.setZMATNR(hzBomLineRecord.getLineID());//物料编码
        zpptci001.setZACTIONID("A");//动作描述代码A/D/U
        zpptci001.setZMAKTX(hzBomLineRecord.getpBomLinePartName());//物料中文描述
        zpptci001.setZMEINS("EA");//基本计量单位
        zpptci001.setZMTART("A006");//物料类型
        zpptci001.setZMRPC("Z01");//MRP控制者
        zpptci001.setZBESKZ("E");//采购类型
        zpptci001.setZMATKL(hzBomLineRecord.getIs2Y() == 1 ? "Y" : "N");//虚拟机标识

//        zpptci001.setGUID("625ad9d3b71f49eb98faa6844bbc7a67");//数据包号，最长32位
//        zpptci001.setZITEM("yCehB");//行号，最长6位
//        zpptci001.setZWERKS("1001");//工厂
//        zpptci001.setZMATNR("S00-5701300WS");//物料编码
//        zpptci001.setZACTIONID("A");//动作描述代码
//        zpptci001.setZMAKTX("白车身骨架总成焊点");//物料中文描述
//        zpptci001.setZMEINS("EA");//基本计量单位
//        zpptci001.setZMTART("A006");//物料类型
//        zpptci001.setZMRPC("Z01");//MRP控制者
//        zpptci001.setZBESKZ("E");//采购类型
//        zpptci001.setZMATKL("SADasD");//虚拟机标识

        zpptci002 = (ZPPTCI001) zpptci001.clone();
        zpptci002.setGUID("QERTYUI");
        tableofzpptci001.getItem().add(zpptci001);
        tableofzpptci001.getItem().add(zpptci002);
        input.value = tableofzpptci001;


        zpptcsap001.zppTCSAP001(input, output);
        if (output.value.getItem() != null && output.value.getItem().size() > 0) {
            System.out.println(output.value.getItem().get(0).getMESSAGE());
            System.out.println(output.value.getItem().get(0).getGUID());
            System.out.println(output.value.getItem().get(0).getTYPE());
            System.out.println(output.value.getItem().get(0).getZITEM());
        }

        System.out.println(JSON.toJSONString(hzBomLineRecord));
    }
}
