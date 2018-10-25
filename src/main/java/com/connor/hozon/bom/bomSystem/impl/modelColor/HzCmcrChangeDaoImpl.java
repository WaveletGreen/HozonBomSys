/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.option.ChangeCmcrOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.modelColor.HzCmcrChange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCmcrChange")
public class HzCmcrChangeDaoImpl extends BasicDaoImpl<HzCmcrChange> implements HzCmcrChangeDao {

    private final static HzCmcrChange CHANGE_POJO = new HzCmcrChange();
    private final static Logger LOGGER = LoggerFactory.getLogger(HzCmcrChangeDaoImpl.class);

    public HzCmcrChangeDaoImpl() {
        clz = HzCmcrChangeDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 插入变更后数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    public Long insertAfter(HzCmcrChange cmcr) throws Exception {
        preSetAfter(cmcr);
        return executeInsert(cmcr, "insert");

    }


    /**
     * 插入变更前数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    public Long insertBefore(HzCmcrChange cmcr) throws Exception {
        preSetBefore(cmcr);
        return executeInsert(cmcr, "insert");
    }

    /**
     * 插入变更后数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    @Override
    public Long insertAfterSelective(HzCmcrChange cmcr) throws Exception {
        preSetAfter(cmcr);
        return executeInsert(cmcr, "insertSelective");

    }

    /**
     * 插入变更前数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    @Override
    public Long insertBeforeSelective(HzCmcrChange cmcr) throws Exception {
        preSetBefore(cmcr);
        return executeInsert(cmcr, "insertSelective");
    }

    /**
     * 变更后数据主键查找
     *
     * @param id 主键
     * @return 变更对象
     */
    @Override
    public HzCmcrChange selectAfterByPrimaryKey(Long id) throws Exception {
        preSetAfter(CHANGE_POJO);
        CHANGE_POJO.setCmcrCgId(id);
        return super.selectByPrimaryKey(CHANGE_POJO);
    }

    /**
     * 变更前数据主键查找
     *
     * @param id 主键
     * @return 变更对象
     */
    @Override
    public HzCmcrChange selectBeforeByPrimaryKey(Long id) throws Exception {
        preSetBefore(CHANGE_POJO);
        CHANGE_POJO.setCmcrCgId(id);
        return super.selectByPrimaryKey(CHANGE_POJO);
    }

    /**
     * 主键删除变更后数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Override
    public int deleteAfterByPrimaryKey(Long id) throws Exception {
        preSetAfter(CHANGE_POJO);
        CHANGE_POJO.setCmcrCgId(id);
        return super.deleteByPrimaryKey(CHANGE_POJO);
    }

    /**
     * 主键删除变更前数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Override
    public int deleteBeforeByPrimaryKey(Long id) throws Exception {
        preSetBefore(CHANGE_POJO);
        CHANGE_POJO.setCmcrCgId(id);
        return super.deleteByPrimaryKey(CHANGE_POJO);
    }

    /**
     * 主键更新变更后数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    @Override
    public int updateAfterByPrimaryKey(HzCmcrChange cmcr) throws Exception {
        preSetAfter(cmcr);
        return super.updateByPrimaryKey(cmcr);
    }

    /**
     * 主键更新变更前数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    @Override
    public int updateBeforeByPrimaryKey(HzCmcrChange cmcr) throws Exception {
        preSetBefore(cmcr);
        return super.updateByPrimaryKey(cmcr);
    }

    /**
     * 主键选择更新变更前数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    @Override
    public int updateAfterByPrimaryKeySelective(HzCmcrChange cmcr) throws Exception {
        preSetAfter(cmcr);
        return super.updateByPrimaryKey(cmcr);
    }

    /**
     * 主键选择更新变更后数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    @Override
    public int updateBeforeByPrimaryKeySelective(HzCmcrChange cmcr) throws Exception {
        preSetBefore(cmcr);
        return super.updateByPrimaryKey(cmcr);
    }


    @Override
    public int insertAfterList(List<HzCmcrChange> hzCmcrChangesAfter) throws Exception{
        preSetAfterList(hzCmcrChangesAfter);
        return executeInsertList(hzCmcrChangesAfter, "insertList");
    }

    @Override
    public int insertBeforeList(List<HzCmcrChange> hzCmcrChangesLastAfter) throws Exception{
        preSetBeforeList(hzCmcrChangesLastAfter);
        return executeInsertList(hzCmcrChangesLastAfter,"insertList");
    }

    @Override
    public List<HzCmcrChange> selectLastAfter(List<HzCmcrChange> hzCmcrChangesLastAfter) throws Exception{
        preSetAfterList(hzCmcrChangesLastAfter);
        return executeSelectLast(hzCmcrChangesLastAfter, "selectLastAfter");
    }

    private List<HzCmcrChange> executeSelectLast(List<HzCmcrChange> hzCmcrChangesLastAfter, String by) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("whichTable",hzCmcrChangesLastAfter.get(0).getWhichTable());
        map.put("mainId",hzCmcrChangesLastAfter.get(0).getCmcrSrcMainCfg());
        map.put("hzCmcrChangesLastAfter",hzCmcrChangesLastAfter);
        return baseSQLUtil.executeQueryByPass(new HzCmcrChange(), map,clzName+"."+by);
    }

    @Deprecated
    @Override
    public int deleteByPrimaryKey(HzCmcrChange hzCmcrChange) {
        try {
            if (ChangeCmcrOptions.validateMainTable(hzCmcrChange)) {
                return super.deleteByPrimaryKey(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Deprecated
    @Override
    public int insert(HzCmcrChange hzCmcrChange) {
        try {
            if (ChangeCmcrOptions.validateMainTable(hzCmcrChange)) {
                return super.insert(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Deprecated
    @Override
    public int insertSelective(HzCmcrChange hzCmcrChange) {
        try {
            if (ChangeCmcrOptions.validateMainTable(hzCmcrChange)) {
                return super.insertSelective(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Deprecated
    @Override
    public HzCmcrChange selectByPrimaryKey(HzCmcrChange hzCmcrChange) {
        try {
            if (ChangeCmcrOptions.validateMainTable(hzCmcrChange)) {
                return super.selectByPrimaryKey(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    @Override
    public int updateByPrimaryKeySelective(HzCmcrChange hzCmcrChange) {
        try {
            if (ChangeCmcrOptions.validateMainTable(hzCmcrChange)) {
                return super.updateByPrimaryKeySelective(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Deprecated
    @Override
    public int updateByPrimaryKey(HzCmcrChange hzCmcrChange) {
        try {
            if (ChangeCmcrOptions.validateMainTable(hzCmcrChange)) {
                return super.updateByPrimaryKey(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 执行插入指令
     *
     * @param cmcr 变更主数据对象
     * @param by   执行语句
     * @return 对象的主键
     */
    private Long executeInsert(HzCmcrChange cmcr, String by) {
        baseSQLUtil.executeInsert(cmcr,
                clzName + "." + by);
        if (cmcr != null)
            return cmcr.getCmcrCgId();
        else {
            return -1L;
        }
    }

    /**
     * 执行批量插入指令
     *
     * @param HzCmcrChangeList 变更主数据对象
     * @param by   执行语句
     * @return 对象的主键
     */
    private int executeInsertList(List<HzCmcrChange> HzCmcrChangeList, String by) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("whichTable",HzCmcrChangeList.get(0).getWhichTable());
        map.put("seqName",HzCmcrChangeList.get(0).getSeqName());
        map.put("hzCmcrChangeList",HzCmcrChangeList);
        return baseSQLUtil.executeInsert(map,
                clzName + "." + by);

    }

