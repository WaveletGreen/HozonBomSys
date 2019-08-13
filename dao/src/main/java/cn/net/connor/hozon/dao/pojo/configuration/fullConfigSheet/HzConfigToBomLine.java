/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.fullConfigSheet;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 配置值与BOM行的映射关系
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzConfigToBomLine {
    /**
     * 主键
     */
    private String id;
    /**
     * 对应的BOM行的PUID
     */
    private String bomLineId;
    /**
     * 配置名
     */
    private String featureValueCode;
    /**
     * 特性值描述
     */
    private String featureValueDesc;
    /**
     * 族名(特性名称)
     */
    private String featureCode;
    /**
     * 特性描述
     */
    private String featureDesc;
    /**
     * 数模层的PUID
     */
    private String bomDigifaxId;
    /**
     * BOMLine行名
     */
    private String bomLineName;
    /**
     * 指向选项值的puid，做为外键
     */
    private String featureValueToBomLineId;
    /**
     * 项目UID
     */
    private String projectUid;
}