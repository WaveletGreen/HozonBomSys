package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzBomRecycleByPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzEbomTreeQuery;
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
    Integer findMinOrderNumWhichGreaterThanThisOrderNum(String projectId,int orderNum);
}
