package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.resources.dto.EbomTestDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/23
 * @Description:
 */
public class Test1 {
    public static  void main(String[] a){
//        List<EbomTestDTO> arrayList = new ArrayList();
//        EbomTestDTO r1 = new EbomTestDTO();
//        r1.setLevel("1000Y");
//        r1.setHigh(500);
//        arrayList.add(r1);
//        for(int i = 0;i<10;i++){
//            EbomTestDTO r = new EbomTestDTO();
//            r.setLevel(i+"Y");
//            r.setHigh(i);
//            arrayList.add(r);
//        }
//        EbomTestDTO r = new EbomTestDTO();
//        r.setLevel("1Y");
//        r.setHigh(5);
//        arrayList.add(r);
//        System.out.println(JSON.toJSONString(arrayList));

//        String s1 = "2.10.100.300";
//        String l  = s1.split("\\.")[s1.split("\\.").length-1];
//        int s2 = s1.lastIndexOf(".");
//        String s3 = s1.substring(0,s2);
//        System.out.println(s3);
//        System.out.println(l);
        BigDecimal bigDecimal = new BigDecimal("");
        String str = bigDecimal.toPlainString();
        System.out.println(str);
        String s4 =String.valueOf( bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP));
        System.out.println(s4);
        double number = 2456.345565756767;
        BigDecimal dec = new BigDecimal(number);
        System.out.println(dec.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue());
        String s = "2.3455666";
        boolean b = s.contains(".");
        String s1 = s.substring(0,3);
        System.out.println(b);
        System.out.println(s.length());
        System.out.println(s1);
    }
}
