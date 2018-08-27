package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.resources.dto.EbomTestDTO;

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

        String s1 = "2.10.100.300";
        String l  = s1.split("\\.")[s1.split("\\.").length-1];
        int s2 = s1.lastIndexOf(".");
        String s3 = s1.substring(0,s2);
        System.out.println(s3);
        System.out.println(l);
    }
}
