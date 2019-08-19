/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.service.change.vwo.impl;

import cn.net.connor.hozon.common.util.DateStringUtils;
import cn.net.connor.hozon.dao.dao.change.vwo.HzVwoInfoDao;
import cn.net.connor.hozon.dao.pojo.change.vwo.HzVwoInfo;
import cn.net.connor.hozon.dao.query.change.vwo.HzVwoFormListQuery;
import com.connor.hozon.bom.service.change.vwo.HzVwoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Service
public class HzVwoInfoServiceImpl implements HzVwoInfoService {
    @Autowired
    private HzVwoInfoDao hzVwoInfoDao;

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean doDeleteByPrimaryKey(Long id) {
        return hzVwoInfoDao.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    @Override
    public Long doInsert(HzVwoInfo record)  {
        hzVwoInfoDao.insert(record);
        return record.getId();
    }

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    @Override
    public HzVwoInfo doSelectByPrimaryKey(Long id) {
        return hzVwoInfoDao.selectByPrimaryKey(id);
    }

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    @Override
    public boolean doUpdateByPrimaryKey(HzVwoInfo record)  {
        return hzVwoInfoDao.updateByPrimaryKey(record) > 0 ? true : false;
    }

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    @Override
    public HzVwoInfo doFindMaxAreaVwoNum() {
        return hzVwoInfoDao.findMaxAreaVwoNum();
    }

    /**
     * 根据分页进行查询
     *
     * @param queryBase
     */
    @Override
    public List<HzVwoInfo> doSelectListByProjectUid(HzVwoFormListQuery queryBase, String projectUid) {
        Map<String, Object> params = new HashMap<>();
        params.put("param", queryBase);
        params.put("projectUid", projectUid);
        return hzVwoInfoDao.selectListByProjectUid(params);
    }

    /**
     * 获取总数
     *
     * @param projectUid
     * @return
     */
    @Override
    public int count(String projectUid) {
        return hzVwoInfoDao.count(projectUid);
    }

    /**
     * 生成VWO号码
     */
    @Override
    public HzVwoInfo generateVWONum() {
        Date now = new Date();
        HzVwoInfo hzVwoInfo = doFindMaxAreaVwoNum();
        if (hzVwoInfo == null || hzVwoInfo.getVwoNum() == null) {
            hzVwoInfo = new HzVwoInfo();
            //当月第一位vwo号
            hzVwoInfo.setVwoNum("VC" + DateStringUtils.dateToString4(now) + "0001");
        } else {
            hzVwoInfo.setVwoNum("VC" + String.valueOf(Long.parseLong(hzVwoInfo.getVwoNum().substring(2)) + 1));
        }
        return hzVwoInfo;
    }

    /**
     * 配色方案vwo发布
     * @param info
     * @return
     */
    public boolean updateByVwoId(HzVwoInfo info) {
        if(hzVwoInfoDao.updateByVwoId(info)==1){
            return true;
        }else {
            return false;
        }
    }
}
