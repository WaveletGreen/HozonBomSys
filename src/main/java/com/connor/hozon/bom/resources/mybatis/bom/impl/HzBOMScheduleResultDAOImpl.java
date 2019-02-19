package com.connor.hozon.bom.resources.mybatis.bom.impl;

import com.connor.hozon.bom.resources.mybatis.bom.HzBOMScheduleResultDAO;
import org.springframework.stereotype.Service;
import sql.BaseSQLUtil;
import sql.pojo.bom.HzBOMScheduleResult;

/**
 * @Author: haozt
 * @Date: 2019/1/3
 * @Description:
 */
@Service("hzBOMScheduleResultDAO")
public class HzBOMScheduleResultDAOImpl extends BaseSQLUtil implements HzBOMScheduleResultDAO {

    @Override
    public int insert(HzBOMScheduleResult hzBOMScheduleResult) {
        return super.insert("HzBOMScheduleResultDAOImpl_insert",hzBOMScheduleResult);
    }

    @Override
    public HzBOMScheduleResult getBOMScheduleResult(Long orderId) {
        return super.findForObject("HzBOMScheduleResultDAOImpl_getBOMScheduleResult",orderId);
    }
}
