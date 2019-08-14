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
public class QueryTree extends QueryBase {
	private String code;
	private String icon;
	private String name;
	private Long pId;
	private Long treeOrder;
	private String url;
	private String state;
}
