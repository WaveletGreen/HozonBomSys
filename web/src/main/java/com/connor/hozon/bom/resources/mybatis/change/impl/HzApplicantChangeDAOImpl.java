package com.connor.hozon.bom.resources.mybatis.change.impl;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.mybatis.change.HzApplicantChangeDAO;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import cn.net.connor.hozon.dao.pojo.change.change.HzApplicantChangeRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description:
 */
@Service("hzApplicantChangeDAO")
public class HzApplicantChangeDAOImpl extends BaseSQLUtil implements HzApplicantChangeDAO {
    @Override
    public int insert(HzApplicantChangeRecord record) {
        return super.insert("HzApplicantChangeDAOImpl_insert",record);
    }

    @Override
    public int update(HzApplicantChangeRecord record) {
        return super.update("HzApplicantChangeDAOImpl_update",record);
    }

    @Override
    public List<HzApplicantChangeRecord> findApplicantionList(HzApplicantChangeRecord record) {
        Map<String,Object> map = new HashMap<>();
        //map.put("id",query.getId());
        //map.put("auditResult","");
        User user = UserInfo.getUser();
        map.put("applicantId",user.getId());//申请人ID

        return  super.findForList("HzApplicantChangeDAOImpl_findAuditorList",map);
    }
}
