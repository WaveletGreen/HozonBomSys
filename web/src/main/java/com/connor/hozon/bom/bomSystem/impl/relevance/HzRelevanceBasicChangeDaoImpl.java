/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.relevance;

import com.connor.hozon.bom.bomSystem.dao.relevance.HzRelevanceBasicChangeDao;
import com.connor.hozon.bom.bomSystem.dao.relevance.HzRelevanceBasicDao;
import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryResultBean;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import org.springframework.stereotype.Repository;
import sql.pojo.cfg.relevance.HzRelevanceBasic;
import sql.pojo.cfg.relevance.HzRelevanceBasicChange;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
//@Service
@Repository
public class HzRelevanceBasicChangeDaoImpl extends BasicDaoImpl<HzRelevanceBasic> implements HzRelevanceBasicChangeDao {

    public HzRelevanceBasicChangeDaoImpl(){
        this.clz = HzRelevanceBasicChangeDao.class;
        this.clzName = clz.getCanonicalName()+".";
    }

    @Override
    public int insert(HzRelevanceBasicChange record) {
        return 0;
    }

    @Override
    public int insertSelective(HzRelevanceBasicChange record) {
        return 0;
    }

    @Override
    public int insertList(List<HzRelevanceBasicChange> hzRelevanceBasicChanges) {
        return baseSQLUtil.executeInsert(hzRelevanceBasicChanges,clzName+"insertList");
    }

    @Override
    public List<HzRelevanceBasicChange> selectByOrderChangeId(Long orderChangeId) {
        HzRelevanceBasicChange hzRelevanceBasicChange = new HzRelevanceBasicChange();
        hzRelevanceBasicChange.setChangeOrderId(orderChangeId);
        return baseSQLUtil.executeQuery(hzRelevanceBasicChange,clzName+"selectByOrderChangeId");
    }

    @Override
    public HzRelevanceBasicChange selectMaxVersionByProject(String projectPuid) {
        HzRelevanceBasicChange hzRelevanceBasicChange = new HzRelevanceBasicChange();
        hzRelevanceBasicChange.setRbProjectUid(projectPuid);
        return baseSQLUtil.executeQueryById(hzRelevanceBasicChange,clzName+"selectMaxVersionByProject");
    }

    @Override
    public List<HzRelevanceBasicChange> selectByVersionAndProjectId(HzRelevanceBasicChange hzRelevanceBasicChangeQueryBefor) {
        return baseSQLUtil.executeQuery(hzRelevanceBasicChangeQueryBefor,clzName+"selectByVersionAndProjectId");
    }

    @Override
    public int deleteByChangeOrderid(Long orderChangeId) {
        return baseSQLUtil.executeDelete(orderChangeId,clzName+"deleteByChangeOrderid");
    }

    @Override
    public List<HzRelevanceBasicChange> selectLastexecutedByProjectId(String projectPuid) {
        return baseSQLUtil.executeQueryByPass(new HzRelevanceBasicChange(), projectPuid,clzName+"selectLastexecutedByProjectId");
    }

    @Override
    public int updateStatusByIOrderId(HzRelevanceBasicChange hzRelevanceBasicChange) {
        return baseSQLUtil.executeUpdate(hzRelevanceBasicChange,clzName+"updateStatusByIOrderId");
    }

    @Override
    public HzRelevanceBasicChange selectByLasteBySrc(HzRelevanceBasic hzRelevanceBasic) {
        HzRelevanceBasicChange hzRelevanceBasicChange = new HzRelevanceBasicChange();
        hzRelevanceBasicChange.setSrcId(hzRelevanceBasic.getId());
        return baseSQLUtil.executeQueryById(hzRelevanceBasicChange ,clzName+"selectByLasteBySrc");
    }

    @Override
    public HzRelevanceBasicChange selectByVersion(HzRelevanceBasicChange hzRelevanceBasicChangeQueryBefor) {
        return baseSQLUtil.executeQueryById(hzRelevanceBasicChangeQueryBefor,clzName+"selectLastVersion");
    }

    @Override
    public HzRelevanceBasicChange selectMaxVersion(HzRelevanceBasicChange hzRelevanceBasicChange) {
        return baseSQLUtil.executeQueryById(hzRelevanceBasicChange,clzName+"selectMaxVersion");
    }
}