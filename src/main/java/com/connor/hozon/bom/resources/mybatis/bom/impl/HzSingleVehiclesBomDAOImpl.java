package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzSingleVehiclesBomDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequest;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzSingleVehiclesBomRecord;

import java.util.ArrayList;
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
        try {
            if (ListUtil.isNotEmpty(records)) {
                int size = records.size();
                //分批插入数据 一次1000条
                int i = 0;
                int cout = 0;
                synchronized (this){
                    if (size > 1000) {
                        for (i = 0; i < size / 1000; i++) {
                            List<HzSingleVehiclesBomRecord> list = new ArrayList<>();
                            for (int j = 0; j < 1000; j++) {
                                list.add(records.get(cout));
                                cout++;
                            }
                            super.insert("HzSingleVehiclesBomDAOImpl_insertList",list);

                        }
                    }
                    if (i * 1000 < size) {
                        List<HzSingleVehiclesBomRecord> list = new ArrayList<>();
                        for (int j = 0; j < size - i * 1000; j++) {
                            list.add(records.get(cout));
                            cout++;
                        }
                        super.insert("HzSingleVehiclesBomDAOImpl_insertList",list);
                    }
                }

            }
        }catch (Exception e){
            return 0;
        }
        return 1;
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