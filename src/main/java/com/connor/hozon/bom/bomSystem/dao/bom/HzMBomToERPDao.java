package com.connor.hozon.bom.bomSystem.dao.bom;

import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HzMBomToERPBean;

import java.util.List;

@Configuration
public interface HzMBomToERPDao {
    List<HzMBomToERPBean> selectByBatch(String projectUid,List<String> list);
}
