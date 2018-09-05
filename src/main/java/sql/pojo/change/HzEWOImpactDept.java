package sql.pojo.change;

import java.util.Objects;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
public class HzEWOImpactDept {
    private Long id;

    private Long deptId;

    private String ewoNo;

    private String projectId;

    private Integer checkFlag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HzEWOImpactDept that = (HzEWOImpactDept) o;
        return Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, deptId, ewoNo, projectId, checkFlag);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
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

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }
}
