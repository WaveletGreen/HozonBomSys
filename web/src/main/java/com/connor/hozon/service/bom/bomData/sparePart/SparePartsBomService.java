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
import cn.net.connor.hozon.services.response.bom.sparepart.SparePartBomQueryPageResponseVO;
import cn.net.connor.hozon.services.response.bom.sparepart.SparePartQueryEbomPageResponseVO;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.resources.domain.query.HzEbomByPageQuery;

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
    SparePartBomQueryPageResponseVO selectPageByProjectId(SparePartOfProjectRequestQueryDTO query);

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
    SparePartQueryEbomPageResponseVO getEbomList(HzEbomByPageQuery query);

    /**
     * 引用EBOM的数据
     * @param lines
     * @return
     */
    @Deprecated
    JSONObject quoteEbomLines(SparePartQuoteEbomLinesPostDTO lines);

    /**
     * 向上引用父层数据，直到2Y，向下引用到最终的子层是备件的所有沿路结构
     *
     * 当前选中的结构数据，向上查找所有的父层结构，直到2Y为止。
     *
     * 向下查找所有子层结构，只要有1层的数据是备件，则沿路的数据都要带出来，可以进行从后往前找，找到第一个数据时备件的数据，然后将这个数据往前找直到当前的数据位置的一层
     *
     * @param dto
     * @return
     */
    JSONObject quoteEbomLinesUpAndDown(SparePartQuoteEbomLinesPostDTO dto);

    /**
     * 递归查询测试
     * @param data
     * @return
     */
    JSONObject selectRecursionByTopLayerId(SparePartPostDTO data);


}
