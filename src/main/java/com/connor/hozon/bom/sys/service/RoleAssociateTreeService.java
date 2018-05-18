package com.connor.hozon.bom.sys.service;

import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.common.base.service.GenericService;
import com.connor.hozon.bom.sys.dao.RoleAssociateTreeDao;

import com.connor.hozon.bom.sys.entity.QueryRoleAssociateTree;
import com.connor.hozon.bom.sys.entity.RoleAssociateTree;
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