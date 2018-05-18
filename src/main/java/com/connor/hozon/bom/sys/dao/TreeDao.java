package com.connor.hozon.bom.sys.dao;

import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.sys.entity.QueryTree;
import com.connor.hozon.bom.sys.entity.Tree;
import com.connor.hozon.bom.sys.entity.User;


import java.util.List;

/**
 * @author linzf
 **/
public interface TreeDao extends GenericDao<Tree, QueryTree> {

	/**
	 * 功能描述：加载用户的菜单树的数据
	 * 
	 * @param user
	 * @return
	 */
	List<Tree> loadUserTree(User user);
}