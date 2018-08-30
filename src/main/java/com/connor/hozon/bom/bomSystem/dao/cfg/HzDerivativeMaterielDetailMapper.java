package com.connor.hozon.bom.bomSystem.dao.cfg;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzDerivativeMaterielDetail;
import sql.pojo.cfg.HzDerivativeMaterielDetailExample;

public interface HzDerivativeMaterielDetailMapper {
    long countByExample(HzDerivativeMaterielDetailExample example);

    int deleteByExample(HzDerivativeMaterielDetailExample example);

    int deleteByPrimaryKey(BigDecimal id);

    int insert(HzDerivativeMaterielDetail record);

    int insertSelective(HzDerivativeMaterielDetail record);

    List<HzDerivativeMaterielDetail> selectByExample(HzDerivativeMaterielDetailExample example);

    HzDerivativeMaterielDetail selectByPrimaryKey(BigDecimal id);

    int updateByExampleSelective(@Param("record") HzDerivativeMaterielDetail record, @Param("example") HzDerivativeMaterielDetailExample example);

    int updateByExample(@Param("record") HzDerivativeMaterielDetail record, @Param("example") HzDerivativeMaterielDetailExample example);

    int updateByPrimaryKeySelective(HzDerivativeMaterielDetail record);

    int updateByPrimaryKey(HzDerivativeMaterielDetail record);
}