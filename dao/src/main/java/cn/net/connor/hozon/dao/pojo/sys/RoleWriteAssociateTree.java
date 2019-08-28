/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.sys;

import lombok.Data;

/**
 * 写权限
 * @Author: haozt
 * @Date: 2018/11/5
 * @Description:
 */
@Data
public class RoleWriteAssociateTree {
    /**
     * 角色主键
     */
    private Long roleId;
    /**
     * 对应的树形主键
     */
    private Long treeId;

    public RoleWriteAssociateTree() {
    }

    public RoleWriteAssociateTree(Long roleId, Long treeId) {
        this.roleId = roleId;
        this.treeId = treeId;
    }
}
