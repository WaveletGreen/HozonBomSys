/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.query.sys;


import cn.net.connor.hozon.common.entity.QueryBase;
import lombok.Data;

/**
 *@author linzf
 **/
@Data
public class QueryOrgGroup extends QueryBase {
	private Long existingNum;
	private String groupCode;
	private String name;
	private String node;
	private Long num;
	private String parentNode;
	private String groupId;

}
