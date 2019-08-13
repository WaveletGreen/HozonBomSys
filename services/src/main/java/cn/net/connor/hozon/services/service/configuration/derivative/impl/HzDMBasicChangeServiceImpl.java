/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.configuration.derivative.impl;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDerivativeMaterielBasicChangeDao;
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
    private HzDerivativeMaterielBasicChangeDao hzDerivativeMaterielBasicChangeDao;


    @Override
    public int insertList(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
        return hzDerivativeMaterielBasicChangeDao.insertList(hzDMBasicChangeBeans);
    }

    @Override
    public List<HzDMBasicChangeBean> selectByFormid(Long changeFromId) {
        return hzDerivativeMaterielBasicChangeDao.selectByFormid(changeFromId);
    }

    @Override
    public HzDMBasicChangeBean selectBefor(HzDMBasicChangeBean hzDMBasicChangeBean) {
        return hzDerivativeMaterielBasicChangeDao.selectBefor(hzDMBasicChangeBean);
    }

    @Override
    public List<HzDMBasicChangeBean> selectAfter(Long formId) {
        return hzDerivativeMaterielBasicChangeDao.selectAfter(formId);
    }

    @Override
    public List<HzDMBasicChangeBean> selectLastByPuid(List<HzComposeDelDto> delDtos) {
        return hzDerivativeMaterielBasicChangeDao.selectLastById(delDtos);
    }

    @Override
    public int updateStatusByOrderId(Long orderId, int status) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderId);
        map.put("status",status);
        return hzDerivativeMaterielBasicChangeDao.updateStatusByOrderId(map);
    }

    @Override
    public int deleteByChangeIds(List<HzDMBasicChangeBean> hzDMBasicChangeBeans) {
        return hzDerivativeMaterielBasicChangeDao.deleteByChangeIds(hzDMBasicChangeBeans);
    }

    @Override
    public List<HzDMBasicChangeBean> selectNotEffect(List<Long> changeMaterielFeatureIds) {
        return hzDerivativeMaterielBasicChangeDao.selectNotEffect(changeMaterielFeatureIds);
    }
}
