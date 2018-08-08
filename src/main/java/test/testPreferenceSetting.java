package test;

import com.connor.hozon.HzBomSysApplication;
import com.connor.hozon.bom.bomSystem.service.cfg.HzMaterielCfgService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sql.pojo.cfg.HzMaterielCfgBean;

import java.util.List;

@SpringBootTest(classes = HzBomSysApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class testPreferenceSetting {
    @Autowired
    HzMaterielCfgService hzMaterielCfgService;

    @Test
    public void testHzMaterielCfgService() {
        hzMaterielCfgService=new HzMaterielCfgService();
        HzMaterielCfgBean bean = new HzMaterielCfgBean();
        bean.setPuid("F6CAFB8DA09A465DB0BA259D88563C84");
//        bean.setProjectUid("e5969e81-0339-4e3a-98a9-a918f64e4289");
        List<HzMaterielCfgBean> result = hzMaterielCfgService.doSelectByDiff(bean);
        System.out.println();
    }
}
