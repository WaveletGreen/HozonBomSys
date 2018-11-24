package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.resources.enumtype.ChangeTableNameEnum;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzEbomRecordDAOImpl;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:
 */
public class Test {

    public static void main(String[] a) throws Exception{
        ExecutorService pool = Executors.newFixedThreadPool(5);
//        String puids = "c0b1630e-66fc-4578-930e-f419ff3463df," +
//                "c03323e8-87b1-4f47-a751-2f936a363293,635d3355-8b60-4749-b67a-b2e2677547aa";
//        HzEbomRecordDAO dao = new HzEbomRecordDAOImpl();
//        List<HzEPLManageRecord> recordList = dao.getEbomRecordsByPuids(puids,"1c128c60-84a2-4076-9b1c-f7093e56e4df");
//        System.out.println(JSON.toJSONString(recordList));
//        long m = System.currentTimeMillis();
//         pool.execute(new Runnable() {
//            long  sum = 0L;
//            @Override
//            public void run() {
//                for(long i  =0L;i<100000000L;i++){
//                    for(long j = 0L;j<10L;j++){
//
//                    }
//                    sum+=i;
//                }
//                System.out.println(sum);
//            }
//
//        });
//        long b = System.currentTimeMillis();
//        System.out.println((b-m)+"ms");
//        if(pool != null){
//            pool.shutdown();
//        }
//        long p = System.currentTimeMillis();
//        long s = 0L;
//        for(long i  =0L;i<100000000L;i++){
//            for(long j = 0L;j<10L;j++){
//            }
//            s+=i;
//        }
//        System.out.println(s);
//        long o = System.currentTimeMillis();
//        System.out.println((o-p)+"ms");
//        String s = ChangeTableNameEnum.getMbomTableName(1,"M");
//        System.out.println(s);

//        String s ="00";
//        int i = Integer.valueOf(s+1);
//        String s1 = String.format("%02d",Integer.valueOf(s+1));
//        System.out.println(s1);
//        System.out.println(i);
        List<HzEPLManageRecord> records  = new ArrayList<>();
        for(int i =0;i<10;i++){
            HzEPLManageRecord record = new HzEPLManageRecord();
            record.setPuid("sss"+i);
            records.add(record);
        }


        List<HzEPLManageRecord> recordList = new ArrayList<>();
        for(int i=0;i<8;i++){
            HzEPLManageRecord r = new HzEPLManageRecord();
            r.setPuid("sss"+i);
            r.setLineID("111");
            recordList.add(r);
        }

        List<HzEPLManageRecord> eplManageRecords = new ArrayList<>();
        for(int i=0;i<records.size();i++){
            records.get(i).setOrderId(1L);
            for(HzEPLManageRecord record:recordList){
                if(records.get(i).equals(record)){
                    records.remove(records.get(i));
                    i--;
                    break;
                }
            }
        }
        eplManageRecords.addAll(records);
        System.out.println(JSON.toJSONString(eplManageRecords));

    }
}
