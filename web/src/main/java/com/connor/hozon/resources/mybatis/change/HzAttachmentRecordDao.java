package com.connor.hozon.resources.mybatis.change;


import cn.net.connor.hozon.dao.pojo.change.change.HzAttachmentRecord;

import java.util.List;

public interface HzAttachmentRecordDao {
    int deleteByPrimaryKey(Long id);

    int insert(HzAttachmentRecord record);

    int insertSelective(HzAttachmentRecord record);

    HzAttachmentRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HzAttachmentRecord record);

    int updateByPrimaryKey(HzAttachmentRecord record);

    List<HzAttachmentRecord> selectByChangeNo(String changeNo);
}