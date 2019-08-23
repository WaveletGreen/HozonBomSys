/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.sys;

import cn.net.connor.hozon.common.entity.WriteResultRespDTO;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 14:36
 * @Modified By:
 */
public interface UserRolePrivilegeService {
    WriteResultRespDTO hasPrivilege(String url, List<String> urls);
}
