/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.bom;

import cn.net.connor.hozon.dao.pojo.bom.bom.HzSingleVehicleDosage;

import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/10/16
 * @Description:
 */
@Deprecated
public interface HzSingleVehicleDosageDAO {

    /**
     * 批量插入
     * @param list
     * @return
     */
    int insertList(List<HzSingleVehicleDosage> list);

    /**
     * 批量修改
     * @param hzSingleVehicleDosages
     * @return
     */
    int updateList(List<HzSingleVehicleDosage> hzSingleVehicleDosages);

    /**
     * 根据bomLine ID查询单车用量信息
     * @param eBomPuid
     * @param projectId
     * @return
     */
    List<HzSingleVehicleDosage> findSingleVehicleByBomPuid(String eBomPuid,String projectId);


}
