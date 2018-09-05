package integration.option;

public enum CorrelateTypeOption {
    ///////////////////////////相关性类型/////////////////////////
    /**
     * 相关性类型:发布可执行
     */
    CorrelateType_1("1"),
    /**
     * 相关性类型：锁定不执行，可标识为删除状态
     */
    CorrelateType_3("3");
    /***
     * 描述
     */
    private String desc;

    /**
     * 构造方法
     *
     * @param desc 枚举描述
     */
    private CorrelateTypeOption(String desc) {
        this.desc = desc;
    }

    public String GetDesc() {
        return desc;
    }
}
