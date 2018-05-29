package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ModelColor;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 10:52
 */
@Configuration
public interface HzCfg0ModelColorDao {
    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据配置器的puid，获取到所有的颜色车型的信息，包括其中的系统配置信息，map中的信息是经过排序后的
     * Date: 2018/5/23 9:52
     *
     * @param color
     * @return
     */
    List<HzCfg0ModelColor> selectByMainId(HzCfg0ModelColor color);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据主键更新1条颜色车型信息
     * Date: 2018/5/23 9:53
     *
     * @param color
     * @return
     */
    int updateByPrimaryKey(HzCfg0ModelColor color);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据主键获取到1条颜色车型信息
     * Date: 2018/5/23 9:54
     *
     * @param color
     * @return
     */
    HzCfg0ModelColor selectByPrimaryKey(HzCfg0ModelColor color);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 获取到所有的颜色车型
     * Date: 2018/5/23 9:54
     *
     * @return
     */
    List<HzCfg0ModelColor> selectAll();

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 插入1条颜色车型
     * Date: 2018/5/23 9:55
     *
     * @param color
     * @return
     */
    int insertOne(HzCfg0ModelColor color);

    /**
     * Author: Fancyears·Maylos·Mayways
     * Description: 根据主键批量删除颜色车型
     * Date: 2018/5/23 9:55
     *
     * @param colors
     * @return
     */
    int deleteByBatch(List<HzCfg0ModelColor> colors);
}
