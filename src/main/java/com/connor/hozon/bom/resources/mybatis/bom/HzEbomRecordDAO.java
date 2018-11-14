package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzImportEbomRecord;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomRecordDAO {

    int insert(HzEPLManageRecord record);

    /**
     * 批量插入
     * @param records
     * @return
     */
    int insertList(List<HzEPLManageRecord> records,String tableName);

    /**
     * 复制层级的单条插入EBOM
     * @param record
     * @return
     */
    int insert2(HzEPLManageRecord record);

    int importList(List<HzImportEbomRecord> records);

    boolean sortNumRepeat(String projectId,String sortNum);

    boolean lineIndexRepeat(String projectId,String lineIndex);

    /**
     * 查询零件号是否重复
     * @param projectId
     * @param lineId
     * @return
     */
    boolean checkItemIdIsRepeat(String projectId,String lineId);

    /**
     * 批量删除
     * @param puids 主键ids 中间全部用英文逗号隔开
     * @return
     */
    int deleteList(String puids);

    int delete(String puid);

    /**
     * EBOM 批量更新 根据零件号来更新
     * @param records
     * @return
     */
    int updateList(List<HzBomLineRecord> records);

    int findIsHasByPuid(String puid, String projectId);

    Page<HzEPLManageRecord> getHzEbomPage(HzEbomByPageQuery query);
    /**
     * 查询回收站
     * @param query
     * @return
     */
    Page<HzEPLManageRecord> getHzRecycleRecord(HzBomRecycleByPageQuery query);
    /**
     * 分页获取EBOM结构树
     * @param query
     * @return
     */
    Page<HzEPLManageRecord> getHzEbomTreeByPage(HzEbomByPageQuery query);


    /**
     * 找出一条bomLine的全部子bom sql递归查找
     * @param query
     * @return
     */
    List<HzEPLManageRecord> getHzBomLineChildren(HzEbomTreeQuery query);
    /**
     * 找一条EBOM  EBOM是EPL子集 这里直接返回EPL 全信息了
     * @param puid
     * @param projectId
     * @return
     */
    HzEPLManageRecord findEbomById(String puid,String projectId);
    /**
     * 根据父层零件ID找父层puid
     * @param itemId
     * @param projectId
     * @return
     */
    List<HzEPLManageRecord> findEbomByItemId(String itemId,String projectId);
    /**
     * 逆向找父层
     * @param projectId
     * @param lineId
     * @return
     */
    List<HzEPLManageRecord> getHzBomLineParent(String projectId,String lineId);

    HzEPLManageRecord getHasDeletedBom(String puid,String projectId);

    List<HzEPLManageRecord> findEbom(Map<String,Object> map);
    /**
     * 找出大于当前排序号的最小排序号
     * @param projectId
     * @return
     */
    String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,String sortNum);
    /**
     * 查询全部的2Y层是否颜色件信息
     * @param projectId
     * @return
     */
    List<HzEPLManageRecord> getAll2YBomRecord(String projectId,Integer colorPart);

    List<HzEPLManageRecord> getSameNameLineId(String lineId,String projectId);

    List<HzEPLManageRecord> getPaintAndWhiteBody(String puid,String projectId);

    /**
     * 查询获取puids 所对应的BOM Line
     * @param puids
     * @param projectId
     * @return
     */
    List<HzEPLManageRecord> getEbomRecordsByPuids(String puids,String projectId);

}
