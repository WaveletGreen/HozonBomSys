package com.connor.hozon.bom.resources.mybatis.work;

import com.connor.hozon.bom.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.work.HzWorkProcedure;
import sql.pojo.work.HzWorkProcess;

import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public interface HzWorkProcedureDAO {
    int insert(HzWorkProcedure hzWorkProcedure);

    int update(HzWorkProcedure hzWorkProcedure);

    int update2(HzWorkProcedure hzWorkProcedure);

    int delete(String puid);

    Page<HzWorkProcess> findHzWorkProcessByPage(HzWorkProcessByPageQuery query);

    Page<HzWorkProcess> findHzWorkProcessByPage2(HzWorkProcessByPageQuery query);

    HzWorkProcess getHzWorkProcess(String materielId,String projectId);

    HzWorkProcess getHzWorkProcess2(String materielId,String projectId, String procedureDesc);

    HzWorkProcedure getHzWorkProcessByMaterielId(String materielId);

    int updateSendFlag(Map<String,Object> map);

    int insertHzWorkProcedures(List<HzWorkProcedure> hzWorkProcedures);

    List<HzWorkProcedure> findHzWorkProcessByProjectId(String projectId);

    int deleteHzWorkProcessByMaterielIds(List<HzWorkProcedure> hzWorkProceduresDel);

    List<String> queryProcessDescsByPuid(List<String> puidList);

    int deleteHzWorkProcesses(List<HzWorkProcedure> hzWorkProceduresDel);
}
