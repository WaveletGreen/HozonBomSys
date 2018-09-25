package com.connor.hozon.bom.interaction.impl;

import com.connor.hozon.bom.bomSystem.dao.BasicDaoImpl;
import com.connor.hozon.bom.interaction.dao.HzSingleVehiclesDao;
import com.connor.hozon.bom.interaction.inquirer.HzSingleVehiclesInquirer;
import org.springframework.context.annotation.Configuration;
import sql.pojo.interaction.HzSingleVehicles;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Maywas
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
    private List<HzSingleVehicles> selectListByInquirer(HzSingleVehiclesInquirer inquirer) {
        return baseSQLUtil.executeQueryByPass(VEHICLES, inquirer, clzName + ".selectListByInquirer");
    }

    /**
     * 根据项目查找所有的单车清单基本信息
     *
     * @param inquirer
     * @return
     */
    private List<HzSingleVehicles> selectOrgByInquirer(HzSingleVehiclesInquirer inquirer) {
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
        HzSingleVehiclesInquirer inquirer = new HzSingleVehiclesInquirer();
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
        HzSingleVehiclesInquirer inquirer = new HzSingleVehiclesInquirer();
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
        HzSingleVehiclesInquirer inquirer = new HzSingleVehiclesInquirer();
        inquirer.setSvlDmbId(svlDmbId);
        inquirer.setSvlProjectUid(projectUid);
        List<HzSingleVehicles> vehicles = selectListByInquirer(inquirer);
        return vehicles == null || vehicles.size() <= 0 ? null : vehicles.get(0);
    }

}
