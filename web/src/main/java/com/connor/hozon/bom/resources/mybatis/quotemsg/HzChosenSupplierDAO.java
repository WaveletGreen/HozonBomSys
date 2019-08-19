/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.quotemsg;


import com.connor.hozon.bom.resources.domain.model.HzChosenSupplier;

import java.util.List;

public interface HzChosenSupplierDAO {
    int insert(HzChosenSupplier record);

    int insertSelective(HzChosenSupplier record);

    List<HzChosenSupplier> selectPage(HzChosenSupplier hzChosenSupplier);

    HzChosenSupplier selectById(Long id);

    List<HzChosenSupplier> selectProjectId(String projectPuid);

    int update(HzChosenSupplier hzChosenSupplier);

    int delete(String[] ids);

    List<HzChosenSupplier> selectCreator(HzChosenSupplier hzChosenSupplierQuery);
}