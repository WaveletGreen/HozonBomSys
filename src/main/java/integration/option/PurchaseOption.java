package integration.option;

public enum PurchaseOption {

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
    /**
     * 只有总成和分总成才有X
     */
    PURCHASE_X("X");
    /***
     * 描述
     */
    private String desc;

    /**
     * 构造方法
     *
     * @param desc 枚举描述
     */
    private PurchaseOption(String desc) {
        this.desc = desc;
    }

    public String GetDesc() {
        return desc;
    }
}
