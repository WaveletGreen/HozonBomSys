package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.dto.request.DeleteHzMbomReqDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.*;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomRecord;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.project.HzMaterielRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/5/24
 */
public interface HzMbomRecordDAO {
    /**
     * 批量插入MBOM维护信息
     * @return
     */
    int insertList(List<HzMbomLineRecord> records);

    /**
     * 插入单条记录
     * @param record
     * @return
     */
    int insert(HzMbomRecord record);
    /**
     * 更新MBOM维护信息
     * @param record
     * @return
     */
    int update(HzMbomLineRecord record);

    /**
     * 删除BOM维护 通过外键删除
     * @param eBomPuid
     * @return
     */
    int recoverBomById(String eBomPuid);

    /**
     * 批量删除
     * @param reqDTOS
     * @return
     */
    int deleteList(List<DeleteHzMbomReqDTO> reqDTOS);

    /**
     * 获取MBOM信息 分页查询
     * @param query
     * @return
     */
    Page<HzMbomLineRecord> findMbomForPage(HzMbomByPageQuery query);

    /**
     * 查询一条Mbom信息
     * @param map
     * @return
     */
    HzMbomLineRecord findHzMbomByPuid(Map<String,Object> map);


    /**
     * 查询MBOM对应车型信息
     * @param projectId
     * @return
     */
    List<HzMbomLineRecord> getHzVehicleModelName(String projectId);

    /**
     * 获取超级MBOM 作废
     * @param query
     * @return
     */
    @Deprecated
    Page<HzMbomLineRecord> getHzSuberMbomByPage(HzMbomByPageQuery query);

    /**
     * 获取一条超级MBOM信息 作废
     * @param projectId
     * @param pPuid
     * @return
     */
    @Deprecated
    HzMbomLineRecord getHzSuperMbomByPuid(String projectId,String pPuid);

    @Deprecated
    HzMbomLineRecord getHzMbom(String projectId,String parentPuid);

    /**
     * 获取MBOM的数量
     * @param projectId
     * @return
     */
    Integer getHzMbomTotalCount(String projectId);

    /**
     * 获取MBOM最大排序值
     * @param projectId
     * @return
     */
    Integer getHzMbomMaxOrderNum(String projectId);

    /**
     * 获取2Y层 分页获取 MBOM数据 （虚拟物料主数据）
     * @param query
     * @return
     */
    List<HzMbomLineRecord> findHz2YMbomRecord(HzMbomByPageQuery query);

    /**
     * 根据BOM来源获取不同bom 分页获取
     * @param query
     * @return
     */
    List<HzMbomLineRecord> findHzMbomByResource(HzMbomByPageQuery query);


    /**
     * 获取2Y层 全部获取 MBOM数据 （虚拟物料主数据）
     * @param query
     * @return
     */
    List<HzMbomLineRecord> findHz2YMbomRecordAll(HzMbomByPageQuery query);

    /**
     * 根据BOM来源获取不同bom 全部获取
     * @param query
     * @return
     */
    List<HzMbomLineRecord> findHzMbomByResourceAll(HzMbomByPageQuery query);

    /**
     * 获取MBOM结构树 sql递归获取
     * @param query
     * @return
     */
    List<HzMbomLineRecord> getHzMbomTree(HzMbomTreeQuery query);


    Page<HzMbomLineRecord> getHzMbomRecycleRecord(HzBomRecycleByPageQuery query);
}
