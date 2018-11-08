/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.iservice.project;

import com.connor.hozon.bom.bomSystem.service.project.IProject;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
public interface IHzVehicleService {
    /**
     * 根据主键获取1条数据
     *
     * @param puid 主键
     * @return
     */
    public HzVehicleRecord doGetByPuid(String puid);

    /**
     * 插入1条车型数据
     *
     * @param vehicle 车型数据
     * @return
     */
    public boolean doInsertOne(HzVehicleRecord vehicle);

    /**
     * 根据主键删除1条数据
     *
     * @param puid 主键
     * @return
     */
    public boolean doDeleteByPuid(String puid);

    /**
     * 根据主键更1条车型数据
     *
     * @param vehicle 车型数据，自带主键
     * @return
     */
    public boolean doUpdateByPuid(HzVehicleRecord vehicle);

    /**
     * 验证数据是否正确
     *
     * @param vehicle
     * @return
     */
    public boolean validate(HzVehicleRecord vehicle);

    /**
     * 加载所有车型信息
     *
     * @return
     */
    List<HzVehicleRecord> doGetAllVehicle();

    /**
     * 根据车型代码，寻找车型信息
     * @param s 代码
     * @return
     */
    HzVehicleRecord doGetByVehicleCode(String s);

    /**
     * 车型代号查重
     *
     * @param vehicle 车型对象
     * @return
     */
    JSONObject doValidateCodeWithPuid(IProject vehicle);
}
