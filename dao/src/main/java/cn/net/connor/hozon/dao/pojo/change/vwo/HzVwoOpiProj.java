/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.pojo.change.vwo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */

@Data
public class HzVwoOpiProj {
    /**
     * 主键
     */
    private Long id;
    /**
     * vwo外键
     */
    private Long opiVwoId;
    /**
     * 项目经理意见
     */
    private String opiProjMngOpinion;
    /**
     * 项目经理是否同意
     */
    private Integer opiProjMngAgreement;
    /**
     * 项目经理决策日期
     */
    private Date opiProjMngOptionDate;
    /**
     * 项目经理决策说明
     */
    private String opiProjMngComment;
    /**
     * 创建时间
     */
    private Date opiProjMngCreateDate;
    /**
     * 更新时间
     */
    private Date opiProjMngUpdateDate;
    /**
     * 创建人
     */
    private String opiProjMngCreator;
    /**
     * 更新人
     */
    private String opiProjMngUpdater;
    /**
     * 状态，没到这个人决策则不会出现选择项
     */
    private Integer opiProjMngStage;

    /**
     *PTM经理ID
     */
    private Long opiProjMngUserId;
    /**
     * PTM经理用户名
     */
    private String opiProjMngUserName;
}