/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package cn.net.connor.hozon.dao.pojo.configuration.model;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
public class HzCfg0ModelRecord {
    /**
     * 唯一键
     */
    private String puid;
    /**
     * 车型名称
     */
    private String objectName;
    /**
     * 车型描述
     */
    private String objectDesc;
    /**
     * 属于的配置item的puid
     */
    private String modelMainRecord;
    /**
     * 基本信息，在TC中不进行维护，在BOM系统进行维护，并每一个单一车型的基本信息都一样
     */
    private String modelBasicDetail;
    /**
     * 是否已发送到SAP
     */
    private Integer isSent;
    /**
     * 创建时间
     */
    private Date modelCreateDate;
    /**
     * 创建时间
     */
    private Date modelUpdateDate;

    /**
     *配置管理，这个字段不在车型主数据里而是在详情数据里{@link HzCfg0ModelDetail#pModelCfgMng}
     */
    private String management;
//瞎几把重写
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if(o instanceof  HzCfg0ModelRecord){
//            final HzCfg0ModelRecord that = (HzCfg0ModelRecord) o;
//            if(StringUtils.isNotBlank(this.objectName)){
//                return this.objectName.equals(that.getObjectName());
//            }else if(StringUtils.isNotBlank(puid)){
//                return this.puid.equals(that.getPuid());
//            }
//            return false;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(objectName);
//    }
}