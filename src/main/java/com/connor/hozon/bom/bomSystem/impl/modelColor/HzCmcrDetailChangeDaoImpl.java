/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrDetailChangeDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.option.ChangeCmcrOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sql.pojo.cfg.modelColor.HzCmcrDetailChange;
import sql.pojo.cfg.modelColor.HzCmcrDetailChange;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/11 10:04
 * @Modified By:
 */
public class HzCmcrDetailChangeDaoImpl extends BasicDaoImpl<HzCmcrDetailChange> implements HzCmcrDetailChangeDao {
    private final static HzCmcrDetailChange CHANGE_POJO = new HzCmcrDetailChange();

    private final static Logger LOGGER = LoggerFactory.getLogger(BasicDaoImpl.class);

    public HzCmcrDetailChangeDaoImpl() {
        clz = HzCmcrDetailChangeDao.class;
        clzName = clz.getCanonicalName();
    }

    /**
     * 插入变更后数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    public int insertDetailAfter(HzCmcrDetailChange cmcr) throws Exception {
        preSetAfter(cmcr);
        return executeInsert(cmcr, "insert");
    }


    /**
     * 插入变更前数据
     *
     * @param cmcr cmcr对象
     * @return 返回主键
     */
    public int insertDetailBefore(HzCmcrDetailChange cmcr) throws Exception {
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
    public int insertDetailAfterSelective(HzCmcrDetailChange cmcr) throws Exception {
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
    public int insertDetailBeforeSelective(HzCmcrDetailChange cmcr) throws Exception {
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
    public HzCmcrDetailChange selectDetailAfterByPrimaryKey(Long id) throws Exception {
        preSetAfter(CHANGE_POJO);
        CHANGE_POJO.setCmcrDetailCgId(id);
        return super.selectByPrimaryKey(CHANGE_POJO);
    }

    /**
     * 变更前数据主键查找
     *
     * @param id 主键
     * @return 变更对象
     */
    @Override
    public HzCmcrDetailChange selectDetailBeforeByPrimaryKey(Long id) throws Exception {
        preSetBefore(CHANGE_POJO);
        CHANGE_POJO.setCmcrDetailCgId(id);
        return super.selectByPrimaryKey(CHANGE_POJO);
    }

    /**
     * 主键删除变更后数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Override
    public int deleteDetailAfterByPrimaryKey(Long id) throws Exception {
        preSetAfter(CHANGE_POJO);
        CHANGE_POJO.setCmcrDetailCgId(id);
        return super.deleteByPrimaryKey(CHANGE_POJO);
    }

    /**
     * 主键删除变更前数据
     *
     * @param id 主键
     * @return 影响行数
     */
    @Override
    public int deleteDetailBeforeByPrimaryKey(Long id) throws Exception {
        preSetBefore(CHANGE_POJO);
        CHANGE_POJO.setCmcrDetailCgId(id);
        return super.deleteByPrimaryKey(CHANGE_POJO);
    }

    /**
     * 主键更新变更后数据
     *
     * @param cmcr 变更对象
     * @return 影响行数
     */
    @Override
    public int updateDetailAfterByPrimaryKey(HzCmcrDetailChange cmcr) throws Exception {
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
    public int updateDetailBeforeByPrimaryKey(HzCmcrDetailChange cmcr) throws Exception {
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
    public int updateDetailAfterByPrimaryKeySelective(HzCmcrDetailChange cmcr) throws Exception {
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
    public int updateDetailBeforeByPrimaryKeySelective(HzCmcrDetailChange cmcr) throws Exception {
        preSetBefore(cmcr);
        return super.updateByPrimaryKey(cmcr);
    }

    @Deprecated
    @Override
    public int deleteByPrimaryKey(HzCmcrDetailChange hzCmcrChange) {
        try {
            if (ChangeCmcrOption.validateDetailTable(hzCmcrChange)) {
                return super.deleteByPrimaryKey(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Deprecated
    @Override
    public int insert(HzCmcrDetailChange hzCmcrChange) {
        try {
            if (ChangeCmcrOption.validateDetailTable(hzCmcrChange)) {
                return super.insert(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Deprecated
    @Override
    public int insertSelective(HzCmcrDetailChange hzCmcrChange) {
        try {
            if (ChangeCmcrOption.validateDetailTable(hzCmcrChange)) {
                return super.insertSelective(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Deprecated
    @Override
    public HzCmcrDetailChange selectByPrimaryKey(HzCmcrDetailChange hzCmcrChange) {
        try {
            if (ChangeCmcrOption.validateDetailTable(hzCmcrChange)) {
                return super.selectByPrimaryKey(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    @Override
    public int updateByPrimaryKeySelective(HzCmcrDetailChange hzCmcrChange) {
        try {
            if (ChangeCmcrOption.validateDetailTable(hzCmcrChange)) {
                return super.updateByPrimaryKeySelective(hzCmcrChange);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Deprecated
    @Override
    public int updateByPrimaryKey(HzCmcrDetailChange hzCmcrChange) {
        try {
            if (ChangeCmcrOption.validateDetailTable(hzCmcrChange)) {
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
    private int executeInsert(HzCmcrDetailChange cmcr, String by) {
        return baseSQLUtil.executeInsert(cmcr,
                clzName + "." + by);
    }

    private void preSetAfter(HzCmcrDetailChange cmcrd) throws Exception {
        if (cmcrd == null) {
            Exception e = new Exception("变更对象不能为空");
            LOGGER.error("变更对象不能为空", e);
            throw e;
        }
        cmcrd.setWhichTable(ChangeCmcrOption.DETAIL_AFTER_TABLE.getDesc());
        cmcrd.setSeqName(ChangeCmcrOption.DETAIL_AFTER_SEQ.getDesc());
    }

    private void preSetBefore(HzCmcrDetailChange cmcrd) throws Exception {
        if (cmcrd == null) {
            Exception e = new Exception("变更对象不能为空");
            LOGGER.error("变更对象不能为空", e);
            throw e;
        }
        cmcrd.setWhichTable(ChangeCmcrOption.DETAIL_BEFORE_TABLE.getDesc());
        cmcrd.setSeqName(ChangeCmcrOption.DETAIL_BEFORE_SEQ.getDesc());
    }
}
