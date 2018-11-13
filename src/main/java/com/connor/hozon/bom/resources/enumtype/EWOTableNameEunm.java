package com.connor.hozon.bom.resources.enumtype;

/**
 * @Author: haozt
 * @Date: 2018/10/25
 * @Description: EWO设变相关表名
 */
public enum EWOTableNameEunm {
    HZ_EBOM_DEFAULT("HZ_BOM_LINE_RECORD"),//默认EBOM表
    HZ_EBOM_BEFORE("HZ_EBOM_REOCRD_BEFORE_CHANGE"),//EBOM变更前历史记录
    HZ_EBOM_AFTER("HZ_EBOM_REOCRD_AFTER_CHANGE")//EBOM变更后历史记录
    ;
    EWOTableNameEunm(String tableName) {
        this.tableName = tableName;
    }


    private String tableName;

    public String getTableName() {
        return tableName;
    }


    /**
     * 默认表名
     * @return
     */
    public static String defaultTableName(){
        return EWOTableNameEunm.HZ_EBOM_DEFAULT.getTableName();
    }

    public static String tableName(String  type){
        if("A".equals(type)){
            return EWOTableNameEunm.HZ_EBOM_AFTER.getTableName();
        }else if("B".equals(type)){
            return EWOTableNameEunm.HZ_EBOM_BEFORE.getTableName();
        }else{
            return defaultTableName();
        }
    }
}
