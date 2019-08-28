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
public class QueryUserRole extends QueryBase {
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 角色名
	 */
	private String roleName;
}
