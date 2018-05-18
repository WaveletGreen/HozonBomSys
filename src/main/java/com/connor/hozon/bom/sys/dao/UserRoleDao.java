package com.connor.hozon.bom.sys.dao;


import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.sys.entity.QueryUserRole;
import com.connor.hozon.bom.sys.entity.UserRole;

/**
 *@author linzf
 **/
public interface UserRoleDao extends GenericDao<UserRole, QueryUserRole> {

    /**
     * 功能描述：获取权限菜单数据
     * @param entity
     * @return
     */
    UserRole getUserRoleAssociate(UserRole entity);
	
}