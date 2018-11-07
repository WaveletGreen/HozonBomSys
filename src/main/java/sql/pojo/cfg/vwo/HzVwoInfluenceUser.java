/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.vwo;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: VWO影响人员
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVwoId() {
        return vwoId;
    }

    public void setVwoId(Long vwoId) {
        this.vwoId = vwoId;
    }

    public String getVwoProSectionChiefName() {
        return vwoProSectionChiefName;
    }

    public void setVwoProSectionChiefName(String vwoProSectionChiefName) {
        this.vwoProSectionChiefName = vwoProSectionChiefName;
    }

    public Long getVwoProSectionChiefId() {
        return vwoProSectionChiefId;
    }

    public void setVwoProSectionChiefId(Long vwoProSectionChiefId) {
        this.vwoProSectionChiefId = vwoProSectionChiefId;
    }

    public Long getVwoVehicleBodyUserId() {
        return vwoVehicleBodyUserId;
    }

    public void setVwoVehicleBodyUserId(Long vwoVehicleBodyUserId) {
        this.vwoVehicleBodyUserId = vwoVehicleBodyUserId;
    }

    public String getVwoVehicleBodyUserName() {
        return vwoVehicleBodyUserName;
    }

    public void setVwoVehicleBodyUserName(String vwoVehicleBodyUserName) {
        this.vwoVehicleBodyUserName = vwoVehicleBodyUserName;
    }

    public Long getVwoElectricApplianceId() {
        return vwoElectricApplianceId;
    }

    public void setVwoElectricApplianceId(Long vwoElectricApplianceId) {
        this.vwoElectricApplianceId = vwoElectricApplianceId;
    }

    public String getVwoElectricApplianceName() {
        return vwoElectricApplianceName;
    }

    public void setVwoElectricApplianceName(String vwoElectricApplianceName) {
        this.vwoElectricApplianceName = vwoElectricApplianceName;
    }

    public Long getVwoProjectManagerId() {
        return vwoProjectManagerId;
    }

    public void setVwoProjectManagerId(Long vwoProjectManagerId) {
        this.vwoProjectManagerId = vwoProjectManagerId;
    }

    public String getVwoProjectManagerName() {
        return vwoProjectManagerName;
    }

    public void setVwoProjectManagerName(String vwoProjectManagerName) {
        this.vwoProjectManagerName = vwoProjectManagerName;
    }

    public Long getVwoChangeCoordinatorId() {
        return vwoChangeCoordinatorId;
    }

    public void setVwoChangeCoordinatorId(Long vwoChangeCoordinatorId) {
        this.vwoChangeCoordinatorId = vwoChangeCoordinatorId;
    }

    public String getVwoChangeCoordinatorName() {
        return vwoChangeCoordinatorName;
    }

    public void setVwoChangeCoordinatorName(String vwoChangeCoordinatorName) {
        this.vwoChangeCoordinatorName = vwoChangeCoordinatorName;
    }

    public Long getVwoChassisId() {
        return vwoChassisId;
    }

    public void setVwoChassisId(Long vwoChassisId) {
        this.vwoChassisId = vwoChassisId;
    }

    public String getVwoChassisName() {
        return vwoChassisName;
    }

    public void setVwoChassisName(String vwoChassisName) {
        this.vwoChassisName = vwoChassisName;
    }

    public Long getVwoIeoId() {
        return vwoIeoId;
    }

    public void setVwoIeoId(Long vwoIeoId) {
        this.vwoIeoId = vwoIeoId;
    }

    public String getVwoIeoName() {
        return vwoIeoName;
    }

    public void setVwoIeoName(String vwoIeoName) {
        this.vwoIeoName = vwoIeoName;
    }

    public Integer getSelectId() {
        return selectId;
    }

    public void setSelectId(Integer selectId) {
        this.selectId = selectId;
    }

    public Long getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(Long selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

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
