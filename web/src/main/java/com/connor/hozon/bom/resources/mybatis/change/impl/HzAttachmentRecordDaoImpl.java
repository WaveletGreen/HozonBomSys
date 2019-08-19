/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.dao.BasicDaoImpl;
import com.connor.hozon.bom.resources.mybatis.change.HzAttachmentRecordDao;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.change.change.HzAttachmentRecord;

import java.util.List;

@Service("hzAttachmentRecordDao")
public class HzAttachmentRecordDaoImpl extends BasicDaoImpl<HzAttachmentRecord> implements HzAttachmentRecordDao {

    public HzAttachmentRecordDaoImpl(){
        clz = HzAttachmentRecordDao.class;
        clzName = clz.getCanonicalName()+".";
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(HzAttachmentRecord record) {
        return 0;
    }

    @Override
    public int insertSelective(HzAttachmentRecord record) {
        return 0;
    }

    @Override
    public HzAttachmentRecord selectByPrimaryKey(Long id) {
        HzAttachmentRecord hzAttachmentRecord = new HzAttachmentRecord();
        hzAttachmentRecord.setId(id);
        return baseSQLUtil.executeQueryById(hzAttachmentRecord,clzName+"selectByPrimaryKey");
    }

    @Override
    public int updateByPrimaryKeySelective(HzAttachmentRecord record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(HzAttachmentRecord record) {
        return 0;
    }

    @Override
    public List<HzAttachmentRecord> selectByChangeNo(String changeNo) {
        return baseSQLUtil.executeQueryByPass(new HzAttachmentRecord(),changeNo,clzName+"selectByChangeNo");
    }
}
