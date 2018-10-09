package sql.pojo.cfg;

public class HzCfg0OptionFamily {
    /**
     * 主键
     */
    private String puid;
    /**
     * 主配置外键，可从项目UID获取到
     */
    private String pOfCfg0Main;
    /**
     * 特性代码
     */
    private String pOptionfamilyName;
    /**
     * 特性描述
     */
    private String pOptionfamilyDesc;
    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpOfCfg0Main() {
        return pOfCfg0Main;
    }

    public void setpOfCfg0Main(String pOfCfg0Main) {
        this.pOfCfg0Main = pOfCfg0Main == null ? null : pOfCfg0Main.trim();
    }

    public String getpOptionfamilyName() {
        return pOptionfamilyName;
    }

    public void setpOptionfamilyName(String pOptionfamilyName) {
        this.pOptionfamilyName = pOptionfamilyName == null ? null : pOptionfamilyName.trim();
    }

    public String getpOptionfamilyDesc() {
        return pOptionfamilyDesc;
    }

    public void setpOptionfamilyDesc(String pOptionfamilyDesc) {
        this.pOptionfamilyDesc = pOptionfamilyDesc;
    }
}