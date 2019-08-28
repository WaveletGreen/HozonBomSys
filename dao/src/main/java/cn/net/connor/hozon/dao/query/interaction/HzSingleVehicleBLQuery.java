/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.query.interaction;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:
 * @Date: Created in 2018/9/25 13:20
 * @Modified By:
 */
@Data
public class HzSingleVehicleBLQuery {
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
