package com.connor.hozon.resources.service.change;

import com.connor.hozon.resources.domain.dto.response.HzChangeListRespDTO;
import com.connor.hozon.resources.domain.query.HzChangeListByPageQuery;

import java.util.List;

public interface HzChangeListService {
    /**
     * 查询列表-TC同步变更单引用对象
     * @return
     */
    List<HzChangeListRespDTO> findChangeList(HzChangeListByPageQuery query);

}
