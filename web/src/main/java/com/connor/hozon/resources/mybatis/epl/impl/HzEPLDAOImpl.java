package com.connor.hozon.resources.mybatis.epl.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.resources.domain.query.HzEPLQuery;
import com.connor.hozon.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.page.PageRequestParam;
import com.connor.hozon.resources.util.Result;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLRecord;
import cn.net.connor.hozon.common.exception.HzDBException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description:
 */
@Service("hzEPLDAO")
public class HzEPLDAOImpl extends BaseSQLUtil implements HzEPLDAO {
    @Override
    public int insert(HzEPLRecord record) {
        return super.insert("HzEPLDAOImpl_insert",record);
    }

    @Override
    public int update(HzEPLRecord record) {
        return super.update("HzEPLDAOImpl_update",record);
    }

    @Override
    public int delete(String ids,List<Long> list) {
        if(StringUtils.isNotBlank(ids)){
            List<String> l = Lists.newArrayList(ids.split(","));
            return super.delete("HzEPLDAOImpl_delete",l);
        }else if(ListUtils.isNotEmpty(list)){
            return super.delete("HzEPLDAOImpl_delete",list);
        }else {
            return 0;
        }
    }

    @Override
    public int deleteByIds(List<Long> list) {
        return super.update("HzEPLDAOImpl_deleteByIds",list);
    }

    @Override
    public Result partIdRepeat(HzEPLQuery query) {
        try {
            Long id = query.getId();
            if(id != null){
                query.setId(null);//不根据id 来查重
            }
            HzEPLRecord record  = getEPLRecordById(query);
            if(record == null){
                return Result.build(false,record);
            }
            if(record.getId().equals(id)){
                return Result.build(false,record);
            }
            return Result.build(true,record);
        }catch (Exception e){
            e.printStackTrace();
            throw new HzDBException("数据查询异常!",e);
        }
    }

    @Override
    public HzEPLRecord getEPLRecordById(HzEPLQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        map.put("partId",query.getPartId());
        map.put("projectId",query.getProjectId());
        return (HzEPLRecord)super.findForObject("HzEPLDAOImpl_getEPLRecordById",map);
    }

    @Override
    public Page<HzEPLRecord> getEplRecordByPage(HzEPLByPageQuery query) {
        PageRequestParam param = new PageRequestParam();
        param.setPageNumber(query.getPage());
        if(BOMTransConstants.ALL.equals(query.getLimit())){
            param.setAllNumber(true);
        }else {
            param.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("partId",query.getPartId().trim());
        map.put("partOfWhichDept",query.getPartOfWhichDept().trim());
        map.put("partResource",query.getPartResource());
        map.put("projectId",query.getProjectId());
        map.put("status",query.getStatus());
        param.setFilters(map);
        return super.findPage("HzEPLDAOImpl_getEplRecordByPage","HzEPLDAOImpl_getEplTotalCount",param);
    }

    @Override
    public HzEPLRecord selectByPartId(String partId) {
        HzEPLRecord hzEPLRecord = new HzEPLRecord();
        hzEPLRecord.setPartId(partId);
        return (HzEPLRecord)super.findForObject("HzEPLDAOImpl_selectByPartId",hzEPLRecord);
    }

    @Override
    public List<HzEPLRecord> selectByprojectPuid(String projectPuid) {
        return super.findForList("HzEPLDAOImpl_selectByprojectPuid",projectPuid);
    }

    @Override
    public int updateList(List<HzEPLRecord> hzEPLRecordsUpdate) {
        return super.update("HzEPLDAOImpl_updateList",hzEPLRecordsUpdate);
    }

    @Override
    public int insertList(List<HzEPLRecord> hzEPLRecords) {
        if(ListUtils.isEmpty(hzEPLRecords)){
            return 0;
        }
        int size = hzEPLRecords.size();
        synchronized (this){
            if(size > 1000){
                Map<Integer,List<HzEPLRecord>> map = HzBomSysFactory.spiltList(hzEPLRecords);
                for(List<HzEPLRecord> list : map.values()){
                    super.insert("HzEPLDAOImpl_insertList",list);
                }
            }else {
                super.insert("HzEPLDAOImpl_insertList",hzEPLRecords);
            }
        }
        return size;
    }

    @Override
    public List<Long> getEPLIdsWherePartIdEndWithWSAndGDT(String projectId) {
        return super.findForList("HzEPLDAOImpl_getEPLIdsWherePartIdEndWithWSAndGDT",projectId);
    }

    @Override
    public List<Long> getVirtualAssemblyEPLRecord(String projectId) {
        return super.findForList("HzEPLDAOImpl_getVirtualAssemblyEPLRecord",projectId);
    }

    @Override
    public int updateVirtualAssemblyEnToZh(List<Long> longs) {
        return super.update("HzEPLDAOImpl_updateVirtualAssemblyEnToZh",longs);
    }
}
