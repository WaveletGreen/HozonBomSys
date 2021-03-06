/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.dao.configuration.fullConfigSheet;

import cn.net.connor.hozon.dao.dao.configuration.BasicDao;
import cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet.HzConfigToBomLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 忘了
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Repository
public interface HzConfigToBomLineDao extends BasicDao<HzConfigToBomLine> {
    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(@Param("id") String id);


    /**
     * 根据主键搜索
     *
     * @param id
     * @return
     */
    HzConfigToBomLine selectByPrimaryKey(@Param("id") String id);

    /**
     * 根据项目ID和Bomline UID查找一条配置+BOMLine关联数据
     *
     * @return
     */
    HzConfigToBomLine selectByBLUidAndPrjUid(HzConfigToBomLine record);


    /**
     * 批量插入
     *
     * @param records
     * @return
     */
    int insertByBatch(List<HzConfigToBomLine> records);
}