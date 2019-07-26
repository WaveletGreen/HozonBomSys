package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.domain.query.HzEWOImpactReferenceQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOImpactReferenceDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.change.HzEWOImpactReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/8/15
 * @Description:
 */
@Service("HzEWOImpactReferenceDAO")
public class HzEWOImpactReferenceDAOImpl extends BaseSQLUtil implements HzEWOImpactReferenceDAO {
    @Override
    public int insertList(List<HzEWOImpactReference> referenceList) {
        return super.insert("HzEWOImpactReferenceDAOImpl_insertList",referenceList);
    }

    @Override
    public int updateList(List<HzEWOImpactReference> referenceList) {
        return super.update("HzEWOImpactReferenceDAOImpl_updateList",referenceList);
    }

    @Override
    public List<HzEWOImpactReference> findImpactReferences(HzEWOImpactReferenceQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("ewoNo",query.getEwoNo());
        return super.findForList("HzEWOImpactReferenceDAOImpl_findImpactReferences",map);
    }

    @Override
    public HzEWOImpactReference findHzImpactReference(HzEWOImpactReferenceQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",query.getProjectId());
        map.put("ewoNo",query.getEwoNo());
        map.put("impactAnalysisId",query.getImpactAnalysisId());
        return (HzEWOImpactReference)super.findForObject("HzEWOImpactReferenceDAOImpl_findHzImpactReference",map);
    }
}
