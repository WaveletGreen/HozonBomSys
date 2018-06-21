package sql.pojo.cfg;

public class HzCfg0BomLineOfModel {

    private String objectName;
    /**
     * 车型模型描述
     */
    private String objectDesc;
    /**
     * 选项值
     */
    private String pCfg0OptionValue;
    /**
     * 本地解析变量，用于打点图，与=1，或=0，非=-1，用于解析是否是
     */
    private Short pParseLogicValue;
    /**
     * 配置项的值
     */
    private String pCfg0ObjectId;
    /**
     * 配置项描述
     */
    private String pCfg0Desc;
    /**
     * 族值
     */
    private String pCfg0FamilyName;
    /**
     * 特性值，已废除，用pCfg0ObjectId代替
     */
    @Deprecated
    private String pH9featurename;
    /**
     * 特性英文名稱
     */
    private String pH9featureenname;
    /**
     * 特性描述，已废除，用pCfg0Desc代替
     */
    @Deprecated
    private String pH9featuredesc;
    /**
     * 对应的bom行名
     */
    private String pBomLineName;
    /**
     * 对应的bom行id
     */
    private String pBomLineId;
    /**
     * bom行归属的部门
     */
    private String pBomOfWhichDept;
    /**
     * 归属的的模型
     */
    private String modelPuid;

    public String getObjectName() {
        return objectName;
    }

    public static final String[] selfDesc =
            {
                    "pBomOfWhichDept", "pBomLineId", "pBomLineName", "pH9featureenname", "pCfg0Desc", "pCfg0ObjectId", "comment"
            };

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectDesc() {
        return objectDesc;
    }

    public void setObjectDesc(String objectDesc) {
        this.objectDesc = objectDesc;
    }

    public String getpCfg0OptionValue() {
        return pCfg0OptionValue;
    }

    public void setpCfg0OptionValue(String pCfg0OptionValue) {
        this.pCfg0OptionValue = pCfg0OptionValue == null ? null : pCfg0OptionValue.trim();
    }

    public Short getpParseLogicValue() {
        return pParseLogicValue;
    }

    public void setpParseLogicValue(Short pParseLogicValue) {
        this.pParseLogicValue = pParseLogicValue;
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

    public String getpBomLineName() {
        return pBomLineName;
    }

    public void setpBomLineName(String pBomLineName) {
        this.pBomLineName = pBomLineName == null ? null : pBomLineName.trim();
    }

    public String getpBomLineId() {
        return pBomLineId;
    }

    public void setpBomLineId(String pBomLineId) {
        this.pBomLineId = pBomLineId;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getModelPuid() {
        return modelPuid;
    }

    public void setModelPuid(String modelPuid) {
        this.modelPuid = modelPuid;
    }
}