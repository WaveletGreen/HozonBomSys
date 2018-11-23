package com.connor.hozon.bom.interaction.dao;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.interaction.HzSingleVehicleBomLineBean;

import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/25 13:25
 * @Modified By:
 */
@Configuration
public interface HzSingleVehicleBomLineDao extends BasicDao<HzSingleVehicleBomLineBean> {
    /**
     * 查询一个单车的所有2Y
     * @param projectUid 项目UID
     * @param dmbId 单车详细信息主配置
     * @return
     */
    List<HzSingleVehicleBomLineBean> selectByProjectUidWithSv(String projectUid, Long dmbId);
}
