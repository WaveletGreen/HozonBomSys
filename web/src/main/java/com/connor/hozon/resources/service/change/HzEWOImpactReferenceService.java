package com.connor.hozon.resources.service.change;

import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzEWOImpactReferenceQuery;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOImpactReference;

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
