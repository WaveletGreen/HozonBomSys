package com.connor.hozon.bom.bomSystem.dao.cfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzDerivativeMaterielBasic;

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
}