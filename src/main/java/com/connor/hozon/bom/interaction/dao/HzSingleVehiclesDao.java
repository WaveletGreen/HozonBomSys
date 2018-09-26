package com.connor.hozon.bom.interaction.dao;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.interaction.HzSingleVehicles;

import java.util.List;

/**
 * 单车清单，带颜色
 */
@Configuration
public interface HzSingleVehiclesDao extends BasicDao<HzSingleVehicles> {

    /**
     * 根据项目查找所有的单车清单基本信息
     *
     * @param svlProjectUid
     * @return
     */
    List<HzSingleVehicles> selectByProjectUid(String svlProjectUid);

    /**
     * 根据项目查找所有的单车清单基本信息
     *
     * @param projectUid
     * @return
     */
    List<HzSingleVehicles> selectOrgByProjectUid(String projectUid);

    /**
     * 根据配置信息查找1个单车基本信息
     *
     * @param svlDmbId
     * @return
     */
    HzSingleVehicles selectByDmbIdWithProjectUid(Long svlDmbId, String projectUid);

    int insert(HzSingleVehicles hzSingleVehicles);

}