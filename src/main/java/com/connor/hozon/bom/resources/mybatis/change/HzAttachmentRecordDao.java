package com.connor.hozon.bom.resources.mybatis.change;


import org.springframework.context.annotation.Configuration;
import sql.pojo.change.HzAttachmentRecord;

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