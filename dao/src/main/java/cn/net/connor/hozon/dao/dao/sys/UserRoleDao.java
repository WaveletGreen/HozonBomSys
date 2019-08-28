/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.sys;


import cn.net.connor.hozon.dao.pojo.sys.UserRole;
import cn.net.connor.hozon.dao.query.sys.QueryUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author linzf
 **/
@Repository
public interface UserRoleDao extends GenericDao<UserRole, QueryUserRole> {

    /**
     * 功能描述：获取权限菜单数据
     * @param entity
     * @return
     */
    UserRole getUserRoleAssociate(UserRole entity);

    /**
     * 查询当前角色权限的引用关系数量
     * @param id 角色id
     * @return
     */
    Integer getUserRoleReferenceCount(Long id);

    UserRole getUserWriteRoleAssociate(UserRole userRole);

    List<UserRole> findUserRoleListByIds(List<Long> ids);


    List<UserRole> findUserRoleAndUserByRoleName(String roleName);
}