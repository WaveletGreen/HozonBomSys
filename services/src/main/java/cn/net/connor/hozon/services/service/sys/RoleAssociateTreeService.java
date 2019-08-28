/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.sys;

import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import cn.net.connor.hozon.dao.dao.sys.RoleAssociateTreeDao;
import cn.net.connor.hozon.dao.pojo.sys.RoleAssociateTree;
import cn.net.connor.hozon.dao.query.sys.QueryRoleAssociateTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author linzf
 **/
@Service("roleAssociateTreeService")
@Transactional(rollbackFor = { IllegalArgumentException.class })
public class RoleAssociateTreeService extends GenericService<RoleAssociateTree, QueryRoleAssociateTree> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private RoleAssociateTreeDao roleAssociateTreeDao;

	@Override
	protected GenericDao<RoleAssociateTree, QueryRoleAssociateTree> getDao() {
		return roleAssociateTreeDao;
	}
}