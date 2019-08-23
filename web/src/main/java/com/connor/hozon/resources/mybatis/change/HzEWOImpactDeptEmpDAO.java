package com.connor.hozon.resources.mybatis.change;

import com.connor.hozon.resources.domain.query.HzEWOImpactDeptQuery;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOImpactDeptEmp;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
public interface HzEWOImpactDeptEmpDAO {
    int insertList(List<HzEWOImpactDeptEmp> ewoImpactDeptEmps);

    int updateList(List<HzEWOImpactDeptEmp> ewoImpactDeptEmps);

    List<HzEWOImpactDeptEmp> findImpactDeptList(HzEWOImpactDeptQuery query);
}
