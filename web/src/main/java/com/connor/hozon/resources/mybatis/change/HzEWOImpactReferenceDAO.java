package com.connor.hozon.resources.mybatis.change;

import com.connor.hozon.resources.domain.query.HzEWOImpactReferenceQuery;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOImpactReference;

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
