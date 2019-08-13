/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.service.configuration.derivative;

import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzComposeDelDto;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasicChangeBean;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/5 16:11
 * @Modified By:
 */
public interface HzDMBasicChangeService {

    public int insertList(List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans);

    public List<HzDerivativeMaterielBasicChangeBean> selectByFormid(Long changeFromId);

    public HzDerivativeMaterielBasicChangeBean selectBefor(HzDerivativeMaterielBasicChangeBean hzDerivativeMaterielBasicChangeBeanAfter);

    public List<HzDerivativeMaterielBasicChangeBean> selectAfter(Long formId);

    public List<HzDerivativeMaterielBasicChangeBean> selectLastByPuid(List<HzComposeDelDto> delDtos);

    public int updateStatusByOrderId(Long orderId, int status);

    public int deleteByChangeIds(List<HzDerivativeMaterielBasicChangeBean> hzDerivativeMaterielBasicChangeBeans);

    public List<HzDerivativeMaterielBasicChangeBean> selectNotEffect(List<Long> changeMaterielFeatureIds);
}
