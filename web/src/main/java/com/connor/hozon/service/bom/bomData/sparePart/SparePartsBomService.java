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
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartBomQueryResponse;
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
    SparePartBomQueryResponse selectPageByProjectId(SparePartOfProjectRequestQueryDTO query);

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
     * 创建备件bom对应的ebom的table title
     *
     * @return
     */
    LinkedHashMap<String, String> createRelEbomTitle();

    /**
     * 获取到ebom数据
     *
     * @param query
     * @return
     */
    JSONObject getEbomList(HzEbomByPageQuery query);

    JSONObject quoteEbomLines(SparePartQuoteEbomLinesPostDTO lines);
}
