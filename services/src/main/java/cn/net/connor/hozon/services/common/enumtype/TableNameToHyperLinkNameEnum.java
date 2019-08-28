package cn.net.connor.hozon.services.common.enumtype;

/**
 * @Author: haozt
 * @Date: 2018/11/13
 * @Description: 根据数据库表名转换超链接名称
 */
public enum TableNameToHyperLinkNameEnum {
    HZ_EBOM("HZ_EBOM_REOCRD_AFTER_CHANGE","EBOM管理"),//EBOM
    HZ_PBOM("HZ_PBOM_REOCRD_AFTER_CHANGE","PBOM管理"),
    HZ_MBOM("HZ_MBOM_REOCRD_AFTER_CHANGE","超级MBOM"),
    HZ_MBOM_PRODUCT("HZ_MBOM_PRODUCT_AFTER_CHANGE","白车身生产"),
    HZ_MBOM_FINANCE("HZ_MBOM_FINANCE_AFTER_CHANGE","白车身财务"),
    HZ_MATERIEL("HZ_MATERIEL_AFTER_CHANGE","物料数据"),
    HZ_WORK_PROCEDURE("HZ_WORK_PROCEDURE_AFTER","工艺路线"),
    //特性变更表
    HZ_CFG0_AFTER_CHANGE_RECORD("HZ_CFG0_AFTER_CHANGE_RECORD","特性变更"),
    //配色方案变更表
    HZ_CMCR_AFTER_CHANGE("HZ_CMCR_AFTER_CHANGE","配色方案"),
    //衍生物料变更表
    HZ_DM_BASIC_CHANGE("HZ_DM_BASIC_CHANGE","配置物料"),
    //全配置变更表
    HZ_FULL_CFG_MAIN_RECORD_CHANGE("HZ_FULL_CFG_MAIN_RECORD_CHANGE","全BOM配置"),

    //相关性变更表单
    HZ_RELEVANCE_BASIC_CHANGE("HZ_RELEVANCE_BASIC_CHANGE","相关性"),
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
