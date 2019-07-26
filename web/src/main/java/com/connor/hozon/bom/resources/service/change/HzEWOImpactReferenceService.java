package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOImpactReferenceQuery;
import sql.pojo.change.HzEWOImpactReference;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/15
 * @Description:
 */
public interface HzEWOImpactReferenceService {

    List<HzEWOImpactReference> getHzEWOImpactReferences(HzEWOImpactReferenceQuery query);

    WriteResultRespDTO editHzEWOImpactReference(HzEWOImpactReference reference);
}
