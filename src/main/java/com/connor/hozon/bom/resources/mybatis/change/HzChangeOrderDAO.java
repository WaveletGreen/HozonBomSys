package com.connor.hozon.bom.resources.mybatis.change;

import com.connor.hozon.bom.resources.domain.query.HzChangeOrderByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.change.HzChangeOrderRecord;

import java.util.List;

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

    /**
     * 查询变更清单 根据项目id+id
     * @param
     * @return
     */
    HzChangeOrderRecord findHzChangeOrderRecordById(HzChangeOrderByPageQuery query,Long id);
    /**
     * 分页获取“我的申请”表单记录
     * @param query
     * @return
     */
    Page<HzChangeOrderRecord> findHzChangeOrderRecordByPageId(HzChangeOrderByPageQuery query);
    /**
     * 查询变更清单 根据项目id+id（TC同步）
     * @param
     * @return
     */
    HzChangeOrderRecord findHzChangeOrderRecordByIdTC(HzChangeOrderByPageQuery query,Long id);

    /**
     * 分页获取表单记录
     * @param query
     * @return
     */
    Page<HzChangeOrderRecord> findHzChangeOrderRecordByPage(HzChangeOrderByPageQuery query);

    List<HzChangeOrderRecord> findHzChangeOrderRecordByProjectId(String projectUid);
}
