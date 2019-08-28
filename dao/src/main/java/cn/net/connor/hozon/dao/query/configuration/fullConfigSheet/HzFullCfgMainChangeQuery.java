/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.query.configuration.fullConfigSheet;

import lombok.Data;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/13 11:17
 * @Modified By:
 */
@Data
public class HzFullCfgMainChangeQuery {
    private Long orderId;
    private Integer status;


    public HzFullCfgMainChangeQuery() {
    }

    public HzFullCfgMainChangeQuery(Long orderId, Integer status) {
        this.orderId = orderId;
        this.status = status;
    }
}
