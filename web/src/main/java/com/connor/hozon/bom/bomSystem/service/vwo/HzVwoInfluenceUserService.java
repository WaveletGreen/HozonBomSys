/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.vwo;

import cn.net.connor.hozon.dao.dao.change.vwo.HzVwoInfluenceUserDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.vwo.IHzVwoInfluenceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInfluenceUser;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/20 16:21
 * @Modified By:
 */
@Service("hzVwoInfluenceUserService")
public class HzVwoInfluenceUserService implements IHzVwoInfluenceUserService {
    @Autowired
    HzVwoInfluenceUserDao hzVwoInfluenceUserDao;

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public int doDeleteByPrimaryKey(Long id) {
        return hzVwoInfluenceUserDao.deleteByPrimaryKey(id);
    }

    /**
     * 全插入
     *
     * @param record
     * @return
     */
    @Override
    public int doInsert(HzVwoInfluenceUser record) {
        return hzVwoInfluenceUserDao.insert(record);
    }

    /**
     * 非空插入
     *
     * @param record
     * @return
     */
    @Override
    public int doInsertSelective(HzVwoInfluenceUser record) {
        return hzVwoInfluenceUserDao.insertSelective(record);
    }

    /**
     * 主键查询
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfluenceUser doSelectByPrimaryKey(Long id) {
        return hzVwoInfluenceUserDao.selectByPrimaryKey(id);
    }

    /**
     * 根据VWO主键进行查询
     *
     * @param vwoId
     * @return
     */
    @Override
    public HzVwoInfluenceUser doSelectByVwoId(Long vwoId) {
        return hzVwoInfluenceUserDao.selectByVwoId(vwoId);
    }

    /**
     * 主键+非空更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKeySelective(HzVwoInfluenceUser record) {
        return hzVwoInfluenceUserDao.updateByPrimaryKeySelective(record);
    }

    /**
     * 全更新
     *
     * @param record
     * @return
     */
    @Override
    public int doUpdateByPrimaryKey(HzVwoInfluenceUser record) {
        return hzVwoInfluenceUserDao.updateByPrimaryKey(record);
    }
}
