/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.sys;


import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import cn.net.connor.hozon.dao.pojo.sys.OrgGroup;
import cn.net.connor.hozon.dao.query.sys.QueryOrgGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author linzf
 **/
@Repository
public interface OrgGroupDao extends GenericDao<OrgGroup, QueryOrgGroup> {

	/**
	 * 功能描述：根据父节点来查询最大的节点的值
	 * 
	 * @param parentNode
	 * @return
	 */
	String getMaxOrgGroup(String parentNode);

	/**
	 * 功能描述：根据菜单节点NODE来查询节点数据
	 * 
	 * @param node
	 * @return
	 */
	OrgGroup findByNode(String node);

	/**
	 * 获取部门信息
	 * @param groupId
	 * @return
	 */
	OrgGroup queryOrgGroupById(Long groupId);

	List<OrgGroup> queryAllOrgGroup();

	/**
	 * 查询部门下面的人员数量
	 * @param groupId
	 * @return
	 */
	Integer queryOrgGroupUserCount(Long groupId);

	Integer queryOrgGroupNameExist(String deptName);
}