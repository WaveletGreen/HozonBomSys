/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.vwo;

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
    private Long id;

    private Long opiVwoId;

    private String opiProjMngOpinion;

    private Integer opiProjMngAgreement;

    private Date opiProjMngOptionDate;

    private String opiProjMngComment;

    private Date opiProjMngCreateDate;

    private Date opiProjMngUpdateDate;

    private String opiProjMngCreator;

    private String opiProjMngUpdater;

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