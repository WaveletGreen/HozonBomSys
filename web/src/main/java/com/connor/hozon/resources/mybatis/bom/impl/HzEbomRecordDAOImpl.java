package com.connor.hozon.resources.mybatis.bom.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.common.constant.BOMTransConstants;
import cn.net.connor.hozon.dao.pojo.bom.epl.EbomWithPbomData;
import com.connor.hozon.resources.domain.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.resources.domain.query.*;
import cn.net.connor.hozon.services.common.enumtype.ChangeTableNameEnum;
import com.connor.hozon.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.page.PageRequestParam;
import cn.net.connor.hozon.common.exception.HzBomException;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzImportEbomRecord;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;
import cn.net.connor.hozon.common.exception.HzDBException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
@Service("hzEbomRecordDAO")
public class HzEbomRecordDAOImpl extends BaseSQLUtil implements HzEbomRecordDAO {
    @Override
    public Page<HzEPLManageRecord> getHzEbomPage(HzEbomByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        request.setPageNumber(query.getPage());
        if (BOMTransConstants.ALL.equals(query.getLimit())) {
            request.setAllNumber(true);
        } else {
            request.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("isHas", query.getIsHas());
        map.put("pBomOfWhichDept", query.getpBomOfWhichDept() != null ? query.getpBomOfWhichDept().trim() : query.getpBomOfWhichDept());
        map.put("lineIndex", query.getLineIndex());
        map.put("lineId", query.getLineId() != null ? query.getLineId().trim() : query.getLineId());
        map.put("pBomLinePartClass", query.getpBomLinePartClass());
        map.put("pBomLinePartResource", query.getpBomLinePartResource());
        map.put("sparePart", query.getSparePart());//增加一个备件过滤
        request.setFilters(map);
        return super.findPage("HzEbomRecordDAOImpl_getHzEbomList", "HzEbomRecordDAOImpl_findTotalCount", request);

    }

    @Override
    public HzEPLManageRecord findEbomById(String puid, String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("puid", puid);
        map.put("projectId", projectId);
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findEbomById", map);
    }

    @Override
    public int findIsHasByPuid(String puid, String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("puid", puid);
        map.put("projectId", projectId);
        return (int) super.findForObject("HzEbomRecordDAOImpl_findIsHasByPuid", map);
    }

    @Override
    public Integer findEBomTotalCount(String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        return super.findForObject("HzEbomRecordDAOImpl_findTotalCount", map);
    }

    @Override
    public List<HzEPLManageRecord> getHzBomLineParent(String projectId, String lineId) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("lineId", lineId);
        return super.findForList("HzEbomRecordDAOImpl_getHzBomLineParent", map);
    }


    /**
     * 找到一条bom的所有同父的子bom
     *
     * @param puid
     * @param projectId
     * @return
     */
    public List<HzEPLManageRecord> getSameHzBomLineByOne(String puid, String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("puid", puid);
        return super.findForList("HzEbomRecordDAOImpl_getSameHzBomLineByOne", map);
    }


    /**
     * 找出一条bomLine的全部子bom
     *
     * @param query
     * @return
     */
    public List<HzEPLManageRecord> getHzBomLineChildren(HzEbomTreeQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("puid", query.getPuid());
        map.put("colorPart", query.getIsColorPart());
        map.put("isCarPart", query.getIsColorPart());
        return super.findForList("HzEbomRecordDAOImpl_getHzBomLineChildren", map);
    }

    /**
     * 找出一条bomLine的全部子bom
     *
     * @param query
     * @return
     */
    @Override
    public List<EbomWithPbomData> getEbomRecursionWithPbom(HzEbomRecursionQuery query) {
        return super.findForList("HzEbomRecordDAOImpl_getEbomRecursionWithPbom", query);
    }

    @Override
    public List<EbomWithPbomData> getCurrentTreeNodePathUpAndDown(HzEbomRecursionQuery query) {
        return super.findForList("HzEbomRecordDAOImpl_getCurrentTreeNodePathUpAndDown", query);
    }

