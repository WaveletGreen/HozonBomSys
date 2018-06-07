package sql.pojo.project;

import java.util.Date;

public class HzPlatformRecord {
    private String puid;

    private String pPertainToBrandPuid;

    private String pPlatformCode;

    private String pPlatformName;

    private Date pPlatformCreateDate;

    private Date pPlatformLastModDate;

    private String pPlatformComment;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpPertainToBrandPuid() {
        return pPertainToBrandPuid;
    }

    public void setpPertainToBrandPuid(String pPertainToBrandPuid) {
        this.pPertainToBrandPuid = pPertainToBrandPuid == null ? null : pPertainToBrandPuid.trim();
    }

    public String getpPlatformCode() {
        return pPlatformCode;
    }

    public void setpPlatformCode(String pPlatformCode) {
        this.pPlatformCode = pPlatformCode;
    }

    public String getpPlatformName() {
        return pPlatformName;
    }

    public void setpPlatformName(String pPlatformName) {
        this.pPlatformName = pPlatformName;
    }

    public Date getpPlatformCreateDate() {
        return pPlatformCreateDate;
    }

    public void setpPlatformCreateDate(Date pPlatformCreateDate) {
        this.pPlatformCreateDate = pPlatformCreateDate;
    }

    public Date getpPlatformLastModDate() {
        return pPlatformLastModDate;
    }

    public void setpPlatformLastModDate(Date pPlatformLastModDate) {
        this.pPlatformLastModDate = pPlatformLastModDate;
    }

    public String getpPlatformComment() {
        return pPlatformComment;
    }

    public void setpPlatformComment(String pPlatformComment) {
        this.pPlatformComment = pPlatformComment;
    }
}