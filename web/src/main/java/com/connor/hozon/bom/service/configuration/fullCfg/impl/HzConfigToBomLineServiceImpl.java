/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.configuration.fullCfg.impl;

import cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet.HzConfigToBomLineDao;
import com.connor.hozon.bom.service.configuration.fullCfg.HzConfigToBomLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzConfigToBomLine;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public class HzConfigToBomLineServiceImpl implements HzConfigToBomLineService {
    @Autowired
    HzConfigToBomLineDao hzConfigToBomLineDao;

    /**
     * 主键删除
     *
     * @param puid 主键值
     * @return
     */
    @Override
    public boolean deleteByPrimaryKey(String puid) {
        return hzConfigToBomLineDao.deleteByPrimaryKey(puid) > 0 ? true : false;
    }

    /**
     * 插入单条数据
     *
     * @param record 配置值对应的bom行
     * @return
     */
    @Override
    public boolean insert(HzConfigToBomLine record) {
        return hzConfigToBomLineDao.insert(record) > 0 ? true : false;
    }

    /**
     * 根据主键搜索
     *
     * @param puid 主键值
     * @return
     */
    @Override
    public HzConfigToBomLine selectByPrimaryKey(String puid) {
        return hzConfigToBomLineDao.selectByPrimaryKey(puid);
    }

    /**
     * 根据主键更新
     *
     * @param record @{@link HzConfigToBomLine}
     * @return
     */
    @Override
    public boolean updateByPrimaryKey(HzConfigToBomLine record) {
        return hzConfigToBomLineDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 批量插入
     *
     * @param records @{@link HzConfigToBomLine}的集合，进行批量插入
     * @return
     */
    @Override
    public boolean insertByBatch(List<HzConfigToBomLine> records) {
        return hzConfigToBomLineDao.insertByBatch(records) > 0 ? true : false;
    }

    /**
     * 根据项目ID和Bomline UID查找一条配置+BOMLine关联数据
     *
     * @param projectUid 项目UID
     * @param bomLineUid BOM行UID
     * @return
     */
    @Override
    public HzConfigToBomLine selectByBLUidAndPrjUid(String projectUid, String bomLineUid) {
        HzConfigToBomLine record = new HzConfigToBomLine();
        record.setProjectUid(projectUid);
        record.setBomLineId(bomLineUid);
        return hzConfigToBomLineDao.selectByBLUidAndPrjUid(record);
    }
}
