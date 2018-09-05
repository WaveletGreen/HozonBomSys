package com.connor.hozon.bom.bomSystem.dao.cfg;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzDerivativeMaterielDetail;

import java.util.List;

public interface HzDerivativeMaterielDetailDao extends BasicDao<HzDerivativeMaterielDetail> {

    /**
     * 基本信息外键查询，附带特性值信息
     *
     * @param detail
     * @return
     */
    List<HzDerivativeMaterielDetail> selectByBasicWithCfg(HzDerivativeMaterielDetail detail);

}