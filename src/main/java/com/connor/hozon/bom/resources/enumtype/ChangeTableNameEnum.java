package com.connor.hozon.bom.resources.enumtype;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:设变相关的数据库表名
 */
public enum  ChangeTableNameEnum {

    HZ_EBOM("HZ_EBOM_REOCRD_AFTER_CHANGE"),
    ;

    /**
     * 数据库表名
     */
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    ChangeTableNameEnum(String tableName){
        this.tableName = tableName;
    }
}