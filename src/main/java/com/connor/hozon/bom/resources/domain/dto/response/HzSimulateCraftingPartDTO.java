package com.connor.hozon.bom.resources.domain.dto.response;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/26 16:10
 * @Modified By:
 */
@Data
public class HzSimulateCraftingPartDTO {
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
