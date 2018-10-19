package sql.pojo.cfg.color;

import lombok.Data;

import java.util.Date;

@Data
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
     * 修改者
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
     * 可用状态,1生效，0草稿状态，-1废止状态
     */
    private Integer pColorStatus;

    /**
     * 是否删除,0删除，1现存
     */
    private Integer pColorIsDeleted;

    /**
     * 映射到数据库字段
     * @param property
     * @return
     */
    public String reflectToDBField(String property) {
        switch (property) {
            /**
             * 主键
             */
            case "puid":
                return "PUID";
            /**
             * 颜色集
             */
            case "pColorOfSet":
                return "p_Color_Of_Set";
            /**
             * 颜色名称
             */
            case "pColorName":
                return "p_Color_Name";
            /**
             * 颜色代码
             */
            case "pColorCode":
                return "p_Color_Code";
            /**
             * 备注
             */
            case "pColorComment":
                return "p_Color_Comment";
            /**
             * 是否多色
             */
            case "pColorIsMultiply":
                return "p_Color_Is_Multiply";
            /**
             * 色板
             */
            case "pColorPlate":
                return "p_Color_Plate";
            /**
             * 修改者
             */
            case "pColorModifier":
                return "p_Color_Modifier";
            /**
             * 创建时间
             */
            case "pColorCreateDate":
                return "p_Color_Create_Date";
            /**
             * 废止时间
             */
            case "pColorAbolishDate":
                return "p_Color_Abolish_Date";
            /**
             * 生效时间
             */
            case "pColorEffectedDate":
                return "p_Color_Effected_Date";
            default:
                return null;
        }
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
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

    public Date getpColorModifyDate() {
        return pColorModifyDate;
    }

    public void setpColorModifyDate(Date pColorModifyDate) {
        this.pColorModifyDate = pColorModifyDate;
    }

    public Integer getpColorStatus() {
        return pColorStatus;
    }

    public void setpColorStatus(Integer pColorStatus) {
        this.pColorStatus = pColorStatus;
    }

    public Integer getpColorIsDeleted() {
        return pColorIsDeleted;
    }

    public void setpColorIsDeleted(Integer pColorIsDeleted) {
        this.pColorIsDeleted = pColorIsDeleted;
    }
}