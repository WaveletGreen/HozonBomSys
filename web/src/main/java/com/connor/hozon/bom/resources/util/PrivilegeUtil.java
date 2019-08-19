/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.util;

import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import cn.net.connor.hozon.dao.pojo.sys.User;
import cn.net.connor.hozon.dao.pojo.sys.UserRole;

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
        if(ListUtils.isEmpty(user.getRoles())){
            return false;
        }
        if(user.getRoles().size() == 1 && user.getRoles().get(0).equals(ROLE_USER)){
            return false;
        }
        for(UserRole userRole :user.getRoles()){
            if(ROLE_ADMIN.equals(userRole.getName())|| ROLE_DINER.equals(userRole.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前用户是否为系统管理员
     * @return
     */
    public static  boolean administrator(){
        //session中获取的user
        User user = UserInfo.getUser();
        if(null == user){
            return false;
        }
        if(ListUtils.isEmpty(user.getRoles())){
            return false;
        }
        for(UserRole role:user.getRoles()){
            if(ROLE_ADMIN.equals(role.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * session 存在缓存 获取的权限信息不实时
     * 可以根据数据库中查出最新权限信息进行实时权限判断
     * 判断用户是否具有系统管理员权限
     * @param user
     * @return
     */
    public static boolean hasAdministratorPrivilege(User user){
        if(null==user || null ==user.getRoles()){
            return false;
        }
        for(UserRole role:user.getRoles()){
            if(ROLE_ADMIN.equals(role.getName())){
                return true;
            }
        }
        return false;
    }
}
