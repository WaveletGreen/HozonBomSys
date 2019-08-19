/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.bom;

import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartPostDTO;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartBomQueryResponse;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:15
 * @Modified By:
 */
public interface SparePartsBomService {
    /**
     * 分页查询
     * @param query
     * @return
     */
    SparePartBomQueryResponse selectPageByProjectId(SparePartOfProjectQuery query);

    /**
     * 保存单条备件数据
     * @param data
     * @return
     */
    JSONObject saveSparePart(SparePartPostDTO data);

    JSONObject addSparePartChild(SparePartPostDTO data);

    JSONObject deleteList(List<SparePartPostDTO> data);

    JSONObject updateSparePart(SparePartPostDTO data);
}
