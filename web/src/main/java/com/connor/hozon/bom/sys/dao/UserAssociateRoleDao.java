package com.connor.hozon.bom.sys.dao;


import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.sys.entity.QueryUserAssociateRole;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.entity.UserAssociateRole;
import org.springframework.stereotype.Service;

/**
 *@author linzf
 **/
@Service("userAssociateRoleDao")
public interface UserAssociateRoleDao extends GenericDao<UserAssociateRole, QueryUserAssociateRole> {

    /**
     * 功能描述：根据用户的ID来删除用户的权限数据
     * @param user
     * @return
     */
    int removeUserRole(User user);
}