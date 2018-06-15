package com.connor.hozon.bom.bomSystem.service.iservice.project;

import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;

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
    public int doInsertOne(HzVehicleRecord vehicle);

    /**
     * 根据主键删除1条数据
     *
     * @param puid 主键
     * @return
     */
    public int doDeleteByPuid(String puid);

    /**
     * 根据主键更1条车型数据
     *
     * @param vehicle 车型数据，自带主键
     * @return
     */
    public int doUpdateByPuid(HzVehicleRecord vehicle);

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
}
