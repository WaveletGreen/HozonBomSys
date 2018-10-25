package com.connor.hozon.bom.resources.enumtype;

/**
 * @Author: haozt
 * @Date: 2018/10/25
 * @Description: MWO设变相关表名
 */
public enum  MWOTableNameEnum {
    /**
     * PBOM
     */
    HZ_PBOM_DEFAULT("HZ_PBOM_RECORD","PD"),//默认PBOM表
    HZ_PBOM_BEFORE("HZ_PBOM_REOCRD_BEFORE_CHANGE","PB"),//PBOM变更前历史记录
    HZ_PBOM_AFTER("HZ_PBOM_REOCRD_AFTER_CHANGE","PA"),//PBOM变更后历史记录

    /**
     * MBOM
     */
    HZ_MBOM_DEFAULT("HZ_MBOM_RECORD","MD"),//默认MBOM表
    HZ_MBOM_BEFORE("HZ_MBOM_REOCRD_BEFORE_CHANGE","MB"),//MBOM变更前历史记录
    HZ_MBOM_AFTER("HZ_MBOM_REOCRD_AFTER_CHANGE","MA"),//MBOM变更后历史记录

    /**
     * MBOM生产
     */
    HZ_MBOM_PRODUCT_DEFAULT("HZ_MBOM_OF_PRODUCT","MPD"),//默认生产型BOM表
    HZ_MBOM_PRODUCT_BEFORE("HZ_MBOM_OF_PRODUCT_BEFORE_CHANGE","MPB"),//生产型BOM变更前历史记录
    HZ_MBOM_PRODUCT_AFTER("HZ_MBOM_OF_PRODUCT_AFTER_CHANGE","MPA"),//生产型BOM变更后历史记录

    /**
     * MBOM财务
     */
    HZ_MBOM_FINANCE_DEFAULT("HZ_MBOM_OF_FINANCE","MFD"),//默认财务型BOM表
    HZ_MBOM_FINANCE_BEFORE("HZ_MBOM_OF_FINANCE_BEFORE_CHANGE","MFB"),//财务型BOM变更前历史记录
    HZ_MBOM_FINANCE_AFTER("HZ_MBOM_OF_FINANCE_AFTER_CHANGE","MFA")//财务型BOM变更后历史记录

    ;



    MWOTableNameEnum(String tableName,String tableCode) {
        this.tableName = tableName;
        this.tableCode = tableCode;
    }


    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 数据库表名代码
     */
    private String tableCode;


    public String getTableName() {
        return tableName;
    }

    public String getTableCode() {
        return tableCode;
    }

    /**
     * MWO表名
     * @param code MWO设变表 代码
     * @return
     */
    public static String getMWOTableNameEnum(String code) {
        for(MWOTableNameEnum t: MWOTableNameEnum.values()) {
            if(t.getTableCode().equals(code)) {
                return t.getTableName();
            }
        }
        return null;
    }
}
