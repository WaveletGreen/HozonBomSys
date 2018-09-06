package com.connor.hozon.bom.resources.domain.dto.response;

/**
 * @Author: haozt
 * @Date: 2018/8/21
 * @Description:
 */
public class HzEWOImpactDeptRespDTO {
    private Long id;

    private String deptName;

    private Long deptId;

    private Integer checked;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
