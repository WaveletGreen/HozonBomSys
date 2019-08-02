/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.pojo.change.vwo;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */
@Data
public class HzVwoInformChanges {
    /**
     * 主键
     */
    private Long id;
    /**
     * vwo_主键，作为外键
     */
    private Long vwoId;
    /**
     * vwo号
     */
    private String vwoNum;
    /**
     * 关联人员ID
     */
    private Long personId;
    /**
     * 关联人员人员名称
     */
    private String personName;
    /**
     * 关联人员的部门ID
     */
    private Long personDeptId;
    /**
     * 关联人员的部门名称
     */
    private String personDeptName;
    /**
     * 关联零件号
     */
    private String partId;
    /**
     * 关联零件名称
     */
    private String partName;
    /**
     * 关联的零件PUID，数据库中主键，当作外键
     */
    private String partPuid;

}