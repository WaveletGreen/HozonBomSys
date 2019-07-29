/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.depository.color;

import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.depository.color.HzCfg0ColorSet;
import cn.net.connor.hozon.dao.pojo.depository.color.HzColorSetQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 配色方案基础dao层
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzCfg0ColorSetDao extends BasicDao<HzCfg0ColorSet>{
    /**
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 检索所有的颜色集
     * @Date: 2018/5/21 17:09
     * @param queryBase
     */
    List<HzCfg0ColorSet> selectAll(QueryBase queryBase);

    /**
     * 根据颜色代码寻找颜色对象
     *
     * @param entity
     * @return 找到的颜色对象
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据颜色的id，找到该颜色对象
     * @Date: 2018/5/21 17:09
     */
    HzCfg0ColorSet selectByColorCode(HzCfg0ColorSet entity);



    /**
     * @param entity 更新的颜色对象的状态
     * @return 影响行数
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行更细颜色信息
     * @Date: 2018/5/21 17:10
     */
    int updateStatusByPrimaryKey(HzCfg0ColorSet entity);

    /**
     * @param entity 传入的颜色集合
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 批量删除颜色信息
     * @Date: 2018/5/21 17:10
     */
    int deleteByBatch(List<HzCfg0ColorSet> entity);

    /**
     * @param entity 传入的颜色集合
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 批量逻辑删除颜色信息，设置颜色删除状态为0
     * @Date: 2018/5/21 17:10
     */
    int logicDeleteByBatch(List<HzCfg0ColorSet> entity);

    /**
     * 查询全部的颜色数量
     * @return
     */
    int tellMeHowManyOfIt(HzColorSetQuery hzColorSetQueryDto);
}
