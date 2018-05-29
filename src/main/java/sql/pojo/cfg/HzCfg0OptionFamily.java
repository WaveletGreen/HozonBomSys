package sql.pojo.cfg;

public class HzCfg0OptionFamily {
    private String puid;

    private String pOfCfg0Main;

    private String pOptionfamilyName;

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
}