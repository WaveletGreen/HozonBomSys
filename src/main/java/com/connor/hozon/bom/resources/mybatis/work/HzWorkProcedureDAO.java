package com.connor.hozon.bom.resources.mybatis.work;

import sql.pojo.work.HzWorkProcedure;

/**
 * @Author: haozt
 * @Date: 2018/6/30
 * @Description:
 */
public interface HzWorkProcedureDAO {
    int insert(HzWorkProcedure hzWorkProcedure);

    int update(HzWorkProcedure hzWorkProcedure);

    int delete(String puid);

}
