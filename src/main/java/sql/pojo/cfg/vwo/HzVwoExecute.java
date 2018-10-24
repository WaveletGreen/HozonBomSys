/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.vwo;

import lombok.Data;

import java.util.Date;
/**
 * @Author: Fancyears·Maylos·Mayways
 * @Date :  2018/10/15 15:58
 * @Description: VWO表单分发与实施对象 x
 * @Modified By:
 */
@Data
public class HzVwoExecute {
    /**
     * 主键
     */
    private Long exeId;
    /**
     * VWO号外键
     */
    private Long exeVwoId;
    /**
     * 部门
     */
    private String exeDept;
    /**
     * 人员
     */
    private String exeUser;
    /**
     * 角色
     */
    private String exeRole;
    /**
     * 任务
     */
    private String exeMission;
    /**
     * 计划完成时间
     */
    private Date exePlanFinishDate;
    /**
     * 状态
     */
    private String exeStatus;
    /**
     * 证明
     */
    private String exeProof;
    /**
     * 创建时间
     */
    private Date exeCreateDate;
    /**
     * 创建人
     */
    private String exeCreator;
    /**
     * 更新时间
     */
    private Date exeUpdateDate;
    /**
     * 更新人
     */
    private String exeUpdater;
    /**
     * 用户的id，保留，该id是在mysql中的，无法进行级联删除
     */
    private Long exeUserId;
    /**
     * 保留字段
     */
    private String exeReserve2;
    /**
     * 保留字段
     */
    private String exeReserve3;
    /**
     * 保留字段
     */
    private String exeReserve4;
    /**
     * 保留字段
     */
    private String exeReserve5;
    /**
     * 保留字段
     */
    private String exeReserve6;
    /**
     * 保留字段
     */
    private String exeReserve7;
    /**
     * 保留字段
     */
    private String exeReserve8;
    /**
     * 保留字段
     */
    private String exeReserve9;
    /**
     * 保留字段
     */
    private String exeReserve10;

}