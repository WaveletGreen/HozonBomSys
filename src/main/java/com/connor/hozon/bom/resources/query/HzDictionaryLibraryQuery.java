package com.connor.hozon.bom.resources.query;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/30
 * Time: 20:13
 */
public class HzDictionaryLibraryQuery extends DefaultPageQuery{
    /**
     *特征组代码
     */
    private String groupCode;
    /**
     *特征组中文
     */
    private String groupCh;
    /**
     *特征族代码
     */
    private String famillyCode;
    /**
     *特征族中文
     */
    private String famillyCh;
    /**
     *特征值
     */
    private String eigenValue;
    /**
     *特征值描述中文
     */
    private String valueDescCh;

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
}
