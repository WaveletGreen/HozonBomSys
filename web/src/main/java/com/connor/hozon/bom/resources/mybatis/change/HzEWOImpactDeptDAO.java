/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.change;

import com.connor.hozon.bom.resources.domain.query.HzEWOImpactDeptQuery;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOAllImpactDept;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOImpactDept;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
public interface HzEWOImpactDeptDAO {
    int insertList(List<HzEWOImpactDept> hzEWOImpactDepts);

    int updateList(List<HzEWOImpactDept> hzEWOImpactDepts);

    List<HzEWOImpactDept> findEWOImpactDeptList(HzEWOImpactDeptQuery query);

    List<HzEWOAllImpactDept> findEWOAllImpactDept();
}
