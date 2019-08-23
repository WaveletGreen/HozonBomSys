package com.connor.hozon.dao.interaction;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import org.springframework.context.annotation.Configuration;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicles;

import java.util.List;
import java.util.Map;

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

    /**
     * 批量插入
     * @param hzSingleVehicles
     * @return
     */
    int insertList(List<HzSingleVehicles> hzSingleVehicles);

    /**
     * 查询存在记录
     * @param projectId
     * @param svlCfgMaterialUid
     * @return
     */
    boolean checkExist(String projectId,String svlCfgMaterialUid);

    /**
     * 更新单车信息
     * @param hzSingleVehicles
     * @return
     */
    int updateSingleVehicles(HzSingleVehicles hzSingleVehicles);

    /**
     * 获取一条单车清单信息
     * @param projectId
     * @param id
     * @return
     */
    HzSingleVehicles getSingleVehiclesById(String projectId,Long id);

    List<HzSingleVehicles> selectByIds(List<HzSingleVehicles> hzSingleVehicles);

    int doUpdateIsSent(Map<String,Object> map);
}