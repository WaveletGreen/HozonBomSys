package com.connor.hozon.resources.service.bom;

import com.connor.hozon.resources.domain.dto.response.HzEbomLevelRespDTO;
import com.connor.hozon.resources.domain.dto.response.HzEbomRespDTO;
import com.connor.hozon.resources.domain.dto.response.HzLouRespDTO;
import com.connor.hozon.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.resources.domain.query.HzEbomTreeQuery;
import com.connor.hozon.resources.domain.query.HzLouaQuery;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.util.Result;
import cn.net.connor.hozon.dao.pojo.bom.epl.HzEPLManageRecord;

import java.util.List;

/**
 * Created by haozt on 2018/06/06
 */
public interface HzEBOMReadService {
    /**
     * 分页获取EBOM信息
     * @param query
     * @return
     */
    Page<HzEbomRespDTO> getHzEbomPage(HzEbomByPageQuery query);
    /**
     * 查询一条bom信息
     * @param puid
     * @param projectId
     * @return
     */
    HzEbomRespDTO fingEbomById(String puid, String projectId);

    HzEbomLevelRespDTO fingEbomLevelById(String puid, String projectId);

    int findIsHasByPuid(String puid, String projectId);

    /**
     * 找出当前bom的全部子bom  递归查找 bom结构树
     * @param
     * @return
     */
    List<HzEPLManageRecord> findCurrentBomChildren(HzEbomTreeQuery query);

    /**
     * 向上查询找到2y层BOM
     * @param projectId
     * @param puid
     * @return
     */
    HzEPLManageRecord findParentFor2Y(String projectId,String puid);

    /**
     * 获取LOA信息
     * @param query
     * @return
     */
    HzLouRespDTO getHzLouInfoById(HzLouaQuery query);

    /**
     * 检查是否关联特性
     * @param puids BOM 端数据
     * @param projectUid 项目id
     * @return
     */
    Result checkConnectWithFeature(List<String> puids, String projectUid);
}