    private void preSetAfter(HzCmcrChange cmcr) throws Exception {
        if (cmcr == null) {
            Exception e = new Exception("变更对象不能为空");
            LOGGER.error("变更对象不能为空", e);
            throw e;
        }
        cmcr.setWhichTable(ChangeCmcrOptions.AFTER_TABLE.getDesc());
        cmcr.setSeqName(ChangeCmcrOptions.AFTER_SEQ.getDesc());
    }

    private void preSetAfterList(List<HzCmcrChange> cmcr) throws Exception {
        if (cmcr == null||cmcr.size()==0) {
            Exception e = new Exception("变更对象不能为空");
            LOGGER.error("变更对象不能为空", e);
            throw e;
        }
        for(HzCmcrChange hzCmcrChangeAfter : cmcr){
            hzCmcrChangeAfter.setWhichTable(ChangeCmcrOptions.AFTER_TABLE.getDesc());
            hzCmcrChangeAfter.setSeqName(ChangeCmcrOptions.AFTER_SEQ.getDesc());
        }
    }

    private void preSetBefore(HzCmcrChange cmcr) throws Exception {
        if (cmcr == null) {
            Exception e = new Exception("变更对象不能为空");
            LOGGER.error("变更对象不能为空", e);
            throw e;
        }
        cmcr.setWhichTable(ChangeCmcrOptions.BEFORE_TABLE.getDesc());
        cmcr.setSeqName(ChangeCmcrOptions.BEFORE_SEQ.getDesc());
    }

    private void preSetBeforeList(List<HzCmcrChange> hzCmcrChangesLastAfter) throws Exception {
        if (hzCmcrChangesLastAfter == null||hzCmcrChangesLastAfter.size()==0) {
            Exception e = new Exception("变更对象不能为空");
            LOGGER.error("变更对象不能为空", e);
            throw e;
        }
        for(HzCmcrChange hzCmcrChange : hzCmcrChangesLastAfter){
            hzCmcrChange.setWhichTable(ChangeCmcrOptions.BEFORE_TABLE.getDesc());
            hzCmcrChange.setSeqName(ChangeCmcrOptions.BEFORE_SEQ.getDesc());
        }
    }
}
