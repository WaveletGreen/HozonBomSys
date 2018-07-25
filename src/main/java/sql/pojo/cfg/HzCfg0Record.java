package sql.pojo.cfg;

import java.util.Date;

public class HzCfg0Record {
    /**
     * PUId，主键
     */
    private String puid;
    /**
     * 配置值
     */
    private String pCfg0ObjectId;
    /**
     * 配置值的描述
     */
    private String pCfg0Desc;
    /**
     * 组值，也是组的ID
     */
    private String pCfg0FamilyName;
    /**
     * 对应数据库的puid
     */
    private String pCfg0FamilyPuid;
    /**
     * 产品配置器的数据库puid
     */
    private String pCfg0MainItemPuid;
    /**
     * 需要被废弃
     */
    @Deprecated
    private String pH9featurename;
    /**
     * 特性英文名称
     */
    private String pH9featureenname;
    /**
     * 需要被废弃
     */
    @Deprecated
    private String pH9featuredesc;
    /**
     * 组的描述
     */
    private String pCfg0FamilyDesc;
    /**
     * 类中特有的，对应是新加进来的特性值还是原有的那张值表，目前有2个值：HZ_CFG0_RECORD和HZ_CFG0_ADD_CFG_RECORD
     */
    private String whichTable;
    /**
     * 归属项目，类中特有的
     */
    private String projectPuid;
    /**
     * 相关性代码，目前手输入
     */
    private String pCfg0Relevance;
    /**
     * 特性是否已发送到ERP
     */
    private Integer isFeatureSent;
    /**
     * 相关性是否已发送到ERP
     */
    private Integer isRelevanceSent;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最后一次修改时间
     */
    private Date lastModifyDate;

    private String creator;
    private String lastModifier;

    public String getPuid() {
        return puid;
    }

    private Integer length = 10;

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpCfg0ObjectId() {
        return pCfg0ObjectId;
    }

    public void setpCfg0ObjectId(String pCfg0ObjectId) {
        this.pCfg0ObjectId = pCfg0ObjectId == null ? null : pCfg0ObjectId.trim();
    }

    public String getpCfg0Desc() {
        return pCfg0Desc;
    }

    public void setpCfg0Desc(String pCfg0Desc) {
        this.pCfg0Desc = pCfg0Desc == null ? null : pCfg0Desc.trim();
    }

    public String getpCfg0FamilyName() {
        return pCfg0FamilyName;
    }

    public void setpCfg0FamilyName(String pCfg0FamilyName) {
        this.pCfg0FamilyName = pCfg0FamilyName == null ? null : pCfg0FamilyName.trim();
    }

    public String getpCfg0FamilyPuid() {
        return pCfg0FamilyPuid;
    }

    public void setpCfg0FamilyPuid(String pCfg0FamilyPuid) {
        this.pCfg0FamilyPuid = pCfg0FamilyPuid == null ? null : pCfg0FamilyPuid.trim();
    }

    public String getpCfg0MainItemPuid() {
        return pCfg0MainItemPuid;
    }

    public void setpCfg0MainItemPuid(String pCfg0MainItemPuid) {
        this.pCfg0MainItemPuid = pCfg0MainItemPuid == null ? null : pCfg0MainItemPuid.trim();
    }

    public String getpH9featurename() {
        return pH9featurename;
    }

    public void setpH9featurename(String pH9featurename) {
        this.pH9featurename = pH9featurename == null ? null : pH9featurename.trim();
    }

    public String getpH9featureenname() {
        return pH9featureenname;
    }

    public void setpH9featureenname(String pH9featureenname) {
        this.pH9featureenname = pH9featureenname == null ? null : pH9featureenname.trim();
    }

    public String getpH9featuredesc() {
        return pH9featuredesc;
    }

    public void setpH9featuredesc(String pH9featuredesc) {
        this.pH9featuredesc = pH9featuredesc == null ? null : pH9featuredesc.trim();
    }

    public String getpCfg0FamilyDesc() {
        return pCfg0FamilyDesc;
    }

    public void setpCfg0FamilyDesc(String pCfg0FamilyDesc) {
        this.pCfg0FamilyDesc = pCfg0FamilyDesc == null ? null : pCfg0FamilyDesc.trim();
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getWhichTable() {
        return whichTable;
    }

    public void setWhichTable(String whichTable) {
        this.whichTable = whichTable;
    }

    public String getProjectPuid() {
        return projectPuid;
    }

    public void setProjectPuid(String projectPuid) {
        this.projectPuid = projectPuid;
    }

    public String getpCfg0Relevance() {
        return pCfg0Relevance;
    }

    public void setpCfg0Relevance(String pCfg0Relevance) {
        this.pCfg0Relevance = pCfg0Relevance;
    }

    public Integer getIsFeatureSent() {
        return isFeatureSent;
    }

    public void setIsFeatureSent(Integer isFeatureSent) {
        this.isFeatureSent = isFeatureSent;
    }

    public Integer getIsRelevanceSent() {
        return isRelevanceSent;
    }

    public void setIsRelevanceSent(Integer isRelevanceSent) {
        this.isRelevanceSent = isRelevanceSent;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

}