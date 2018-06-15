package com.connor.hozon.bom.bomSystem.dao.project;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.project.HzVehicleRecord;

import java.util.List;

@Configuration
public interface HzVehicleRecordDao {
    /**
     * 根据主键删除
     *
     * @param puid puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid") String puid);

    /**
     * 插入1条完整的车型数据
     *
     * @param record 车型数据
     * @return
     */
    int insert(HzVehicleRecord record);

    /**
     * 根据主键获取1条车型数据
     *
     * @param puid puid
     * @return
     */
    HzVehicleRecord selectByPrimaryKey(@Param("puid") String puid);

    /**
     * 根据主键更新1条车型数据
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzVehicleRecord record);

    /**
     * 查找所有的车型信息
     *
     * @return
     */
    List<HzVehicleRecord> selectAll();
}