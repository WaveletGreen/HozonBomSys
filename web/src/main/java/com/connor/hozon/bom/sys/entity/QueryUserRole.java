package com.connor.hozon.bom.sys.entity;


import cn.net.connor.hozon.common.entity.QueryBase;

/**
 *@author linzf
 **/
public class QueryUserRole extends QueryBase {
	private String name;
	private String roleName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
