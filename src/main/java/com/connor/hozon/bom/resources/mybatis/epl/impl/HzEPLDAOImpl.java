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
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        map.put("partId",query.getPartId());
        return (int)super.findForObject("HzEPLDAOImpl_partIdRepeat",query)>0;
    }

    @Override
    public HzEPLRecord getPartFromEPLRecordById(HzEPLQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        map.put("partId",query.getPartId());
        return (HzEPLRecord)super.findForObject("HzEPLDAOImpl_getPartFromEPLRecordById",query);
    }

    @Override
    public Page<HzEPLRecord> getEplRecordByPage(HzEPLByPageQuery query) {
        PageRequestParam param = new PageRequestParam();
        param.setPageSize(query.getPageSize());
        if(BOMTransConstants.ALL.equals(query.getLimit())){
            param.setAllNumber(true);
        }else {
            param.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("partId",query.getPartId().trim());
        map.put("partName",query.getPartName().trim());
        map.put("partOfWhichDept",query.getPartOfWhichDept().trim());
        map.put("partResource",query.getPartResource().trim());
        map.put("projectId",query.getProjectId());
        param.setFilters(map);
        return super.findPage("HzEPLDAOImpl_getEplRecordByPage","HzEPLDAOImpl_getEplTotalCount",param);
    }
}
