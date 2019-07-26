package com.connor.hozon.bom.resources.mybatis.change;

import com.connor.hozon.bom.resources.domain.query.HzEWOImpactDeptQuery;
import sql.pojo.change.HzEWOAllImpactDept;
import sql.pojo.change.HzEWOImpactDept;

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
