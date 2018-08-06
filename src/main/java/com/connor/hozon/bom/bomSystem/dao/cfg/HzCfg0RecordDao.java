package com.connor.hozon.bom.bomSystem.dao.cfg;

import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.HzCfg0Record;

import java.util.List;
import java.util.Map;


public interface HzCfg0RecordDao {

    /***
     * 根据主键删除在Hz_Cfg0_Record表中插入1条数据的数据
     * @param puid
     * @return
     */
    int deleteByPrimaryKey(@Param("puid") String puid);

    /***
     * 根据主键删除在Hz_Cfg0_Record表中插入1条数据的数据
     * @param puid
     * @return
     */
    int deleteAddCfgByPrimaryKey(@Param("puid") String puid);

    /**
     * 在Hz_Cfg0_Record表中插入1条数据
     *
     * @param record
     * @return
     */
    int insert(HzCfg0Record record);

    /***
     * 根据项目的puid搜索所有的特性信息
     * @param projectPuid
     * @param queryBase
     * @return
     */
    List<HzCfg0Record> selectListByProjectPuid(@Param("projectPuid") String projectPuid, QueryBase queryBase);

    /***
     * 根据项目的puid搜索所有新添加的特性信息
     * @param projectPuid
     * @return
     */
    List<HzCfg0Record> selectAddedCfgListByProjectPuid(@Param("projectPuid") String projectPuid);

    List<HzMaterielFeatureBean> selectMaterielFeatureByProjectPuid(@Param("projectPuid") String projectPuid);
    /***
     * 根据puid查找1条特性数据
     * @param puid
     * @return
     */
    HzCfg0Record selectByPrimaryKey(@Param("puid") String puid);

    /***
     * 根据puid查找1条特性数据
     * @param puid
     * @return
     */
    HzCfg0Record selectOneAddedCfgByPuid(@Param("puid") String puid);

    /***
     * 根据puid更新1条特性数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzCfg0Record record);

    /**
     * 添加内饰颜色等新加的特性，不属于产品配置器
     *
     * @param record
     * @return
     */
    int insertAddCfg(HzCfg0Record record);

    int updateAddedCfgByPrimaryKey(HzCfg0Record record);

    int deleteAddedCfgByList(List<HzCfg0Record> records);

    List<HzCfg0Record> selectCfg0ListByPuids(Map<String,Object> _map);

    List<HzCfg0Record>  selectByCodeAndDesc(HzCfg0Record record);

    int deleteCfgByList(List<HzCfg0Record> records);

    int setIsSent(Map<String,Object> _map);

    int tellMeHowManyOfThose(String projectPuid);
}