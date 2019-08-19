package com.connor.hozon.bom.resources.mybatis.wokeList.impl;

import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.bom.resources.domain.query.HzWorkListBasicInfoQuery;
import com.connor.hozon.bom.resources.mybatis.wokeList.HzWorkListDAO;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.workList.HzWorkListRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hzWorkListDAO")
public class HzWorkListDAOImpl extends BaseSQLUtil implements HzWorkListDAO {

    @Override
    public int insert(HzWorkListRecord record) {
        return super.insert("HzWorkListDAOImpl_insert",record);
    }

    @Override
    public int update(HzWorkListRecord record) {
        return super.update("HzWorkListDAOImpl_update",record);
    }

    @Override
    public int count(String user,String projectId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        User user1 = UserInfo.getUser();
        map.put("auditer",user);//接口人
        return (int) super.findForObject("HzWorkListDAOImpl_count",map);
    }

    @Override
    public List<HzWorkListRecord> findWorkList(Map<String, Object> map) {
        return super.findForList("HzWorkListDAOImpl_findWorkList",map);
    }

    @Override
    public List<HzWorkListRecord> findHzWorkListBasicInfoList(HzWorkListBasicInfoQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        //map.put("changeNum",query.getChangeNum());
        //map.put("projectId",query.getProjectId());
        return  super.findForList("HzWorkListDAOImpl_findHzWorkListBasicInfoList",query);
    }

    @Override
    public List<HzWorkListRecord> findHzWorkListBasicInfoList1(HzWorkListBasicInfoQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        map.put("changeNum",query.getChangeNum());
        map.put("projectId",query.getProjectId());
        map.put("launcher",query.getLauncher());
        map.put("launchTimeStart",query.getLaunchTimeStart());
        map.put("launchTimeEnd",query.getLaunchTimeEnd());
        User user = UserInfo.getUser();
        map.put("auditer",user.getLogin());//接口人

        return  super.findForList("HzWorkListDAOImpl_findHzWorkListBasicInfoList1",map);
    }

    @Override
    public List<HzWorkListRecord> findHzWorkListBasicInfoList2(HzWorkListBasicInfoQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        map.put("changeNum",query.getChangeNum());
        map.put("projectId",query.getProjectId());
        map.put("launcher",query.getLauncher());
        map.put("launchTimeStart",query.getLaunchTimeStart());
        map.put("launchTimeEnd",query.getLaunchTimeEnd());
        User user = UserInfo.getUser();
        map.put("auditer",user.getLogin());//接口人
        return  super.findForList("HzWorkListDAOImpl_findHzWorkListBasicInfoList2",map);
    }

    @Override
    public List<HzWorkListRecord> findHzWorkListBasicInfoList3(HzWorkListBasicInfoQuery query) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",query.getId());
        map.put("changeNum",query.getChangeNum());
        map.put("projectId",query.getProjectId());
        //map.put("launcher",query.getLauncher());
        map.put("launchTimeStart",query.getLaunchTimeStart());
        map.put("launchTimeEnd",query.getLaunchTimeEnd());
        User user = UserInfo.getUser();
        map.put("launcher",user.getLogin());//发起人
        return  super.findForList("HzWorkListDAOImpl_findHzWorkListBasicInfoList3",map);
    }
}
