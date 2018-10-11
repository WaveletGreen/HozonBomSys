/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.modelColor;

import com.connor.hozon.bom.bomSystem.dao.modelColor.HzCmcrChangeDao;
import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.option.ChangeCmcrOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sql.pojo.cfg.modelColor.HzCmcrChange;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/10/11 10:03
 * @Modified By:
 */
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

    @Deprecated
    @Override
    public int deleteByPrimaryKey(HzCmcrChange hzCmcrChange) {
        try {
            if (ChangeCmcrOption.validateMainTable(hzCmcrChange)) {
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
            if (ChangeCmcrOption.validateMainTable(hzCmcrChange)) {
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
            if (ChangeCmcrOption.validateMainTable(hzCmcrChange)) {
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
            if (ChangeCmcrOption.validateMainTable(hzCmcrChange)) {
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
            if (ChangeCmcrOption.validateMainTable(hzCmcrChange)) {
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
            if (ChangeCmcrOption.validateMainTable(hzCmcrChange)) {
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

    private void preSetAfter(HzCmcrChange cmcr) throws Exception {
        if (cmcr == null) {
            Exception e = new Exception("变更对象不能为空");
            LOGGER.error("变更对象不能为空", e);
            throw e;
        }
        cmcr.setWhichTable(ChangeCmcrOption.AFTER_TABLE.getDesc());
        cmcr.setSeqName(ChangeCmcrOption.AFTER_SEQ.getDesc());
    }

    private void preSetBefore(HzCmcrChange cmcr) throws Exception {
        if (cmcr == null) {
            Exception e = new Exception("变更对象不能为空");
            LOGGER.error("变更对象不能为空", e);
            throw e;
        }
        cmcr.setWhichTable(ChangeCmcrOption.BEFORE_TABLE.getDesc());
        cmcr.setSeqName(ChangeCmcrOption.BEFORE_SEQ.getDesc());
    }
}
