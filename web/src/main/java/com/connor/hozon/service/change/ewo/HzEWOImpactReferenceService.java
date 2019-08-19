/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.ewo;

import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEWOImpactReferenceQuery;
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
