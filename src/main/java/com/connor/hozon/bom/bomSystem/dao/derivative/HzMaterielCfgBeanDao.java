/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.derivative;

import sql.pojo.cfg.derivative.HzMaterielCfgBean;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: ERP集成使用的，忘记干嘛用的了
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
public interface HzMaterielCfgBeanDao {
    /**
     * 根据项目UID或车型模型的UID获取到整车物料属性
     * <p><strong>请手动设置projectUid和puid的互斥关系，</strong>即设置了projectUid，则请相对应的设置puid=null，返回值则为一组数据；反之设置projectUid=null，返回值为1个</p>
     *
     * @param bean 包含projectUid和puid互斥关系的整车物料属性bean
     * @return 一组整车物料属性bean或单个值，根据projectUid和puid决定，所有的返回数据都存放在List中
     */
    List<HzMaterielCfgBean> selectByDiff(HzMaterielCfgBean bean);

    int updateIsSent(Map<String, Object> map);
}