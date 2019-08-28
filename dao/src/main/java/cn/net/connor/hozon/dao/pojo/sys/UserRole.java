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
import java.util.Objects;

/**
 *@author linzf
 **/
@Data
public class UserRole {
	/**
	 * 主键
	 */
	private long id;
	private String name;
	private String roleName;

	private List<Tree> treeList;
    // 临时采访菜单数集合的数据
	private String treeArray;
	/**
	 * 用户编号
	 */
	private Long userId;

	public void packagingTrees(String treeArray){
		Tree tree = null;
		List<Tree> trees = new ArrayList<>();
		for(String id:treeArray.split(",")){
			if(!id.isEmpty()){
				tree = new Tree(Long.parseLong(id));
				trees.add(tree);
			}
		}
		this.setTreeList(trees);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserRole role = (UserRole) o;
		return id == role.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
