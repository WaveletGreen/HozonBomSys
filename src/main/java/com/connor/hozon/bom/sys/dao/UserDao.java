package com.connor.hozon.bom.sys.dao;



import com.connor.hozon.bom.common.base.dao.GenericDao;
import com.connor.hozon.bom.sys.entity.QueryUser;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author linzf
 **/
@Service("userDao")
public interface UserDao extends GenericDao<User, QueryUser> {

	/**
	 * 功能描述：统计组织架构底下的用户
	 * 
	 * @param queryUser
	 * @return
	 */
	int countGroupUser(QueryUser queryUser);

	/**
	 * 功能描述：查询组织架构底下的用户
	 * 
	 * @param queryUser
	 * @return
	 */
	List<User> findGroupUserByPage(QueryUser queryUser);

	/**
	 * 功能描述：更新用户状态为可用或者不可用
	 * 
	 * @param user
	 * @return
	 */
	int userControl(User user);

	/**
	 * 功能描述：根据账号来获取用户信息
	 * 
	 * @param login
	 * @return
	 */
	User findByLogin(String login);

	/**
	 * 功能描述：更新用户的最迟登陆时间
	 * 
	 * @param user
	 * @return
	 */
	int updateLogin(User user);

	//TODO by haozt on 7/23/2018
	//更改用户密码
	int updatePassword(User user);

	/**
	 * 根据部门id 获取当前部门下的用户
	 * @param groupId
	 * @return
	 *  todo by haozt on 8/17/2018
	 */
	List<User> findUserByGroupId(String groupId);

	/**
	 * 获取当前全部用户
	 * @return
	 *  todo by haozt on 8/20/2018
	 */
	List<User> findAllUser();

	/**
	 * 获取当前用户 根据id
	 * param state 账户状态 启用 禁用
	 * @return
	 *  todo by haozt on 8/21/2018
	 */
	User findUserById(Map<String,Object> map);
}