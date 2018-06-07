package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.dto.request.FindHzEbomRecordReqDTO;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomRecordDAO {
    /**
     * 查询EBOM 全属性
     * @param reqDTO
     * @return
     */
    List<HzEPLManageRecord> getHzEbomList(FindHzEbomRecordReqDTO reqDTO);
}
