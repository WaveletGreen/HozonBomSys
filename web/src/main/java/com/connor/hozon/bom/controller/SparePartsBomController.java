/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.controller;

import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import com.connor.hozon.bom.service.sparePart.SparePartBomQueryResponse;
import com.connor.hozon.bom.service.sparePart.SparePartsBomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 备件Bom Controller
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 9:09
 * @Modified By:
 */
@Controller
@RequestMapping("sparePartsBom")
public class SparePartsBomController {
    @Autowired
    private SparePartsBomService sparePartsBomService;

    /**
     * 分页查询出项目中的备件
     * @param query
     * @return
     */
    @RequestMapping("selectPageByProjectId")
    @ResponseBody
    public SparePartBomQueryResponse selectPageByProjectId(SparePartOfProjectQuery query) {
        return  sparePartsBomService.selectPageByProjectId(query);
    }
}
