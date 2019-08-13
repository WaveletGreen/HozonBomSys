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
 * @Date: Created in  2019/8/8 15:38
 * @Modified By:
 */
@Data
public class HzConfigBomLineBean {
    /**
     * 特性值主键，存储在Hz_Cfg0_Record表中的主键
     */
    private String featureValueId;
    /**
     * 特性描述
     */
    private String featureDesc;
    /**
     * 特性值代码，数字+英文
     */
    private String featureValueCode;
    /**
     * BOM条数主键，存储在HZ_Bomline_record表中
     */
    private String bomLineId;
    /**
     * bomline名称
     */
    private String bomLineName;
    /**
     * 项目ID
     */
    private String projectId;
}
