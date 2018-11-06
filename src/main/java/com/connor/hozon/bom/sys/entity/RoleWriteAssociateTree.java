package com.connor.hozon.bom.sys.entity;

/**
 * @Author: haozt
 * @Date: 2018/11/5
 * @Description:
 */
public class RoleWriteAssociateTree {

    private Long roleId;
    private Long treeId;

    public RoleWriteAssociateTree() {
    }

    public RoleWriteAssociateTree(Long roleId, Long treeId) {
        this.roleId = roleId;
        this.treeId = treeId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }
}
