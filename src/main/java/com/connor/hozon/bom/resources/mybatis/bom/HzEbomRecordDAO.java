package com.connor.hozon.bom.resources.mybatis.bom;

import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzEbomByPageQuery;
import sql.pojo.epl.HzEPLManageRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEbomRecordDAO {


    Page<HzEPLManageRecord> getHzEbomPage(HzEbomByPageQuery query);

    /**
     * 找一条EBOM EBOM  EBOM是EPL子集 这里直接返回EPL 全信息了
     * @param puid
     * @param projectId
     * @return
     */
    HzEPLManageRecord findEbomById(String puid,String projectId);

    /**
     * 找出一条bomLine的全部子bom sql递归查找
     * @param map
     * @return
     */
    List<HzEPLManageRecord> getHzBomLineChildren(Map map);

    /**
     * 查询零件号是否重复
     * @param projectId
     * @param lineId
     * @return
     */
    boolean checkItemIdIsRepeat(String projectId,String lineId);

}
