/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.change;

import com.connor.hozon.bom.resources.domain.dto.request.HzAuditorChangeDTO;
import cn.net.connor.hozon.dao.pojo.change.change.HzAuditorChangeRecord;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:审核人审核记录
 */
public interface HzAuditorChangeDAO {
    /**
     * 新增一条记录
     *
     * @param record
     * @return
     */
    int insert(HzAuditorChangeRecord record);

    //待办事项
    List<HzAuditorChangeRecord> findAuditorList(HzAuditorChangeRecord record);

    //已处理事项
    List<HzAuditorChangeRecord> findAuditorList2(HzAuditorChangeRecord record);

    //
    List<HzAuditorChangeRecord> findAuditorListById(Long id);

    //统计任务个数
    int count(int userId);

    /**
     * 根据表单的ID查询<strong>一个</strong>审核人意见的数据
     *
     * @param orderId 变更表单的ID
     * @return
     */
    HzAuditorChangeRecord findByOrderId(Long orderId, Long userId);

    /**
     * 根据表单的ID查询<strong>多个记录</strong>并保存审核人意见的数据
     * @param orderId
     * @param userId
     * @return
     */
    List<HzAuditorChangeRecord> findByOrdersId(Long orderId, Long userId);

    int updateByPrimaryKeySelective(HzAuditorChangeRecord record);

    int updateAuditorRecord(HzAuditorChangeDTO reqDTO);
}
