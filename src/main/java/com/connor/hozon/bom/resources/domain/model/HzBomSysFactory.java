package com.connor.hozon.bom.resources.domain.model;

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
}
