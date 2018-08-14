package com.connor.hozon.bom.bomSystem.dao.cfg.vwo;

import com.connor.hozon.bom.common.base.entity.QueryBase;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.List;
import java.util.Map;

public interface HzVwoInfoDao {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    int insert(HzVwoInfo record);

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    HzVwoInfo selectByPrimaryKey(Long id);

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(HzVwoInfo record);

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    HzVwoInfo findMaxAreaVwoNum();

    /**
     * 分页查询
     * @param params
     * @return
     */
    List<HzVwoInfo> selectListByProjectUid(Map<String,Object> params);

    /**
     * 获取当前项目下的变更总数
     * @param projectUid
     * @return
     */
    int tellMeHowManyOfIt(@Param("projectUid") String projectUid);
}