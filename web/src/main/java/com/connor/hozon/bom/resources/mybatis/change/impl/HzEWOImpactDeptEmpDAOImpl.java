/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.domain.query.HzEWOImpactDeptQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactDeptEmpDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOImpactDeptEmp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
@Service("HzEWOImpactDeptEmpDAO")
public class HzEWOImpactDeptEmpDAOImpl extends BaseSQLUtil implements HzEWOImpactDeptEmpDAO {

    @Override
    public int insertList(List<HzEWOImpactDeptEmp> ewoImpactDeptEmps) {
        return super.insert("HzEWOImpactDeptEmpDAOImpl_insertList",ewoImpactDeptEmps);
    }

    @Override
    public int updateList(List<HzEWOImpactDeptEmp> ewoImpactDeptEmps) {
        return super.update("HzEWOImpactDeptEmpDAOImpl_updateList",ewoImpactDeptEmps);
    }

    @Override
    public List<HzEWOImpactDeptEmp> findImpactDeptList(HzEWOImpactDeptQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("impactDeptId",query.getDeptId());
        map.put("projectId",query.getProjectId());
        map.put("ewoNo",query.getEwoNo());
        return super.findForList("HzEWOImpactDeptEmpDAOImpl_findImpactDeptList",map);
    }
}
