package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactDeptDAO;
import com.connor.hozon.bom.resources.query.HzEWOImpactDeptQuery;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzEWOAllImpactDept;
import sql.pojo.change.HzEWOImpactDept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/8/20
 * @Description:
 */
@Service("HzEWOImpactDeptDAO")
public class HzEWOImpactDeptDAOImpl extends BaseSQLUtil implements HzEWOImpactDeptDAO {

    @Override
    public int insertList(List<HzEWOImpactDept> hzEWOImpactDepts) {
        return super.insert("HzEWOImpactDeptDAOImpl_insertList",hzEWOImpactDepts);
    }

    @Override
    public int updateList(List<HzEWOImpactDept> hzEWOImpactDepts) {
        return super.update("HzEWOImpactDeptDAOImpl_updateList",hzEWOImpactDepts);
    }

    @Override
    public List<HzEWOImpactDept> findEWOImpactDeptList(HzEWOImpactDeptQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("ewoNo",query.getEwoNo());
        map.put("deptId",query.getDeptId());
        map.put("projectId",query.getProjectId());
        return super.findForList("HzEWOImpactDeptDAOImpl_findEWOImpactDeptList",map);
    }

    @Override
    public List<HzEWOAllImpactDept> findEWOAllImpactDept() {
        return super.findForList("HzEWOImpactDeptDAOImpl_findEWOAllImpactDept",null);
    }
}
