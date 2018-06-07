package sql.pojo.cfg;

public class HzCfg0Record {
    private String puid;

    private String pCfg0ObjectId;

    private String pCfg0Desc;

    private String pCfg0FamilyName;

    private String pCfg0FamilyPuid;

    private String pCfg0MainItemPuid;

    private String pH9featurename;

    private String pH9featureenname;

    private String pH9featuredesc;

    private String pCfg0FamilyDesc;

    public String getPuid() {
        return puid;
    }
    private Integer length=10;

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpCfg0ObjectId() {
        return pCfg0ObjectId;
    }

    public void setpCfg0ObjectId(String pCfg0ObjectId) {
        this.pCfg0ObjectId = pCfg0ObjectId == null ? null : pCfg0ObjectId.trim();
    }

    public String getpCfg0Desc() {
        return pCfg0Desc;
    }

    public void setpCfg0Desc(String pCfg0Desc) {
        this.pCfg0Desc = pCfg0Desc == null ? null : pCfg0Desc.trim();
    }

    public String getpCfg0FamilyName() {
        return pCfg0FamilyName;
    }

    public void setpCfg0FamilyName(String pCfg0FamilyName) {
        this.pCfg0FamilyName = pCfg0FamilyName == null ? null : pCfg0FamilyName.trim();
    }

    public String getpCfg0FamilyPuid() {
        return pCfg0FamilyPuid;
    }

    public void setpCfg0FamilyPuid(String pCfg0FamilyPuid) {
        this.pCfg0FamilyPuid = pCfg0FamilyPuid == null ? null : pCfg0FamilyPuid.trim();
    }

    public String getpCfg0MainItemPuid() {
        return pCfg0MainItemPuid;
    }

    public void setpCfg0MainItemPuid(String pCfg0MainItemPuid) {
        this.pCfg0MainItemPuid = pCfg0MainItemPuid == null ? null : pCfg0MainItemPuid.trim();
    }

    public String getpH9featurename() {
        return pH9featurename;
    }

    public void setpH9featurename(String pH9featurename) {
        this.pH9featurename = pH9featurename == null ? null : pH9featurename.trim();
    }

    public String getpH9featureenname() {
        return pH9featureenname;
    }

    public void setpH9featureenname(String pH9featureenname) {
        this.pH9featureenname = pH9featureenname == null ? null : pH9featureenname.trim();
    }

    public String getpH9featuredesc() {
        return pH9featuredesc;
    }

    public void setpH9featuredesc(String pH9featuredesc) {
        this.pH9featuredesc = pH9featuredesc == null ? null : pH9featuredesc.trim();
    }

    public String getpCfg0FamilyDesc() {
        return pCfg0FamilyDesc;
    }

    public void setpCfg0FamilyDesc(String pCfg0FamilyDesc) {
        this.pCfg0FamilyDesc = pCfg0FamilyDesc == null ? null : pCfg0FamilyDesc.trim();
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}