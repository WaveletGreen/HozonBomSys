package com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo;

import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import sql.pojo.cfg.vwo.HzVwoInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/10 13:53
 * @Modified By:
 */
public interface IHzVwoInfoService {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    boolean doDeleteByPrimaryKey(Long id);

    /**
     * 插入1条vwo
     *
     * @param record
     * @return
     */
    Long doInsert(HzVwoInfo record);

    /**
     * 主键搜索
     *
     * @param id
     * @return
     */
    HzVwoInfo doSelectByPrimaryKey(Long id);

    /**
     * 更新vwo
     *
     * @param record
     * @return
     */
    boolean doUpdateByPrimaryKey(HzVwoInfo record);

    /**
     * 寻找当月最大的vwo
     *
     * @return
     */
    HzVwoInfo doFindMaxAreaVwoNum();

    /**
     * 根据分页进行查询
     */
    List<HzVwoInfo> doSelectListByProjectUid(QueryBase queryBase, String projectUid);

    /**
     * 当前项目下的总数
     *
     * @param projectUid
     * @return
     */
    int tellMeHowManyOfIt(String projectUid);

    /**
     * 生成VWO号码
     */
    HzVwoInfo generateVWONum() ;
}
