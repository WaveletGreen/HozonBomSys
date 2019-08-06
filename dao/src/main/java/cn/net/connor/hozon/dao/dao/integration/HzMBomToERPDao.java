/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.integration;

import cn.net.connor.hozon.dao.pojo.integration.HzMBomToERPBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: ERP集成用的MBOM dao层，只筛选重要数据，其他不要
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzMBomToERPDao {
    /**
     * 根据项目UID和MBOM行的UID列表获取到一组记录
     * 包含下面的选项
     * <p>
     * projectUid 项目的UID
     * <p>
     * MBOM记录的UID列表
     *
     * @return
     */
    List<HzMBomToERPBean> selectByUidOfBatch(Map<String, Object> params);

    /**
     * 根据项目UID和MBOM行的父层UID列表获取一组父层的数据，需要从@{@link HzMBomToERPBean}中getParentUid获取到父层Uid
     *
     * @param projectUid 项目UID
     * @param list       父层UID列表
     * @return
     */
    List<HzMBomToERPBean> selectbyParentUidOfBatch(String projectUid, List<String> list);

    /**
     * 根据项目UID，除去2Y层，将所有2Y以下的所有数据取出
     * 包含下面的选项
     * <p>
     * projectUid 项目UID
     * <p>
     * is2Y       是否是2Y层，如果要所有，传0和1以外的值即可
     *
     * @return
     */
    List<HzMBomToERPBean> selectByProjectUidWithCondition(Map<String, Object> params);
}
