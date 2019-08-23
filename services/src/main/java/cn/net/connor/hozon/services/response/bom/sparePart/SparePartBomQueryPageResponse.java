/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.response.bom.sparePart;

import cn.net.connor.hozon.services.response.BasePageResponseDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 返回给前端的备件BOM查询对象
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:58
 * @Modified By:
 */
@Data
public class SparePartBomQueryPageResponse extends BasePageResponseDTO<SparePartDataResponseDTO> implements Serializable {

    private static final long serialVersionUID = -7105193470122152107L;

    public SparePartBomQueryPageResponse(int totalCount, List<SparePartDataResponseDTO> result) {
        this.totalCount = totalCount;
        this.result = result;
    }
}
