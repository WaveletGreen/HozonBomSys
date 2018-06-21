package sql.pojo.cfg;

import java.util.Date;

public class HzFcfgBomLvl1ListOperation {
    /**
     * 主键
     */
    private String puid;
    /**
     * 操作类型
     */
    private Object pOprationTypeName;
    /**
     * 操作人
     */
    private Object pOprator;
    /**
     * 操作日期
     */
    private Date pOprationDate;
    /**
     * 操作信息的状态
     */
    private Short pOprationStatus;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public Object getpOprationTypeName() {
        return pOprationTypeName;
    }

    public void setpOprationTypeName(Object pOprationTypeName) {
        this.pOprationTypeName = pOprationTypeName;
    }

    public Object getpOprator() {
        return pOprator;
    }

    public void setpOprator(Object pOprator) {
        this.pOprator = pOprator;
    }

    public Date getpOprationDate() {
        return pOprationDate;
    }

    public void setpOprationDate(Date pOprationDate) {
        this.pOprationDate = pOprationDate;
    }

    public Short getpOprationStatus() {
        return pOprationStatus;
    }

    public void setpOprationStatus(Short pOprationStatus) {
        this.pOprationStatus = pOprationStatus;
    }
}