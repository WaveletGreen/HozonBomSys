package com.connor.hozon.bom.resources.domain.query;

/**
 * @Author: haozt
 * @Date: 2018/6/27
 * @Description:
 */
public class HzEPLByPageQuery extends DefaultPageQuery {
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

    /**
     * 数据库存放的层级关系
     */
    private String lineIndex;
    /**
     * 是否有子层
     */
    private Integer isHas;

    private Integer pFastener;

    public Integer getpFastener() {
        return pFastener;
    }

    public void setpFastener(Integer pFastener) {
        this.pFastener = pFastener;
    }

    public Integer getIsHas() {
        return isHas;
    }

    public void setIsHas(Integer isHas) {
        this.isHas = isHas;
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

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }
}
