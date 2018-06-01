package sql.pojo.project;

public class HzProjectLibs {
    private String puid;

    private String pProjectName;

    private String pProjectString;

    private String pProjectObjectType;

    private String pProjectItemId;
    /**
     * 归属平台
     */
    private String pProjectPertainToPlatform;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpProjectName() {
        return pProjectName;
    }

    public void setpProjectName(String pProjectName) {
        this.pProjectName = pProjectName;
    }

    public String getpProjectString() {
        return pProjectString;
    }

    public void setpProjectString(String pProjectString) {
        this.pProjectString = pProjectString;
    }

    public String getpProjectObjectType() {
        return pProjectObjectType;
    }

    public void setpProjectObjectType(String pProjectObjectType) {
        this.pProjectObjectType = pProjectObjectType;
    }

    public String getpProjectItemId() {
        return pProjectItemId;
    }

    public void setpProjectItemId(String pProjectItemId) {
        this.pProjectItemId = pProjectItemId == null ? null : pProjectItemId.trim();
    }

    public String getpProjectPertainToPlatform() {
        return pProjectPertainToPlatform;
    }

    public void setpProjectPertainToPlatform(String pProjectPertainToPlatform) {
        this.pProjectPertainToPlatform = pProjectPertainToPlatform;
    }
}