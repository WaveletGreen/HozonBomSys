package com.connor.hozon.bom.sys.service;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.sys.dao.UserRoleDao;
import com.connor.hozon.bom.sys.entity.Tree;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: haozt
 * @Date: 2018/11/7
 * @Description: 用户角色权限判断
 */
@Service("userRolePrivilegeService")
@Transactional(rollbackFor={IllegalArgumentException.class})
public class UserRolePrivilegeService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private UserService userService;

    WriteResultRespDTO hasPrivilege(){
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        try {
            User user = UserInfo.getUser();//获取当前登录用户
            //session 中获取的用户信息 不是实时数据
            User u = userService.findByLogin(user.getLogin());//系统中存在的用户
            if(u == null){
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg("当前登录用户不存在！");
            }
            if(ListUtil.isEmpty(u.getRoles())){
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg("当前用户没有操作权限");
            }
            Set<Tree> trees = new HashSet<>();
            List<UserRole> userRoles = u.getRoles();
            for(UserRole role :userRoles){
                UserRole userRole = userRoleDao.getUserWriteRoleAssociate(role);//已开通用户权限
                if(userRole == null){
                    continue;
                }
                trees.addAll(userRole.getTreeList());
            }

            if(ListUtil.isNotEmpty(trees)){
                //写文件来判断

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return respDTO;
    }


}
