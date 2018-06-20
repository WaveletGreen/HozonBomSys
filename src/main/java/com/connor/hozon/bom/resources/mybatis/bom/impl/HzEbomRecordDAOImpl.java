package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.dto.request.FindHzEbomRecordReqDTO;
import com.connor.hozon.bom.resources.mybatis.bom.HzEbomRecordDAO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;

/**
 * Created by haozt on 2018/06/06
 */
@Service("HzEbomRecordDAO")
public class HzEbomRecordDAOImpl extends BaseSQLUtil implements HzEbomRecordDAO {

    @Override
    public List<HzEPLManageRecord> getHzEbomList(FindHzEbomRecordReqDTO reqDTO) {
        return super.findForList("HzEbomRecordDAOImpl_getHzEbomList",null);
    }
}
