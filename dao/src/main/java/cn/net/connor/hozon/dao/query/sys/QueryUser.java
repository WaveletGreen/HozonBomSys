/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.query.sys;


import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.dao.pojo.sys.OrgGroup;
import lombok.Data;

/**
 * @author linzf
 **/
@Data
public class QueryUser extends QueryBase {
    private String login;
    private String password;
    private String userName;
    private String address;
    private String job;
    private Long groupId;
    private String birthDate;
    private String city;
    private String district;
    private String province;
    private String streetAddress;
    private String state;
    private String type;
    private String lastLoginDate;
    private OrgGroup orgGroup;
}
