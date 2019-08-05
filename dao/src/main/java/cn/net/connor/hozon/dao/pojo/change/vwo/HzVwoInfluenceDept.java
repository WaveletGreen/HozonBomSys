/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.pojo.change.vwo;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: VWO影响部门
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */
@Data
public class HzVwoInfluenceDept {
    /**
     * 主键
     */
    private Long id;
    /**
     * 智能驾驶研究院，Intelligent driving Research Institute
     */
    private Integer vwoIdri;
    /**
     * 智能座舱事业部,Intelligent cockpit business unit
     */
    private Integer vwoIcbu;
    /**
     * 电动化底盘中心,
     * <p>
     * Motorized chassis Center
     */
    private Integer vwoMcc;
    /**
     * 电子电器中心
     * <p>
     * Electronic and electrical Center
     */
    private Integer vwoEec;
    /**
     * 内外饰中心,Interior decoration Center
     */
    private Integer vwoIdc;
    /**
     * 动总集成中心 Dynamic integration center
     */
    private Integer vwoDic;
    /**
     * 车身中心 Body center
     */
    private Integer vwoVbc;
    /**
     * VWO的ID，外键
     */
    private Long vwoId;

}