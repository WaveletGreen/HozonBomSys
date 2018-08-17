package com.connor.hozon.bom.sys.dao;


import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.QueryOrgGroup;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linzf
 **/
@Service("OrgGroupDao")
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
}