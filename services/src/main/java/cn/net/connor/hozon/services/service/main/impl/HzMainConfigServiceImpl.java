/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.main.impl;

import cn.net.connor.hozon.dao.dao.main.HzMainConfigDao;
import cn.net.connor.hozon.dao.pojo.main.HzMainConfig;
import cn.net.connor.hozon.services.service.main.HzMainConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/5/31 14:00
 * @Modified By:
 */
@Service("hzCfg0MainService")
public class HzMainConfigServiceImpl implements HzMainConfigService {
    @Autowired
    HzMainConfigDao hzMainConfigDao;

    public HzMainConfig selectByProjectId(String projectPuid) {
        return hzMainConfigDao.selectByProjectId(projectPuid);
    }

    public HzMainConfig doGetByPrimaryKey(String puid) {
        return hzMainConfigDao.selectByPrimaryKey(puid);
    }

    /**
     * 选择更新
     *
     * @param mainRecord
     * @return
     */
    public boolean doUpdateByPrimaryKeySelective(HzMainConfig mainRecord) {
        return hzMainConfigDao.updateByPrimaryKeySelective(mainRecord) > 0 ? true : false;
    }

}
