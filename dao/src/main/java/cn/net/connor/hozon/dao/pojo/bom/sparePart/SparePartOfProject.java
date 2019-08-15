/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.bom.sparePart;

import lombok.Data;
import lombok.ToString;

/**
 * 项目中的备件对象
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:03
 * @Modified By:
 */
@Data
@ToString
public class SparePartOfProject {
    /**
     * 主键
     */
    private Long id;
    /**
     * 项目ID
     */
    private String projectId;
    /**
     * 备件零件ID
     */
    private Long sparePartId;
    /**
     * 保留字段
     */
    private String reserved1;
    /**
     * 保留字段
     */
    private String reserved2;
    /**
     * 保留字段
     */
    private String reserved3;
    /**
     * 保留字段
     */
    private String reserved4;
    /**
     * 保留字段
     */
    private String reserved5;
}