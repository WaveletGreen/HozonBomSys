package sql.pojo.cfg;

/**
 * 所有外键都进行了级联删除
 */
public class HzColorModel {
    /**
     * 主键
     */
    private String puid;
    /**
     * 模型外键
     */
    private String modelUid;
    /**
     * 配置值外键
     */
    private String cfgUid;
    /**
     * 颜色外键
     */
    private String colorUid;
    /**
     * 项目外键
     */
    private String cfgMainUid;
    /**
     * 色集
     */
    private String pColorOfSet;
    /**
     * 颜色名称
     */
    private String pColorName;
    /**
     * 颜色代号
     */
    private String pColorCode;
    /**
     * 色板
     */
    private String pColorPlate;
    /**
     * 是否多色
     */
    private String pColorIsMultiply;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getModelUid() {
        return modelUid;
    }

    public void setModelUid(String modelUid) {
        this.modelUid = modelUid;
    }

    public String getCfgUid() {
        return cfgUid;
    }

    public void setCfgUid(String cfgUid) {
        this.cfgUid = cfgUid;
    }

    public String getColorUid() {
        return colorUid;
    }

    public void setColorUid(String colorUid) {
        this.colorUid = colorUid;
    }

    public String getCfgMainUid() {
        return cfgMainUid;
    }

    public void setCfgMainUid(String cfgMainUid) {
        this.cfgMainUid = cfgMainUid;
    }

    public String getpColorOfSet() {
        return pColorOfSet;
    }

    public void setpColorOfSet(String pColorOfSet) {
        this.pColorOfSet = pColorOfSet;
    }

    public String getpColorName() {
        return pColorName;
    }

    public void setpColorName(String pColorName) {
        this.pColorName = pColorName;
    }

    public String getpColorCode() {
        return pColorCode;
    }

    public void setpColorCode(String pColorCode) {
        this.pColorCode = pColorCode;
    }

    public String getpColorPlate() {
        return pColorPlate;
    }

    public void setpColorPlate(String pColorPlate) {
        this.pColorPlate = pColorPlate;
    }

    public String getpColorIsMultiply() {
        return pColorIsMultiply;
    }

    public void setpColorIsMultiply(String pColorIsMultiply) {
        this.pColorIsMultiply = pColorIsMultiply;
    }
}