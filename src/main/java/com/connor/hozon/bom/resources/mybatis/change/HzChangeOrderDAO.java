package com.connor.hozon.bom.resources.mybatis.change;

import sql.pojo.change.HzChangeOrderRecord;

/**
 * @Author: haozt
 * @Date: 2018/11/12
 * @Description:变更单
 */
public interface HzChangeOrderDAO {
    /**
     * 新增变更单
     * @param record
     * @return
     */
    int insert(HzChangeOrderRecord record);

    /**
     * 编辑变更单
     * @param record
     * @return
     */
    int update(HzChangeOrderRecord record);

    /**
     * 删除变更单
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 检查变更单号是否重复
     * @param changeNo
     * @return
     */
    boolean changeNoExist(String changeNo);

    /**
     * 查询变更清单 根据id
     * @param id
     * @return
     */
    HzChangeOrderRecord findHzChangeOrderRecordById(Long id);


}