    @Override
    public int deleteList(String puids, List<String> list) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(puids)) {
            map.put("puids", Lists.newArrayList(puids.split(",")));
        } else if (ListUtils.isNotEmpty(list)) {
            map.put("puids", list);
        }
        return super.update("HzEbomRecordDAOImpl_deleteList", map);
    }

    @Override
    public int deleteByPuids(List<String> puids, String tableName) {
        int size = puids.size();
        Map<String, Object> m = new HashMap<>();
        m.put("tableName", tableName);
        try {
            if (size > 1000) {
                Map<Integer, List<String>> map = HzBomSysFactory.spiltList(puids);
                for (List<String> v : map.values()) {
                    m.put("puids", v);
                    super.delete("HzEbomRecordDAOImpl_deleteByPuids", m);
                }
            } else {
                m.put("puids", puids);
                super.delete("HzEbomRecordDAOImpl_deleteByPuids", m);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return size;
    }


    @Override
    public int updateListByPuids(List<HzEPLManageRecord> records) {
        if (ListUtils.isEmpty(records)) {
            return 0;
        }
        try {
            synchronized (this) {
                int size = records.size();
                if (size > 1000) {
                    Map<Integer, List<HzEPLManageRecord>> map = HzBomSysFactory.spiltList(records);
                    for (List<HzEPLManageRecord> value : map.values()) {
                        super.update("HzEbomRecordDAOImpl_updateListByPuids", value);
                    }
                } else {
                    super.update("HzEbomRecordDAOImpl_updateListByPuids", records);
                }
                return size;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HzDBException("数据更新失败！", e);
        }
    }

    @Override
    public int updateListByEplId(List<HzEPLManageRecord> records) {
        if (ListUtils.isEmpty(records)) {
            return 0;
        }
        try {
            synchronized (this) {
                int size = records.size();
                if (size > 1000) {
                    Map<Integer, List<HzEPLManageRecord>> map = HzBomSysFactory.spiltList(records);
                    for (List<HzEPLManageRecord> value : map.values()) {
                        super.update("HzEbomRecordDAOImpl_updateListByEplId", value);
                    }
                } else {
                    super.update("HzEbomRecordDAOImpl_updateListByEplId", records);
                }
                return size;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HzDBException("数据更新失败！", e);
        }
    }

    @Override
    public int updateEBOMListByEplId(List<HzEPLManageRecord> records) {
        if (ListUtils.isEmpty(records)) {
            return 0;
        }
        try {
            synchronized (this) {
                int size = records.size();
                if (size > 1000) {
                    Map<Integer, List<HzEPLManageRecord>> map = HzBomSysFactory.spiltList(records);
                    for (List<HzEPLManageRecord> value : map.values()) {
                        super.update("HzEbomRecordDAOImpl_updateEBOMListByEplId", value);
                    }
                } else {
                    super.update("HzEbomRecordDAOImpl_updateEBOMListByEplId", records);
                }
                return size;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new HzDBException("数据更新失败！", e);
        }
    }

    @Override
    public Page<HzEPLManageRecord> getHzRecycleRecord(HzBomRecycleByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        Map map = new HashMap();
        map.put("projectId", query.getProjectId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzEbomRecordDAOImpl_getHzRecycleRecord", "HzEbomRecordDAOImpl_findRecycleTotalCount", request);
    }

    @Override
    public HzEPLManageRecord getHasDeletedBom(String puid, String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("puid", puid);
        map.put("projectId", projectId);
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findHasDeletedBom", map);
    }

    @Override
    public List<HzEPLManageRecord> findEbom(Map<String, Object> map) {

        return super.findForList("HzEbomRecordDAOImpl_findEbom", map);
    }


    @Override
    public String findMinOrderNumWhichGreaterThanThisOrderNum(String projectId, String sortNum) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("sortNum", Double.parseDouble(sortNum));
        return (String) super.findForObject("HzEbomRecordDAOImpl_findMinOrderNumWhichGreaterThanThisOrderNum", map);
    }

    @Override
    public int insert(HzEPLManageRecord record) {
        if (StringUtils.isBlank(record.getTableName())) {
            record.setTableName(ChangeTableNameEnum.HZ_EBOM.getTableName());
        }
        return super.insert("HzEbomRecordDAOImpl_insert", record);
    }

    @Override
    public int update(HzEPLManageRecord record) {
        return super.update("HzEbomRecordDAOImpl_updateAllChild", record);
    }

    @Override
    public int insertList(List<HzEPLManageRecord> records, String tableName) {
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", tableName);
        int size = records.size();
        //分批插入数据 一次1000条
        int i = 0;
        int cout = 0;
        try {
            synchronized (this) {
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzEPLManageRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        map.put("list", list);//map key相同 value会被替代
                        super.insert("HzEbomRecordDAOImpl_insertList", map);
                    }
                }
                if (i * 1000 < size) {
                    List<HzEPLManageRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    map.put("list", list);
                    super.insert("HzEbomRecordDAOImpl_insertList", map);
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public int importList(List<HzImportEbomRecord> records) {
        int size = records.size();
        //分批插入数据 一次1000条
        int i = 0;
        int cout = 0;
        try {
            synchronized (this) {
                if (size > 1000) {
                    for (i = 0; i < size / 1000; i++) {
                        List<HzImportEbomRecord> list = new ArrayList<>();
                        for (int j = 0; j < 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.insert("HzEbomRecordDAOImpl_importList", list);
                    }
                }
                if (i * 1000 < size) {
                    List<HzImportEbomRecord> list = new ArrayList<>();
                    for (int j = 0; j < size - i * 1000; j++) {
                        list.add(records.get(cout));
                        cout++;
                    }
                    super.insert("HzEbomRecordDAOImpl_importList", list);
                }
            }
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HzBomException("数据插入失败！", e);
        }

    }

    @Override
    public boolean lineIndexRepeat(String projectId, String lineIndex) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("lineIndex", lineIndex);
        return (int) super.findForObject("HzEbomRecordDAOImpl_lineIndexRepeat", map) > 0;
    }

    @Override
    public List<HzEPLManageRecord> getAll2YBomRecord(String projectId, Integer colorPart) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        map.put("colorPart", colorPart);
        return super.findForList("HzEbomRecordDAOImpl_getAll2YBomRecord", map);
    }

    @Override
    public List<HzEPLManageRecord> getSameNameLineId(String lineId, String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("lineId", lineId);
        map.put("projectId", projectId);
        return super.findForList("HzEbomRecordDAOImpl_getSameNameLineId", map);
    }

    @Override
    public List<HzEPLManageRecord> getPaintAndWhiteBody(String puid, String projectId) {
        Map<String, Object> map = new HashMap<>();
        map.put("puid", puid);
        map.put("projectId", projectId);
        return super.findForList("HzEbomRecordDAOImpl_getPaintAndWhiteBody", map);
    }

    @Override
    public List<HzEPLManageRecord> getEbomRecordsByPuids(HzChangeDataDetailQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("puids", query.getPuids());
        map.put("projectId", query.getProjectId());
        if (null != query.getRevision()) {
            map.put("revision", query.getRevision() ? "" : null);
        } else {
            map.put("revision", null);
        }
        map.put("status", query.getStatus());
        map.put("tableName", query.getTableName());
        map.put("orderId", query.getOrderId());
        return super.findForList("HzEbomRecordDAOImpl_getEbomRecordsByPuids", map);
    }

    @Override
    public HzEPLManageRecord getEBomRecordByPuidAndRevision(HzChangeDataDetailQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("puid", query.getPuid());
        map.put("projectId", query.getProjectId());
        if (null != query.getRevision()) {
            map.put("revision", query.getRevision() ? query.getRevisionNo() : null);
        } else {
            map.put("revision", null);
        }
        map.put("status", query.getStatus());
        map.put("tableName", query.getTableName());
        return super.findForObject("HzEbomRecordDAOImpl_getEBomRecordByPuidAndRevision", map);
    }

    @Override
    public List<HzEPLManageRecord> getEbomRecordsByOrderId(HzChangeDataDetailQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("tableName", query.getTableName());
        map.put("orderId", query.getOrderId());
        map.put("status", query.getStatus());
        if (null != query.getRevision()) {
            map.put("revision", query.getRevision() ? "1" : "0");
        } else {
            map.put("revision", null);
        }
        return super.findForList("HzEbomRecordDAOImpl_getEbomRecordsByOrderId", map);
    }

    @Override
    public HzEPLManageRecord getMaxLineIndexFirstNum(String projectId) {
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_getMaxLineIndexFirstNum", projectId);
    }

    /**
     * 找出最大排序号
     *
     * @return
     * @author haozt
     */
    public Double findMaxBomOrderNum(String projectId) {
        String maxSortNum = super.findForObject("HzEbomRecordDAOImpl_findMaxBomOrderNum", projectId);
        if (maxSortNum == null) {
            return null;
        }
        return Double.parseDouble(maxSortNum);
    }

    /**
     * 找出子中对应排序号的数据
     *
     * @param lineId
     * @param lineNo
     * @return
     */
    @Override
    public HzEPLManageRecord findEbomChildrenByLineIndex(String lineId, String lineNo) {
        HzEPLManageRecord hzEPLManageRecord = new HzEPLManageRecord();
        hzEPLManageRecord.setPuid(lineId);
        hzEPLManageRecord.setLineIndex(lineNo);
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findEbomChildrenByLineIndex", hzEPLManageRecord);
    }

    @Override
    public HzEPLManageRecord findPreviousEbom(HzEPLManageRecord hzEPLManageRecord) {
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findPreviousEbom", hzEPLManageRecord);
    }

    @Override
    public HzEPLManageRecord findNextLineIndex(String lineId, String lineNo) {
        HzEPLManageRecord hzEPLManageRecord = new HzEPLManageRecord();
        hzEPLManageRecord.setPuid(lineId);
        hzEPLManageRecord.setLineIndex(lineNo);
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findNextLineIndex", hzEPLManageRecord);
    }

    @Override
    public List<HzEPLManageRecord> findBaseEbomById(String lineId, String projectId) {
        Map<String, String> map = new HashMap<>();
        map.put("lineId", lineId);
        map.put("projectId", projectId);
        return super.findForList("HzEbomRecordDAOImpl_findBaseEbomById", map);
    }

    @Override
    public HzEPLManageRecord findNextSortNum(HzEPLManageRecord hzEPLManageRecordPrevious) {
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findNextSortNum", hzEPLManageRecordPrevious);
    }

    @Override
    public HzEPLManageRecord findMinEBOMRecordWhichLineNoGreaterCurrentLineNo(HzBOMQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("is2Y", query.getIs2Y());
        map.put("parentId", query.getParentId());
        map.put("projectId", query.getProjectId());
        map.put("lineNo", query.getLineNo());
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findMinEBOMRecordWhichLineNoGreaterCurrentLineNo", map);
    }

    @Override
    public HzEPLManageRecord findMaxEBOMRecordWhichLineNoLessCurrentNo(HzBOMQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("is2Y", query.getIs2Y());
        map.put("parentId", query.getParentId());
        map.put("projectId", query.getProjectId());
        map.put("lineNo", query.getLineNo());
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findMaxEBOMRecordWhichLineNoLessCurrentNo", map);
    }

    @Override
    public List<HzEPLManageRecord> findEBOMRecordsByEPLId(HzBOMQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("eplId", query.getEplId());
        map.put("projectId", query.getProjectId());
        return super.findForList("HzEbomRecordDAOImpl_findEBOMRecordsByEPLId", map);
    }

    @Override
    public List<HzEPLManageRecord> findNextLevelRecordByParentId(HzBOMQuery query) {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", query.getProjectId());
        map.put("parentId", query.getParentId());
        return super.findForList("HzEbomRecordDAOImpl_findNextLevelRecordByParentId", map);
    }

    @Override
    public List<HzEPLManageRecord> findALLBOM(String projectId) {
        return super.findForList("HzEbomRecordDAOImpl_findALLBOM", projectId);
    }

    @Override
    public int updateEPLList(List<HzEPLManageRecord> hzEPLManageRecords) {
        return super.update("HzEbomRecordDAOImpl_updateEPLList", hzEPLManageRecords);
    }

    @Override
    public int updateByDto(UpdateHzEbomReqDTO reqDTO) {
        return super.update("HzEbomRecordDAOImpl_updateByDto", reqDTO);
    }

    @Override
    public HzEPLManageRecord findEbom2Y(Map<String, Object> map) {
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findEbom2Y", map);
    }

    @Override
    public Page<HzEPLManageRecord> getHzEbomTreeByPage(HzEbomByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        request.setPageNumber(query.getPage());
        if (BOMTransConstants.ALL.equals(query.getLimit())) {
            request.setAllNumber(true);
        } else {
            request.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("puids", Lists.newArrayList(query.getPuids().split(",")));
        map.put("projectId", query.getProjectId());
        request.setFilters(map);
        return super.findPage("HzEbomRecordDAOImpl_getHzEbomTreeByPage", "HzEbomRecordDAOImpl_getHzEbomTreeTotalCount", request);

    }
}
