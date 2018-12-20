package com.connor.hozon.bom.resources.mybatis.epl.impl;

import com.connor.hozon.bom.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEPLQuery;
import com.connor.hozon.bom.resources.mybatis.epl.HzEPLDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import com.google.common.collect.Lists;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.epl.HzEPLRecord;

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
    public int delete(String ids) {
        List<String> list = Lists.newArrayList(ids.split(","));
        return super.delete("HzEPLDAOImpl_delete",list);
    }

    @Override
    public boolean partIdRepeat(HzEPLQuery query) {
        try {
            Long id = query.getId();
            if(id != null){
                query.setId(null);//不根据id 来查重
            }
            HzEPLRecord record  = getEPLRecordById(query);
            if(record == null){
                return false;
            }
            if(record.getId().equals(id)){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public HzEPLRecord getEPLRecordById(HzEPLQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        map.put("partId",query.getPartId());
        map.put("projectId",query.getProjectId());
        return (HzEPLRecord)super.findForObject("HzEPLDAOImpl_getEPLRecordById",query);
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
        return super.insert("HzEPLDAOImpl_insertList",hzEPLRecords);
    }
}
