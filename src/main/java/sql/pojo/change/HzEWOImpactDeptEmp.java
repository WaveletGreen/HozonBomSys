package sql.pojo.change;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
public class HzEWOImpactDeptEmp {
    private Long id;

    private Long impactDeptId;

    private Long userId;

    private Integer validFlag;

    private String ewoNo;

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
