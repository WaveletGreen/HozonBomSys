/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.sys;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author linzf
 **/
@Data
public class User implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 3644250777603394517L;

    /**
     * 主键
     */
    private Integer id;
    /**
     * 登录的账号
     */
    private String login;
    /**
     * 密码
     */
    private String password;
    /**
     * 显示的用户名
     */
    private String userName;
    /**
     * 地址
     */
    private String address;
    /**
     * 职位
     */
    private String job;
    /**
     * ？
     */
    private long groupId;
    /**
     * 生日
     */
    private Date birthDate;
    /**
     * 所在城市
     */
    private String city;
    /**
     * 地区
     */
    private String district;
    /**
     * 省
     */
    private String province;
    /**
     * 街道
     */
    private String streetAddress;
    /**
     * 状态
     */
    private String state;
    /**
     * 类型
     */
    private String type;
    /**
     * 最后一次登录时间
     */
    private Date lastLoginDate;
    // 用户角色信息
    private List<UserRole> roles;
    // 权限集合数据
    private String roleArray;
    // 所在分组的集合
    private OrgGroup orgGroup;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 员工工号
     */
    private String userNo;

    public User() {

    }

    public User(Integer id) {
        this.id = id;
    }

    public static String reflectToDBField(String code) {
        switch (code) {
            case "userName":
                return "user_name";
            case "groupId":
                return "group_Id";
            case "id":
                return "id";
            default:
                return null;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        if (this.getRoles() != null) {
            List<UserRole> roles = this.getRoles();
            for (UserRole role : roles) {
                if (role.getName() != null) {
                    auths.add(new SimpleGrantedAuthority(role.getName()));
                }
            }
        }
        return auths;
    }

    /**
     * 功能描述：组装角色数据集合
     *
     * @param roleArray
     */
    public void packagingRoles(String roleArray) {
        List<UserRole> roles = new ArrayList<UserRole>();
        if (roleArray != null) {
            UserRole userRole = null;
            for (String roleId : roleArray.split(",")) {
                if (!roleId.isEmpty()) {
                    userRole = new UserRole();
                    userRole.setId(Long.parseLong(roleId));
                    roles.add(userRole);
                }
            }
        }
        this.setRoles(roles);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
