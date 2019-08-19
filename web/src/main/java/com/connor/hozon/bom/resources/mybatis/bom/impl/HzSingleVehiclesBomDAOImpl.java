package com.connor.hozon.bom.resources.mybatis.bom.impl;

import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.bom.resources.domain.constant.BOMTransConstants;
import com.connor.hozon.bom.resources.domain.model.HzBomSysFactory;
import com.connor.hozon.bom.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import com.connor.hozon.bom.resources.executors.ExecutorServices;
import com.connor.hozon.bom.resources.mybatis.bom.HzSingleVehiclesBomDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.page.PageRequestParam;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.exception.HzBomException;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.bom.bom.HzSingleVehiclesBomRecord;
import cn.net.connor.hozon.common.exception.HzDBException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Author: haozt
 * @Date: 2018/9/27
 * @Description:
 */
@Service("hzSingleVehiclesBomDAO")
public class HzSingleVehiclesBomDAOImpl extends BaseSQLUtil implements HzSingleVehiclesBomDAO {
    @Override
    public int insertList(List<HzSingleVehiclesBomRecord> records) {
        try {
            if(ListUtils.isEmpty(records)){
                return 0;
            }
            synchronized (this){
                Map<Integer,List<HzSingleVehiclesBomRecord>> map = HzBomSysFactory.spiltList(records);
                ExecutorServices executorServices = new ExecutorServices(8);
                ExecutorService service = executorServices.getPool();
                for(List<HzSingleVehiclesBomRecord> value :map.values()){
                    service.execute(() -> {
                        super.insert("HzSingleVehiclesBomDAOImpl_insertList",value);
                    });
                }
                if(service != null){
                    service.shutdown();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new HzBomException("单车BOM数据插入失败！",e);
        }
        return records.size();
    }


    @Override
    public int deleteByProjectId(String projectId) {
        if(StringUtil.isEmpty(projectId)){
            return -1;
        }
        return super.delete("HzSingleVehiclesBomDAOImpl_deleteByProjectId",projectId);
    }

    @Override
    public List<String> getAllPuidByProjectId(String projectId) throws HzDBException {
        List<String> puids = new ArrayList<>();
        List<HzSingleVehiclesBomRecord> list = super.findForList("HzSingleVehiclesBomDAOImpl_getAllPuidByProjectId",projectId);;
        if(ListUtils.isNotEmpty(list)){
            list.forEach(record -> {
                puids.add(record.getMBomPuid());
            });
        }
        return puids;
    }

    @Override
    public Page<HzSingleVehiclesBomRecord> getHzSingleVehiclesBomByPage(HzSingleVehiclesBomByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        request.setPageNumber(query.getPage());
        if(BOMTransConstants.ALL.equals(query.getLimit())){
            request.setAllNumber(true);
        }else {
            request.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("isHas",query.getIsHas());
        map.put("pBomOfWhichDept",query.getPBomOfWhichDept().trim());
        map.put("lineIndex",query.getLineIndex());
        map.put("lineId",query.getLineId().trim());
        map.put("pBomLinePartClass",query.getPBomLinePartClass());
        map.put("pBomLinePartResource",query.getPBomLinePartResource());
        map.put("singleVehiclesId",query.getSingleVehiclesId());
        request.setFilters(map);
        return super.findPage("HzSingleVehiclesBomDAOImpl_getHzSingleVehiclesBomByPage","HzSingleVehiclesBomDAOImpl_getHzSingleVehiclesBomTotalCount",request);
    }

    @Override
    public Page<HzSingleVehiclesBomRecord> getHzSingleVehiclesBomTreeByPage(HzSingleVehiclesBomByPageQuery query) {
        PageRequestParam request = new PageRequestParam();
        request.setPageNumber(query.getPage());
        if(BOMTransConstants.ALL.equals(query.getLimit())){
            request.setAllNumber(true);
        }else {
            request.setPageSize(Integer.valueOf(query.getLimit()));
        }
        Map map = new HashMap();
        map.put("projectId",query.getProjectId());
        map.put("eBomPuids", Lists.newArrayList(query.getEBomPuids().split(",")));
        map.put("singleVehiclesId",query.getSingleVehiclesId());
        request.setFilters(map);
        return super.findPage("HzSingleVehiclesBomDAOImpl_getHzSingleVehiclesBomTreeByPage","HzSingleVehiclesBomDAOImpl_getHzSingleVehiclesBomTreeTotalCount",request);

    }
}
