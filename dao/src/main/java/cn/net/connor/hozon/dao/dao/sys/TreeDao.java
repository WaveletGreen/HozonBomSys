/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.sys;

import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import cn.net.connor.hozon.dao.query.sys.QueryTree;
import cn.net.connor.hozon.dao.pojo.sys.Tree;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @author linzf
 **/
@Repository
public interface TreeDao extends GenericDao<Tree, QueryTree> {

	/**
	 * 功能描述：加载用户的菜单树的数据
	 * 
	 * @param user
	 * @return
	 */
	List<Tree> loadUserTree(User user);
}