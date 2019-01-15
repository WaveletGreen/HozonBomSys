package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzMbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateMbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.*;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomLineRecordVO;
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
     * 根据零件号更新
     * @param records
     * @return
     */
    int updateList(List<HzMbomLineRecord> records);
    /**
     * 插入单条记录
     * @param record
     * @return
     */
    int insert(HzMbomRecord record);

    int insert2(HzMbomLineRecord record);

    /**
     * 更新MBOM维护信息
     * @param record
     * @return
     */
    int update(HzMbomLineRecord record);

    /**
     * 导入修改超级MBOM
     */
    int updateInput(HzMbomLineRecord record);

    /**
     * 导入修改生产MBOM
     */
    int updateInputProduct(HzMbomLineRecord record);

    /**
     * 导入修改财务MBOM
     */
    int updateInputFinance(HzMbomLineRecord record);

    /**
     * 删除BOM恢复 通过外键恢复
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
     * 查询Mbom信息
     * @param map
     * @return
     */
    List<HzMbomLineRecord> findHzMbomByPuid(Map<String,Object> map);

    List<HzMbomLineRecord> findHzMbomByPuid_before(Map<String,Object> map);

    int insert_before(HzMbomLineRecord record);

    int update_before(HzMbomLineRecord record);

    List<HzMbomLineRecord> findHzMbomByPuid_after(Map<String,Object> map);

    int insert_after(HzMbomLineRecord record);

    int update_after(HzMbomLineRecord record);

    List<HzMbomLineRecord> findMbomByItemId(String itemId,String projectId);

    boolean checkItemIdIsRepeat(String projectId, String lineId);

    /**
     * 查询MBOM对应车型信息
     * @param projectId
     * @return
     */
    @Deprecated
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
    @Deprecated
    Integer getHzMbomTotalCount(String projectId);

    /**
     * 获取MBOM最大排序值
     * @param projectId
     * @return
     */
    Double getHzMbomMaxOrderNum(String projectId);

    /**
     * 获取2Y层 分页获取 MBOM数据 （虚拟物料主数据）
     * @param query
     * @return
     */
    @Deprecated
    List<HzMbomLineRecord> findHz2YMbomRecord(HzMbomByPageQuery query);

    /**
     * 根据BOM来源获取不同bom 分页获取
     * @param query
     * @return
     */
    @Deprecated
    List<HzMbomLineRecord> findHzMbomByResource(HzMbomByPageQuery query);


    /**
     * 获取2Y层 全部获取 MBOM数据 （虚拟物料主数据）
     * @param query
     * @return
     */
    @Deprecated
    List<HzMbomLineRecord> findHz2YMbomRecordAll(HzMbomByPageQuery query);

    /**
     * 根据BOM来源获取不同bom 全部获取
     * @param query
     * @return
     */
    @Deprecated
    List<HzMbomLineRecord> findHzMbomByResourceAll(HzMbomByPageQuery query);

    /**
     * 获取MBOM结构树 sql递归获取
     * @param query
     * @return
     */
    List<HzMbomLineRecord> getHzMbomTree(HzMbomTreeQuery query);


    Page<HzMbomLineRecord> getHzMbomRecycleRecord(HzBomRecycleByPageQuery query);

    Integer getMaxLineIndexFirstNum(String projectId);

    int delete(String eBomPuid);


    String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,String orderNum);


    List<HzMbomLineRecord> findHzMbomAll(String projectId,String tableName);


    int insertVO(HzMbomLineRecordVO hzMbomLineRecordVO);

    int updateVO(HzMbomLineRecordVO hzMbomLineRecordVO);

    /**
     * 通过外键ID 和层级查询MBOM
     * @param ebomPuid
     * @param lineIndex
     * @return
     */
    HzMbomLineRecord findHzMbomByEbomIdAndLineIndex(String ebomPuid,String lineIndex,String tableName);


    int deleteMbomList(HzMbomLineRecordVO record);

    /**
     * 删除当前项目的MBOM数据
     * @param projectId
     * @return
     */
    int deleteMbomByProjectId(String projectId,String tableName);


    /**
     * 分页展示MBOM树状结构
     * @param query
     * @return
     */
    Page<HzMbomLineRecord> getHzMbomTreeByPage(HzMbomByPageQuery query);


    /**
     * 根据puids 获取数据结果集
     * @param query
     * @return
     */
    List<HzMbomLineRecord> getMbomRecordsByPuids(HzChangeDataDetailQuery query);



    HzMbomLineRecord getMBomRecordByPuidAndRevision(HzChangeDataDetailQuery query);


    List<HzMbomLineRecord> getMbomRecordsByOrderId(HzChangeDataDetailQuery query);

    /**
     * 检查油漆物料是否存在
     * @param query
     * @return
     */
    boolean checkPaintMaterielRepeat(HzMbomPaintMaterielRepeatQuery query);

    /**
     * 查询当前BOM的子一层结构
     * @param hzBOMQuery
     * @return
     */
    List<HzMbomLineRecord> getNextBomStructure(HzBOMQuery hzBOMQuery);

    /**
     * 获取MBOM信息
     * @param hzBOMQuery 参数信息
     * @return
     */
    List<HzMbomLineRecord> getHzMbomByBomQuery(HzBOMQuery hzBOMQuery);

}
