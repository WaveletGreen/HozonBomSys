/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet;

import cn.net.connor.hozon.dao.basic.MyBatisBaseDao;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgModel;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzFullCfgWithCfg;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 全配置BOM一级清单基础车型对应的配置项
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzFullCfgModelDao extends MyBatisBaseDao<HzFullCfgModel,Long,HzFullCfgModel> {

    List<String> selectCfg(String puid);

    void insertCfgs(List<HzFullCfgModel> cfgs);

    List<HzFullCfgModel> selectByMainPuid(@Param("flModVersion") Long flModVersion);

    int updateByHzFullCfgModelList(List<HzFullCfgModel> hzFullCfgModels);

    int updateByBomLinePuid(HzFullCfgWithCfg hzFullCfgWithCfg);

    List<HzFullCfgModel> selectByModelUid(@Param("modelUid") String modelUid);

    List<HzFullCfgModel> selectByModelUidWithMarks(HzFullCfgModel withCfg);

    int updateByHzFullCfgModelListCfg(List<HzFullCfgModel> hzFullCfgModels);

    int insertListAll(List<HzFullCfgModel> hzFullCfgModels);
}