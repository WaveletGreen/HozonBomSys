package com.connor.hozon.bom.bomSystem.dao.derivative;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzDerivativeMaterielBasic;

import java.util.List;
import java.util.Map;

/**
 * 配置物料基础dao层
 */
@Configuration
public interface HzDerivativeMaterielBasicDao extends BasicDao<HzDerivativeMaterielBasic> {
    /**
     * 根据项目查找项目下的所有配置物料特性数据
     *
     * @param params basic=HzDerivativeMaterielBasic,condition=筛选条件，暂无
     * @return
     */
    List<HzDerivativeMaterielBasic> selectByProjectUid(Map<String, Object> params);

    /**
     * 车型模型+配色模型组成唯一的配置物料特性数据
     * @param modelUid 车型模型UID
     * @param colorModel 配色模型UID
     * @return
     */
    HzDerivativeMaterielBasic selectByModelAndColorUid(String modelUid, String colorModel);
}