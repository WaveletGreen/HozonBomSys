package webservice.option;

/***
 * 物料类型
 */
public enum MaterialOption {
    ////////////////////////////物料类型//////////////////////////
    /**
     * 可配置物料（超级物料，所有车型数据的集合）
     */
    MATERIAL_CONFIGURABLE("A006"),
    /***
     * 配置后整车（整车成品，单车主数据）
     */
    MATERIAL_CONFIGURED_VEHICLE("A001"),
    /**
     * 材料（暂无）
     */
    MATERIAL_ORG("A002"),
    /***
     * 备件及工具（暂无）
     */
    MATERIAL_SPARE_PART_AND_TOOLS("A003"),
    /**
     * 辅料
     */
    MATERIAL_ACCESSORIES("A004");

    /***
     * 描述
     */
    private String desc;

    /**
     * 构造方法
     *
     * @param desc 枚举描述
     */
    private MaterialOption(String desc) {
        this.desc = desc;
    }

    public String GetDesc() {
        return desc;
    }
}
