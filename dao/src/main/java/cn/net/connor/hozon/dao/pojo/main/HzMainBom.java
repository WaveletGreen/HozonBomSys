/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.main;

import lombok.Data;

import java.util.Date;

@Data
public class HzMainBom {
	/**
	 * 主键
	 */
	private String puid;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 数模层名称
	 */
	private String digifaxName;
	/**
	 * 从TC来的数模层UID
	 */
	private String digifaxUidFromTC;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 项目ID
	 */
	private String projectId;
	/**
	 * 最后一次更新时间
	 */
	private Date lastModifyDate;
	/**
	 * 最后一次更新人
	 */
	private String updater;

}