package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
@Service("HzEbomRecordDAO")
public class HzEbomRecordDAOImpl extends BaseSQLUtil implements HzEbomRecordDAO {

    @Override
    public List<HzEPLManageRecord> getHzEbomList(FindForPageReqDTO reqDTO) {
        return super.findForList("HzEbomRecordDAOImpl_getHzEbomList",null);
    }

    @Override
    public Page<HzEPLManageRecord> getHzEbomPage(FindForPageReqDTO recordReqDTO) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",recordReqDTO.getProjectId());
        request.setPageNumber(recordReqDTO.getPage());
        request.setPageSize(recordReqDTO.getLimit());
        request.setFilters(map);
        return super.findForPage("HzEbomRecordDAOImpl_getHzEbomList","HzEbomRecordDAOImpl_findTotalCount",request);

    }

    @Override
    public HzEPLManageRecord findEbomById(String puid,String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        map.put("projectId",projectId);
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_getHzEbomList",map);
    }


}
