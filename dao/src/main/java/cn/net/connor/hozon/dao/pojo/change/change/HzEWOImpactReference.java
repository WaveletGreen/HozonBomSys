package cn.net.connor.hozon.dao.pojo.change.change;

/**
 * @Author: haozt
 * @Date: 2018/8/14
 * @Description: EWO表单 影响分析 引用关系
 */
public class HzEWOImpactReference {
    /**
     * 主键
     */
    private Long id;

    /**
     * 影响分析id
     */
    private Long impactAnalysisId;

    /**
     * ewo号
     */
    private String ewoNo;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 是否勾选
     */
    private Integer checked;

    /**
     * 影响分析内容
     */
    private String content;

    /**
     * 类型 A B
     * A 工程师自查清单
     * B 涉及软件/程序/数据 变更信息
     */
    private String type;

    /**
     * 前端页面勾选的影响分析 ids 进行保存或者更改
     * 这些没进行DTO和DO之间的转化
     */
    private String impactAnalysisIds;

    public String getImpactAnalysisIds() {
        return impactAnalysisIds;
    }

    public void setImpactAnalysisIds(String impactAnalysisIds) {
        this.impactAnalysisIds = impactAnalysisIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImpactAnalysisId() {
        return impactAnalysisId;
    }

    public void setImpactAnalysisId(Long impactAnalysisId) {
        this.impactAnalysisId = impactAnalysisId;
    }

    public String getEwoNo() {
        return ewoNo;
    }

    public void setEwoNo(String ewoNo) {
        this.ewoNo = ewoNo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
