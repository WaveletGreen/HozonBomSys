package com.connor.hozon.bom.bomSystem.dao.vwo;

import com.connor.hozon.bom.bomSystem.dao.BasicDao;
import org.apache.ibatis.annotations.Param;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.List;
import java.util.Map;

public interface HzVwoInfoDao extends BasicDao<HzVwoInfo> {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);


    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    HzVwoInfo selectByPrimaryKey(Long id);

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    HzVwoInfo findMaxAreaVwoNum();

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    List<HzVwoInfo> selectListByProjectUid(Map<String, Object> params);

    /**
     * 获取当前项目下的变更总数
     *
     * @param projectUid
     * @return
     */
    int tellMeHowManyOfIt(@Param("projectUid") String projectUid);

    int updateByVwoId(HzVwoInfo info);
}