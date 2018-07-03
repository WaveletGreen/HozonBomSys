package webservice.option;

public enum BomOption {
    /***BOM类型：生产**/
    BOM_TYPE_PRODUCTION("1"),
    /**
     * BOM类型：财务
     */
    BOM_TYPE_FINANCE("6");
    /***
     * 描述
     */
    private String desc;

    /**
     * 构造方法
     *
     * @param desc 枚举描述
     */
    private BomOption(String desc) {
        this.desc = desc;
    }

    public String GetDesc() {
        return desc;
    }
}
