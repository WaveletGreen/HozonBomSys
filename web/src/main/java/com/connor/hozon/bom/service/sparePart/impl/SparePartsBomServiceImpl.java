/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.sparePart.impl;

import cn.net.connor.hozon.dao.dao.bom.sparePart.SparePartDataDao;
import cn.net.connor.hozon.dao.dao.bom.sparePart.SparePartOfProjectDao;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import com.connor.hozon.bom.service.sparePart.SparePartBomQueryResponse;
import com.connor.hozon.bom.service.sparePart.SparePartsBomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 备件BOM service层
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 9:10
 * @Modified By:
 */
@Service
public class SparePartsBomServiceImpl implements SparePartsBomService {
    @Autowired
    private SparePartOfProjectDao sparePartOfProjectDao;

    @Autowired
    SparePartDataDao sparePartDataDao;
    /**
     * 分页查询出项目
     *
     * @param query
     * @return
     */
    public SparePartBomQueryResponse selectPageByProjectId(SparePartOfProjectQuery query) {
//        result.put("totalCount", totalCount);
//        result.put("result", records);
        int count=sparePartOfProjectDao.count(query);
        List<SparePartData>  result=sparePartDataDao.selectPageByQuery(query);
        return new SparePartBomQueryResponse(count,result);
    }
}
