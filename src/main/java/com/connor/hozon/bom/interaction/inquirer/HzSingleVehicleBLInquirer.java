package com.connor.hozon.bom.interaction.inquirer;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/25 13:20
 * @Modified By:
 */
@Data
public class HzSingleVehicleBLInquirer {
    /**
     * 配置物料主id
     */
    private Long dmbId;
    /**
     * 项目UID
     */
    private String projectUid;
    /**
     * 是否为空的一并查出来，为空的一起查出来的话，会多出一个车身颜色的数据，但是车身颜色对应的BOMLine是null，默认不查询为空的数据
     */
    private Boolean isNotNull = true;
}
