package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.resources.domain.query.HzEWOBasicInfoQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOBasicInfoDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.change.change.HzEWOBasicInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/8/8
 * @Description:
 */
@Service("HzEWOBasicInfoDAO")
public class HzEWOBasicInfoDAOImpl extends BaseSQLUtil implements HzEWOBasicInfoDAO {
    @Override
    public int insert(HzEWOBasicInfo hzEWOBasicInfo) {
        return super.insert("HzEWOBasicInfoDAOImpl_insert",hzEWOBasicInfo);
    }

    @Override
    public int update(HzEWOBasicInfo hzEWOBasicInfo) {
        return super.update("HzEWOBasicInfoDAOImpl_update",hzEWOBasicInfo);
    }

    @Override
    public List<HzEWOBasicInfo> findHzEWOBasicInfoList(HzEWOBasicInfoQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        map.put("ewoNo",query.getEwoNo());
        map.put("projectId",query.getProjectId());
        return  super.findForList("HzEWOBasicInfoDAOImpl_findHzEWOBasicInfoList",query);
    }

    @Override
    public String getMaxEWONoLastFourIndexInThisMonth(String projectId, String ym) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("ym",ym);
        return (String)super.findForObject("HzEWOBasicInfoDAOImpl_getMaxEWONoLastFourIndexInThisMonth",map);
    }


}
