/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.dao.configuration.feature;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielDetail;
import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeature;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 获取到啊配置器的系统层，系统层是从TC来，不允许更改，所有的配置数据都不应该修改TC的数据为准
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzFeatureDao extends BasicDao<HzFeature>{

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param mainId 产品配置器的puid
     * @return 返回一组系统名称
     */
    List<HzFeature> selectByProjectIdWithOrderMainId(@Param("mainId") String mainId);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param mainId 产品配置器的puid
     * @return 返回一组系统名称
     */
    List<HzFeature> selectByProjectIdWithOrderPuid(@Param("projectId") String mainId);
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param param
     * @return 返回一组系统名称
     */
    List<HzFeature> selectNameByMap(Map<String,Object> param);
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 配色方案的表头，筛选带颜色和不带颜色的2Y
     * Date: 2018/5/23 9:49
     *
     * @param param
     * @return 返回一组特性
     */
    List<HzFeature> selectForColorBluePrint(Map<String,Object> param);

    /**
     * @param family
     * @return
     */
    HzFeature selectByCodeAndDescWithMain(HzFeature family);

    /**
     * @param family 包含主配置UID，描述和配置代码
     * @return
     */
    List<HzFeature> selectByCodeAndDescWithMain2(HzFeature family);

    List<HzFeature> selectByDM(List<HzDerivativeMaterielDetail> hzDMDetailChangeBeans);

//    int deleteByFamilyName(List<HzFeatureValue> familyNames);

    int deleteByFamilyName(Map<String, Object> params);
}