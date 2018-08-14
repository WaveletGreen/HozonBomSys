package com.connor.hozon.bom.resources.util;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.entity.UserRole;

/**
 * @Author: haozt
 * @Date: 2018/8/14
 * @Description: 操作bom系统时权限判断
 */
public class PrivilegeUtil {
    public static  final String ROLE_ADMIN = "ROLE_ADMIN";//系统管理员

    public static final String ROLE_USER = "ROLE_USER";//普通用户

    public  static final String ROLE_DINER = "ROLE_DINER";//BOM系统使用者

    /**
     * 写数据权限
     * @return
     */
    public static boolean writePrivilege(){
        User user = UserInfo.getUser();
        if(null == user){
            return false;
        }
        if(ListUtil.isEmpty(user.getRoles())){
            return false;
        }
        if(user.getRoles().size() == 1 && user.getRoles().get(0).equals(ROLE_USER)){
            return false;
        }
        for(UserRole userRole :user.getRoles()){
            if(userRole.getName().equals(ROLE_ADMIN)|| userRole.getName().equals(ROLE_DINER)){
                return true;
            }
        }
        return false;
    }

}
