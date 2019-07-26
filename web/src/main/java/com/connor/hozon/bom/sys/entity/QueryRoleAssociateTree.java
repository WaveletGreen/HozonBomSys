package com.connor.hozon.bom.sys.entity;


import com.connor.hozon.bom.common.base.entity.QueryBase;

/**
 *@author linzf
 **/
public class QueryRoleAssociateTree extends QueryBase {
	private Long roleId;
	private Long treeId;

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
