package com.connor.hozon.bom.bomSystem.dao.derivative;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.derivative.HzCfg0ToModelRecord;

import java.util.List;

@Configuration
public interface HzCfg0ToModelRecordDao  extends BasicDao<HzCfg0ToModelRecord>{

    int deleteByPrimaryKey(String puid);


    HzCfg0ToModelRecord selectByPrimaryKey(String puid);

    /**
     * 批量更新发送到SAP的状态
     *
     * @param list
     * @return
     */
    int setIsSent(@Param("list") List<HzCfg0ToModelRecord> list);
}