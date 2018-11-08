/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.option;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sql.pojo.cfg.modelColor.HzCmcrChange;
import sql.pojo.cfg.modelColor.HzCmcrDetailChange;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
public enum ChangeCmcrOptions {
    /**
     * CMCR变更后表名
     */
    AFTER_TABLE("HZ_CMCR_AFTER_CHANGE"),
    /***
     * CMCR变更后序列
     */
    AFTER_SEQ("HZ_SEQ_CMCR_AF_CG_ID"),
    /**
     * CMCR变更前表名
     */
    BEFORE_TABLE("HZ_CMCR_BEFORE_CHANGE"),
    /***
     * CMCR变更前序列
     */
    BEFORE_SEQ("HZ_SEQ_CMCR_BF_CG_ID"),
    /**
     * CMCR详情变更后表名
     */
    DETAIL_AFTER_TABLE("HZ_CMCR_DETAIL_AFTER_CHANGE"),
    /***
     * CMCR详情变更后序列
     */
    DETAIL_AFTER_SEQ("HZ_SEQ_CMCR_DET_AF_CG_ID"),
    /**
     * CMCR详情变更前表名
     */
    DETAIL_BEFORE_TABLE("HZ_CMCR_DETAIL_BEFORE_CHANGE"),
    /***
     * CMCR详情变更前序列
     */
    DETAIL_BEFORE_SEQ("HZ_SEQ_CMCR_DET_BF_CG_ID");
    // 成员变量
    @Getter
    private String desc;

    public final static Set<String> MAIN_TABLE_LIST;
    public final static Set<String> DETAIL_TABLE_LIST;

    private final static Logger LOGGER = LoggerFactory.getLogger(ChangeCmcrOptions.class);

    static {
        MAIN_TABLE_LIST = new HashSet<>();
        DETAIL_TABLE_LIST = new HashSet<>();

        MAIN_TABLE_LIST.add(ChangeCmcrOptions.AFTER_TABLE.desc);
        MAIN_TABLE_LIST.add(ChangeCmcrOptions.BEFORE_TABLE.desc);

        DETAIL_TABLE_LIST.add(ChangeCmcrOptions.DETAIL_AFTER_TABLE.desc);
        DETAIL_TABLE_LIST.add(ChangeCmcrOptions.DETAIL_BEFORE_TABLE.desc);
    }

    // 构造方法
    ChangeCmcrOptions(String desc) {
        this.desc = desc;
    }

    public static boolean validateMainTable(HzCmcrChange change) throws Exception {
        Exception e;
        if (change == null) {
            return false;
        }
        if (!MAIN_TABLE_LIST.contains(change.getWhichTable())) {
            e = new Exception("错误，请规范使用变更枚举，主数据枚举使用错误");
            LOGGER.error("枚举使用错误", e);
            throw e;
        }
        return true;
    }

    public static boolean validateDetailTable(HzCmcrDetailChange tableName) throws Exception {
        Exception e;
        if (tableName == null) {
            return false;
        }
        if (!DETAIL_TABLE_LIST.contains(tableName)) {
            e = new Exception("错误，请规范使用变更枚举，从数据枚举使用错误");
            LOGGER.error("枚举使用错误", e);
            throw e;
        }
        return true;
    }
}
