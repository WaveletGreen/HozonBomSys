package com.connor.hozon.bom.sys.service;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.dao.UserRoleDao;
import com.connor.hozon.bom.sys.entity.Tree;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
public class UserRolePrivilegeService {
    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserRoleDao userRoleDao;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    private UserService userService;

    /**
     * 是否具有操作权限
     * @param url 当前用户请求url
     * @param urls 文件读取的url
     * @return
     */
    public WriteResultRespDTO hasPrivilege(String url,List<String> urls){
        WriteResultRespDTO respDTO = new WriteResultRespDTO();
        try {
            User user = UserInfo.getUser();//获取当前登录用户
            if(user == null){
                return WriteResultRespDTO.failResultRespDTO("登录已失效，请重新登录！");
            }
            //session 中获取的用户信息 不是实时数据
            User u = userService.findByLogin(user.getLogin());//系统中存在的用户
            if(u == null){
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg("当前登录用户不存在!");
                return respDTO;
            }
            if(ListUtil.isEmpty(u.getRoles())){
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                respDTO.setErrMsg(WriteResultRespDTO.NOT_PRIVILEGE);
                return respDTO;
            }
            if(PrivilegeUtil.hasAdministratorPrivilege(u)){
                respDTO.setErrCode(WriteResultRespDTO.SUCCESS_CODE);
                return respDTO;
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
            String treeName = "";
            if(urls.contains(url)){
                int index = urls.indexOf(url);
                for(int i = index;i>=0;i--){
                    if(urls.get(i).contains("--")){
                        treeName = urls.get(i).replaceAll("--","");
                        break;
                    }
                }
            }
            if(StringUtil.isEmpty(treeName)){
                respDTO.setErrMsg(WriteResultRespDTO.NOT_PRIVILEGE);
                respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return respDTO;
            }
            if(ListUtil.isNotEmpty(trees)){
                //写文件来判断
                for(Tree t:trees){
                    if(t.getName().equals(treeName)){
                        respDTO.setErrCode(WriteResultRespDTO.SUCCESS_CODE);
                        return respDTO;
                    }
                }
            }
            respDTO.setErrMsg(WriteResultRespDTO.NOT_PRIVILEGE);
            respDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
        }catch (Exception e){
            e.printStackTrace();
            return respDTO;
        }
        return respDTO;
    }


}
