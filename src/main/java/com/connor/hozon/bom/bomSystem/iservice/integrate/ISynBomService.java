/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.integrate;

import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public interface ISynBomService {
    /**
     * 执行更新的时候传，有可能时传多条，传多条也演变成传单挑数据传输，先找到父层，再找到子层，父+子一条一条传即可
     *
     * @param puidOfUpdate 需要更新的数据的puid
     * @return
     */
    JSONObject updateByUids(String projectPuid, String puidOfUpdate);

    /**
     * 删除时候传
     *
     * @param puidsOfDelete 需要删除的数据的puid
     * @return
     */
    JSONObject deleteByUids(String projectPuid, List<String> puidsOfDelete);

    /**
     * 添加1行
     *
     * @param puidOfNew 新增加的数据的puid
     * @return
     */
    JSONObject addOne(String projectPuid, String puidOfNew);

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    JSONObject synAllByProjectUid(String projectPuid);

}
