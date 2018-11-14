package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzEbomRecordDAOImpl;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:
 */
public class Test {
    public static void main(String[] a){
        String puids = "c0b1630e-66fc-4578-930e-f419ff3463df," +
                "c03323e8-87b1-4f47-a751-2f936a363293,635d3355-8b60-4749-b67a-b2e2677547aa";
        HzEbomRecordDAO dao = new HzEbomRecordDAOImpl();
        List<HzEPLManageRecord> recordList = dao.getEbomRecordsByPuids(puids,"1c128c60-84a2-4076-9b1c-f7093e56e4df");
        System.out.println(JSON.toJSONString(recordList));
    }
}
