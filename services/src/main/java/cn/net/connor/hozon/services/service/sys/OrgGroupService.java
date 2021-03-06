/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.sys;


import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import cn.net.connor.hozon.dao.dao.sys.OrgGroupDao;
import cn.net.connor.hozon.dao.pojo.sys.OrgGroup;
import cn.net.connor.hozon.dao.query.sys.QueryOrgGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author linzf
 **/
@Service("orgGroupService")
@Transactional(rollbackFor = { IllegalArgumentException.class })
public class OrgGroupService extends GenericService<OrgGroup, QueryOrgGroup> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private OrgGroupDao orgGroupDao;

	@Override
	protected GenericDao<OrgGroup, QueryOrgGroup> getDao() {
		return orgGroupDao;
	}

	/**
	 * 功能描述：根据父节点来查询最大的节点的值
	 * 
	 * @param parentNode
	 * @return
	 */
	public String getMaxOrgGroup(String parentNode) {
		return orgGroupDao.getMaxOrgGroup(parentNode);
	}

	/**
	 * 功能描述：根据菜单节点NODE来查询节点数据
	 * 
	 * @param node
	 * @return
	 */
	public OrgGroup findByNode(String node) {
		return orgGroupDao.findByNode(node);
	}

	/**
	 * 查询部门下面的人员数量
	 * @param id
	 * @return
	 */
	public Integer findOrgGroupUserCount(Long id){
		return orgGroupDao.queryOrgGroupUserCount(id);
	}

	/**
	 * 查询部门名称是否已存在
	 * @param deptName
	 * @return
	 */
	public boolean deptNameIsRepeat(String deptName){
		return orgGroupDao.queryOrgGroupNameExist(deptName)>0;
	}
}