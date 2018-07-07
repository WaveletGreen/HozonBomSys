package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.query.HzPbomTreeQuery;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzPbomLineRecord;
import sql.pojo.bom.HzPbomRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/5/25
 */
public interface HzPbomRecordDAO {


    HzPbomLineRecord getPbomById(Map<String,Object> map);

    /**
     * 插入 PBOM管理信息
     * @param record
     * @return
     */
    int insert(HzPbomRecord record);

    int insertList(List<HzPbomLineRecord> records);
    /**
     * 编辑 PBOM管理信息
     * @param record
     * @return
     */
    int update(HzPbomRecord record);

    /**
     * 删除PBOM管理 通过外键删除
     * @param ePuid
     * @return
     */
    int deleteByForeignId(String ePuid);

    /**
     * 根据项目id 和id 获取所有的pbom
     * @return
     */
    List<HzPbomLineRecord> getHzPbomById(Map<String,Object> map);

    /**
     * 分页获取pbom信息
     * @param query
     * @return
     */
    Page<HzPbomLineRecord>  getHzPbomRecordByPage(HzPbomByPageQuery query);

    /**
     * 查询一条pbom 根据外键id
     * @param eBomPuid
     * @return
     */
    HzPbomRecord getHzPbomByEbomPuid(String eBomPuid);

    /**
     * 获取Pbom 总数
     * @param
     * @return
     */
    int getHzBomLineCount(String projectId);

    int getHzPbomMaxOrderNum();

    List<HzPbomLineRecord> getHzPbomTree(HzPbomTreeQuery query);
}
