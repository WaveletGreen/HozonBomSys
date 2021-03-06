package com.connor.hozon.resources.mybatis.bom;

import cn.net.connor.hozon.dao.pojo.bom.epl.EbomWithPbomData;
import com.connor.hozon.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.resources.domain.query.*;
import com.connor.hozon.resources.page.Page;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzImportEbomRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomRecordDAO {

    int insert(HzEPLManageRecord record);

    int update(HzEPLManageRecord record);

    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    int insertList(List<HzEPLManageRecord> records, String tableName);


    int importList(List<HzImportEbomRecord> records);


    boolean lineIndexRepeat(String projectId, String lineIndex);

    /**
     * 可选择向上递归或者向下递归查询当前选中的节点树形结构
     *
     * @param query up=true则向上递归查询父层信息，down=true则向下查询子信息
     *              <p>
     *              如果2次调用up=true和down=true时，会出现1条重复的数据，这条数据时当前选中的节点信息，需要进行去重
     *              <p>
     *              isColorPart可以用来筛选颜色件信息
     *              <p>
     *              isCarPart没有用来进行筛选
     * @return
     */
    List<EbomWithPbomData> getEbomRecursionWithPbom(HzEbomRecursionQuery query);

    /**
     * 获取到当前选中的节点的上下节点路径信息
     *
     * @param query 只需要传入projectId和当前节点ID信息即可，其他查询参数不需要
     * @return
     */
    List<EbomWithPbomData> getCurrentTreeNodePathUpAndDown(HzEbomRecursionQuery query);

    /**
     * 批量删除
     *
     * @param puids 主键ids 中间全部用英文逗号隔开
     * @param list  删除参数 两种参数 只传一种就行
     * @return
     */
    int deleteList(String puids, List<String> list);

    /**
     * 删除 直接delete
     *
     * @param puids
     * @return
     */
    int deleteByPuids(List<String> puids, String tableName);

    /**
     * 根据主键进行批量更新
     *
     * @param records
     * @return
     */
    int updateListByPuids(List<HzEPLManageRecord> records);

    /**
     * 根据零件号（EPL ID）
     *
     * @param records
     * @return
     */
    int updateListByEplId(List<HzEPLManageRecord> records);

    int updateEBOMListByEplId(List<HzEPLManageRecord> records);

    int findIsHasByPuid(String puid, String projectId);

    /**
     * 查询当前项目下的EBOM 数量
     *
     * @param projectId 项目id
     * @return BOM 数目
     */
    Integer findEBomTotalCount(String projectId);

    Page<HzEPLManageRecord> getHzEbomPage(HzEbomByPageQuery query);

    /**
     * 查询回收站
     *
     * @param query
     * @return
     */
    Page<HzEPLManageRecord> getHzRecycleRecord(HzBomRecycleByPageQuery query);

    /**
     * 分页获取EBOM结构树
     *
     * @param query
     * @return
     */
    Page<HzEPLManageRecord> getHzEbomTreeByPage(HzEbomByPageQuery query);

    /**
     * 找到一条bom的所有同父的子bom
     *
     * @param puid
     * @param projectId
     * @return
     */
    List<HzEPLManageRecord> getSameHzBomLineByOne(String puid, String projectId);


    /**
     * 找出一条bomLine的全部子bom sql递归查找
     *
     * @param query
     * @return
     */
    List<HzEPLManageRecord> getHzBomLineChildren(HzEbomTreeQuery query);

    /**
     * 找一条EBOM  EBOM是EPL子集 这里直接返回EPL 全信息了
     *
     * @param puid
     * @param projectId
     * @return
     */
    HzEPLManageRecord findEbomById(String puid, String projectId);

    /**
     * 逆向找父层
     *
     * @param projectId
     * @param lineId
     * @return
     */
    List<HzEPLManageRecord> getHzBomLineParent(String projectId, String lineId);

    HzEPLManageRecord getHasDeletedBom(String puid, String projectId);

    List<HzEPLManageRecord> findEbom(Map<String, Object> map);

    /**
     * 找出大于当前排序号的最小排序号
     *
     * @param projectId
     * @return
     */
    String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId, String sortNum);

    /**
     * 查询全部的2Y层是否颜色件信息
     *
     * @param projectId
     * @return
     */
    List<HzEPLManageRecord> getAll2YBomRecord(String projectId, Integer colorPart);

    List<HzEPLManageRecord> getSameNameLineId(String lineId, String projectId);

    List<HzEPLManageRecord> getPaintAndWhiteBody(String puid, String projectId);

    /**
     * 查询获取puids 所对应的BOM Line
     *
     * @return
     */
    List<HzEPLManageRecord> getEbomRecordsByPuids(HzChangeDataDetailQuery query);

    HzEPLManageRecord getEBomRecordByPuidAndRevision(HzChangeDataDetailQuery query);


    /**
     * 根据表单id查询变更数据
     *
     * @param query
     * @return
     */
    List<HzEPLManageRecord> getEbomRecordsByOrderId(HzChangeDataDetailQuery query);

    /**
     * 找EBOM 2Y层lineIndex的第一位最大值
     *
     * @param projectId
     * @return maxLineIndexFirstNum  lineIndex
     */
    HzEPLManageRecord getMaxLineIndexFirstNum(String projectId);

    /**
     * 找出最大排序号
     *
     * @param projectId
     * @return
     */
    Double findMaxBomOrderNum(String projectId);

    HzEPLManageRecord findEbomChildrenByLineIndex(String lineId, String lineNo);

    HzEPLManageRecord findPreviousEbom(HzEPLManageRecord hzEPLManageRecord);

    HzEPLManageRecord findNextLineIndex(String lineId, String lineNo);

    List<HzEPLManageRecord> findBaseEbomById(String lineId, String projectId);

    HzEPLManageRecord findNextSortNum(HzEPLManageRecord hzEPLManageRecordPrevious);

    /**
     * 找出大于当前查找编号的最小记录 只找出当前父层的子一层 或者只找出2Y层
     *
     * @param query lineNo 查找编号
     * @return
     */
    HzEPLManageRecord findMinEBOMRecordWhichLineNoGreaterCurrentLineNo(HzBOMQuery query);

    /**
     * 找出小于当前查找编号的最大记录 只找出当前父层的子一层 或者只找出2Y层
     *
     * @param query
     * @return
     */
    HzEPLManageRecord findMaxEBOMRecordWhichLineNoLessCurrentNo(HzBOMQuery query);

    int updateEPLList(List<HzEPLManageRecord> hzEPLManageRecordsFather);

    int updateByDto(UpdateHzEbomReqDTO reqDTO);

    HzEPLManageRecord findEbom2Y(Map<String, Object> map);

    /**
     * 根据EPLId 查询EBOM记录
     *
     * @param query
     * @return
     */
    List<HzEPLManageRecord> findEBOMRecordsByEPLId(HzBOMQuery query);

    /**
     * 获取子一层记录
     *
     * @param query
     * @return
     */
    List<HzEPLManageRecord> findNextLevelRecordByParentId(HzBOMQuery query);

    /**
     * 测试代码 为了同步正式机数据
     *
     * @param projectId
     * @return
     */
    @Deprecated
    List<HzEPLManageRecord> findALLBOM(String projectId);
}
