package sql.pojo.project;

import java.util.Date;

public class HzBrandRecord {
    private String puid;

    private String pBrandCode;

    private String pBrandName;

    private Date pBrandCreateDate;

    private Date pBrandLastModDate;

    private String pBrandComment;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpBrandCode() {
        return pBrandCode;
    }

    public void setpBrandCode(String pBrandCode) {
        this.pBrandCode = pBrandCode;
    }

    public String getpBrandName() {
        return pBrandName;
    }

    public void setpBrandName(String pBrandName) {
        this.pBrandName = pBrandName;
    }

    public Date getpBrandCreateDate() {
        return pBrandCreateDate;
    }

    public void setpBrandCreateDate(Date pBrandCreateDate) {
        this.pBrandCreateDate = pBrandCreateDate;
    }

    public Date getpBrandLastModDate() {
        return pBrandLastModDate;
    }

    public void setpBrandLastModDate(Date pBrandLastModDate) {
        this.pBrandLastModDate = pBrandLastModDate;
    }

    public String getpBrandComment() {
        return pBrandComment;
    }

    public void setpBrandComment(String pBrandComment) {
        this.pBrandComment = pBrandComment;
    }
}