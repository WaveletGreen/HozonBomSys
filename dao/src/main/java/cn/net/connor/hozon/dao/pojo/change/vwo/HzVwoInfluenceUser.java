/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.pojo.change.vwo;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: VWO影响人员
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */

@Data
public class HzVwoInfluenceUser {
    /**
     * 主键
     */
    private Long id;
    /**
     * vwo id，外键
     */
    private Long vwoId;
    /**
     * 专业科长名,Professional section chief
     */
    private String vwoProSectionChiefName;
    /**
     * 专业科长ID,Professional section chief
     */
    private Long vwoProSectionChiefId;
    /**
     * 车身人员ID
     */
    private Long vwoVehicleBodyUserId;
    /**
     * 车身人员名
     */
    private String vwoVehicleBodyUserName;
    /**
     * 电器人员ID
     */
    private Long vwoElectricApplianceId;
    /**
     * 电器人员名
     */
    private String vwoElectricApplianceName;
    /**
     * 项目经理ID
     */
    private Long vwoProjectManagerId;
    /**
     * 项目经理名
     */
    private String vwoProjectManagerName;
    /**
     * 变更协调员ID
     */
    private Long vwoChangeCoordinatorId;
    /**
     * 变更协调员名
     */
    private String vwoChangeCoordinatorName;
    /**
     * 底盘人员ID
     */
    private Long vwoChassisId;
    /**
     * 底盘人员名
     */
    private String vwoChassisName;
    /**
     * 内外饰人员ID、Interior and exterior ornaments
     */
    private Long vwoIeoId;
    /**
     * 内外饰人员名
     */
    private String vwoIeoName;
    /**
     * 选择项的ID
     */
    private Integer selectId;
    /**
     * 选则的用户ID
     */
    private Long selectedUserId;

    /**
     * 将前端传输过来的选择类型映射到具体的字段上
     * @param selectType
     * @return
     */
    public static String getNameFromType(Integer selectType) {
        switch (selectType) {
            case 1:
                return "vwoProSectionChief";
            case 2:
                return "vwoChangeCoordinator";
            case 3:
                return "vwoVehicleBodyUser";
            case 4:
                return "vwoChassis";
            case 5:
                return "vwoElectricAppliance";
            case 6:
                return "vwoIeo";
            case 7:
                return "vwoProjectManager";
            default:
                return null;
        }
    }
}
