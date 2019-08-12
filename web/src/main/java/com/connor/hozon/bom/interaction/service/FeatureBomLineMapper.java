/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.interaction.service;

import cn.net.connor.hozon.dao.pojo.interaction.FeatureBomLineRelationHistory;
import cn.net.connor.hozon.dao.pojo.interaction.HzConfigBomLineBean;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/8 17:00
 * @Modified By:
 */
@Mapper
public interface FeatureBomLineMapper {
    //当前接口的实例
    FeatureBomLineMapper INSTANCE = Mappers.getMapper(FeatureBomLineMapper.class);

    /**
     * 单个数据对应
     * @param bean
     * @return
     */
    FeatureBomLineRelationHistory map(HzConfigBomLineBean bean);

    /**
     * list转换
     * @param list
     * @return
     */
    List<FeatureBomLineRelationHistory> mapToList(List<HzConfigBomLineBean> list);
}
