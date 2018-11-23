package com.connor.hozon.bom.interaction.impl;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.interaction.dao.HzSingleVehicleBomLineDao;
import com.connor.hozon.bom.interaction.inquirer.HzSingleVehicleBLInquirer;
import org.springframework.context.annotation.Configuration;
import sql.pojo.interaction.HzSingleVehicleBomLineBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/25 14:53
 * @Modified By:
 */
@Configuration
public class HzSingleVehicleBomLineDaoImpl extends BasicDaoImpl<HzSingleVehicleBomLineBean> implements HzSingleVehicleBomLineDao {

    private final static HzSingleVehicleBomLineBean BEAN = new HzSingleVehicleBomLineBean();

    public HzSingleVehicleBomLineDaoImpl() {
        clz = HzSingleVehicleBomLineDao.class;
        clzName = clz.getCanonicalName();
    }

    private List<HzSingleVehicleBomLineBean> selectListByInquirer(HzSingleVehicleBLInquirer inquirer) {
        return baseSQLUtil.executeQueryByPass(BEAN, inquirer, clzName + ".selectListByInquirer");
    }

    /**
     * 查询
     *
     * @param projectUid 项目UID
     * @param dmbId 单车详细信息主配置
     * @return
     */
    @Override
    public List<HzSingleVehicleBomLineBean> selectByProjectUidWithSv(String projectUid, Long dmbId) {
        HzSingleVehicleBLInquirer inquirer = new HzSingleVehicleBLInquirer();
        inquirer.setDmbId(dmbId);
        inquirer.setProjectUid(projectUid);
        inquirer.setIsNotNull(true);
        return selectListByInquirer(inquirer);
    }
}
