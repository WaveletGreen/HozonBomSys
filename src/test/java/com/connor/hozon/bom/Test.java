package com.connor.hozon.bom;

import com.connor.hozon.bom.resources.mybatis.bom.HzMbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzMbomRecordDAOImpl;
import sql.pojo.bom.HzMbomRecord;
import webservice.Author;
import webservice.logic.Bom;
import webservice.logic.MasterMaterial;
import webservice.option.ActionFlagOption;
import webservice.option.BomOption;
import webservice.option.MaterialOption;
import webservice.service.impl.bom5.TransBomService;
import webservice.service.impl.masterMaterial1.TransMasterMaterialService;

import java.util.UUID;

public class Test extends Author {

    public static void main(String[] args) {
        MasterMaterial masterMaterial = new MasterMaterial();

        String guid = UUID.randomUUID().toString().replaceAll("-", "");

        HzMbomRecordDAO mbomRecordDAO = new HzMbomRecordDAOImpl();

        HzMbomRecord mbomRecord = mbomRecordDAO.findHzMbomByeBomPuid("Q_YpLyl446t5TA");

        if (mbomRecord != null) {
            String guid3 = UUID.randomUUID().toString().replaceAll("-", "");
            MasterMaterial masterMaterial2 = new MasterMaterial();
        }


        masterMaterial.setGUID(guid);
        masterMaterial.setLineNum("S00123");

        masterMaterial.setActionFlag(ActionFlagOption.ADD);


        masterMaterial.setFactory("1001");
        masterMaterial.setMaterialCode("S00-15212SDA");
        masterMaterial.setMaterialName("普通物料");
        masterMaterial.setMRPController(MaterialOption.MRP_ASSEMBLY);
        masterMaterial.setUnit("EA");
        masterMaterial.setPurchaseType(MaterialOption.PURCHASE_EMPTY);
        masterMaterial.setMaterialType(MaterialOption.MATERIAL_ACCESSORIES);
        masterMaterial.setVertureFlag("50");

        TransMasterMaterialService service = new TransMasterMaterialService();
        service.getInput().getItem().add(masterMaterial.getZpptci001());
        service.execute();
        service.getOut();
        System.out.println(service.getOut().getItem().get(0).getTYPE());
        System.out.println();


        Bom bom = new Bom();
        String guid2 = UUID.randomUUID().toString().replaceAll("-", "");

        bom.setPackNo(guid2);
        bom.setLineNum("1");

        bom.setActionFlag(ActionFlagOption.ADD);
        bom.setHeadOfBomLine("S00-5000010");
        bom.setChildOfBomLine("S00-5000010AAB");
        bom.setAssemblyPoint("C");
        bom.setFactory("1001");
        bom.setNumber("1");
        bom.setOrderOfBomLine("2");
        bom.setStation("A01");
        bom.setUseWorkshop("车间1");
        bom.setUnit("EA");
        bom.setModifyCode("S001");
        bom.setBomType(BomOption.BOM_TYPE_PRODUCTION);
        bom.setStockLocation("CP04");

        TransBomService bomService = new TransBomService();
        bomService.getInput().getItem().add(bom.getZpptci005());
        bomService.execute();
        bomService.getOut();
        System.out.println(bomService.getOut().getItem().get(0).getTYPE());
        System.out.println();
    }
}
