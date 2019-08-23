/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.sys;

import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.dao.dao.sys.GenericDao;
import cn.net.connor.hozon.dao.dao.sys.RoleAssociateTreeDao;
import cn.net.connor.hozon.dao.dao.sys.RoleWriteAssociateTreeDao;
import cn.net.connor.hozon.dao.dao.sys.UserRoleDao;
import cn.net.connor.hozon.dao.pojo.sys.RoleAssociateTree;
import cn.net.connor.hozon.dao.pojo.sys.RoleWriteAssociateTree;
import cn.net.connor.hozon.dao.pojo.sys.Tree;
import cn.net.connor.hozon.dao.pojo.sys.UserRole;
import cn.net.connor.hozon.dao.query.sys.QueryUserRole;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *@author linzf
 **/
@Service("userRoleService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class UserRoleService extends GenericService<UserRole, QueryUserRole> {
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private UserRoleDao userRoleDao;
	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private RoleAssociateTreeDao roleAssociateTreeDao;

	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private RoleWriteAssociateTreeDao roleWriteAssociateTreeDao;
	@Override
	protected GenericDao<UserRole, QueryUserRole> getDao() {
		return userRoleDao;
	}

	/**
	 * 功能描述：获取权限菜单数据
	 * @param entity
	 * @return
	 */
	public UserRole getUserRoleAssociate(UserRole entity){
		return userRoleDao.getUserRoleAssociate(entity);
	}


	/**
	 * 功能描述：获取用户写权限菜单数据
	 * @param entity
	 * @return
	 */
	public UserRole getUserWriteRoleAssociate(UserRole entity){
		return userRoleDao.getUserWriteRoleAssociate(entity);
	}
	/**
	 * 查询当前角色权限的引用关系
	 * @param id 角色id
	 * @return
	 */
	public Integer getUserRoleReferenceCount(Long id){
		return userRoleDao.getUserRoleReferenceCount(id);
	}
	/**
	 * 功能描述：删除角色数据
	 * @param entityList
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean removeBath(List<UserRole> entityList) throws Exception {
        for(UserRole userRole:entityList){
			roleAssociateTreeDao.removeTreeByRoleId(userRole);
		}
		return super.removeBath(entityList);
	}

	/**
	 * 增加角色数据
	 * @param entity 保存对象
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean save(UserRole entity) throws Exception {
		entity.packagingTrees(entity.getTreeArray());
		List<Tree> treeList = entity.getTreeList();
		boolean success = super.save(entity);
		for(Tree tree:treeList){
			roleAssociateTreeDao.save(new RoleAssociateTree(entity.getId(),tree.getId()));
		}
		return success;
	}

	@Override
	public boolean update(UserRole entity) throws Exception {
		entity.packagingTrees(entity.getTreeArray());
		List<Tree> treeList = entity.getTreeList();
		roleAssociateTreeDao.removeTreeByRoleId(entity);
		for(Tree tree:treeList){
			roleAssociateTreeDao.save(new RoleAssociateTree(entity.getId(),tree.getId()));
		}
		return super.update(entity);
	}

	/**
	 * 保存设定的用户权限信息
	 * @param userRole
	 * @return
	 */
	public boolean saveRoleWritePrivilege(UserRole userRole) {
		try {
			userRole.packagingTrees(userRole.getTreeArray());
			List<String> list = new ArrayList<>();
			if(StringUtils.isNotBlank(userRole.getTreeArray())){
				list = Lists.newArrayList(userRole.getTreeArray().split(","));
			}

			roleWriteAssociateTreeDao.removeTreeByRoleId(userRole);
			if(ListUtils.isNotEmpty(list)){
				for(String s:list){
					roleWriteAssociateTreeDao.save(new RoleWriteAssociateTree(userRole.getId(),Long.valueOf(s)));
				}
			}
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}


	public List<UserRole> findUserRoleListByIds(List<String> ids){
		List<Long> list  = new ArrayList<>();
		if(ListUtils.isNotEmpty(ids)){
			ids.forEach(id->{
				list.add(Long.parseLong(id));
			});
		}
		return userRoleDao.findUserRoleListByIds(list);
	}

	public List<UserRole> findUserRoleAndUserByRoleName(String roleName){
		return userRoleDao.findUserRoleAndUserByRoleName(roleName);
	}
}