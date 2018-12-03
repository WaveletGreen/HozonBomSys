package integration.option;

/**
 * 与SAP交互使用的选项，只能是"A"、"U"、"D"传输代码值，其他不生效
 */
public enum ActionFlagOption {
    /**
     * 添加
     */
    ADD("A"),
    /***
     * 删除
     */
    DELETE("D"),
    /***
     * 更新
     */
    UPDATE("U"),
    /**
     * 物料主数据，修改时不用传ACTIONID
     */
    UPDATE_EMPTY("");


    // 成员变量
    private String desc;

    // 构造方法
    private ActionFlagOption(String desc) {
        this.desc = desc;
    }

    /**
     * 获取描述
     */
    public String GetDesc() {
        return desc;
    }
}
