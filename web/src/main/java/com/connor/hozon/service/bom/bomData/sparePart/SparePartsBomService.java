/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.service.bom.bomData.sparePart;

import cn.net.connor.hozon.services.request.bom.sparePart.SparePartOfProjectRequestQueryDTO;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartPostDTO;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartQuoteEbomLinesPostDTO;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartBomQueryPageResponse;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartQueryEbomPageResponseDTO;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.resources.domain.query.HzEbomByPageQuery;
import org.springframework.ui.Model;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:15
 * @Modified By:
 */
public interface SparePartsBomService {
    /**
     * 分页查询出项目
     * 先将controller的查询对象转成dao层的查询对象再进行查询
     * <p>
     * 查询完成后，将dao层的对象再转成controller层使用的对象
     *
     * @param query controller和service连接对象
     * @return
     */
    SparePartBomQueryPageResponse selectPageByProjectId(SparePartOfProjectRequestQueryDTO query);

    /**
     * 保存单条备件数据
     *
     * @param data
     * @return
     */
    JSONObject saveSparePart(SparePartPostDTO data);

    /**
     * 添加子层
     *
     * @param data
     * @return
     */
    JSONObject addSparePartChild(SparePartPostDTO data);

    /**
     * 批量删除
     *
     * @param data
     * @return
     */
    JSONObject deleteList(List<SparePartPostDTO> data);

    /**
     * 更新单条备件零件数据
     *
     * @param data controller层post请求对象
     * @return
     */
    JSONObject updateSparePart(SparePartPostDTO data);

    /**
     * 获取到单车用量的title
     * @param projectId
     * @return
     */
    LinkedHashMap<String, String> getVehicleUsageTitle(String projectId, String breakType);

    /**
     * 创建备件bom对应的ebom的table title
     *
     * @return
     * @param projectId
     */
    LinkedHashMap<String, String> createRelEbomTitle(String projectId);

    /**
     * 获取到ebom数据
     *
     * @param query
     * @return
     */
    SparePartQueryEbomPageResponseDTO getEbomList(HzEbomByPageQuery query);

    /**
     * 引用EBOM的数据
     * @param lines
     * @return
     */
    JSONObject quoteEbomLines(SparePartQuoteEbomLinesPostDTO lines);

    /**
     * 递归查询测试
     * @param data
     * @return
     */
    JSONObject selectRecursionByTopLayerId(SparePartPostDTO data);
}
