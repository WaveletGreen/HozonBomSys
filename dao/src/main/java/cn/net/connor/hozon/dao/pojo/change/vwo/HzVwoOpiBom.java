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
public class HzVwoOpiBom {
    /**
     * 主键
     */
    private Long id;
    /**
     * vwo号
     */
    private Long opiVwoId;
    /**
     * BOM经理意见
     */
    private String opiBomMngOpinion;
    /**
     * BOM经理抉择
     */
    private Integer opiBomMngAgreement;
    /**
     * BOM经理抉择日期
     */
    private Date opiBomMngOptionDate;
    /**
     * BOM经理备注
     */
    private String opiBomMngComment;
    /**
     * 创建时间
     */
    private Date opiBomMngCreateDate;
    /**
     * 修改时间
     */
    private Date opiBomMngUpdateDate;
    /**
     * 主键opiBomMngCmcrCreator
     */
    private String opiBomMngCreator;
    /**
     * 主键
     */
    private String opiBomMngUpdater;
    /**
     * 主键
     */
    private Integer opiBomMngStage;
    /**
     *BOM经理ID
     */
    private Long opiBomMngUserId;
    /**
     * BOM经理用户名
     */
    private String opiBomMngUserName;
}