/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.work;

import com.connor.hozon.bom.resources.domain.query.HzWorkByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkCenter;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public interface HzWorkCenterDAO {
    /**
     * 新增工作中心主数据
     * @param hzWorkCenter
     * @return
     */
    int insert(HzWorkCenter hzWorkCenter);

    /**
     * 编辑工艺中心主数据
     * @param hzWorkCenter
     * @return
     */
    int update(HzWorkCenter hzWorkCenter);

    /**
     * 查询工作中心主数据 分页
     * @param query
     * @return
     */
    Page findWorkCenterForPage(HzWorkByPageQuery query);

    /**
     * 删除工作中心主数据
     * @param puid
     * @return
     */
    int delete(String puid);

    /**
     * 查询一条数据
     * @param puid
     * @return
     */
    HzWorkCenter findWorkCenterById(String puid);

    /**
     * 查询一条数据
     * @param pWorkCode
     * @return
     */
    List<HzWorkCenter> findWorkCenter(String pWorkCode);
}
