package sql.pojo.cfg;

import java.util.Date;

public class HzCfg0ColorSet {
    /**
     * 主键
     */
    private String puid;

    /**
     * 颜色集
     */
    private String pColorOfSet;

    /**
     * 颜色名称
     */
    private String pColorName;
    /**
     * 颜色代码
     */
    private String pColorCode;
    /**
     * 备注
     */
    private String pColorComment;
    /**
     * 是否多色
     */
    private String pColorIsMultiply;
    /**
     * 色板
     */
    private String pColorPlate;
    /**
     * 色板
     */
    private String pColorModifier;

    /**
     * 创建时间
     */
    private Date pColorCreateDate;
    /**
     * 生效时间
     */
    private Date pColorEffectedDate;
    /**
     * 废止时间
     */
    private Date pColorAbolishDate;
    /**
     * 生效时间
     */
    private String strColorEffectedDate;
    /**
     * 废止时间
     */
    private String strColorAbolishDate;
    /**
     * 修改时间
     */
    private Date pColorModifyDate;
    /**
     * 可用状态
     */
    private Integer pColorStatus;

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
        return pColorComment == null ? "" : pColorComment;
    }

    public void setpColorComment(String pColorComment) {
        this.pColorComment = pColorComment;
    }


    public String getpColorIsMultiply() {
        return pColorIsMultiply;
    }

    public void setpColorIsMultiply(String pColorIsMultiply) {
        this.pColorIsMultiply = pColorIsMultiply;
    }

    public String getpColorPlate() {
        return pColorPlate;
    }

    public void setpColorPlate(String pColorPlate) {
        this.pColorPlate = pColorPlate;
    }

    public String getpColorModifier() {
        return pColorModifier;
    }

    public void setpColorModifier(String pColorModifier) {
        this.pColorModifier = pColorModifier;
    }

    public Date getpColorCreateDate() {
        return pColorCreateDate;
    }

    public void setpColorCreateDate(Date pColorCreateDate) {
        this.pColorCreateDate = pColorCreateDate;
    }

    public Date getpColorEffectedDate() {
        return pColorEffectedDate;
    }

    public void setpColorEffectedDate(Date pColorEffectedDate) {
        this.pColorEffectedDate = pColorEffectedDate;
    }

    public Date getpColorAbolishDate() {
        return pColorAbolishDate;
    }

    public void setpColorAbolishDate(Date pColorAbolishDate) {
        this.pColorAbolishDate = pColorAbolishDate;
    }

    public Date getpColorModifyDate() {
        return pColorModifyDate;
    }

    public void setpColorModifyDate(Date pColorModifyDate) {
        this.pColorModifyDate = pColorModifyDate;
    }

    public String getStrColorEffectedDate() {
        return strColorEffectedDate;
    }

    public void setStrColorEffectedDate(String strColorEffectedDate) {
        this.strColorEffectedDate = strColorEffectedDate;
    }

    public String getStrColorAbolishDate() {
        return strColorAbolishDate;
    }

    public void setStrColorAbolishDate(String strColorAbolishDate) {
        this.strColorAbolishDate = strColorAbolishDate;
    }

    public Integer getpColorStatus() {
        return pColorStatus;
    }

    public void setpColorStatus(Integer pColorStatus) {
        this.pColorStatus = pColorStatus;
    }
}