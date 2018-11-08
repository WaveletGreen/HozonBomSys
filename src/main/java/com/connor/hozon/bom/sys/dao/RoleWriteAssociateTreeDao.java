package com.connor.hozon.bom.sys.dao;

import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.sys.entity.QueryRoleAssociateTree;
import com.connor.hozon.bom.sys.entity.RoleWriteAssociateTree;
import com.connor.hozon.bom.sys.entity.UserRole;
import org.springframework.stereotype.Service;

/**
 * @Author: haozt
 * @Date: 2018/11/5
 * @Description:
 */
@Service("roleWriteAssociateTreeDao")
public interface RoleWriteAssociateTreeDao extends GenericDao<RoleWriteAssociateTree, QueryRoleAssociateTree> {
    /**
     * 功能描述：根据角色ID来删除关联的菜单数据
     * @param userRole
     * @return
     */
    int removeTreeByRoleId(UserRole userRole);
}
