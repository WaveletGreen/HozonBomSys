package com.connor.hozon.bom.resources.mybatis.bom;

import sql.pojo.bom.HzBomState;

/**
 * @Author: haozt
 * @Date: 2018/6/14
 * @Description:
 */
public interface HzBomStateDAO {
    int insert(HzBomState hzBomState);

    int update(HzBomState hzBomState);

    HzBomState findStateById(String puid);
}
