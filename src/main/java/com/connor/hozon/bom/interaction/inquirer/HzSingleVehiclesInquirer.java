package com.connor.hozon.bom.interaction.inquirer;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 单车配置查询器
 * @Date: Created in 2018/9/25 12:14
 * @Modified By:
 */
@Data
public class HzSingleVehiclesInquirer {
    /**
     * 项目UID
     */
    private String svlProjectUid;

    /**
     * 配置物料特性表某一行外键
     */
    private Long svlDmbId;
}
