package sql.pojo.change;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:全部的影响部门
 */
public class HzEWOAllImpactDept {
    /**
     * id
     */
    private Long id;

    /**
     * 部门名称
     */
    private String deptName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
