package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0OptionFamily;

import java.util.List;
import java.util.Map;

/**
 * Author: Fancyears·Maylos·Mayways
 * Description: 获取到啊配置器的系统层，系统层是从TC来，不允许更改，所有的配置数据都不应该修改TC的数据为准
 * Date: 2018/5/23 9:47
 */
@Configuration
public interface HzCfg0OptionFamilyDao {
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据主键获取到一个配置系统数据
     * Date: 2018/5/23 9:48
     *
     * @param family 配置系统传入的主键
     * @return HzCfg0OptionFamily
     */
    HzCfg0OptionFamily selectByPrimaryKey(HzCfg0OptionFamily family);

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
     * @param family
     * @return
     */
    HzCfg0OptionFamily selectByCodeAndDescWithMain(HzCfg0OptionFamily family);

    /**
     * @param family 包含主配置UID，描述和配置代码
     * @return
     */
    List<HzCfg0OptionFamily> selectByCodeAndDescWithMain2(HzCfg0OptionFamily family);

    /**
     * 插入1个配置族
     *
     * @param family
     * @return
     */
    int insert(HzCfg0OptionFamily family);
}