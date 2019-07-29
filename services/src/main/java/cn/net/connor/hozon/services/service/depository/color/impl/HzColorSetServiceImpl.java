/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.depository.color.impl;

import cn.net.connor.hozon.dao.dao.depository.color.HzCfg0ColorSetDao;
import cn.net.connor.hozon.dao.pojo.depository.color.HzColorSetQuery;
import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.services.service.depository.color.HzColorSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.depository.color.HzCfg0ColorSet;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
@Transactional
public class HzColorSetServiceImpl implements HzColorSetService {
    @Autowired
    private HzCfg0ColorSetDao hzCfg0ColorSetDao;


    public Map<String, Object> selectAll(HzColorSetQuery query) {
        Date now = new Date();
        Map<String, Object> result = new HashMap<>();
        Map<String,String> queryMap = new HashMap<>();
        query.setSort(HzColorSetQuery.reflectToDBField(query.getSort()));
        List<HzCfg0ColorSet> colorSet = hzCfg0ColorSetDao.selectAll(query);
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
        result.put("totalCount", hzCfg0ColorSetDao.tellMeHowManyOfIt(query));
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
        return hzCfg0ColorSetDao.selectAll(queryBase);
    }

    /**
     * 获取全部颜色
     *
     * @return
     */
    public List<HzCfg0ColorSet> doGetAll() {
        return hzCfg0ColorSetDao.selectAll(new HzColorSetQuery());
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
        return hzCfg0ColorSetDao.deleteByBatch(entity) > 0 ? true : false;
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
