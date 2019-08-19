/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.sys;


import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import cn.net.connor.hozon.dao.dao.sys.RoleAssociateTreeDao;
import cn.net.connor.hozon.dao.dao.sys.TreeDao;
import cn.net.connor.hozon.dao.pojo.sys.Tree;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.dao.query.sys.QueryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *@author linzf
 **/
@Service("treeService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class TreeService extends GenericService<Tree, QueryTree> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private TreeDao treeDao;
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private RoleAssociateTreeDao roleAssociateTreeDao;

	@Override
	protected GenericDao<Tree, QueryTree> getDao() {
		return treeDao;
	}

	/**
	 * 功能描述：删除菜单的数据
	 * @param entity 删除对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delete(Tree entity) throws Exception {
		roleAssociateTreeDao.removeTreeByTreeId(entity);
		return super.delete(entity);
	}

	/**
	 * 功能描述：加载用户的菜单树的数据
	 * @param user
	 * @return
	 */
	public List<Tree> loadUserTree(User user){
		return treeDao.loadUserTree(user);
	}


}