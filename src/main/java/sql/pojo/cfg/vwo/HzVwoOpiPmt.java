/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.vwo;

import lombok.Data;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */
@Data
public class HzVwoOpiPmt {
    /**
     * 主键
     */
    private Long id;
    /**
     * 主键
     */
    private Long opiVwoId;
    /**
     * 主键
     */
    private String opiPmtMngOpinion;
    /**
     * 主键
     */
    private Integer opiPmtMngAgreement;
    /**
     * 主键
     */
    private Date opiPmtMngOptionDate;
    /**
     * 主键
     */
    private String opiPmtMngComment;
    /**
     * 主键
     */
    private Date opiPmtMngCreateDate;
    /**
     * 主键
     */
    private Date opiPmtMngUpdateDate;
    /**
     * 主键
     */
    private String opiPmtMngCreator;
    /**
     * 主键
     */
    private String opiPmtMngUpdater;
    /**
     * 主键
     */
    private Integer opiPmtMngStage;

    /**
     *PTM经理ID
     */
    private Long opiPmtMngUserId;
    /**
     * PTM经理用户名
     */
    private String opiPmtMngUserName;
}