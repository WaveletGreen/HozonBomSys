package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.query.HzEbomByPageQuery;
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
    public Page<HzEPLManageRecord> getHzEbomPage(HzEbomByPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getpBomOfWhichDept());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getLimit());
        request.setFilters(map);
        return super.findForPage("HzEbomRecordDAOImpl_getHzEbomList","HzEbomRecordDAOImpl_findTotalCount",request);

    }

    @Override
    public HzEPLManageRecord findEbomById(String puid,String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("puid",puid);
        map.put("projectId",projectId);
        return (HzEPLManageRecord) super.findForObject("HzEbomRecordDAOImpl_findEbomById",map);
    }

    /**
     * 找出一条bomLine的全部子bom
     * @param map
     * @return
     */
    public List<HzEPLManageRecord> getHzBomLineChildren(Map map){
        return super.findForList("HzEbomRecordDAOImpl_getHzBomLineChildren",map);
    }

    @Override
    public boolean checkItemIdIsRepeat(String projectId, String lineId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("lineID",lineId);
        return (int)super.findForObject("HzEbomRecordDAOImpl_checkItemIdIsRepeat",map)>0;
    }

}
