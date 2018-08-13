package com.connor.hozon.bom.resources.service.change.impl;

import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.dto.request.InitiatingProcessReqDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzEWOBasicInfoDAO;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.service.change.HzEWOService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.sys.dao.OrgGroupDao;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.change.HzEWOBasicInfo;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/8/13
 * @Description:
 */

@Service("HzEWOService")
public class HzEWOServiceImpl implements HzEWOService {

    @Autowired
    private HzBomLineRecordDaoImpl hzBomLineRecordDao;

    @Autowired
    private HzEbomRecordDAO hzEbomRecordDAO;

    @Autowired
    private HzEWOBasicInfoDAO hzEWOBasicInfoDAO;

    @Autowired
    private OrgGroupDao orgGroupDao;
    @Override
    public OperateResultMessageRespDTO initiatingProcessForEwoChange(InitiatingProcessReqDTO reqDTO) {
        String puids = reqDTO.getPuids();
        User user = UserInfo.getUser();
        String createYM = DateUtil.getTodayTextYMD();
        Date createTime = new Date();
        String lastFourIndex = hzEWOBasicInfoDAO.getMaxEWONoLastFourIndexInThisMonth(reqDTO.getProjectId(),createYM);
        if(lastFourIndex == null){
            lastFourIndex = "0001";
        }else {
            lastFourIndex =getEwoNoLastFourIndex(lastFourIndex);
        }
        String ewoNo = createYM+lastFourIndex;
        HzEWOBasicInfo hzEWOBasicInfo = new HzEWOBasicInfo();
        hzEWOBasicInfo.setFormCreateTime(createTime);
        OrgGroup orgGroup  = orgGroupDao.queryOrgGroupById(user.getGroupId());
        if(orgGroup != null && orgGroup.getName() != null){
            hzEWOBasicInfo.setDept(orgGroup.getName());
        }else {
            hzEWOBasicInfo.setDept("未知");
        }
        hzEWOBasicInfo.setEwoNo(ewoNo);
        hzEWOBasicInfo.setOriginatorId(Long.valueOf(user.getId()));
        hzEWOBasicInfo.setOriginator(user.getUsername());
        hzEWOBasicInfo.setProjectId(reqDTO.getProjectId());
        hzEWOBasicInfoDAO.insert(hzEWOBasicInfo);




        return OperateResultMessageRespDTO.getSuccessResult();
    }

    private String getEwoNoLastFourIndex(String str){
        String lastFourIndex = "";
        int i = Integer.valueOf(str)+1;
        int length = (i+"").length();
        if(length<4){
            StringBuffer stringBuffer = new StringBuffer();
            for(int j = 0;j<length-4;j++){
                stringBuffer.append("0");
            }
            lastFourIndex = stringBuffer.toString()+String.valueOf(i);
        }else {
            lastFourIndex = String.valueOf(i);
        }
        return lastFourIndex;
    }
}
