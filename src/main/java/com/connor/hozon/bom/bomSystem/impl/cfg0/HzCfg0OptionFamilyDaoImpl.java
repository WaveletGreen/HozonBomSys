/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.impl.cfg0;

import com.connor.hozon.bom.bomSystem.impl.BasicDaoImpl;
import com.connor.hozon.bom.bomSystem.dao.cfg0.HzCfg0OptionFamilyDao;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzCfg0OptionFamilyDao")
public class HzCfg0OptionFamilyDaoImpl extends BasicDaoImpl<HzCfg0OptionFamily> implements HzCfg0OptionFamilyDao {
    private final static HzCfg0OptionFamily FAMILY = new HzCfg0OptionFamily();

    public HzCfg0OptionFamilyDaoImpl() {
        clz = HzCfg0OptionFamilyDao.class;
        clzName = clz.getCanonicalName();
    }

    @Override
    public List<HzCfg0OptionFamily> selectNameByMainId(String mainId) {
        return baseSQLUtil.executeQueryByPass(FAMILY, mainId, clzName + ".selectNameByMainId");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param mainId 产品配置器的puid
     * @return 返回一组系统名称
     */
    @Override
    public List<HzCfg0OptionFamily> selectNameByMainId2(String mainId) {
        return baseSQLUtil.executeQueryByPass(FAMILY, mainId, clzName + ".selectNameByMainId2");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param param
     * @return 返回一组系统名称
     */
    @Override
    public List<HzCfg0OptionFamily> selectNameByMap(Map<String, Object> param) {
        return baseSQLUtil.executeQueryByPass(FAMILY, param, clzName + ".selectNameByMap");
    }

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param param
     * @return 返回一组系统名称
     */
    @Override
    public List<HzCfg0OptionFamily> selectForColorBluePrint(Map<String, Object> param) {
        return baseSQLUtil.executeQueryByPass(FAMILY, param, clzName + ".selectForColorBluePrint");
    }


    @Override
    public HzCfg0OptionFamily selectByCodeAndDescWithMain(HzCfg0OptionFamily family) {
        return baseSQLUtil.executeQueryById(family, clzName + ".selectByCodeAndDescWithMain");
    }

    /**
     * @param family 包含主配置UID，描述和配置代码
     * @return
     */
    @Override
    public List<HzCfg0OptionFamily> selectByCodeAndDescWithMain2(HzCfg0OptionFamily family) {
        return baseSQLUtil.executeQuery(family, clzName + ".selectByCodeAndDescWithMain");
    }

}
