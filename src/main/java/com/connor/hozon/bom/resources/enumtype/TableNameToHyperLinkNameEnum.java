package com.connor.hozon.bom.resources.enumtype;

/**
 * @Author: haozt
 * @Date: 2018/11/13
 * @Description: 根据数据库表名转换超链接名称
 */
public enum TableNameToHyperLinkNameEnum {

    /**
     * EBOM
     */
    HZ_EBOM("HZ_EBOM_REOCRD_AFTER_CHANGE","EBOM设变数据详情"),//EBOM

    ;

    private String tableName;

    private String linkName;


    TableNameToHyperLinkNameEnum(String tableName,String linkName){
        this.tableName = tableName;
        this.linkName = linkName;
    }
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }



    /**
     * 获取超链接名
     * @param tableName 数据库表名
     * @return
     */
    public static String getHyperLinkName(String tableName) {
        for(TableNameToHyperLinkNameEnum t: TableNameToHyperLinkNameEnum.values()) {
            if(t.getTableName().equals(tableName)) {
                return t.getLinkName();
            }
        }
        return null;
    }

    /**
     * 获取表名
     * @param linkName 超链接名
     * @return
     */
    public static String getTableName(String linkName) {
        for(TableNameToHyperLinkNameEnum t: TableNameToHyperLinkNameEnum.values()) {
            if(t.getLinkName().equals(linkName)) {
                return t.getTableName();
            }
        }
        return null;
    }
}
