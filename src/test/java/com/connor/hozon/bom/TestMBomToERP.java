package com.connor.hozon.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzMBomToERPDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sql.pojo.bom.HzMBomToERPBean;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMBomToERP {
    @Autowired
    HzMBomToERPDao hzMBomToERPDao;

    @Test
    public void test1() {
        String projectUid = "514762CB57204113BFAC56A5740AF1F8";
        List<String> list = new ArrayList<>();
        list.add("64b61af1-6285-4f9e-8fc6-cd01938f43e7");
        list.add("5cd73138-5b1b-4b4a-a4f6-3cde37040b81");
        list.add("2511e4c2-8e36-4e06-82ab-035b7c06c319");
//        List<HzMBomToERPBean> beans = hzMBomToERPDao.selectByBatch(projectUid, list);
        System.out.println();
    }
}
