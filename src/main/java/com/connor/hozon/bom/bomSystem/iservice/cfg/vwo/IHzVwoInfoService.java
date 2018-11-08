/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.cfg.vwo;

import com.connor.hozon.bom.bomSystem.dto.vwo.HzVwoFormListQueryBase;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface IHzVwoInfoService {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    boolean doDeleteByPrimaryKey(Long id);

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    Long doInsert(HzVwoInfo record) ;

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    HzVwoInfo doSelectByPrimaryKey(Long id);

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    boolean doUpdateByPrimaryKey(HzVwoInfo record) ;

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    HzVwoInfo doFindMaxAreaVwoNum();

    /**
     * 根据分页进行查询
     */
    List<HzVwoInfo> doSelectListByProjectUid(HzVwoFormListQueryBase queryBase, String projectUid);

    /**
     * 当前项目下的总数
     *
     * @param projectUid
     * @return
     */
    int tellMeHowManyOfIt(String projectUid);

    /**
     * 生成VWO号码
     */
    HzVwoInfo generateVWONum() ;

}
