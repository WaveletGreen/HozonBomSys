/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.sys;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linzf
 **/
@Data
public class Tree implements Comparable<Tree> {
    /**
     * 主键
     */
    private long id;
    /**
     * 树形结构代码
     */
    private String code;
    /**
     * 图标
     */
    private String icon;
    /**
     * 树形结构名称
     */

    private String name;
    /**
     * 父层id
     */
    private long pId;
    /**
     * 树形结构所在的顺序
     */
    private long treeOrder;
    /**
     * 访问的地址
     */
    private String url;
    /**
     * 状态
     */
    private String state;
    /**
     * 菜单节点是否选中的状态
     */
    private boolean checked;
    /**
     * 父菜单信息
     */
    private Tree tree;
    /**
     * 子菜单节点信息
     */
    private List<Tree> child = new ArrayList<Tree>();

    public Tree() {
    }

    public Tree(Long id) {
        this.id = id;
    }

    public long getpId() {
        return pId;
    }

    public void setpId(long pId) {
        this.pId = pId;
    }

    /**
     * 功能描述：实现集合根据treeOrder字段进行排序的功能
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Tree o) {
        long i = this.getTreeOrder() - o.getTreeOrder();
        return Integer.parseInt(i + "");
    }
}
