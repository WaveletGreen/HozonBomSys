package com.connor.hozon.bom.resources.mybatis.change;

import com.connor.hozon.bom.resources.domain.query.HzEWOImpactReferenceQuery;
import sql.pojo.change.HzEWOImpactReference;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/15
 * @Description: 影响分析dao层
 */
public interface HzEWOImpactReferenceDAO {
    int insertList(List<HzEWOImpactReference> referenceList);

    int updateList(List<HzEWOImpactReference> referenceList);

    List<HzEWOImpactReference> findImpactReferences(HzEWOImpactReferenceQuery query);

    HzEWOImpactReference findHzImpactReference(HzEWOImpactReferenceQuery query);
}
