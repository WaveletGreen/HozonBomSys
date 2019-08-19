/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.mybatis.epl;

import com.connor.hozon.bom.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzEPLQuery;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.util.Result;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLRecord;

import java.util.List;

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
     * @param list
     *        都是批量删除 以上两个参数传一个就行
     * @return
     */
    int delete(String ids,List<Long> list);

    /**
     * 批量删除 改为删除状态 不进行数据删除
     * @param list
     * @return
     */
    int deleteByIds(List<Long> list);

    /**
     * 检查重复 零件号是否重复
     * 同项目内 不允许出现重复零件号
     * @param query
     * @return
     */
    Result partIdRepeat(HzEPLQuery query);

    /**
     * 查询一条零件信息 零件号或者主键
     * @param query
     * @return
     */
    HzEPLRecord getEPLRecordById(HzEPLQuery query);
    /**
     * 分页查询
     * @param query
     * @return
     */
    Page<HzEPLRecord> getEplRecordByPage(HzEPLByPageQuery query);

    HzEPLRecord selectByPartId(String partId);

    List<HzEPLRecord> selectByprojectPuid(String projectPuid);

    int updateList(List<HzEPLRecord> hzEPLRecordsUpdate);

    int insertList(List<HzEPLRecord> hzEPLRecords);

    /**
     * 查询以 WS GDT 结尾的数据 part_id
     * @param projectId 项目id
     * @return EPL ids 集合
     */
    List<Long> getEPLIdsWherePartIdEndWithWSAndGDT(String projectId);

    /**
     * 获取EPL 零件分类为虚拟总成件
     * TC传输过来的部分数据有问题 需要进行转换
     * Virtual Assembly 为虚拟总成件 BOM端进行中文显示而不是英文
     * @param projectId 项目id
     * @return 零件分类为虚拟总成件的 EPL ID
     */
    List<Long> getVirtualAssemblyEPLRecord(String projectId);

    /**
     * 将零件分类为英文虚拟总成件 改为中文
     * @param longs 更改EPL ID集合
     * @return
     */
    int updateVirtualAssemblyEnToZh(List<Long> longs);
}
