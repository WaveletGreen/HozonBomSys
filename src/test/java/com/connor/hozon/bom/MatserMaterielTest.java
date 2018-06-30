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
import webservice.base.maindatas.*;
import webservice.service.impl.masterMaterial.TransMasterMaterialService;

import javax.xml.ws.Holder;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MatserMaterielTest extends Author{
    @Autowired
    HzEbomRecordDAO hzEbomRecordDAO;
    @Autowired
    HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    TransMasterMaterialService service;


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

        //设置数据
        //最长32？
        zpptci001.setGUID(hzBomLineRecord.getPuid().replaceAll("-", ""));
        //最长6位？
        zpptci001.setZITEM(hzBomLineRecord.getPuid().substring(0,5));//行号，最长6位
        zpptci001.setZWERKS("1001");//工厂
        zpptci001.setZMATNR(hzBomLineRecord.getLineID());//物料编码
        zpptci001.setZACTIONID("A");//动作描述代码A/D/U
        zpptci001.setZMAKTX(hzBomLineRecord.getpBomLinePartName());//物料中文描述
        zpptci001.setZMEINS("EA");//基本计量单位
        zpptci001.setZMTART("A006");//物料类型
        zpptci001.setZMRPC("Z01");//MRP控制者
        zpptci001.setZBESKZ("E");//采购类型
        zpptci001.setZMATKL(hzBomLineRecord.getIs2Y()==1?"Y":"N");//虚拟机标识



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
        output.value=tableofzpptco001;
        //设置数据
        //最长32？
        zpptci001.setGUID(hzBomLineRecord.getPuid().replaceAll("-", ""));
        //最长6位？
        zpptci001.setZITEM(hzBomLineRecord.getPuid().substring(0,5));//行号，最长6位
        zpptci001.setZWERKS("1001");//工厂
        zpptci001.setZMATNR(hzBomLineRecord.getLineID());//物料编码
        zpptci001.setZACTIONID("A");//动作描述代码A/D/U
        zpptci001.setZMAKTX(hzBomLineRecord.getpBomLinePartName());//物料中文描述
        zpptci001.setZMEINS("EA");//基本计量单位
        zpptci001.setZMTART("A006");//物料类型
        zpptci001.setZMRPC("Z01");//MRP控制者
        zpptci001.setZBESKZ("E");//采购类型
        zpptci001.setZMATKL(hzBomLineRecord.getIs2Y()==1?"Y":"N");//虚拟机标识

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

        zpptci002= (ZPPTCI001) zpptci001.clone();
        zpptci002.setGUID("QERTYUI");
        tableofzpptci001.getItem().add(zpptci001);
        tableofzpptci001.getItem().add(zpptci002);
        input.value=tableofzpptci001;


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
