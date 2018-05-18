package share.bean;

/***
 * bom行对应的配置项的bean
 *
 * @author Administrator
 *
 */
public class Cfg0OfBomline {
    /**
     * 组族
     */
    private String cfg0FamilyName;
    /**
     * bom行id
     */
    private String bomlinePuid;
    /**
     * 配置的puid
     */
    private String cfg0Puid;
    /**
     * 配置名
     */
    private String cfg0Name;
    /**
     * bomline名
     */
    private String bomlineName;
    /*** 数模层puid*/
    private String bomDigifaxId;
    /**
     * 映射的puid
     */
    private String toCfg0Puid;

    public String getBomlinePuid() {
        return bomlinePuid;
    }

    public void setBomlinePuid(String bomlinePuid) {
        this.bomlinePuid = bomlinePuid;
    }

    public String getCfg0Puid() {
        return cfg0Puid;
    }

    public void setCfg0Puid(String cfg0Puid) {
        this.cfg0Puid = cfg0Puid;
    }

    public String getCfg0Name() {
        return cfg0Name;
    }

    public void setCfg0Name(String cfg0Name) {
        this.cfg0Name = cfg0Name;
    }

    public String getBomlineName() {
        return bomlineName;
    }

    public void setBomlineName(String bomlineName) {
        this.bomlineName = bomlineName;
    }

    public String getCfg0FamilyName() {
        return cfg0FamilyName;
    }

    public void setCfg0FamilyName(String cfg0FamilyName) {
        this.cfg0FamilyName = cfg0FamilyName;
    }

    public String getBomDigifaxId() {
        return bomDigifaxId;
    }

    public void setBomDigifaxId(String bomDigifaxId) {
        this.bomDigifaxId = bomDigifaxId;
    }

    public String getToCfg0Puid() {
        return toCfg0Puid;
    }

    public void setToCfg0Puid(String toCfg0Puid) {
        this.toCfg0Puid = toCfg0Puid;
    }

}
