/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.sys;

import lombok.Data;

/**
 *@author linzf
 **/
@Data
public class UserAssociateRole {
	private int userId;
	private long roleId;

	public UserAssociateRole(){
		super();
	}

	public UserAssociateRole(int userId,long roleId){
		this.userId = userId;
		this.roleId = roleId;
	}

}
