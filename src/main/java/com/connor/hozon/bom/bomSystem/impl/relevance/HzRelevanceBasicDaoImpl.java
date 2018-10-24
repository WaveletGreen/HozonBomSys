/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.relevance;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.relevance.HzRelevanceBasicDao;
import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryDTO;
import com.connor.hozon.bom.bomSystem.dto.relevance.HzRelevanceQueryResultBean;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.relevance.HzRelevanceBasic;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public class HzRelevanceBasicDaoImpl extends BasicDaoImpl<HzRelevanceBasic> implements HzRelevanceBasicDao {
    private final static HzRelevanceBasic BASIC = new HzRelevanceBasic();
    private final static HzRelevanceQueryResultBean BEAN = new HzRelevanceQueryResultBean();

    public HzRelevanceBasicDaoImpl() {
        clz = HzRelevanceBasicDao.class;
    }

    /**
     * 删除项目下的全部相关性
     *
     * @param rbProjectUid
     * @return
     */
    @Override
    public int deleteByProjectUid(String rbProjectUid) {
        return baseSQLUtil.executeDeleteByPass(rbProjectUid, clz.getCanonicalName() + ".deleteByProjectUid");
    }

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    @Override
    public int insertByBatch(List<HzRelevanceBasic> list) {
        return baseSQLUtil.executeInsert(list, clz.getCanonicalName() + ".insertByBatch");
    }

    @Override
    public Long insertBasic(HzRelevanceBasic hzRelevanceBasic) {
        baseSQLUtil.executeInsert(hzRelevanceBasic, clz.getCanonicalName() + ".insertBasic");
        return hzRelevanceBasic.getId();

    }

    /**
     * 分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public List<HzRelevanceQueryResultBean> selectByPage(HzRelevanceQueryDTO dto) {
        return baseSQLUtil.executeQueryByPass(BEAN, dto, clz.getCanonicalName() + ".selectByPage");
    }

    /**
     * 获取当前项目下的相关性总数
     *
     * @param dto
     * @return
     */
    @Override
    public Integer tellMeHowManyOfIt(HzRelevanceQueryDTO dto) {
        List<Integer> result = baseSQLUtil.executeQueryByPass(new Integer(0), dto, clz.getCanonicalName() + ".tellMeHowManyOfIt");
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            return 0;
        }
    }

}
