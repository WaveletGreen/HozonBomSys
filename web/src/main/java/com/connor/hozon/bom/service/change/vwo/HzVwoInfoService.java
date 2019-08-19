/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.change.vwo;

import cn.net.connor.hozon.dao.query.change.vwo.HzVwoFormListQuery;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInfo;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public interface HzVwoInfoService {
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
    List<HzVwoInfo> doSelectListByProjectUid(HzVwoFormListQuery queryBase, String projectUid);

    /**
     * 当前项目下的总数
     *
     * @param projectUid
     * @return
     */
    int count(String projectUid);

    /**
     * 生成VWO号码
     */
    HzVwoInfo generateVWONum() ;


    /**
     * 配色方案vwo发布
     * @param info
     * @return
     */
    boolean updateByVwoId(HzVwoInfo info);
}
