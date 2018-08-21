package com.connor.hozon.bom.resources.mybatis.change;

import com.connor.hozon.bom.resources.query.HzEWOImpactDeptQuery;
import sql.pojo.change.HzEWOImpactDeptEmp;

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
