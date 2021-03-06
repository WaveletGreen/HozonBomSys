/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.derivative;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ModelGroup;
import org.springframework.stereotype.Repository;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 衍生物料物料组，传ERP用到，但是BOM系统并没有进行维护该部分数据，老子也不知道
 *
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzCfg0ModelGroupDao extends BasicDao<HzCfg0ModelGroup> {
    /**
     * 主键删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(String id);


    /**
     * 主键筛选
     * @param id
     * @return
     */
    HzCfg0ModelGroup selectByPrimaryKey(String id);

    /**
     * 根据主配置寻找模型族
     * @param mainUid
     * @return
     */
    HzCfg0ModelGroup selectByMainUid(String mainUid);

    /**
     * 根据主配置寻找族名
     * @param mainUid
     * @return
     */
    String selectGroupNameByMainUid(String mainUid);

    @Override
    int insert(HzCfg0ModelGroup hzCfg0ModelGroup);

    int updateByMainId(HzCfg0ModelGroup hzCfg0ModelGroup);
}