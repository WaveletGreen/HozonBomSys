package com.connor.hozon.bom.resources.mybatis.work;

import com.connor.hozon.bom.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.work.HzWorkProcedure;
import sql.pojo.work.HzWorkProcess;

import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public interface HzWorkProcedureDAO {
    int insert(HzWorkProcedure hzWorkProcedure);

    int update(HzWorkProcedure hzWorkProcedure);

    int delete(String puid);

    Page<HzWorkProcess> findHzWorkProcessByPage(HzWorkProcessByPageQuery query);

    HzWorkProcess getHzWorkProcess(String materielId,String projectId);

    HzWorkProcedure getHzWorkProcessByMaterielId(String materielId);

    int updateSendFlag(Map<String,Object> map);
}
