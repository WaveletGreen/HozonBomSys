/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.change.vwo;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import org.apache.ibatis.annotations.Param;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: vwo表单主数据
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzVwoInfoDao extends BasicDao<HzVwoInfo> {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") Long id);


    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    HzVwoInfo selectByPrimaryKey(@Param("id") Long id);

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    HzVwoInfo findMaxAreaVwoNum();

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    List<HzVwoInfo> selectListByProjectUid(Map<String, Object> params);

    /**
     * 获取当前项目下的变更总数
     *
     * @param projectUid
     * @return
     */
    int count(@Param("projectUid") String projectUid);

    int updateByVwoId(HzVwoInfo info);
}