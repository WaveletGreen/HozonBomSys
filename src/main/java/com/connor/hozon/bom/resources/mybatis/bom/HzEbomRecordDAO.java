package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzImportEbomRecord;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomRecordDAO {


    Page<HzEPLManageRecord> getHzEbomPage(HzEbomByPageQuery query);

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

    int findIsHasByPuid(String puid, String projectId);

    /**
     * 逆向找父层
     * @param projectId
     * @param lineId
     * @return
     */
    List<HzEPLManageRecord> getHzBomLineParent(String projectId,String lineId);

    /**
     * 找出一条bomLine的全部子bom sql递归查找
     * @param query
     * @return
     */
    List<HzEPLManageRecord> getHzBomLineChildren(HzEbomTreeQuery query);

    /**
     * 查询零件号是否重复
     * @param projectId
     * @param lineId
     * @return
     */
    boolean checkItemIdIsRepeat(String projectId,String lineId);

    /**
     * 批量删除
     * @param reqDTOs
     * @return
     */
    int deleteList(List<DeleteHzEbomReqDTO> reqDTOs);

    int delete(String puid);

    /**
     * 查询回收站
     * @param query
     * @return
     */
    Page<HzEPLManageRecord> getHzRecycleRecord(HzBomRecycleByPageQuery query);

    HzEPLManageRecord getHasDeletedBom(String puid,String projectId);

    List<HzEPLManageRecord> findEbom(Map<String,Object> map);

    /**
     * 找出大于当前排序号的最小排序号
     * @param projectId
     * @return
     */
    String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,String sortNum);

    int insert(HzEPLManageRecord record);
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
     * 查询全部的2Y层是否颜色件信息
     * @param projectId
     * @return
     */
    List<HzEPLManageRecord> getAll2YBomRecord(String projectId,Integer colorPart);


    List<HzEPLManageRecord> getSameNameLineId(String lineId,String projectId);

    List<HzEPLManageRecord> getPaintAndWhiteBody(String puid,String projectId);
}
