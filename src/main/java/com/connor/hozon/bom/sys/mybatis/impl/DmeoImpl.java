package com.connor.hozon.bom.sys.mybatis.impl;

import com.connor.hozon.bom.sys.mybatis.Demo;
import sql.BaseSQLUtil;

/**
 * Created by IntelliJ IDEA.
 * User: haozt
 * Date: 2018/5/15
 * Time: 14:01
 * To change this template use File | Settings | File Templates.
 */
public class DmeoImpl extends BaseSQLUtil implements Demo {

    @Override
    public int insert() {
        return super.executeInsert("","sql.mapper.cfg.i.HzCfg0ModelDetailMapper.insert");
    }

}
