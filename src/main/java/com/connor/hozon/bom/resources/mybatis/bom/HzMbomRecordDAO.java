package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;
import sql.pojo.bom.HzMbomRecord;

import java.util.List;

/**
 * Created by haozt on 2018/5/24
 */
public interface HzMbomRecordDAO {
    /**
     * 批量插入MBOM维护信息
     * @return
     */
    int insertList(List<HzMbomRecord> records);

    /**
     * 查询MBOM信息 MBOM维护信息
     */
    List<HzMbomLineRecord> getMBomMaintainRecord();

    /**
     * 更新MBOM维护信息
     * @param record
     * @return
     */
    int update(HzMbomRecord record);

    /**
     * 删除BOM维护 通过外键删除
     * @param pPuid
     * @return
     */
    int deleteByForeignId(String pPuid);

    /**
     * 按条件搜索MBOM在线维护信息
     * @return
     */
    List<HzMbomLineRecord> searchMbomMaintainDetail(HzBomLineRecord record);

    Page<HzMbomLineRecord> findMbomForPage(FindForPageReqDTO reqDTO);
}
