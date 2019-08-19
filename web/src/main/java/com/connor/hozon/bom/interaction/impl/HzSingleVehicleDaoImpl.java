package com.connor.hozon.bom.interaction.impl;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.interaction.dao.HzSingleVehiclesDao;
import cn.net.connor.hozon.dao.query.interaction.HzSingleVehiclesQuery;
import org.springframework.context.annotation.Configuration;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/25 12:04
 * @Modified By:
 */
@Configuration
public class HzSingleVehicleDaoImpl extends BasicDaoImpl<HzSingleVehicles> implements HzSingleVehiclesDao {

    private final static HzSingleVehicles VEHICLES = new HzSingleVehicles();

    public HzSingleVehicleDaoImpl() {
        clz = HzSingleVehiclesDao.class;
        clzName = clz.getCanonicalName();
    }


    /**
     * 根据项目查找所有的单车清单基本信息
     *
     * @param inquirer
     * @return
     */
    private List<HzSingleVehicles> selectListByInquirer(HzSingleVehiclesQuery inquirer) {
        return baseSQLUtil.executeQueryByPass(VEHICLES, inquirer, clzName + ".selectListByInquirer");
    }

    /**
     * 根据项目查找所有的单车清单基本信息
     *
     * @param inquirer
     * @return
     */
    private List<HzSingleVehicles> selectOrgByInquirer(HzSingleVehiclesQuery inquirer) {
        return baseSQLUtil.executeQueryByPass(VEHICLES, inquirer, clzName + ".selectOrgByInquirer");
    }

    /**
     * 根据项目查找所有的单车清单基本信息
     *
     * @param projectUid
     * @return
     */
    @Override
    public List<HzSingleVehicles> selectByProjectUid(String projectUid) {
        HzSingleVehiclesQuery inquirer = new HzSingleVehiclesQuery();
        inquirer.setSvlProjectUid(projectUid);
        return selectListByInquirer(inquirer);
    }

    /**
     * 根据项目查找所有的单车清单基本信息
     *
     * @param projectUid
     * @return
     */
    @Override
    public List<HzSingleVehicles> selectOrgByProjectUid(String projectUid) {
        HzSingleVehiclesQuery inquirer = new HzSingleVehiclesQuery();
        inquirer.setSvlProjectUid(projectUid);
        return selectOrgByInquirer(inquirer);
    }


    /**
     * 根据配置信息查找1个单车基本信息
     *
     * @param svlDmbId
     * @return
     */
    @Override
    public HzSingleVehicles selectByDmbIdWithProjectUid(Long svlDmbId, String projectUid) {
        HzSingleVehiclesQuery inquirer = new HzSingleVehiclesQuery();
        inquirer.setSvlDmbId(svlDmbId);
        inquirer.setSvlProjectUid(projectUid);
        List<HzSingleVehicles> vehicles = selectListByInquirer(inquirer);
        return vehicles == null || vehicles.size() <= 0 ? null : vehicles.get(0);
    }

    @Override
    public int insertList(List<HzSingleVehicles> hzSingleVehicles) {
        return baseSQLUtil.executeInsert(hzSingleVehicles,clzName+".insertList");
    }

    @Override
    public boolean checkExist(String projectId, String svlCfgMaterialUid) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("svlCfgMaterialUid",svlCfgMaterialUid);
        return (int)baseSQLUtil.findForObject(clzName+".checkExist",map)>0;
    }

    @Override
    public int updateSingleVehicles(HzSingleVehicles hzSingleVehicles) {
        return baseSQLUtil.executeUpdate(hzSingleVehicles,clzName+".updateSingleVehicles");
    }

    @Override
    public HzSingleVehicles getSingleVehiclesById(String projectId, Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("id",id);
        return (HzSingleVehicles)baseSQLUtil.findForObject(clzName+".getSingleVehiclesById",map);
    }

    @Override
    public List<HzSingleVehicles> selectByIds(List<HzSingleVehicles> hzSingleVehicles) {
        return baseSQLUtil.executeQueryByPass(new HzSingleVehicles(),hzSingleVehicles,clzName+".selectByIds");
    }

    @Override
    public int doUpdateIsSent(Map<String, Object> map) {
        return baseSQLUtil.executeUpdate(map,clzName+".updateIsSent");
    }

}
