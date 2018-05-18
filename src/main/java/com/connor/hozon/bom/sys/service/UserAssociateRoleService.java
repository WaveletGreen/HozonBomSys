package com.connor.hozon.bom.sys.service;


import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.common.base.service.GenericService;
import com.connor.hozon.bom.sys.dao.UserAssociateRoleDao;

import com.connor.hozon.bom.sys.entity.QueryUserAssociateRole;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.entity.UserAssociateRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *@author linzf
 **/
@Service("userAssociateRoleService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class UserAssociateRoleService extends GenericService<UserAssociateRole, QueryUserAssociateRole> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private UserAssociateRoleDao userAssociateRoleDao;
	@Override
	protected GenericDao<UserAssociateRole, QueryUserAssociateRole> getDao() {
		return userAssociateRoleDao;
	}

	/**
	 * 功能描述：根据用户的ID来删除用户的权限数据
	 * @param user
	 * @return
	 */
	public boolean removeUserRole(User user){
		return userAssociateRoleDao.removeUserRole(user)>0;
	}
}