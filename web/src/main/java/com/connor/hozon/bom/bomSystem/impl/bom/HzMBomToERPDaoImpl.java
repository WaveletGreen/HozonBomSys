/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.bom;

import com.connor.hozon.bom.bomSystem.dao.bom.HzMBomToERPDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzMBomToERPBean;
import sql.IBaseSQLUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzMBomToERPDao")
public class HzMBomToERPDaoImpl extends BaseSQLUtil implements HzMBomToERPDao {

    /**
     * 根据项目UID和MBOM行的UID列表获取到一组记录
     *
     * @param projectUid 项目的UID
     * @param list       MBOM记录的UID列表
     * @return
     */
    @Override
    public List<HzMBomToERPBean> selectByUidOfBatch(String projectUid, List<String> list) {
        Map<String, Object> _param = new HashMap<>();
        _param.put("projectUid", projectUid);
        _param.put("flag", "T.P_MBOM_PUID");
        _param.put("list", list);
        return super.findForList("HzMBomToERPDaoImpl_selectByUidOfBatch",_param);

    }

    /**
     * 根据项目UID和MBOM行的父层UID列表获取一组父层的数据，需要从@{@link HzMBomToERPBean}中getParentUid获取到父层Uid
     *
     * @param projectUid 项目UID
     * @param list       父层UID列表
     * @return
     */
    @Override
    public List<HzMBomToERPBean> selectbyParentUidOfBatch(String projectUid, List<String> list) {
        Map<String, Object> _param = new HashMap<>();
        _param.put("projectUid", projectUid);
        _param.put("flag", "T.p_Ebom_PUID");
        _param.put("list", list);
        return super.findForList("HzMBomToERPDaoImpl_selectByUidOfBatch",_param);

    }

    /**
     * 根据项目UID，筛选2Y层或非2Y层或项目MBOM表下的所有数据
     *
     * @param projectUid 包含项目UID
     * @param is2Y       是否是2Y层，如果要所有，传0和1以外的值即可
     * @return
     */
    @Override
    public List<HzMBomToERPBean> selectByProjectUidWithCondition(String projectUid, Integer is2Y) {
        Map<String, Object> _param = new HashMap<>();
        _param.put("projectUid", projectUid);
        _param.put("is2Y", is2Y);
        return super.findForList("HzMBomToERPDaoImpl_selectByProjectUidWithCondition",_param);
    }
}