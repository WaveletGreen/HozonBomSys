package com.connor.hozon.bom.bomSystem.service.iservice.cfg;

import org.springframework.context.annotation.Configuration;
import sql.pojo.cfg.HzColorModel;

import java.util.List;

@Configuration
public interface IHzColorModelService {
    /**
     * 主键删除
     *
     * @param puid 主键
     * @return
     */
    int doDeleteByPrimaryKey(String puid);

    /**
     * 插入
     *
     * @param record 颜色+配置集合
     * @return
     */
    int doInsert(HzColorModel record);

    /**
     * 主键筛选
     *
     * @param puid 主键
     * @return
     */

    HzColorModel doSelectByPrimaryKey(String puid);

    /**
     * 主键更新
     *
     * @param record 颜色+配置记录
     * @return
     */
    int doUpdateByPrimaryKey(HzColorModel record);

    /**
     * 根据颜色模型
     *
     * @param modelUid
     * @return
     */

    List<HzColorModel> doSelectByModelUid(String modelUid);
    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    List<HzColorModel> doSelectByModelUidWithColor(String modelUid);
    /**
     * 根据颜色模型，包括颜色一起筛选出来
     *
     * @param modelUid 车型UID
     * @return
     */
    List<HzColorModel> doSelectByModelUidWithColor2(String modelUid);
    /**
     * 根据项目ID查找
     *
     * @param projectUid
     * @return
     */

    List<HzColorModel> doSelectByCfgMainUid(String projectUid);

    /**
     * 批量插入
     *
     * @param colorModels 颜色+配置记录集合
     * @return
     */
    int doInsertByBatch(List<HzColorModel> colorModels);

    /**
     * 根据颜色车型和配置更新颜色信息
     * @param model 需要更新的模型集合
     * @return
     */

    boolean doUpdateColorModelWithCfg(HzColorModel model);
}
