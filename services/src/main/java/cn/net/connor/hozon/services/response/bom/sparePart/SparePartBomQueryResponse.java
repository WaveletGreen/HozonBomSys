/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.response.bom.sparePart;

import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import lombok.Data;

import java.util.List;

/**
 * 返回给前端的数据对象
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:58
 * @Modified By:
 */
@Data
public class SparePartBomQueryResponse {
    /**
     * 查询满足结果的总数
     */
    int totalCount;
    /**
     * 查询结果
     */
    List<SparePartData> result;

    public SparePartBomQueryResponse(int totalCount, List<SparePartData> result) {
        this.totalCount = totalCount;
        this.result = result;
    }
}
