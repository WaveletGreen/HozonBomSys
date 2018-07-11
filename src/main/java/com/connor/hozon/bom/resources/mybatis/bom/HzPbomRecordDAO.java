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

    /**
     * 获取pbom信息 根据项目id puid或者零件号
     * @param map
     * @return
     */
    List<HzPbomLineRecord> getPbomById(Map<String,Object> map);

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
    int update(HzPbomLineRecord record);

    /**
     * 删除PBOM管理 通过外键删除
     * @param ePuid
     * @return
     */
    int deleteByForeignId(String ePuid);

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
    HzPbomLineRecord getHzPbomByEbomPuid(String eBomPuid,String projectId);

    /**
     * 获取Pbom 总数
     * @param
     * @return
     */
    int getHzBomLineCount(String projectId);

    int getHzPbomMaxOrderNum();

    List<HzPbomLineRecord> getHzPbomTree(HzPbomTreeQuery query);

    int getMaxLineIndexFirstNum(String projectId);

    boolean checkItemIdIsRepeat(String projectId, String lineId);
}
