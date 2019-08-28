package cn.net.connor.hozon.dao.pojo.change.change;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description: 影响部门关联人员信息
 */
public class HzEWOImpactDeptEmp {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 影响部门
     */
    private Long impactDeptId;

    /**
     * 人员信息
     */
    private Long userId;
    /**
     * 有效性标志
     */
    private Integer validFlag;

    /**
     * ewo流程编号
     */
    private String ewoNo;

    /**
     * 项目信息
     */
    private String projectId;

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

    public Integer getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImpactDeptId() {
        return impactDeptId;
    }

    public void setImpactDeptId(Long impactDeptId) {
        this.impactDeptId = impactDeptId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
