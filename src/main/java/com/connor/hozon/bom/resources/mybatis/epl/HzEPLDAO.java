package com.connor.hozon.bom.resources.mybatis.epl;

import com.connor.hozon.bom.resources.domain.dto.response.HzEPLRecordRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEPLQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.epl.HzEPLRecord;

/**
 * @Author: haozt
 * @Date: 2018/12/18
 * @Description:
 */
public interface HzEPLDAO {
    /**
     * 新增一条记录
     * @param record
     * @return
     */
    int insert(HzEPLRecord record);

    /**
     * 更新一条记录
     * @param record
     * @return
     */
    int update(HzEPLRecord record);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int delete(String ids);

    /**
     * 检查重复 零件号是否重复
     * @param query
     * @return
     */
    boolean partIdRepeat(HzEPLQuery query);

    /**
     * 查询一条零件信息 零件号或者主键
     * @param query
     * @return
     */
    HzEPLRecord getPartFromEPLRecordById(HzEPLQuery query);
    /**
     * 分页查询
     * @param query
     * @return
     */
    Page<HzEPLRecord> getEplRecordByPage(HzEPLByPageQuery query);
}
