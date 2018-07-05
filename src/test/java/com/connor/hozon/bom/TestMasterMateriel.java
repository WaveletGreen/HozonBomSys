package com.connor.hozon.bom;


import com.connor.hozon.bom.resources.mybatis.factory.HzFactoryDAO;
import com.connor.hozon.bom.resources.mybatis.materiel.HzMaterielDAO;
import com.connor.hozon.bom.resources.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.service.materiel.HzMaterielService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sql.pojo.factory.HzFactory;
import sql.pojo.project.HzMaterielRecord;
import webservice.base.helper.UUIDHelper;
import webservice.logic.ReflectMateriel;
import webservice.option.ActionFlagOption;
import webservice.option.MRPControlOption;
import webservice.service.impl.masterMaterial1.TransMasterMaterialService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMasterMateriel {
    @Autowired
    HzMaterielService hzMaterielService;

    @Autowired
    HzMaterielDAO hzMaterielDAO;

    @Autowired
    HzFactoryDAO hzFactoryDAO;

    @Test
    public void test1() {
        HzMaterielQuery query = new HzMaterielQuery();
        query.setPuid("1888f3a8-6880-4ae0-9297-28c5ee7324d9");
        HzMaterielRecord hzMateriel = hzMaterielDAO.getHzMaterielRecord(query);
        ReflectMateriel reflectMateriel = new ReflectMateriel(hzMateriel);

        HzFactory hzFactory = hzFactoryDAO.findFactory("20922d56-8e6f-4b35-b3aa-0c53e4fd2d89", null);
        //设置工厂
        reflectMateriel.setFactory(hzFactory == null ? "1001" : hzFactory.getpFactoryCode());
        //设置动作描述代码
        reflectMateriel.setActionFlagOption(ActionFlagOption.DELETE);
        //设置MRP控制者，公告号和采购类型
        reflectMateriel.setMRPAndPurchase(MRPControlOption.MRP_FICTITIOUS.GetDesc(),
                /*PurchaseOption.PURCHASE_MAKE.GetDesc()*/null,
                "公告号");
        String uuid = UUIDHelper.generateUpperUid();
        String lineNum = uuid.substring(0, 5);
        reflectMateriel.setPackNo(uuid);
        reflectMateriel.setLineNum(lineNum);

        TransMasterMaterialService service = new TransMasterMaterialService();
        service.getInput().getItem().add(reflectMateriel.getMm().getZpptci001());
        service.execute();
        service.getOut();
        System.out.println();
    }


}
