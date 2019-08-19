/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.change.vwo.impl;

import cn.net.connor.hozon.dao.dao.change.vwo.HzVwoInformChangesDao;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInformChanges;
import com.connor.hozon.bom.service.change.vwo.HzVwoInformChangesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/15 16:37
 * @Modified By:
 */
@Service
public class HzVwoInformChangesServiceImpl implements HzVwoInformChangesService {
    @Autowired
    HzVwoInformChangesDao hzVwoInformChangesDao;

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int doDeleteByPrimaryKey(Long id) {
        return hzVwoInformChangesDao.deleteByPrimaryKey(id);
    }

    /**
     * 插入1条数据
     *
     * @param record
     * @return
     */
    @Override
    public int doInsert(HzVwoInformChanges record) {
        return hzVwoInformChangesDao.insert(record);
    }

    /**
     * 选择性插入
     *
     * @param record
     * @return
     */
    @Override
    public int doInsertSelective(HzVwoInformChanges record) {
        return hzVwoInformChangesDao.insertSelective(record);
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInformChanges doSelectByPrimaryKey(Long id) {
        return hzVwoInformChangesDao.selectByPrimaryKey(id);
    }

    /**
     * 根据VWO主键查询所有关联的人员
     *
     * @param vwoId
     * @return
     */
    @Override
    public List<HzVwoInformChanges> doSelectByVwoId(Long vwoId) {
        return hzVwoInformChangesDao.selectByVwoId(vwoId);
    }

    /**
     * 选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKeySelective(HzVwoInformChanges record) {
        return hzVwoInformChangesDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 主键更新，无选择性更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKey(HzVwoInformChanges record) {
        return hzVwoInformChangesDao.updateByPrimaryKey(record);
    }

    /**
     * 获取VWO号下的总关联数
     *
     * @param vwoId
     * @return
     */
    @Override
    public Long tellMeHowManyOfIt(Long vwoId) {
        return hzVwoInformChangesDao.count(vwoId);
    }
}
