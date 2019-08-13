/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.interaction;

import lombok.Data;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/9 11:40
 * @Modified By:
 */
@Data
public class SingleVehicleBomRelation {
    /**
     * 单车BOM对应的单条BOM的ID，存在HZ_Single_Vehicle_Bom中的主键
     */
    private Long svlBomId;

    /**
     * 特性值主键
     */
    private String featureValueId;
    /**
     * 特性描述
     */
    private String featureDesc;
    /**
     * 特性值diamante
     */
    private String featureValueCode;
    /**
     * bomline主键，在hz_Bomline_record表中
     */
    private String bomLineId;
    /**
     * bimline名称
     */
    private String bomLineName;
    /**
     * 检查状态
     */
    private String status;

}
