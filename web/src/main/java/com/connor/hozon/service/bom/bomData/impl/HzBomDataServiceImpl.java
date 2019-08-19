/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.bomData.impl;

import cn.net.connor.hozon.dao.dao.main.HzMainBomDao;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomLineRecord;
import cn.net.connor.hozon.dao.query.configuration.fullConfigSheet.FeatureAnd2YRelationQuery;
import cn.net.connor.hozon.dao.dao.bom.bom.HzBomDataDao;
import com.connor.hozon.dao.bom.impl.HzBomLineRecordDaoImpl;
import com.connor.hozon.service.bom.bomData.HzBomDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service
public class HzBomDataServiceImpl implements HzBomDataService {
    @Autowired
    HzBomDataDao hzBomDataDao;
    @Autowired
    HzMainBomDao hzMainBomDao;
    @Autowired
    HzBomLineRecordDaoImpl hzBomLineRecordDao;
        /**
     * 获取项目下的所有2Y层
     *
     * @param projectPuid
     * @param orderKey
         * @return
     */
    public List<HzBomLineRecord> doSelect2YByProjectPuid(String projectPuid, String orderKey) {
        FeatureAnd2YRelationQuery query=new FeatureAnd2YRelationQuery();
        query.setProjectId(projectPuid);
        query.setOrder(orderKey);
        return hzBomDataDao.select2YByProjectPuid(query);
    }

    /**
     * 传入部门和项目UID，获取部门下2Y层的子总成
     *
     * @param dept       部门名
     * @param projectUid 项目UID
     * @return 一组总成，需要进行查重操作
     */
    public List<HzBomLineRecord> doSelectVehicleAssembly(String dept, String projectUid, String modelUid) {
        HashMap params = new HashMap();
        params.put("dept", dept);
        params.put("projectUid", projectUid);
        params.put("modelUid", modelUid);
        return hzBomDataDao.selectVehicleAssembly(params);
    }

    public int updata2Y(HzBomLineRecord hzBomLineRecord) {
        return hzBomLineRecordDao.updateColorPart(hzBomLineRecord.getPuid(),hzBomLineRecord.getColorPart());
    }
}
