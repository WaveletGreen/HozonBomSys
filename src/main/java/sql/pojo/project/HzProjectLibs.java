/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.project;

import com.connor.hozon.bom.bomSystem.service.project.IProject;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 项目
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */
public class HzProjectLibs implements IProject {
    private String puid;
    /***
     * 项目名
     */
    private String pProjectName;
    /**
     * 归属车型
     */
    private String pProjectPertainToVehicle;
    /***
     * 项目编号
     */
    private String pProjectCode;
    /***
     * 创建者
     */
    private String pProjectOwningUser;
    /***
     * 创建时间
     */
    private Date pProjectCreateDate;
    /***
     * 最后一次修时间
     */
    private Date pProjectLastModDate;
    /**
     * 停产时间
     */
    private Date pProjectDiscontinuationDate;
    /**
     * 项目经理
     */
    private String pProjectManager;
    /**
     * 最后一次修改人
     */
    private String pProjectLastModifier;
    /**
     * 项目详细信息
     */
    private String pProjectDetail;

    /**
     * 内饰颜色代码
     */
    private String pInColorCode;
    /**
     * 内饰颜色代码
     */
    private String pInColorName;
    /**
     * 内饰颜色名称
     */
    private String pColorCode;
    /**
     * 颜色名称
     */
    private String pColorName;
    /**
     * 电池型号
     */
    private String pBatteryModel;
    /**
     * 电机型号
     */
    private String pMotorModel;
    /**
     * 车型年代码
     */
    private String pModelYearCode;
    /**
     * 车型年
     */
    private String pModelYear;
    /**
     * 车身形式代码
     */
    private String pVehicleShapeCode;
    /**
     * 车身变形名称
     */
    private String pVehicleTranName;

    /**
     * 项目经理ID
     */
    @Getter
    @Setter
    private Long projectManagerId;

    public String getpProjectOwningUser() {
        return pProjectOwningUser;
    }

    public void setpProjectOwningUser(String pProjectOwningUser) {
        this.pProjectOwningUser = pProjectOwningUser;
    }

    public Date getpProjectCreateDate() {
        return pProjectCreateDate;
    }

    public void setpProjectCreateDate(Date pProjectCreateDate) {
        this.pProjectCreateDate = pProjectCreateDate;
    }

    public Date getpProjectLastModDate() {
        return pProjectLastModDate;
    }

    public void setpProjectLastModDate(Date pProjectLastModDate) {
        this.pProjectLastModDate = pProjectLastModDate;
    }

    public Date getpProjectDiscontinuationDate() {
        return pProjectDiscontinuationDate;
    }

    public void setpProjectDiscontinuationDate(Date pProjectDiscontinuationDate) {
        if (pProjectDiscontinuationDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(9998, 12, 31, 23, 59, 59);
            this.pProjectDiscontinuationDate = calendar.getTime();
        } else
            this.pProjectDiscontinuationDate = pProjectDiscontinuationDate;
    }

    public String getPuid() {
        return puid;
    }

    @Override
    public String getCode() {
        return this.pProjectCode;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpProjectName() {
        return pProjectName;
    }

    public void setpProjectName(String pProjectName) {
        this.pProjectName = pProjectName;
    }

    public String getpProjectPertainToVehicle() {
        return pProjectPertainToVehicle;
    }

    public void setpProjectPertainToVehicle(String pProjectPertainToVehicle) {
        this.pProjectPertainToVehicle = pProjectPertainToVehicle;
    }

    public String getpProjectCode() {
        return pProjectCode;
    }

    public void setpProjectCode(String pProjectCode) {
        this.pProjectCode = pProjectCode;
    }

    public String getpProjectManager() {
        return pProjectManager;
    }

    public void setpProjectManager(String pProjectManager) {
        this.pProjectManager = pProjectManager;
    }

    public String getpProjectLastModifier() {
        return pProjectLastModifier;
    }

    public void setpProjectLastModifier(String pProjectLastModifier) {
        this.pProjectLastModifier = pProjectLastModifier;
    }

    public String getpProjectDetail() {
        return pProjectDetail;
    }

    public void setpProjectDetail(String pProjectDetail) {
        this.pProjectDetail = pProjectDetail;
    }

    public String getpInColorCode() {
        return pInColorCode;
    }

    public void setpInColorCode(String pInColorCode) {
        this.pInColorCode = pInColorCode;
    }

    public String getpInColorName() {
        return pInColorName;
    }

    public void setpInColorName(String pInColorName) {
        this.pInColorName = pInColorName;
    }

    public String getpColorCode() {
        return pColorCode;
    }

    public void setpColorCode(String pColorCode) {
        this.pColorCode = pColorCode;
    }

    public String getpColorName() {
        return pColorName;
    }

    public void setpColorName(String pColorName) {
        this.pColorName = pColorName;
    }

    public String getpBatteryModel() {
        return pBatteryModel;
    }

    public void setpBatteryModel(String pBatteryModel) {
        this.pBatteryModel = pBatteryModel;
    }

    public String getpMotorModel() {
        return pMotorModel;
    }

    public void setpMotorModel(String pMotorModel) {
        this.pMotorModel = pMotorModel;
    }

    public String getpVehicleShapeCode() {
        return pVehicleShapeCode;
    }

    public void setpVehicleShapeCode(String pVehicleShapeCode) {
        this.pVehicleShapeCode = pVehicleShapeCode;
    }

    public String getpVehicleTranName() {
        return pVehicleTranName;
    }

    public void setpVehicleTranName(String pVehicleTranName) {
        this.pVehicleTranName = pVehicleTranName;
    }

    public String getpModelYearCode() {
        return pModelYearCode;
    }

    public void setpModelYearCode(String pModelYearCode) {
        this.pModelYearCode = pModelYearCode;
    }

    public String getpModelYear() {
        return pModelYear;
    }

    public void setpModelYear(String pModelYear) {
        this.pModelYear = pModelYear;
    }

}