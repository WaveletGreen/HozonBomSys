/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.color;

import com.connor.hozon.bom.bomSystem.dao.color.HzCfg0ColorSetDao;
import com.connor.hozon.bom.bomSystem.dto.HzColorSetQueryDto;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.color.HzCfg0ColorSet;

import java.util.*;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public class HzCfg0ColorSetService {
    @Autowired
    private HzCfg0ColorSetDao hzCfg0ColorSetDao;

//    /**
//     * @param queryBase
//     * @return 颜色集合
//     * Description: 搜索出所有的颜色信息
//     * Date: 2018/5/21 17:08
//     */
//    public Map<String, Object> queryAll2(QueryBase queryBase) {
//        Date now = new Date();
//        Map<String, Object> result = new HashMap<>();
//        List<HzCfg0ColorSet> colorSet = hzCfg0ColorSetDao.queryAll2(queryBase);
//        List<HzCfg0ColorSet> toUpdate = new ArrayList<>();
////        colorSet.stream().filter(
////                set -> now.after(set.getpColorAbolishDate())).filter(
////                set -> set != null).forEach(
////                set -> {
////                    set.setpColorStatus(0);
////                    toUpdate.add(set);
////                });
//        for (HzCfg0ColorSet set : colorSet) {
//            if (set.getpColorAbolishDate() == null) {
//                continue;
//            }
//            if (now.after(set.getpColorAbolishDate())) {
//                toUpdate.add(set);
//            }
//        }
//        //更新废止的颜色信息状态，设置为不可用状态
//        toUpdate.forEach(set -> {
//            hzCfg0ColorSetDao.updateStatusByPrimaryKey(set);
//        });
//        result.put("totalCount", hzCfg0ColorSetDao.tellMeHowManyOfIt());
//        result.put("result", colorSet);
//        return result;
//    }
    public Map<String, Object> queryAll3(HzColorSetQueryDto hzColorSetQueryDto) {
        Date now = new Date();
        Map<String, Object> result = new HashMap<>();
        Map<String,String> queryMap = new HashMap<>();
        List<HzCfg0ColorSet> colorSet = hzCfg0ColorSetDao.queryAll2(hzColorSetQueryDto);
        List<HzCfg0ColorSet> toUpdate = new ArrayList<>();
        for (HzCfg0ColorSet set : colorSet) {
            if (set.getpColorAbolishDate() == null) {
                continue;
            }
            if (now.after(set.getpColorAbolishDate())) {
                toUpdate.add(set);
            }
        }
        //更新废止的颜色信息状态，设置为不可用状态
        toUpdate.forEach(set -> {
            hzCfg0ColorSetDao.updateStatusByPrimaryKey(set);
        });
        result.put("totalCount", hzCfg0ColorSetDao.tellMeHowManyOfIt(hzColorSetQueryDto));
        result.put("result", colorSet);
        return result;
    }

    /**
     * 根据分页条件获取颜色
     *
     * @param queryBase
     * @return
     */
    public List<HzCfg0ColorSet> doGetAll(QueryBase queryBase) {
        return hzCfg0ColorSetDao.queryAll2(queryBase);
    }

    /**
     * 获取全部颜色
     *
     * @return
     */
    public List<HzCfg0ColorSet> doGetAll() {
        return hzCfg0ColorSetDao.queryAll2();
    }

    /**
     * @param entity 颜色
     * @return boolean
     * Description: 根据puid检查是否有颜色信息
     * Date: 2018/5/21 17:08
     */
    public HzCfg0ColorSet getById(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.selectByPrimaryKey(entity);
    }

    /**
     * @param entity 颜色对象
     * @return boolean 只更新1个
     * Description: 执行更细颜色信息，只更新1个
     * Date: 2018/5/21 17:08
     */
    public boolean doUpdate(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.updateByPrimaryKey(entity) == 1 ? true : false;
    }

    /**
     * @param entity 颜色信息的集合
     * @return boolean 是否删除成功
     * Description: 执行批量删除颜色信息
     * Date: 2018/5/21 17:07
     */
    public boolean doDeleteByList(List<HzCfg0ColorSet> entity) {
        return hzCfg0ColorSetDao.deleteByList(entity) > 0 ? true : false;
    }

    /**
     * @param entity 颜色对象
     * @return boolean 是否添加成功
     * Description: 添加一个颜色信息
     * Date: 2018/5/21 17:07
     */
    public boolean doAddOne(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.insert(entity) > 0 ? true : false;
    }

    /**
     * 根据代码获取1个颜色对象
     *
     * @param entity
     * @return
     */
    public HzCfg0ColorSet doGetByColorCode(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.selectByColorCode(entity);
    }

    /**
     * 根据主键更新状态
     *
     * @param entity
     * @return
     */
    public boolean doUpdateStatusByPk(HzCfg0ColorSet entity) {
        return hzCfg0ColorSetDao.updateStatusByPrimaryKey(entity) > 0 ? true : false;
    }

    /**
     * 批量逻辑删除
     *
     * @param entity
     * @return
     */
    public boolean doLogicDeleteByBatch(List<HzCfg0ColorSet> entity) {
        return hzCfg0ColorSetDao.logicDeleteByBatch(entity) > 0 ? true : false;
    }
}
