package com.connor.hozon.resources.mybatis.change;

import com.connor.hozon.resources.domain.query.HzEWOImpactDeptQuery;
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
