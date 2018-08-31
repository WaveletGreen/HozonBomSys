package com.connor.hozon.bom.bomSystem.dao.cfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzDerivativeMaterielBasic;

import java.util.List;

/**
 * 配置物料基础dao层
 */
@Configuration
public interface HzDerivativeMaterielBasicDao extends BasicDao<HzDerivativeMaterielBasic> {
    /**
     * 根据项目查找项目下的所有配置物料特性数据
     *
     * @param basic
     * @return
     */
    List<HzDerivativeMaterielBasic> selectByProjectUid(HzDerivativeMaterielBasic basic);
}