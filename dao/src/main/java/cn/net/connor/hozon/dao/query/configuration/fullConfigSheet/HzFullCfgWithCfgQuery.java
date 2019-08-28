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
 * @Date: Created in  2019/8/13 15:18
 * @Modified By:
 */
@Data
public class HzFullCfgWithCfgQuery {
    /**
     *
     */
    private Long version;
    /**
     * 2Y层主键
     */
    private String bomLineId;


    public HzFullCfgWithCfgQuery() {
    }

    public HzFullCfgWithCfgQuery(Long version, String bomLineId) {
        this.version = version;
        this.bomLineId = bomLineId;
    }
}
