package com.connor.hozon.bom.resources.controller;

import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.resources.dto.EbomTestDTO;
import com.connor.hozon.bom.resources.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import org.mybatis.spring.annotation.MapperScan;

import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/8/22
 * @Description:
 */
public class Test {
    public static void main(String[] a){
        /**
         * 一棵树一棵树的解析 一棵树一棵树的新增
         * 数据的正确性大于效率
         */
        //2 /2Y     3/3Y                   4/4Y ....             10/10Y...
        //10.10         10.10.10           10.10.10.10
        //20.10          20.10.10           20.10.20.10        ....
        //100.50        100.10.100          100.300.200.100      10.10.10.100.10.10.......

        //2Y 3 3 3 3Y 4 4 4 4 4Y 5 5 5Y 6Y 7 7 7  5 5 5  4 4 4Y 5 5 3 3 2/2Y m]

        List<EbomTestDTO> list = generateEbomTree();
        Map<String,String> mapList = new HashMap<>();
        for(int i = 0;i<list.size();i++){//产生父键
            EbomTestDTO testDTO = list.get(i);
            String level = testDTO.getLevel();
            int high = testDTO.getHigh();
            mapList.put(level+"-"+high,UUID.randomUUID().toString());
        }
        System.out.println(JSON.toJSONString(mapList));


       Map<String,String> lineIndexMap = new HashMap<>();
        for(int i = 1;i<list.size();i++){//产生lineIndex
            EbomTestDTO preDTO = list.get(i-1);
            EbomTestDTO currentDTO = list.get(i);
            if(preDTO.getHigh() == 0){
                lineIndexMap.put(preDTO.getLevel()+"-"+0,"10.10");
            }
            String level = currentDTO.getLevel();
            int high = currentDTO.getHigh();
            String currentKey = level+"-"+high;
            String preKey = preDTO.getLevel()+"-"+preDTO.getHigh();
            String currentIndex = "";
            int preLevelNum = Integer.valueOf(preDTO.getLevel().replace("Y",""));
            int currentLevelNum = Integer.valueOf(level.replace("Y",""));

            if(currentLevelNum>preLevelNum){
                for(String key:lineIndexMap.keySet()){
                    if(key.equals(preKey)){
                        StringBuffer s = new StringBuffer(lineIndexMap.get(preKey));
                        currentIndex = s.append(".10").toString();
                        lineIndexMap.put(currentKey,currentIndex);
                        break;
                    }
                }
            }else if(currentLevelNum == preLevelNum){
                for(String key:lineIndexMap.keySet()){
                    if(key.equals(preKey)){
                        String value = lineIndexMap.get(preKey);
                        int lastNum = Integer.valueOf(value.split("\\.")[value.split("\\.").length-1]);
                        int charAtNum = value.lastIndexOf(".");
                        String preIndexDoNotContainLastNum = value.substring(0,charAtNum);
                        lastNum+=10;
                        currentIndex = preIndexDoNotContainLastNum+"."+lastNum;
                        lineIndexMap.put(currentKey,currentIndex);
                        break;
                    }

                }
            }else {
                for(Map.Entry<String,String> entry:lineIndexMap.entrySet()){
                    String parentKey = currentLevelNum+"Y-";
                    if(entry.getKey().indexOf(parentKey)>-1){
                        String value = entry.getValue();
                        int lastNum = Integer.valueOf(value.split("\\.")[value.split("\\.").length-1]);
                        int charAtNum = value.lastIndexOf(".");
                        String preIndexDoNotContainLastNum = value.substring(0,charAtNum);
                        lastNum+=10;
                        currentIndex = preIndexDoNotContainLastNum+"."+lastNum;
                        lineIndexMap.put(currentKey,currentIndex);
                        break;
                    }
                }
            }

        }


        List<EbomTestDTO> testDTOS = new ArrayList<>();
        for(int i = 0;i<list.size();i++){//找父 找lineIndex 最为关键
            EbomTestDTO t = new EbomTestDTO();
            EbomTestDTO testDTO = list.get(i);
            String level = testDTO.getLevel();
           int high = testDTO.getHigh();
           int orderNum = high*100+100;
           String parentId = "";
           String currentKey = testDTO.getLevel()+"-"+high;
           String currentLineIndex = "";
            //找父id
            if(high!=0){
                int m = Integer.valueOf(level.replace("Y",""))-1;
                String parentKey = m+"Y-";
                for(Map.Entry<String,String> entry:mapList.entrySet()){
                    if(entry.getKey().indexOf(parentKey)>-1){
                        parentId = entry.getValue();
                        break;
                    }
                }

                //找lineIndex
                for(String key :lineIndexMap.keySet()){
                    if(key.equals(currentKey)){
                        currentLineIndex = lineIndexMap.get(currentKey);
                        break;
                    }
                }
            }else {
                currentLineIndex = "10.10";
            }
            t.setLineIndex(currentLineIndex);
            t.setParentId(parentId);
            t.setPuid(UUID.randomUUID().toString());
            t.setOrderNum(orderNum);
            testDTOS.add(t);
        }
        System.out.println(JSON.toJSONString(testDTOS));

    }













    public static ArrayList generateEbomTree(){
        ArrayList<EbomTestDTO> reqDTOS = new ArrayList<>();
        EbomTestDTO reqDTO = new EbomTestDTO();
        reqDTO.setLevel("2Y");
        reqDTO.setHigh(0);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("3");
        reqDTO.setHigh(1);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("3");
        reqDTO.setHigh(2);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("3");
        reqDTO.setHigh(3);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("3Y");
        reqDTO.setHigh(4);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("4");
        reqDTO.setHigh(5);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("4");
        reqDTO.setHigh(6);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("4");
        reqDTO.setHigh(7);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("4");
        reqDTO.setHigh(8);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("4Y");
        reqDTO.setHigh(9);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("5");
        reqDTO.setHigh(10);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("5");
        reqDTO.setHigh(11);
        reqDTOS.add(reqDTO);
        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("5Y");
        reqDTO.setHigh(12);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("6Y");
        reqDTO.setHigh(13);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("7");
        reqDTO.setHigh(14);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("7");
        reqDTO.setHigh(15);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("7");
        reqDTO.setHigh(16);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("5");
        reqDTO.setHigh(17);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("5");
        reqDTO.setHigh(18);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("5");
        reqDTO.setHigh(19);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("4");
        reqDTO.setHigh(20);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("4");
        reqDTO.setHigh(21);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("4Y");
        reqDTO.setHigh(22);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("5");
        reqDTO.setHigh(23);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("5");
        reqDTO.setHigh(24);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("3");
        reqDTO.setHigh(25);
        reqDTOS.add(reqDTO);

        reqDTO = new EbomTestDTO();
        reqDTO.setLevel("3");
        reqDTO.setHigh(26);
        reqDTOS.add(reqDTO);
        return reqDTOS;
    }
}
