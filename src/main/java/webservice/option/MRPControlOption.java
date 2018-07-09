package webservice.option;

public enum MRPControlOption {

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
    private MRPControlOption(String desc) {
        this.desc = desc;
    }

    public String GetDesc() {
        return desc;
    }
}
