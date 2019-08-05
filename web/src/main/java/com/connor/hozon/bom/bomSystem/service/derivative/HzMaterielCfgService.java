/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.derivative;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzMaterielCfgBeanDao;
import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzMaterielCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzMaterielCfgBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Service("hzMaterielCfgService")
public class HzMaterielCfgService implements IHzMaterielCfgService {

    @Autowired
    HzMaterielCfgBeanDao hzMaterielCfgBeanDao;

    /**
     * 根据项目UID或车型模型的UID获取到整车物料属性
     * <p><strong>请手动设置projectUid和puid的互斥关系，</strong>即设置了projectUid，则请相对应的设置puid=null，返回值则为一组数据；反之设置projectUid=null，返回值为1个</p>
     *
     * @param bean 包含projectUid和puid互斥关系的整车物料属性bean
     * @return 一组整车物料属性bean或单个值，根据projectUid和puid决定，所有的返回数据都存放在List中
     */
    public List<HzMaterielCfgBean> doSelectByDiff(HzMaterielCfgBean bean) {
        return hzMaterielCfgBeanDao.selectByDiff(bean);
    }

    @Override
    public int doUpdateIsSent(Map<String, Object> map) {
        return hzMaterielCfgBeanDao.updateIsSent(map);
    }
}
