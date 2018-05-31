package sql.pojo.project;

public class HzProjectLibs {
    private String puid;

    private Object pProjectName;

    private Object pProjectString;

    private Object pProjectObjectType;

    private String pProjectItemId;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public Object getpProjectName() {
        return pProjectName;
    }

    public void setpProjectName(Object pProjectName) {
        this.pProjectName = pProjectName;
    }

    public Object getpProjectString() {
        return pProjectString;
    }

    public void setpProjectString(Object pProjectString) {
        this.pProjectString = pProjectString;
    }

    public Object getpProjectObjectType() {
        return pProjectObjectType;
    }

    public void setpProjectObjectType(Object pProjectObjectType) {
        this.pProjectObjectType = pProjectObjectType;
    }

    public String getpProjectItemId() {
        return pProjectItemId;
    }

    public void setpProjectItemId(String pProjectItemId) {
        this.pProjectItemId = pProjectItemId == null ? null : pProjectItemId.trim();
    }
}