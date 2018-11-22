package com.connor.hozon.bom.resources.mybatis.change;

import sql.pojo.change.HzChangeListRecord;

import java.util.List;

public interface HzChangeListDAO {
    //引用对象
    List<HzChangeListRecord> findChangeList(String formID);
}
