package com.connor.hozon.bom.resources.service.change;

import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.query.HzEWOImpactReferenceQuery;
import sql.pojo.change.HzEWOImpactReference;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/15
 * @Description:
 */
public interface HzEWOImpactReferenceService {

    List<HzEWOImpactReference> getHzEWOImpactReferences(HzEWOImpactReferenceQuery query);

    OperateResultMessageRespDTO editHzEWOImpactReference(HzEWOImpactReference reference);
}
