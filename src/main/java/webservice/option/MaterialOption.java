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
    MATERIAL_ACCESSORIES("A004"),
    ///////////////////////////采购类型/////////////////////////
    /**
     * 采购类型:自制件，对应TC系统：make
     */

    PURCHASE_MAKE("E"),
    /**
     * 采购件，对应TC系统：buy
     */
    PURCHASE_BUY("F"),
    /**
     * 特殊采购件
     */
    PURCHASE_SPECIAL("50"),
    /**
     * 不是采购件，留空
     */
    PURCHASE_EMPTY(""),

    ///////////////////////////MRP控制者////////////////////////////
    /**
     * 整车
     */
    MRP_VEHICLE("Z02"),
    /**
     * 虚拟件
     */
    MRP_FICTITIOUS("Z04"),
    /**
     * 总成/分总成
     */
    MRP_ASSEMBLY("Z06"),
    /**
     * 冲压
     */
    MRP_STAMPING("Z07"),
    /**
     * 自制备件
     */
    MRP_SELF_MADE_SPARE_PARTS("Z08"),
    /**
     * 采购件
     */
    MRP_PURCHASE("Z10");


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
