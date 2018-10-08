package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzPbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzPbomTreeQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzPbomLineRecord;

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


    int insertList(List<HzPbomLineRecord> records);
    /**
     * 编辑 PBOM管理信息
     * @param record
     * @return
     */
    int update(HzPbomLineRecord record);

    /**
     * 删除PBOM管理 通过外键删除
     * @param ePuid
     * @return
     */
    int recoverBomById(String ePuid);

    /**
     * 批量删除
     * @param list
     * @return
     */
    int deleteList(List<DeleteHzPbomReqDTO> list);

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

    /**
     * 获取Pbom 总数
     * @param
     * @return
     */
    int getHzBomLineCount(String projectId);

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

    int insertAccessories(String puid, String materielCode);

    List<HzPbomLineRecord> queryAllBomLineIdByPuid(String puid);

    /**
     * 获取当前BOM的子一层结构
     * @param projectId
     * @return
     */
    List<HzPbomLineRecord> getFirstLevelBomByParentId(String parentId,String projectId);

    List<HzPbomLineRecord> queryAllBomLineIdByPuid(String puid, String projectId);
}
