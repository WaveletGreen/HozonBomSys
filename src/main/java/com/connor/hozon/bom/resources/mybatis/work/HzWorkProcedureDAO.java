package com.connor.hozon.bom.resources.mybatis.work;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzWorkProcessByPageQuery;
import sql.pojo.work.HzWorkProcedure;
import sql.pojo.work.HzWorkProcess;

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
}
