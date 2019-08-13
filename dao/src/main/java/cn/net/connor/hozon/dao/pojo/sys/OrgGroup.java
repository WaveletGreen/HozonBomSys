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
public class OrgGroup {
	private long groupId;
	private long existingNum;
	private String groupCode;
	private String name;
	private String node;
	private long num;
	private String parentNode;
	// 父部门信息
	private OrgGroup orgGroup;
}
