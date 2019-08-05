/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.configuration.derivative.impl;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDMBasicChangeDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDMBasicChangeBean;
import cn.net.connor.hozon.services.service.configuration.derivative.HzDMBasicChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/5 16:10
 * @Modified By:
 */
@Service
public class HzDMBasicChangeServiceImpl implements HzDMBasicChangeService {

    @Autowired
    private HzDMBasicChangeDao hzDMBasicChangeDao;


    @Override
    public int insertList(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
        return hzDMBasicChangeDao.insertList(hzDMBasicChangeBeans);
    }

    @Override
    public List<HzDMBasicChangeBean> selectByFormid(Long changeFromId) {
        return hzDMBasicChangeDao.selectByFormid(changeFromId);
    }

    @Override
    public HzDMBasicChangeBean selectBefor(HzDMBasicChangeBean hzDMBasicChangeBean) {
        return hzDMBasicChangeDao.selectBefor(hzDMBasicChangeBean);
    }

    @Override
    public List<HzDMBasicChangeBean> selectAfter(Long formId) {
        return hzDMBasicChangeDao.selectAfter(formId);
    }

    @Override
    public List<HzDMBasicChangeBean> selectLastByPuid(List<HzComposeDelDto> delDtos) {
        return hzDMBasicChangeDao.selectLastById(delDtos);
    }

    @Override
    public int updateStatusByOrderId(Long orderId, int status) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",status);
        return hzDMBasicChangeDao.updateStatusByOrderId(map);
    }

    @Override
    public int deleteByChangeIds(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
        return hzDMBasicChangeDao.deleteByChangeIds(hzDMBasicChangeBeans);
    }

    @Override
    public List<HzDMBasicChangeBean> selectNotEffect(List<Long> changeMaterielFeatureIds) {
        return hzDMBasicChangeDao.selectNotEffect(changeMaterielFeatureIds);
    }
}
