package sql.pojo.cfg;

public class HzCfg0ColorSet {
    private String puid;

    private String pColorOfSet;

    private String pColorName;

    private String pColorCode;

    private String pColorComment;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
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

    public String getpColorComment() {
        return pColorComment;
    }

    public void setpColorComment(String pColorComment) {
        this.pColorComment = pColorComment;
    }
}