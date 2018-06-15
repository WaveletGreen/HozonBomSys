package com.connor.hozon.bom.resources.service.cfg.impl;

import com.connor.hozon.bom.resources.dbdo.config.HzCfg0Record;
import com.connor.hozon.bom.resources.dto.request.FindHzFeatureConfigReqDTO;
import com.connor.hozon.bom.resources.mybatis.cfg.HzConfigRecordDAO;
import com.connor.hozon.bom.resources.mybatis.cfg.impl.HzConfigRecordDAOImpl;
import com.connor.hozon.bom.resources.service.cfg.HzConfigRecordService;
import com.connor.hozon.bom.resources.util.ListUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by haozt on 2018/5/22
 */

@Service("HzConfigRecordService")
public class HzConfigRecordServiceImpl implements HzConfigRecordService {


    private HzConfigRecordDAO dao;
    @Override
    public List<HzCfg0Record> getHzFeatureConfig(FindHzFeatureConfigReqDTO reqDTO) {
        if(dao == null){
            dao = new HzConfigRecordDAOImpl();
        }
        List<HzCfg0Record> records = dao.selectHzFeatureConfig(reqDTO);
        if(ListUtil.isNotEmpty(records)){
            return records;
        }
        return null;
    }
}
