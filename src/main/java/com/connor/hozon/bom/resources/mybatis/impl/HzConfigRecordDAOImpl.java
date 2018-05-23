package com.connor.hozon.bom.resources.mybatis.impl;

import com.connor.hozon.bom.resources.dbdo.config.HzCfg0Record;
import com.connor.hozon.bom.resources.dto.request.FindHzFeatureConfigReqDTO;
import com.connor.hozon.bom.resources.mybatis.HzConfigRecordDAO;
import sql.BaseSQLUtil;

import java.util.List;

/**
 * Created by haozt on 2018/5/22
 */

public class HzConfigRecordDAOImpl extends BaseSQLUtil implements HzConfigRecordDAO {

    @Override
    public List<HzCfg0Record> selectHzFeatureConfig(FindHzFeatureConfigReqDTO dto) {
        return super.findForList("HzConfigRecordDAOImpl_selectHzFeatureConfig",dto);
    }
}
