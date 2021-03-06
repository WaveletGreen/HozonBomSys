package com.connor.hozon.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/6/27
 * @Description:
 */
public class HzPbomByPageQuery extends DefaultPageQuery {
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
    /**
     * 零件分类
     */
    private String pBomLinePartClass;
    /**
     * 零件来源
     */
    private String pBomLinePartResource;
    /**
     * ebom 表 puid
     * 根据此字段来获取BOM结构树
     */
    private String eBomPuids;
    /**
     * 是否展示层级（1展示层级结构  0不展示层级结构）
     */
    private Integer showBomStructure;
    /**
     * 是否为新产生件，(2工艺合件产生,1工艺辅料产生,0否）指工艺合件和工艺辅料产生的件
     */
    private String pIsNewPart;

    public String getpIsNewPart() {
        return pIsNewPart;
    }

    public void setpIsNewPart(String pIsNewPart) {
        this.pIsNewPart = pIsNewPart;
    }

    public Integer getShowBomStructure() {
        return showBomStructure;
    }

    public void setShowBomStructure(Integer showBomStructure) {
        this.showBomStructure = showBomStructure;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
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

    public String geteBomPuids() {
        return eBomPuids;
    }

    public void seteBomPuids(String eBomPuids) {
        this.eBomPuids = eBomPuids;
    }
}
