/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.sys;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 14:36
 * @Modified By:
 */
public interface UserRolePrivilegeService {
    WriteResultRespDTO hasPrivilege(String url, List<String> urls);
}
