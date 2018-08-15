package sql.pojo.cfg;

public class HzCfg0ModelGroup {
    private String id;

    private String tcUid;

    private Object groupDesc;

    private Object groupName;

    private String mainUid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTcUid() {
        return tcUid;
    }

    public void setTcUid(String tcUid) {
        this.tcUid = tcUid == null ? null : tcUid.trim();
    }

    public Object getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(Object groupDesc) {
        this.groupDesc = groupDesc;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    public String getMainUid() {
        return mainUid;
    }

    public void setMainUid(String mainUid) {
        this.mainUid = mainUid == null ? null : mainUid.trim();
    }
}