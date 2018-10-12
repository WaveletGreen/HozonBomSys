package sql.pojo.cfg.modelColor;

import java.util.Date;

/**
 * 二级配色方案
 */
public class HzColorLvl2Model {
    /**
     * 主键
     */
    private String puid;
    /**
     * 颜色车型外键
     */
    private String pModelUid;
    /**
     * 功能层外键，功能层上一层是2Y层，只针对颜色车身
     */
    private String pLvlFunction;
    /**
     * 颜色代码外键
     */
    private String pColorUid;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date modifyDate;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 修改者
     */
    private String modifier;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpModelUid() {
        return pModelUid;
    }

    public void setpModelUid(String pModelUid) {
        this.pModelUid = pModelUid == null ? null : pModelUid.trim();
    }

    public String getpLvlFunction() {
        return pLvlFunction;
    }

    public void setpLvlFunction(String pLvlFunction) {
        this.pLvlFunction = pLvlFunction == null ? null : pLvlFunction.trim();
    }

    public String getpColorUid() {
        return pColorUid;
    }

    public void setpColorUid(String pColorUid) {
        this.pColorUid = pColorUid == null ? null : pColorUid.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}