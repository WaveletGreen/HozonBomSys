/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.sys;


import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import cn.net.connor.hozon.dao.query.sys.QueryUserAssociateRole;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.dao.pojo.sys.UserAssociateRole;
import org.springframework.stereotype.Repository;

/**
 *@author linzf
 **/
@Repository
public interface UserAssociateRoleDao extends GenericDao<UserAssociateRole, QueryUserAssociateRole> {

    /**
     * 功能描述：根据用户的ID来删除用户的权限数据
     * @param user
     * @return
     */
    int removeUserRole(User user);
}