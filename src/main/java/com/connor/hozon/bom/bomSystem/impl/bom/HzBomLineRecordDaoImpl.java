/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.bom;

import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBomLineRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("HzBomLineRecordDaoImpl")
public class HzBomLineRecordDaoImpl extends BaseSQLUtil {

    /**
     * 插入一条数据
     *
     * @param record
     * @return
     * @author haozt
     */
    public int insert(HzBomLineRecord record) {
        if (null == record.getTableName() || "".equals(record.getTableName())) {
            record.setTableName("HZ_BOM_LINE_RECORD");
        }
        return super.insert("HzBomLineRecordDaoImpl_insert", record);
    }

    /**
     * 更新一条数据
     *
     * @param record
     * @return
     * @author haozt
     */
    public int update(HzBomLineRecord record) {
        if (null == record.getTableName() || "".equals(record.getTableName())) {
            record.setTableName("HZ_BOM_LINE_RECORD");
        }
        return super.update("HzBomLineRecordDaoImpl_update", record);
    }

    /**
     * 更新颜色件信息  1是颜色件 0不是
     * @param puid
     * @param isColorPart
     * @return
     */
    public int updateColorPart(String puid,Integer isColorPart){
        Map<String,Object> map  = new HashMap<>();
        map.put("puid",puid);
        map.put("colorPart",isColorPart);
        return super.update("HzBomLineRecordDaoImpl_updateColorPart",map);
    }
    /**
     * 找出最大排序号
     *
     * @return
     * @author haozt
     */
    public Double findMaxBomOrderNum(String projectId) {
        String maxSortNum = (String)super.findForObject("HzBomLineRecordDaoImpl_findMaxBomOrderNum", projectId);
        if(maxSortNum == null){
            return null;
        }
        return Double.parseDouble(maxSortNum);
    }

    /**
     * 找出一条BOMLine 对象 走变更查询单一条记录
     *
     * @param map 过滤器
     * @return
     * @author haozt
     */
    public HzBomLineRecord findBomLineByPuid(Map map) {
        if (null == map.get("tableName") || map.get("tableName").equals("")) {
            map.put("tableName", "HZ_BOM_LINE_RECORD");
        }
        return (HzBomLineRecord) super.findForObject("HzBomLineRecordDaoImpl_findBomLineByPuid", map);
    }


    /**
     * 找出一条BOMLine 对象 与变更无关
     *
     * @param map 需要传入的参数是<strong>projectId----项目ID</strong> <span style='color:red;'>puid---BOMLine主键</span>
     * @return
     * @author haozt
     */
    public HzBomLineRecord findBomLine(Map map) {
        return (HzBomLineRecord) super.findForObject("HzBomLineRecordDaoImpl_findBomLine", map);
    }


    /**
     * 查询设变时BOM 历史记录
     *
     * @param map 过滤器
     * @return
     * @author haozt
     */
    public List<HzBomLineRecord> findBomListForChange(Map map) {
        if (null == map.get("tableName") || map.get("tableName").equals("")) {
            map.put("tableName", "HZ_BOM_LINE_RECORD");
        }
        return super.findForList("HzBomLineRecordDaoImpl_findBomListForChange", map);
    }

    /**
     * 找出全部的2或者2Y层级结构
     * @return
     */
    public List<String> findBomLineIndex(Map map) {
        return super.findForList("HzBomLineRecordDaoImpl_findBomLineIndex", map);
    }

    public Integer getBomLineRecordNumber(String projectId) {
        return (Integer) super.findForObject("HzBomLineRecordDaoImpl_getBomLineRecordNumber", projectId);
    }

    public List<HzBomLineRecord> getLoadingCarPartBom(String projectId) {

        return super.findForList("HzBomLineRecordDaoImpl_getLoadingCarPartBom", projectId);
    }

    public int delete(String puid) {
        return super.delete("HzEbomRecordDAOImpl_delete", puid);
    }

    /**
     * 批量更新
     * 走变更 这里只更新了部分字段
     * @param records
     * @return
     */
    public int updateBatch(List<HzBomLineRecord> records) {
        return super.update("HzEbomRecordDAOImpl_updateBatch", records);
    }


    public Integer getMaxLineIndexFirstNum(String projectId){
        return (Integer) super.findForObject("HzBomLineRecordDaoImpl_getMaxLineIndexFirstNum",projectId);
    }

}
