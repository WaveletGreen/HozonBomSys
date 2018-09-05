package com.connor.hozon.bom.bomSystem.dao.cfg;

import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzColorModel;
import sql.pojo.cfg.HzColorModel2;

import java.util.List;

@Configuration
public interface HzColorModelDao {

    /**
     * 主键删除
     *
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid")String puid);

    /**
     * 插入
     *
     * @param record
     * @return
     */
    int insert(HzColorModel record);

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */

    HzColorModel selectByPrimaryKey(@Param("puid")String puid);

    /**
     * 根据颜色模型
     *
     * @param modelUid 车型UID
     * @return
     */

    List<HzColorModel> selectByModelUid(@Param("modelUid")String modelUid);

    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    List<HzColorModel> selectByModelUidWithColor(@Param("modelUid") String modelUid);

    /**
     * 根据主配置查找
     *
     * @param cfgMainUid 主配置主键
     * @return
     */

    List<HzColorModel> selectByCfgMainUid(@Param("cfgMainUid")String cfgMainUid);

    /**
     * 主键更新
     *
     * @param record 颜色车型
     * @return
     */
    int updateByPrimaryKey(HzColorModel record);

    /**
     * 批量插入
     *
     * @return
     */
    int insertByBatch(List<HzColorModel> colorModels);

    /**
     * 批量更新车型配置的颜色
     * @param model 车型配置集合
     * @return
     */
    int updateColorModelWithCfg(HzColorModel model);

    List<HzColorModel2> selectByProjectPuid(String projectPuid);
}