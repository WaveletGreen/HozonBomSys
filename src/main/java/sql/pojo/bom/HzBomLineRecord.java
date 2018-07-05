package sql.pojo.bom;

public class HzBomLineRecord {
    /**
     * puid
     */
    private String puid;
    /**
     * 父层puid
     */
    private String parentUid;
    /**
     * 是否部门层
     */
    private Integer isDept;
    /**
     * 数模层PUID
     */
    private String bomDigifaxId;
    /**
     * Bom行的层级索引
     */
    private String lineIndex;
    /**
     * 记录TC的BOMLine的Puid，用于快速session.stringToTCObject
     */
    private String linePuid;
    /**
     * Bom行对应的零件号
     */
    private String lineID;
    /**
     * 是否有子层
     */
    private Integer isHas;
    /**
     * 属性集合，是一个LinkedHashMap，需要转换
     */
    private byte[] bomLineBlock;
    /**
     * 是否2Y层
     */
    private Integer is2Y;
    /**
     * 是否零件
     */
    private Integer isPart;
    /**
     * 在Bom结构中的顺序号
     */
    private Integer orderNum;
    /**
     * 2Y层归属哪个部门
     */
    private String pBomOfWhichDept;
    /**
     * 项目的puid，只是方便根据项目查找数据，并不存在数据库中，做项目映射应该使用bomDigifaxId字段对应数模层，再对应项目
     */
    private String projectPuid;
    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;
    /**
     * Bom行对应的零件类别
     */
    private String pBomLinePartClass;

    private String pBomLinePartEnName;

    private String pBomLinePartResource;

    public String getpBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setpBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid == null ? null : parentUid.trim();
    }

    public String getBomDigifaxId() {
        return bomDigifaxId;
    }

    public void setBomDigifaxId(String bomDigifaxId) {
        this.bomDigifaxId = bomDigifaxId == null ? null : bomDigifaxId.trim();
    }

    public byte[] getBomLineBlock() {
        return bomLineBlock;
    }

    public void setBomLineBlock(byte[] bomLineBlock) {
        this.bomLineBlock = bomLineBlock;
    }

    public String getIndex() {
        return lineIndex;
    }

    public void setIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public Integer getIsHas() {
        return isHas;
    }

    public void setIsHas(Integer isHas) {
        this.isHas = isHas;
    }

    public void setHasChildren(boolean isHas) {
        this.setIsHas(isHas ? 1 : 0);
    }

    public String getLinePuid() {
        return linePuid;
    }

    public void setLinePuid(String linePuid) {
        this.linePuid = linePuid;
    }

    public String getLineID() {
        return lineID;
    }

    public void setLineID(String lineID) {
        this.lineID = lineID;
    }

    public Integer getIs2Y() {
        return is2Y;
    }

    public void setIs2Y(Integer is2y) {
        is2Y = is2y;
    }

    public Integer getIsPart() {
        return isPart;
    }

    public void setIsPart(Integer isPart) {
        this.isPart = isPart;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getProjectPuid() {
        return projectPuid;
    }

    public void setProjectPuid(String projectPuid) {
        this.projectPuid = projectPuid;
    }

    public Integer getIsDept() {
        return isDept;
    }

    public void setIsDept(Integer isDept) {
        this.isDept = isDept;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }
}