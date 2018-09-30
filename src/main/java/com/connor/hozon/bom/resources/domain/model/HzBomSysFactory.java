package com.connor.hozon.bom.resources.domain.model;


import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzEbomRecordDAOImpl;
import com.connor.hozon.bom.resources.mybatis.bom.impl.HzPbomRecordDAOImpl;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: haozt
 * @Date: 2018/9/6
 * @Description:
 */
public class HzBomSysFactory {
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
    public static String generateBomSortNum(String projectId,String s1,String s2){
        Double d1 = Double.parseDouble(s1);
        Double d2 = Double.parseDouble(s2);
        if(d1>=d2){
            return null;
        }
        Random random = new Random();
        String s = "";
        while (true){
            double d = random.nextDouble()+d1;
            if(d<d2){
                s = String.valueOf(d);
                break;
            }
        }
        return s;
    }

    public static String resultLineId(String lineId,String projectId){
        String result = lineId; //S00-1001  S00-1001BA  S00-1001111BA
        int length = lineId.length();
        if(lineId.contains("-")){
            String s = lineId.substring(lineId.length()-2,lineId.length());
            Pattern p = Pattern.compile("[a-zA-Z]");
            Matcher matcher = p.matcher(s);
            if(!matcher.find()){
                HzPbomRecordDAO hzPbomRecordDAO = new HzPbomRecordDAOImpl();
                List<HzPbomLineRecord> nameList = hzPbomRecordDAO.getSameNameLineId(lineId,projectId);
                if(ListUtil.isNotEmpty(nameList) && nameList.size()>1){
                   for(HzPbomLineRecord record:nameList){
                       String s1 = record.getLineId().substring(record.getLineId().length()-2,record.getLineId().length());
                       Matcher matcher1 = p.matcher(s1);
                       if(matcher1.find() && record.getLineId().length()==length+2){
                           result = result+"AA";
                           break;
                       }
                   }
                }
            }
        }
        return result;
    }

}
