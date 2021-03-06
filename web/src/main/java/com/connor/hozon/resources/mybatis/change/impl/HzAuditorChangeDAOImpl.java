package com.connor.hozon.resources.mybatis.change.impl;

import cn.net.connor.hozon.services.service.sys.UserInfo;
import com.connor.hozon.resources.domain.dto.request.HzAuditorChangeDTO;
import com.connor.hozon.resources.mybatis.change.HzAuditorChangeDAO;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.change.change.HzAuditorChangeRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:
 */
@Service("hzAuditorChangeDAO")
public class HzAuditorChangeDAOImpl extends BaseSQLUtil implements HzAuditorChangeDAO {
    @Override
    public int insert(HzAuditorChangeRecord record) {
        return super.insert("HzAuditorChangeDAOImpl_insert", record);
    }

    @Override
    public List<HzAuditorChangeRecord> findAuditorList(HzAuditorChangeRecord query) {
        Map<String, Object> map = new HashMap<>();
        //map.put("id",query.getId());
        //map.put("auditResult","");
        User user = UserInfo.getUser();
        map.put("auditorId", user.getId());//审核人ID

        return super.findForList("HzAuditorChangeDAOImpl_findAuditorList", map);
    }

    @Override
    public List<HzAuditorChangeRecord> findAuditorList2(HzAuditorChangeRecord query) {
        Map<String, Object> map = new HashMap<>();
        User user = UserInfo.getUser();
        map.put("auditorId", user.getId());//审核人ID

        return super.findForList("HzAuditorChangeDAOImpl_findAuditorList2", map);
    }

    @Override
    public List<HzAuditorChangeRecord> findAuditorListById(Long id) {
        //Map<String, Object> map = new HashMap<>();
        //map.put("id", record.getId());
        return super.findForList("HzAuditorChangeDAOImpl_findAuditorListById", id);
    }

    @Override
    public int count(int userId) {
        Map<String, Object> map = new HashMap<>();
        //User user1 = UserInfo.getUser();
        map.put("auditorId", userId);//接口人
        return (int) super.findForObject("HzAuditorChangeDAOImpl_count", map);
    }

    @Override
    public HzAuditorChangeRecord findByOrderId(Long orderId, Long userId) {
        HzAuditorChangeRecord record = new HzAuditorChangeRecord();
        record.setOrderId(orderId);
        record.setAuditorId(userId);
        return (HzAuditorChangeRecord) super.findForObject("HzAuditorChangeDAOImpl_findByOrderId", record);
    }

    @Override
    public List<HzAuditorChangeRecord> findByOrdersId(Long orderId, Long userId) {
        HzAuditorChangeRecord record = new HzAuditorChangeRecord();
        record.setOrderId(orderId);
        record.setAuditorId(userId);
        return super.findForList("HzAuditorChangeDAOImpl_findByOrdersId", record);
    }

    @Override
    public int updateByPrimaryKeySelective(HzAuditorChangeRecord record) {
        return super.executeUpdate(record, "HzAuditorChangeDAOImpl_updateByPrimaryKeySelective");
    }

    @Override
    public int updateAuditorRecord(HzAuditorChangeDTO reqDTO) {
        return super.executeUpdate(reqDTO, "HzAuditorChangeDAOImpl_updateAuditorRecord");
    }
}
