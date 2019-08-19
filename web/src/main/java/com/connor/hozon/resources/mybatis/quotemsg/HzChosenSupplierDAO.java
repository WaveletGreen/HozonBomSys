package com.connor.hozon.resources.mybatis.quotemsg;


import com.connor.hozon.resources.domain.model.HzChosenSupplier;

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