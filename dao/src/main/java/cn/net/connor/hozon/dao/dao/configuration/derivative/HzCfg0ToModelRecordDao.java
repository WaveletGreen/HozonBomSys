/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.derivative;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzCfg0ToModelRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 0.0.1版TC同步数据，应该不再使用
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Deprecated
@Repository
public interface HzCfg0ToModelRecordDao  extends BasicDao<HzCfg0ToModelRecord>{

    int deleteByPrimaryKey(String puid);


    HzCfg0ToModelRecord selectByPrimaryKey(String puid);
    /**
     * 批量更新发送到SAP的状态
     *
     * @param list
     * @return
     */
    int setIsSent(@Param("list") List<HzCfg0ToModelRecord> list);
}