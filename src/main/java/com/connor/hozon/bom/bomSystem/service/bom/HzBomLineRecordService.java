/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.bom;

import com.connor.hozon.bom.bomSystem.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzBomLineRecord;

/**
 * @Author: haozt
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("HzBomLineRecordService")
public class HzBomLineRecordService {
    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;
    /**
     * 插入一条数据
     * @param record
     * @return
     */
    public int insert(HzBomLineRecord record){
         return hzBomLineRecordDao.insert(record);
    }

}
