package com.connor.hozon.bom.resources.enumtype;

/**
 * @Author: haozt
 * @Date: 2018/9/21
 * @Description: MBOM相关表名
 */
public enum MbomTableNameEnum {
    HZ_MBOM_OF_FINANCE("HZ_MBOM_OF_FINANCE"),//白车身财务
    HZ_MBOM_OF_PRODUCT("HZ_MBOM_OF_PRODUCT"),//白车身生产
    HZ_MBOM_RECORD("HZ_MBOM_RECORD")//超级MBOM
    ;
    MbomTableNameEnum(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 零件来源信息
     */
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public static String tableName(Integer  type){
        if(Integer.valueOf(1).equals(type)){
            return MbomTableNameEnum.HZ_MBOM_OF_PRODUCT.getTableName();
        }else if(Integer.valueOf(6).equals(type)){
            return MbomTableNameEnum.HZ_MBOM_OF_FINANCE.getTableName();
        }else{
            return MbomTableNameEnum.HZ_MBOM_RECORD.getTableName();
        }
    }
}
