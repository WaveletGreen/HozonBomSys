package com.connor.hozon.resources.mybatis.work;

import com.connor.hozon.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.resources.domain.query.HzWorkProcessByPageQuery;
import com.connor.hozon.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkProcedure;
import cn.net.connor.hozon.dao.pojo.depository.work.HzWorkProcess;

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

    List<HzWorkProcess> getHzWorkProcess(String materielId,String projectId);

    HzWorkProcess getHzWorkProcess2(String materielId,String projectId, String procedureDesc);

    HzWorkProcedure getHzWorkProcessByMaterielId(String materielId);

    int updateSendFlag(Map<String,Object> map);

    int insertHzWorkProcedures(List<HzWorkProcedure> hzWorkProcedures);

    List<HzWorkProcedure> findHzWorkProcessByProjectId(String projectId);

    int deleteHzWorkProcessByMaterielIds(List<HzWorkProcedure> hzWorkProceduresDel);

    List<String> queryProcessDescsByPuid(List<String> puidList);

    int deleteHzWorkProcesses(List<HzWorkProcedure> hzWorkProceduresDel);

    List<HzWorkProcedure> queryProcedures(List<HzWorkProcedure> hzWorkProcedureList);



    /**
     * 变更 根据puids 获取工艺路线集合
     * @param query
     * @return
     */
    List<HzWorkProcedure> getHzWorkProcedureByPuids(HzChangeDataDetailQuery query);

    /**
     * 根据puid 和版本号获取工艺路线
     * @param query
     * @return
     */
    HzWorkProcedure getHzWorkProcedureByPuidAndRevision(HzChangeDataDetailQuery query);

    /**
     * 批量修改
     * @param list
     * @return
     */
    int updateList(List<HzWorkProcedure> list);

    /**
     * 批量修改
     * @param list
     * @return
     */
    int updateWorkProcedureList(List<HzWorkProcedure> list);
    /**
     * 批量新增
     * @param list
     * @param tableName
     * @return
     */
    int insertList(List<HzWorkProcedure> list,String tableName);

    /**
     * 批量删除
     * @param puids
     * @return
     */
    int deleteByPuids(List<String> puids,String tableName);

    List<HzWorkProcedure> getWorkProcedureByOrderId(HzChangeDataDetailQuery query);
}
