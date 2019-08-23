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
 * @Description: 单车配置查询器
 * @Date: Created in 2018/9/25 12:14
 * @Modified By:
 */
@Data
public class HzSingleVehiclesQuery {
    /**
     * 项目UID
     */
    private String svlProjectUid;

    /**
     * 配置物料特性表某一行外键
     */
    private Long svlDmbId;
}
