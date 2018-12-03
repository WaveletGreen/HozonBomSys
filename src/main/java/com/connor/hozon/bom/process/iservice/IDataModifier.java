/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.process.iservice;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:  数据变更接口，分配置的数据修改和BOM的数据修改，分步进行数据表单的修改
 *                              分别修改配置的数据和BOM的数据
 * @Date: Created in  2018/11/22 11:14
 * @Modified By:
 */
public interface IDataModifier {
    /**
     * 配置部分
     *
     * @param orderId
     * @param params
     * @return
     */
    boolean configuration(Long orderId, Object... params);

    /**
     * BOM部分
     *
     * @param orderId
     * @param params
     * @return
     */
    boolean bom(Long orderId, Object... params);
}
