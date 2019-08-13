/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.configuration.derivative.impl;

import cn.net.connor.hozon.dao.dao.configuration.derivative.HzDerivativeMaterielBasicChangeDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasicChangeBean;
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
    public int insertList(List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans) {
        return hzDerivativeMaterielBasicChangeDao.insertList(hzDerivativeMaterielBasicChangeBeans);
    }

    @Override
    public List<HzDerivativeMaterielBasicChangeBean> selectByFormid(Long changeFromId) {
        return hzDerivativeMaterielBasicChangeDao.selectByFormid(changeFromId);
    }

    @Override
    public HzDerivativeMaterielBasicChangeBean selectBefor(HzDerivativeMaterielBasicChangeBean hzDerivativeMaterielBasicChangeBean) {
        return hzDerivativeMaterielBasicChangeDao.selectBefor(hzDerivativeMaterielBasicChangeBean);
    }

    @Override
    public List<HzDerivativeMaterielBasicChangeBean> selectAfter(Long formId) {
        return hzDerivativeMaterielBasicChangeDao.selectAfter(formId);
    }

    @Override
    public List<HzDerivativeMaterielBasicChangeBean> selectLastByPuid(List<HzComposeDelDto> delDtos) {
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
    public int deleteByChangeIds(List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans) {
        return hzDerivativeMaterielBasicChangeDao.deleteByChangeIds(hzDerivativeMaterielBasicChangeBeans);
    }

    @Override
    public List<HzDerivativeMaterielBasicChangeBean> selectNotEffect(List<Long> changeMaterielFeatureIds) {
        return hzDerivativeMaterielBasicChangeDao.selectNotEffect(changeMaterielFeatureIds);
    }
}
