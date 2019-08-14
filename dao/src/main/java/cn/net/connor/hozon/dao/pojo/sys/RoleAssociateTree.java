/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.sys;

import lombok.Data;

/**
 * 读权限
 *
 * @author linzf
 **/
@Data
public class RoleAssociateTree {
    private long roleId;
    private long treeId;

    public RoleAssociateTree() {
        super();
    }

    public RoleAssociateTree(long roleId, long treeId) {
        this.roleId = roleId;
        this.treeId = treeId;
    }
}
