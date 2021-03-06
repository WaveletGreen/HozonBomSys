package com.connor.hozon.resources.mybatis.change.impl;

import cn.net.connor.hozon.services.service.sys.UserInfo;
import cn.net.connor.hozon.common.constant.BOMTransConstants;
import com.connor.hozon.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.page.PageRequestParam;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:
 */
@Service("hzChangeOrderDAO")
public class HzChangeOrderDAOImpl extends BaseSQLUtil implements HzChangeOrderDAO {
    @Override
    public int insert(HzChangeOrderRecord record) {
        return super.insert("HzChangeOrderDAOImpl_insert",record);
    }

    @Override
    public int update(HzChangeOrderRecord record) {
        return super.update("HzChangeOrderDAOImpl_update",record);
    }

    @Override
    public int deleteById(Long id) {

        return super.delete("HzChangeOrderDAOImpl_deleteById",id);
    }

    @Override
    public boolean changeNoExist(String changeNo) {
        return (int)super.findForObject("HzChangeOrderDAOImpl_changeNoExist",changeNo)>0;
    }

    @Override
    public HzChangeOrderRecord findHzChangeOrderRecordById(Long id) {
        return (HzChangeOrderRecord)super.findForObject("HzChangeOrderDAOImpl_findHzChangeOrderRecordById",id);
    }

    @Override
    public HzChangeOrderRecord findHzChangeOrderRecordDTOById(Long id, Long auditId) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("auditId",auditId);
        return (HzChangeOrderRecord)super.findForObject("HzChangeOrderDAOImpl_findHzChangeOrderRecordDTOById",map);
    }

    @Override
    public HzChangeOrderRecord findHzChangeOrderRecordById(HzChangeOrderByPageQuery query,Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("changeNo",query.getChangeNo());
        map.put("projectId",query.getProjectId());
        map.put("originator",query.getOriginator());
        map.put("firstOriginTime",query.getFirstOriginTime());
        map.put("lastOriginTime",query.getLastOriginTime());
        //map.put("",query.get)
        //User user = UserInfo.getUser();
        //map.put("auditer",user.getLogin());//接口人

        return (HzChangeOrderRecord)super.findForObject("HzChangeOrderDAOImpl_findHzChangeOrderRecordByIds",map);
    }

    @Override
    public Page<HzChangeOrderRecord> findHzChangeOrderRecordByPageId(HzChangeOrderByPageQuery query) {
        Map<String,Object> map = new HashMap<>();
        PageRequestParam pageRequestParam = new PageRequestParam();
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        //map.put("id",id);
        map.put("changeNo",query.getChangeNo());
        map.put("projectId",query.getProjectId());
        map.put("originator",query.getOriginator());
        map.put("firstOriginTime",query.getFirstOriginTime());
        map.put("lastOriginTime",query.getLastOriginTime());
        User user = UserInfo.getUser();
        map.put("id",user.getId());
        pageRequestParam.setFilters(map);

        return super.findPage("HzChangeOrderDAOImpl_findHzChangeOrderRecordByPageId","HzChangeOrderDAOImpl_findApplicantTotalCount",pageRequestParam);
    }

    @Override
    public Page<HzChangeOrderRecord> findHzChangeOrderRecordByPageUn(HzChangeOrderByPageQuery query) {
        Map<String,Object> map = new HashMap<>();
        PageRequestParam pageRequestParam = new PageRequestParam();
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        map.put("changeNo",query.getChangeNo());
        map.put("projectId",query.getProjectId());
        map.put("originator",query.getOriginator());
        map.put("firstOriginTime",query.getFirstOriginTime());
        map.put("lastOriginTime",query.getLastOriginTime());
        User user = UserInfo.getUser();
        map.put("id",user.getId());
        pageRequestParam.setFilters(map);

        return super.findPage("HzChangeOrderDAOImpl_findHzChangeOrderRecordByPageUn","HzChangeOrderDAOImpl_findUntreatedTotalCount",pageRequestParam);
    }

    @Override
    public Page<HzChangeOrderRecord> findHzChangeOrderRecordByPagePr(HzChangeOrderByPageQuery query) {
        Map<String,Object> map = new HashMap<>();
        PageRequestParam pageRequestParam = new PageRequestParam();
        pageRequestParam.setPageNumber(query.getPage());
        pageRequestParam.setPageSize(query.getPageSize());
        map.put("changeNo",query.getChangeNo());
        map.put("projectId",query.getProjectId());
        map.put("originator",query.getOriginator());
        map.put("firstOriginTime",query.getFirstOriginTime());
        map.put("lastOriginTime",query.getLastOriginTime());
        User user = UserInfo.getUser();
        map.put("id",user.getId());
        pageRequestParam.setFilters(map);

        return super.findPage("HzChangeOrderDAOImpl_findHzChangeOrderRecordByPagePr","HzChangeOrderDAOImpl_findProcessedTotalCount",pageRequestParam);
    }

    @Override
    public HzChangeOrderRecord findHzChangeOrderRecordByIdTC(HzChangeOrderByPageQuery query,Long id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("changeNo",query.getChangeNo());
        map.put("projectId",query.getProjectId());
        map.put("originator",query.getOriginator());
        map.put("firstOriginTime",query.getFirstOriginTime());
        map.put("lastOriginTime",query.getLastOriginTime());
        //map.put("",query.get)
        //User user = UserInfo.getUser();
        //map.put("auditer",user.getLogin());//接口人

        return (HzChangeOrderRecord)super.findForObject("HzChangeOrderDAOImpl_findHzChangeOrderRecordByIdsTC",map);
    }

    @Override
    public Page<HzChangeOrderRecord> findHzChangeOrderRecordByPage(HzChangeOrderByPageQuery query) {
        Map<String,Object> map = new HashMap<>();
        PageRequestParam pageRequestParam = new PageRequestParam();
        pageRequestParam.setPageNumber(query.getPage());
        if(BOMTransConstants.ALL.equals(query.getLimit())){
            pageRequestParam.setAllNumber(true);
        }else {
            pageRequestParam.setPageSize(Integer.valueOf(query.getLimit()));
        }
        map.put("changeNo",query.getChangeNo().trim());
        map.put("createName",query.getCreateName());
        map.put("originator",query.getOriginator());
        if(Integer.valueOf(1).equals(query.getType())){
            map.put("firstCreateTime",query.getFirstCreateTime());
            map.put("lastCreateTime",query.getLastCreateTime());
        }else if(Integer.valueOf(2).equals(query.getType())){
            map.put("firstOriginTime",query.getFirstOriginTime());
            map.put("lastOriginTime",query.getLastOriginTime());
        }
        map.put("state",query.getState());
        map.put("projectId",query.getProjectId());
        pageRequestParam.setFilters(map);
        return super.findPage("HzChangeOrderDAOImpl_findHzChangeOrderRecordByPage","HzChangeOrderDAOImpl_findHzChangeOrderTotalCount",pageRequestParam);
    }

    @Override
    public List<HzChangeOrderRecord> findHzChangeOrderRecordByProjectId(String projectUid) {
        return super.findForList("HzChangeOrderDAOImpl_findHzChangeOrderRecordByProjectId",projectUid);
    }

    @Override
    public HzChangeOrderRecord selectById(Long vwoId) {
//        HzChangeOrderRecord hzChangeOrderRecord = new HzChangeOrderRecord();
//        hzChangeOrderRecord.setId(vwoId);
        return (HzChangeOrderRecord)super.findForObject("HzChangeOrderDAOImpl_selectById",vwoId);
    }
}
