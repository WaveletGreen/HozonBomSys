/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.dao.cfg0;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.cfg0.HzCfg0OptionFamily;
import sql.pojo.cfg.derivative.HzDMDetailChangeBean;
import sql.pojo.cfg.derivative.HzDerivativeMaterielDetail;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 获取到啊配置器的系统层，系统层是从TC来，不允许更改，所有的配置数据都不应该修改TC的数据为准
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Configuration
public interface HzCfg0OptionFamilyDao extends BasicDao<HzCfg0OptionFamily>{

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param mainId 产品配置器的puid
     * @return 返回一组系统名称
     */
    List<HzCfg0OptionFamily> selectNameByMainId(@Param("mainId") String mainId);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param mainId 产品配置器的puid
     * @return 返回一组系统名称
     */
    List<HzCfg0OptionFamily> selectNameByMainId2(@Param("mainId") String mainId);
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据产品配置器的puid获取到所有的配置系统层
     * Date: 2018/5/23 9:49
     *
     * @param param
     * @return 返回一组系统名称
     */
    List<HzCfg0OptionFamily> selectNameByMap(@Param("param") Map<String,Object> param);
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 配色方案的表头，筛选带颜色和不带颜色的2Y
     * Date: 2018/5/23 9:49
     *
     * @param param
     * @return 返回一组特性
     */
    List<HzCfg0OptionFamily> selectForColorBluePrint(@Param("param") Map<String,Object> param);

    /**
     * @param family
     * @return
     */
    HzCfg0OptionFamily selectByCodeAndDescWithMain(HzCfg0OptionFamily family);

    /**
     * @param family 包含主配置UID，描述和配置代码
     * @return
     */
    List<HzCfg0OptionFamily> selectByCodeAndDescWithMain2(HzCfg0OptionFamily family);

    List<HzCfg0OptionFamily> selectByDM(List<HzDerivativeMaterielDetail> hzDMDetailChangeBeans);
}