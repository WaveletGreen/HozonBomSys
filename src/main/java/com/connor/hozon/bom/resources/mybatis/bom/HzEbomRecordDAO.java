package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.page.Page;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomRecordDAO {
    /**
     * 查询EBOM 全属性
     * @param reqDTO
     * @return
     */
    List<HzEPLManageRecord> getHzEbomList(FindForPageReqDTO reqDTO);

    Page<HzEPLManageRecord> getHzEbomPage(FindForPageReqDTO recordReqDTO);

    /**
     * 找一条EBOM EBOM  EBOM是EPL子集 这里直接返回EPL 全信息了
     * @param puid
     * @param projectId
     * @return
     */
    HzEPLManageRecord findEbomById(String puid,String projectId);

    /**
     * 找出一条bomLine的全部子bom
     * @param map
     * @return
     */
    List<HzEPLManageRecord> getHzBomLineChildren(Map map);

}
