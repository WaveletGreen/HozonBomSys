/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.sys;

import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import cn.net.connor.hozon.dao.query.sys.QueryRoleAssociateTree;
import cn.net.connor.hozon.dao.pojo.sys.RoleWriteAssociateTree;
import cn.net.connor.hozon.dao.pojo.sys.UserRole;
import org.springframework.stereotype.Repository;

/**
 * @Author: haozt
 * @Date: 2018/11/5
 * @Description:
 */
@Repository
public interface RoleWriteAssociateTreeDao extends GenericDao<RoleWriteAssociateTree, QueryRoleAssociateTree> {
    /**
     * 功能描述：根据角色ID来删除关联的菜单数据
     * @param userRole
     * @return
     */
    int removeTreeByRoleId(UserRole userRole);
}
