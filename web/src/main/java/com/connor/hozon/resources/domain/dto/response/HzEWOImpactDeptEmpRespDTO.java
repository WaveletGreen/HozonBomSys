package com.connor.hozon.resources.domain.dto.response;

import cn.net.connor.hozon.common.entity.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/8/21
 * @Description:
 */
public class HzEWOImpactDeptEmpRespDTO extends BaseDTO {
    private Integer checked;

    private Long deptId;

    private String deptName;

    private Long userId;

    private String userName;

    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
