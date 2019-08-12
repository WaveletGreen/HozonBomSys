/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.interaction.bean;

import cn.net.connor.hozon.dao.pojo.interaction.SingleVehicleBomRelation;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/9 16:02
 * @Modified By:
 */
@Data
public class SingleVehicleCheckStatus {
    /**
     * 历史状态数据
     */
    private List<SingleVehicleBomRelation> rel;
    /**
     * 状态吗
     */
    private SingleVehicleStatusCode status;

    public enum SingleVehicleStatusCode {
        Ok(1),//OK
        Duplicate(10);//重复
        //对应的数据库代码
        @Getter
        private int code;

        SingleVehicleStatusCode(int code) {
            this.code = code;
        }
    }
}
