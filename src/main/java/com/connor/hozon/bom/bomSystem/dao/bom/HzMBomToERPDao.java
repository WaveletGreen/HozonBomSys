/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.bom;

import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HzMBomToERPBean;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: ERP集成用的MBOM dao层，只筛选重要数据，其他不要
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzMBomToERPDao {
    /**
     * 根据项目UID和MBOM行的UID列表获取到一组记录
     *
     * @param projectUid 项目的UID
     * @param list       MBOM记录的UID列表
     * @return
     */
    List<HzMBomToERPBean> selectByUidOfBatch(String projectUid, List<String> list);

    /**
     * 根据项目UID和MBOM行的父层UID列表获取一组父层的数据，需要从@{@link HzMBomToERPBean}中getParentUid获取到父层Uid
     *
     * @param projectUid 项目UID
     * @param list       父层UID列表
     * @return
     */
    List<HzMBomToERPBean> selectbyParentUidOfBatch(String projectUid, List<String> list);

    /**
     * 根据项目UID，除去2Y层，将所有2Y以下的所有数据取出
     *
     * @param projectUid 项目UID
     * @param is2Y       是否是2Y层，如果要所有，传0和1以外的值即可
     * @return
     */
    List<HzMBomToERPBean> selectByProjectUidWithCondition(String projectUid, Integer is2Y);
}
