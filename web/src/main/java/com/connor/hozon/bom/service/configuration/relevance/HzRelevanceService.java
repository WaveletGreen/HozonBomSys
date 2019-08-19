/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.configuration.relevance;

import cn.net.connor.hozon.dao.query.configuration.relevance.HzRelevanceQuery;
import net.sf.json.JSONObject;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/19 14:14
 * @Modified By:
 */
public interface HzRelevanceService {
    /**
     * 过滤特性为“车身颜色/HZCSYS”和特性为“内外饰/HZNWS”
     *
     * @param projectPuid
     * @return
     */
    JSONObject addRelevance(String projectPuid) ;

    /**
     * 查询相关性
     *
     * @param dto
     * @return
     */
    Map<String, Object> queryRelevance(HzRelevanceQuery dto);

    //发起变更流程
    JSONObject getChange(Long changeFromId, String projectPuid) ;

    /**
     * 撤销
     *
     * @param projectPuid
     * @return
     */
    JSONObject goBackData(String projectPuid) ;

    void getChangePageModel(String projectUid, Model model) ;
}
