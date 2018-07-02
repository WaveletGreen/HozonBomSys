package com.connor.hozon.bom.resources.mybatis.work.impl;

import com.connor.hozon.bom.resources.mybatis.work.HzWorkProcedureDAO;
import sql.BaseSQLUtil;
import sql.pojo.work.HzWorkCenter;
import sql.pojo.work.HzWorkProcedure;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public class HzWorkProcedureDAOImpl  extends BaseSQLUtil implements HzWorkProcedureDAO {

    @Override
    public int insert(HzWorkProcedure hzWorkProcedure) {
        return super.insert("HzWorkProcedureDAOImpl_insert",hzWorkProcedure);
    }

    @Override
    public int update(HzWorkProcedure hzWorkProcedure) {
        return super.update("HzWorkProcedureDAOImpl_update",hzWorkProcedure);
    }

    @Override
    public int delete(String puid) {
        return super.update("HzWorkProcedureDAOImpl_delete",puid);
    }
}
