/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dto.vwo;

import cn.net.connor.hozon.common.entity.QueryBase;
import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: VWO列表页面查询助手
 * @Date: Created in 2018/10/17 9:16
 * @Modified By:
 */
@Data
public class HzVwoFormListQueryBase extends QueryBase{
    /**
     * VWO编号
     */
    private String vwoNum;
    /**
     * 流程创建时间
     */
    private String vwoCreateDate;
    /**
     * vwo状态，1：发起状态，可进行编辑，100：进入审核状态，等待人员审核/人员正在审核,，999：已发布；899：已终止
     */
    private Integer vwoStatus;
    /**
     * vwo名称，即标题
     */
    private String vwoName;
    /**
     * 用户部门名
     */
    private String userDeptName;
    /**
     * 流程发起者
     */
    private String vwoCreator;
    /**
     * 联系电话
     */
    private String contactPhoneNum;

    /**
     * 要求完成时间
     */
    private String vwoDemandFinishTime;
    /**
     * 变更类型
     */
    private Integer vwoChangeType;
    /**
     * 费用承担部门
     */
    private String vwoCostBearingDept;
    /**
     * 上市类型
     */
    private Integer vwoListedType;
    /**
     * 关联的VWO号
     */
    private String vwoConnectedVwo;

}
