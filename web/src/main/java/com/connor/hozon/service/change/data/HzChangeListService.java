/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.change.data;

import com.connor.hozon.bom.resources.domain.dto.response.HzChangeListRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzChangeListByPageQuery;

import java.util.List;

public interface HzChangeListService {
    /**
     * 查询列表-TC同步变更单引用对象
     * @return
     */
    List<HzChangeListRespDTO> findChangeList(HzChangeListByPageQuery query);

}
