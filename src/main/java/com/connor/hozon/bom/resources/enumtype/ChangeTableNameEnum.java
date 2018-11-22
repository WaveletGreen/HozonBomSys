package com.connor.hozon.bom.resources.enumtype;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.method.P;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:设变相关的数据库表名
 */
public enum  ChangeTableNameEnum {

    HZ_EBOM_AFTER("HZ_EBOM_REOCRD_AFTER_CHANGE"),
    HZ_EBOM("HZ_BOM_LINE_RECORD"),
    HZ_EBOM_BEFORE("HZ_EBOM_REOCRD_BEFORE_CHANGE"),

    HZ_PBOM("HZ_PBOM_RECORD"),
    HZ_PBOM_AFTER("HZ_PBOM_REOCRD_AFTER_CHANGE"),
    HZ_PBOM_BEFORE("HZ_PBOM_REOCRD_BEFORE_CHANGE"),

    HZ_MBOM_AFTER("HZ_MBOM_REOCRD_AFTER_CHANGE"),
    HZ_MBOM("HZ_MBOM_RECORD"),
    HZ_MBOM_BEFORE("HZ_MBOM_REOCRD_BEFORE_CHANGE"),

    HZ_MBOM_FINANCE("HZ_MBOM_OF_FINANCE"),
    HZ_MBOM_FINANCE_AFTER("HZ_MBOM_FINANCE_AFTER_CHANGE"),
    HZ_MBOM_FINANCE_BRFORE("HZ_MBOM_FINANCE_BEFORE_CHANGE"),

    HZ_MBOM_PRODUCT("HZ_MBOM_OF_PRODUCT"),
    HZ_MBOM_PRODUCT_AFTER("HZ_MBOM_PRODUCT_AFTER_CHANGE"),
    HZ_MBOM_PRODUCT_BEFORE("HZ_MBOM_PRODUCT_BEFORE_CHANGE"),

    //特性变更表
    HZ_CFG0_AFTER_CHANGE_RECORD("HZ_CFG0_AFTER_CHANGE_RECORD"),

    //配色方案变更表
    HZ_CMCR_AFTER_CHANGE("HZ_CMCR_AFTER_CHANGE"),

    //衍生物料变更表
    HZ_DM_BASIC_CHANGE("HZ_DM_BASIC_CHANGE"),

    //全配置变更表
    HZ_FULL_CFG_MAIN_RECORD_CHANGE("HZ_FULL_CFG_MAIN_RECORD_CHANGE"),


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

    /**
     * 获取MBOM变更相关表名
     * @param type
     * @param str M MA MB
     * @return
     */
    public static String getMbomTableName(Integer type,String str){

        if(null == type){
            if("M".equals(str)){
                return ChangeTableNameEnum.HZ_MBOM.tableName;
            }else if("MA".equals(str)){
                return ChangeTableNameEnum.HZ_MBOM_AFTER.tableName;
            }else if("MB".equals(str)){
                return ChangeTableNameEnum.HZ_MBOM_BEFORE.tableName;
            }else {
                return null;
            }
        }

        if("M".equals(str)){
            switch (type){
                case 1:return ChangeTableNameEnum.HZ_MBOM_PRODUCT.tableName;
                case 6:return ChangeTableNameEnum.HZ_MBOM_FINANCE.tableName;
                default:return ChangeTableNameEnum.HZ_MBOM.tableName;
            }
        }else if("MA".equals(str)){
            switch (type){
                case 1:return ChangeTableNameEnum.HZ_MBOM_PRODUCT_AFTER.tableName;
                case 6:return ChangeTableNameEnum.HZ_MBOM_FINANCE_AFTER.tableName;
                default:return ChangeTableNameEnum.HZ_MBOM_AFTER.tableName;
            }
        }else if("MB".equals(str)){
            switch (type){
                case 1:return ChangeTableNameEnum.HZ_MBOM_PRODUCT_BEFORE.tableName;
                case 6:return ChangeTableNameEnum.HZ_MBOM_FINANCE_BRFORE.tableName;
                default:return ChangeTableNameEnum.HZ_MBOM_BEFORE.tableName;
            }
        }else {
            return null;
        }
    }
}
