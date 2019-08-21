/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.response.task;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 任务传输对象
 * @Date: Created in 2018/10/19 15:27
 * @Modified By:
 */
@Data
public class HzTaskResponseDTO implements Serializable {
    private static final long serialVersionUID = 8384886595075000636L;
    /**
     * 跳转tab的url
     */
    private String url;
    /**
     * tab的id
     */
    private Long id;
    /**
     * tab显示的名称
     */
    private String text;
    /**
     * 表单类型，对应的审核表单，1：vwo表单,2:ewo表单,3:mwo表单
     */
    private Integer formType;
    /**
     * 目标的ID，VWO的ID时Long，如1，2，3
     */
    private Long targetId;
    /**
     * 目标的名，VWO的name格式未VCYYYYNNNN(20180001)
     */
    private String targetName;
    /**
     * 目标类型，1：特性变更，2：配色方案变更，3：配置物料特性表变更，4，全配置BOM一级清单变更，5：相关性变更
     */
    private Integer targetType;

    /**
     * 保留字段
     */
    private Object reserve;
    private Object reserve2;
    private Object reserve3;
    private Object reserve4;
    private Object reserve5;
    private Object reserve6;
    private Object reserve7;
    private Object reserve8;
    private Object reserve9;
    private Object reserve10;
}
