package com.connor.hozon.bom.resources.domain.dto.request;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/30
 * Time: 15:16
 */
public class AddHzDictionaryLibraryReqDTO {
    /**
     * 主键puid
     */
    private String puid;
    /**
     * 专业户中文
     */
    private String professionCh;
    /**
     *专业户英文
     */
    private String professionEn;
    /**
     *分类中文
     */
    private String classificationCh;
    /**
     *分类英文
     */
    private String classificationEn;
    /**
     *特征组代码
     */
    private String groupCode;
    /**
     *特征组中文
     */
    private String groupCh;
    /**
     *特征组英文
     */
    private String groupEn;
    /**
     *特征族代码
     */
    private String famillyCode;
    /**
     *特征族中文
     */
    private String famillyCh;
    /**
     *特征族英文
     */
    private String famillyEn;
    /**
     *特征值
     */
    private String eigenValue;
    /**
     *特征值描述中文
     */
    private String valueDescCh;
    /**
     *特征值描述英文
     */
    private String valueDescEn;
    /**
     *类型
     */
    private String type;
    /**
     *特征值来源
     */
    private String valueSource;
    /**
     *生效时间
     */
    private Date effectTime;
    /**
     *失效时间
     */
    private Date failureTime;
    /**
     *备注
     */
    private String note;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getProfessionCh() {
        return professionCh;
    }

    public void setProfessionCh(String professionCh) {
        this.professionCh = professionCh;
    }

    public String getProfessionEn() {
        return professionEn;
    }

    public void setProfessionEn(String professionEn) {
        this.professionEn = professionEn;
    }

    public String getClassificationCh() {
        return classificationCh;
    }

    public void setClassificationCh(String classificationCh) {
        this.classificationCh = classificationCh;
    }

    public String getClassificationEn() {
        return classificationEn;
    }

    public void setClassificationEn(String classificationEn) {
        this.classificationEn = classificationEn;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCh() {
        return groupCh;
    }

    public void setGroupCh(String groupCh) {
        this.groupCh = groupCh;
    }

    public String getGroupEn() {
        return groupEn;
    }

    public void setGroupEn(String groupEn) {
        this.groupEn = groupEn;
    }

    public String getFamillyCode() {
        return famillyCode;
    }

    public void setFamillyCode(String famillyCode) {
        this.famillyCode = famillyCode;
    }

    public String getFamillyCh() {
        return famillyCh;
    }

    public void setFamillyCh(String famillyCh) {
        this.famillyCh = famillyCh;
    }

    public String getFamillyEn() {
        return famillyEn;
    }

    public void setFamillyEn(String famillyEn) {
        this.famillyEn = famillyEn;
    }

    public String getEigenValue() {
        return eigenValue;
    }

    public void setEigenValue(String eigenValue) {
        this.eigenValue = eigenValue;
    }

    public String getValueDescCh() {
        return valueDescCh;
    }

    public void setValueDescCh(String valueDescCh) {
        this.valueDescCh = valueDescCh;
    }

    public String getValueDescEn() {
        return valueDescEn;
    }

    public void setValueDescEn(String valueDescEn) {
        this.valueDescEn = valueDescEn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueSource() {
        return valueSource;
    }

    public void setValueSource(String valueSource) {
        this.valueSource = valueSource;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public Date getFailureTime() {
        return failureTime;
    }

    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
