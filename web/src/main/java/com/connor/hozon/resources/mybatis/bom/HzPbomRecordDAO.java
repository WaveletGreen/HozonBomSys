package com.connor.hozon.resources.mybatis.bom;

import com.connor.hozon.resources.domain.dto.request.DeleteHzPbomReqDTO;
import com.connor.hozon.resources.domain.query.*;
import com.connor.hozon.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.resources.domain.query.HzChangeDataDetailQuery;
import com.connor.hozon.resources.domain.query.HzPbomByPageQuery;
import com.connor.hozon.resources.domain.query.HzPbomTreeQuery;
import com.connor.hozon.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzPbomLineRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/5/25
 */
public interface HzPbomRecordDAO {

    /**
     * 获取pbom信息 根据项目id puid或者零件号
     * @param map
     * @return
     */
    List<HzPbomLineRecord> getPbomById(Map<String,Object> map);


    /**
     * 插入 PBOM管理信息
     * @param record
     * @return
     */
    int insert(HzPbomLineRecord record);

    /**
     * 获取pbom_before信息 根据项目id puid或者零件号
     */
    List<HzPbomLineRecord> getPbomById_before(Map<String,Object> map);


    List<HzPbomLineRecord> getPbomById_after(Map<String,Object> map);


    int insertList(List<HzPbomLineRecord> records);

    /**
     * 批量插入变更数据
     * @param records
     * @param tableName
     * @return
     */
    int insertListForChange(List<HzPbomLineRecord> records,String tableName);

    /**
     * 编辑 PBOM管理信息
     * @param record
     * @return
     */
    int update(HzPbomLineRecord record);

    /**
     * 批量更新 根据零件号来更新
     * @param records
     * @return
     */
    int updateList(List<HzPbomLineRecord> records);

    int updatePBOMList(List<HzPbomLineRecord> records);
    /**
     * 批量更新 根据 puid进行更新
     * @param records
     * @return
     */
    int updateListByPuids(List<HzPbomLineRecord> records);

    /**
     * 导入 PBOM管理信息-修改
     */
    int updateInput(HzPbomLineRecord record);


    /**
     * 批量删除
     * @param list
     * @return
     */
    int deleteList(List<DeleteHzPbomReqDTO> list);

    int deleteListByPuids(List<String> puids,String tableName);
    /**
     * 分页获取pbom信息
     * @param query
     * @return
     */
    Page<HzPbomLineRecord>  getHzPbomRecordByPage(HzPbomByPageQuery query);

    /**
     * 查询一条pbom 根据外键id
     * @param eBomPuid
     * @return
     */
    HzPbomLineRecord getHzPbomByEbomPuid(String eBomPuid,String projectId);


    Double getHzPbomMaxOrderNum(String projectId);

    /**
     * 获取PBOM结构树
     * @param query
     * @return
     */
    List<HzPbomLineRecord> getHzPbomTree(HzPbomTreeQuery query);

    Integer getMaxLineIndexFirstNum(String projectId);

    boolean checkItemIdIsRepeat(String projectId, String lineId);


    Page<HzPbomLineRecord> getHzPbomRecycleRecord(HzBomRecycleByPageQuery query);

    String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,String orderNum);

    List<HzPbomLineRecord> getAll2YBomRecord(String projectId);

    int delete(String eBomPuid);

    List<HzPbomLineRecord> findPbom(Map<String,Object> map);


    List<HzPbomLineRecord> getPaintAndWhiteBody(String puid,String projectId);


    List<HzPbomLineRecord> getSameNameLineId(String lineId,String projectId);

    @Deprecated
    int insertAccessories(String puid, String materielCode);


    /**
     * 获取当前BOM的子一层结构
     * @param projectId
     * @return
     */
    List<HzPbomLineRecord> getFirstLevelBomByParentId(String parentId,String projectId);

    List<HzPbomLineRecord> queryAllBomLineIdByPuid(String puid, String projectId);

    /**
     * 分页获取PBOM树结构
     * @param query
     * @return
     */
    Page<HzPbomLineRecord> getPbomTreeByPage(HzPbomByPageQuery query);


    /**
     * 根据PUIDS 获取对应PBOM数据
     * @param query
     * @return
     */
    List<HzPbomLineRecord> getPbomRecordsByPuids(HzChangeDataDetailQuery query);

    /**
     * 根据Puid 进行逻辑删除
     * @param puids
     * @return
     */
    int deleteByPuids(String puids);
    /**
     * 根据Puid 进行物理删除
     * @param puids
     * @return
     */
    int deleteListByPuids(String puids,String tableName);

    HzPbomLineRecord getPBomRecordByPuidAndRevision(HzChangeDataDetailQuery query);

    List<HzPbomLineRecord> getPbomRecordsByOrderId(HzChangeDataDetailQuery query);

    /**
     * 找出大于当前查找编号的最小记录 只找出当前父层的子一层 或者只找出2Y层
     * @param query lineNo 查找编号
     * @return
     */
    HzPbomLineRecord findMinPBOMRecordWhichLineNoGreaterCurrentLineNo(HzBOMQuery query);

    /**
     * 找出小于当前查找编号的最大记录 只找出当前父层的子一层 或者只找出2Y层
     * @param query
     * @return
     */
    HzPbomLineRecord findMaxPBOMRecordWhichLineNoLessCurrentNo(HzBOMQuery query);

    /**
     * 查询PBOM 根据零件号
     * @param hzBOMQuery
     * @return
     */
    List<HzPbomLineRecord> findPbomByLineId(HzBOMQuery hzBOMQuery);

    List<HzPbomLineRecord> findPbomsByEBom(List<HzEPLManageRecord> hzEPLManageRecordsFather);

    HzPbomLineRecord findPbomByEBom(String puid, String projectId);

    HzPbomLineRecord findEbomChildrenByLineIndex(String puid, String lineNo);

    HzPbomLineRecord findNextLineIndex(String puid, String lineNo);

    HzPbomLineRecord findPreviousPbom(HzPbomLineRecord hzPbomLineRecord);

    HzPbomLineRecord findNextSortNum(HzPbomLineRecord hzPbomLineRecord);

}
