package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzSingleVehiclesBomDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzSingleVehiclesBomRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:
 */
@Service("HzSingleVehiclesBomDAO")
public class HzSingleVehiclesBomDAOImpl extends BaseSQLUtil implements HzSingleVehiclesBomDAO {
    @Override
    public int insertList(List<HzSingleVehiclesBomRecord> records) {
        return super.insert("HzSingleVehiclesBomDAOImpl_insertList",records);
    }

    @Override
    public int deleteByProjectId(String projectId) {
        if(StringUtil.isEmpty(projectId)){
            return -1;
        }
        return super.delete("HzSingleVehiclesBomDAOImpl_deleteByProjectId",projectId);
    }

    @Override
    public Page<HzSingleVehiclesBomRecord> getHzSingleVehiclesBomByPage(HzSingleVehiclesBomByPageQuery query) {
        PageRequest request = new PageRequest();
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getPBomOfWhichDept());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId());
        map.put("pBomLinePartClass",query.getPBomLinePartClass());
        map.put("pBomLinePartResource",query.getPBomLinePartResource());
        map.put("singleVehiclesId",query.getSingleVehiclesId());
        request.setPageNumber(query.getPage());
        request.setPageSize(query.getPageSize());
        request.setFilters(map);
        return super.findPage("HzSingleVehiclesBomDAOImpl_getHzSingleVehiclesBomByPage","HzSingleVehiclesBomDAOImpl_getHzSingleVehiclesBomTotalCount",request);
    }
}
