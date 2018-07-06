package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzMbomByPageQuery;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomRecord;

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
    int update(HzMbomRecord record);

    /**
     * 删除BOM维护 通过外键删除
     * @param eBomPuid
     * @return
     */
    int deleteByForeignId(String eBomPuid);

    /**
     * 获取MBOM信息 分页查询
     * @param query
     * @return
     */
    Page<HzMbomLineRecord> findMbomForPage(HzMbomByPageQuery query);

    /**
     * 查询一条Mbom信息 EBOM表和MBOM表
     * @param map
     * @return
     */
    HzMbomLineRecord findHzMbomByPuid(Map<String,Object> map);

    /**
     * 查询一条MBOM信息 MBOM表
     * @param eBomPuid
     * @return
     */
    HzMbomRecord findHzMbomByeBomPuid(String eBomPuid);

    List<HzMbomLineRecord> getHzVehicleModelName(String projectId);

    Page<HzMbomLineRecord> getHzSuberMbomByPage(HzMbomByPageQuery query);

    /**
     * 获取一条超级MBOM信息
     * @param projectId
     * @param pPuid
     * @return
     */
    HzMbomLineRecord getHzSuperMbomByPuid(String projectId,String pPuid);

    HzMbomLineRecord getHzMbom(String projectId,String parentPuid);

    Integer getHzMbomTotalCount(String projectId);

    Integer getHzMbomMaxOrderNum(String projectId);
}