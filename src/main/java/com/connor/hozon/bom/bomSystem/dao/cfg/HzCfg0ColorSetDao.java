package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzCfg0ColorSet;

import java.util.List;

/**
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/21
 * Time: 14:43
 */
@Configuration
public interface HzCfg0ColorSetDao {
    /**
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 检索所有的颜色集
     * @Date: 2018/5/21 17:09
     */
    List<HzCfg0ColorSet> queryAll2();

    /**
     * @param entity
     * @return 找到的颜色对象
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据颜色的id，找到该颜色对象
     * @Date: 2018/5/21 17:09
     */
    HzCfg0ColorSet selectById(HzCfg0ColorSet entity);

    /**
     * 根据颜色代码寻找颜色对象
     *
     * @param entity
     * @return 找到的颜色对象
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 根据颜色的id，找到该颜色对象
     * @Date: 2018/5/21 17:09
     */
    HzCfg0ColorSet selectByColorCode(HzCfg0ColorSet entity);

    /**
     * @param entity
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 插入一个颜色信息
     * @Date: 2018/5/21 17:10
     */
    int insertOne(HzCfg0ColorSet entity);

    /**
     * @param entity 更新的颜色对象
     * @return 影响行数
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行更细颜色信息
     * @Date: 2018/5/21 17:10
     */
    int updateOne(HzCfg0ColorSet entity);

    /**
     * @param entity 更新的颜色对象的状态
     * @return 影响行数
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 执行更细颜色信息
     * @Date: 2018/5/21 17:10
     */
    int updateStatusByPk(HzCfg0ColorSet entity);

    /**
     * @param entity 传入的颜色集合
     * @return
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 批量删除颜色信息
     * @Date: 2018/5/21 17:10
     */
    int deleteByList(List<HzCfg0ColorSet> entity);
}
