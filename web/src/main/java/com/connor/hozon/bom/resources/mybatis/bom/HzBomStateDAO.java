package com.connor.hozon.bom.resources.mybatis.bom;

import cn.net.connor.hozon.dao.pojo.bom.bom.HzBomState;

/**
 * @Author: haozt
 * @Date: 2018/6/14
 * @Description: 废弃 已不再使用
 */
@Deprecated
public interface HzBomStateDAO {
    int insert(HzBomState hzBomState);

    int update(HzBomState hzBomState);

    HzBomState findStateById(String puid);
}
