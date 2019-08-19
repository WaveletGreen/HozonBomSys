/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.dto.response;

import com.connor.hozon.bom.resources.domain.dto.BaseDTO;
import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 模拟合成工艺合件的结果
 * @Date: Created in 2018/9/26 16:10
 * @Modified By:
 */
@Data
public class HzSimulateCraftingPartDTO extends BaseDTO {
    /**
     * 主键
     */
    private String puid;
    /**
     * 父层UID
     */
    private String parentUid;
    /**
     * 零件号
     */
    private String lineId;
}
