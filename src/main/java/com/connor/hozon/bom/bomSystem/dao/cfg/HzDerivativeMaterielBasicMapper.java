package com.connor.hozon.bom.bomSystem.dao.cfg;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzDerivativeMaterielBasic;
import sql.pojo.cfg.HzDerivativeMaterielBasicExample;

public interface HzDerivativeMaterielBasicMapper {
    long countByExample(HzDerivativeMaterielBasicExample example);

    int deleteByExample(HzDerivativeMaterielBasicExample example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(HzDerivativeMaterielBasic record);

    int insertSelective(HzDerivativeMaterielBasic record);

    List<HzDerivativeMaterielBasic> selectByExample(HzDerivativeMaterielBasicExample example);

    HzDerivativeMaterielBasic selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(@Param("record") HzDerivativeMaterielBasic record, @Param("example") HzDerivativeMaterielBasicExample example);

    int updateByExample(@Param("record") HzDerivativeMaterielBasic record, @Param("example") HzDerivativeMaterielBasicExample example);

    int updateByPrimaryKeySelective(HzDerivativeMaterielBasic record);

    int updateByPrimaryKey(HzDerivativeMaterielBasic record);
}