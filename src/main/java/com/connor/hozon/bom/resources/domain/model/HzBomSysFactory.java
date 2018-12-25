package com.connor.hozon.bom.resources.domain.model;


import com.connor.hozon.bom.resources.util.ListUtil;
import org.apache.commons.lang.StringUtils;

import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: haozt
 * @Date: 2018/9/6
 * @Description:
 */
public class HzBomSysFactory {
    /*最小步进，用于产生随机数**/
    private static final  int MIN_STEP = 2;

    /**
     * 获取bom系统的层级和级别 和查找编号
     * @param lineIndex
     * @param is2Y
     * @param hasChildren
     * @return String[0]层级  String[1]级别  String[3]级查找编号
     */
    public static String[] getLevelAndRank(String lineIndex, Integer is2Y, Integer hasChildren) {
        int level = (lineIndex.split("\\.")).length;
        String line = "";
        int rank = 0;
        if (null != is2Y && is2Y.equals(1)) {
            line = "2Y";
            rank = 1;
        } else if (null != is2Y && is2Y.equals(0)) {
            if (hasChildren != null && hasChildren.equals(1)) {
                line = level + "Y";
                rank = level - 1;
            } else if (hasChildren != null && hasChildren.equals(0)) {
                line = String.valueOf((level-1));
                rank = level - 1 ;
            } else {
                line = "";//错误数据
            }
        } else {
            line = "";
        }
        int length = lineIndex.split("\\.").length-1;
        int s1 = Integer.valueOf(lineIndex.split("\\.")[length]);
        return new String[]{line, String.valueOf(rank),String.format("%04d",s1)};
    }


    /**
     * 产生一个介于两个数字之间的一个数
     * @param s1 较小数
     * @param s2 较大数
     * @return
     */
    public static String generateBomSortNum(String s1,String s2){
        try {

            if(StringUtils.isBlank(s1) || StringUtils.isBlank(s2)){
                return null;
            }

            Double d1 = Double.parseDouble(s1);
            Double d2 = Double.parseDouble(s2);

            if(d1 >= d2){
                return null;
            }

            double diff = d2 -d1;
            int step = (int)diff;
            if(diff<= MIN_STEP){
                return resultNum(d1,d2,0);
            } else {
                return resultNum(d1,d2,step >> 1);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static String resultLineId(String lineId){
        String result = lineId; //S00-1001  S00-1001BA  S00-1001111BA
        if(lineId.contains("-")){
            String s = lineId.substring(lineId.length()-2,lineId.length());
            Pattern p = Pattern.compile("[a-zA-Z]");
            Matcher matcher = p.matcher(s);
            if(!matcher.find()){
                result = lineId +"AA";
            }

//            if(!matcher.find()){
//                HzPbomRecordDAO hzPbomRecordDAO = new HzPbomRecordDAOImpl();
//                List<HzPbomLineRecord> nameList = hzPbomRecordDAO.getSameNameLineId(lineId,projectId);
//                if(ListUtil.isNotEmpty(nameList) && nameList.size()>1){
//                   for(HzPbomLineRecord record:nameList){
//                       String s1 = record.getLineId().substring(record.getLineId().length()-2,record.getLineId().length());
//                       Matcher matcher1 = p.matcher(s1);
//                       if(matcher1.find() && record.getLineId().length()==length+2){
//                           result = result+"AA";
//                           break;
//                       }
//                   }
//                }
//            }
        }
        return result;
    }

    /**
     * 当list.size比较大时，对list进行分割
     * 整理为每个list 大小为1000
     * 以map的形式返回
     * @param list
     * @return
     */
    public static<T> Map<Integer,List<T>> spiltList(List<T> list){
        Map<Integer,List<T>> map = new HashMap<>();
        int size = list.size();
        int mapIndex =1;
        int i=0;
        while (size>1000){
            List<T> l = new ArrayList<>();
            for(;i<mapIndex*1000;i++){
                l.add(list.get(i));
                size--;
            }
            map.put(mapIndex,l);
            mapIndex++;
        }
        List<T> l = new ArrayList<>();
        for(;i<list.size();i++){
            l.add(list.get(i));
        }
        map.put(mapIndex,l);
        return map;
    }

    /**
     * 产生以步进为基础的 介于两个数中间的某个数
     * @param d1 较小数
     * @param d2 较大数
     * @param step 步进
     * @return
     */
    private static String resultNum(Double d1,Double d2,int step){
        String result ="";
        while (true){
            Random random = new Random();
            Double d = random.nextDouble() + d1 + step;
            if(d <d2 ){
                result  = String.valueOf(d);
                break;
            }
        }
        return result;
    }

}
