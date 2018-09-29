package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/6/27
 * @Description:
 */
public class HzMbomByPageQuery extends DefaultPageQuery {
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 层级
     */
    private String level;

    /**
     * 零件号
     */
    private String lineId;

    /**
     * 专业
     */
    private String pBomOfWhichDept;

    private Integer isHas;

    private String lineIndex;

    private  String cfg0ModelRecordId;

    /**
     * 备件
     */
    private String sparePart;
    /**
     * 零件来源
     */
    private String pBomLinePartResource;

    /**
     * 零件分类
     */
    private String pBomLinePartClass;

    /**
     * 类型 1白车身生产BOM  6 白车身财务BOM
     */
    private Integer type;

    /**
     * 数据库表名
     */
    private String tableName;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public String getSparePart() {
        return sparePart;
    }

    public void setSparePart(String sparePart) {
        this.sparePart = sparePart;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
    }

    public Integer getIsHas() {
        return isHas;
    }

    public void setIsHas(Integer isHas) {
        this.isHas = isHas;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getCfg0ModelRecordId() {
        return cfg0ModelRecordId;
    }

    public void setCfg0ModelRecordId(String cfg0ModelRecordId) {
        this.cfg0ModelRecordId = cfg0ModelRecordId;
    }
}
