package com.connor.hozon.bom.bomSystem.dao.modelColor;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.modelColor.HzCfg0ModelColor;
import sql.pojo.cfg.modelColor.HzCfg0ModelColorDetail;
import sql.pojo.cfg.modelColor.HzColorModel2;

import java.util.List;

@Configuration
public interface HzColorModelDao extends BasicDao<HzCfg0ModelColorDetail> {

    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid") String puid);


    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */

    HzCfg0ModelColorDetail selectByPrimaryKey(@Param("puid") String puid);

    /**
     * 根据颜色模型
     *
     * @param modelUid 车型UID
     * @return
     */

    List<HzCfg0ModelColorDetail> selectByModelUid(@Param("modelUid") String modelUid);

    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    List<HzCfg0ModelColorDetail> selectByModelUidWithColor(@Param("modelUid") String modelUid);

    /**
     * 根据主配置查找
     *
     * @param cfgMainUid 主配置主键
     * @return
     */

    List<HzCfg0ModelColorDetail> selectByCfgMainUid(@Param("cfgMainUid") String cfgMainUid);

    /**
     * 批量插入
     *
     * @return
     */
    int insertByBatch(List<HzCfg0ModelColorDetail> colorModels);

    /**
     * 批量更新车型配置的颜色
     *
     * @param model 车型配置集合
     * @return
     */
    int updateColorModelWithCfg(HzCfg0ModelColorDetail model);

    List<HzColorModel2> selectByProjectPuid(String projectPuid);

    List<HzCfg0ModelColorDetail> selectByModelUidWithColor2(String modelUid);

    /**
     * 根据颜色车型查询对应的车型配置
     * @param hzCfg0ModelColors
     * @return
     */
    List<HzCfg0ModelColorDetail> selectByModelColors(List<HzCfg0ModelColor> hzCfg0ModelColors);
}