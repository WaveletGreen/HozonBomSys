/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.change.vwo;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoExecute;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzVwoExecuteDao extends BasicDao<HzVwoExecute> {
    /**
     * 根据VWO号ID查询
     *
     * @param exeVwoId vwo号的ID，主键
     * @return 一组分发与实施对象
     */
    List<HzVwoExecute> selectByVwoId(@Param("exeVwoId") Long exeVwoId);

    /**
     * 批量删除发布与实施数据
     *
     * @param executes 发布与实施数据
     * @return 影响行数
     */
    int deleteByBatch(List<HzVwoExecute> executes);
}