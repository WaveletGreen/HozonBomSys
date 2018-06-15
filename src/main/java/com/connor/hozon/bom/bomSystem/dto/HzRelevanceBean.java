package com.connor.hozon.bom.bomSystem.dto;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/8 9:13
 */
public class HzRelevanceBean {
    /**
     * 序号
     */
    private Integer index;
    /**
     * 相关性
     */
    private String relevance;
    /***相关性描述*/
    private String relevanceDesc;
    /**
     * 相关性代码
     */
    private String relevanceCode;
    /**
     * puid，用于标识是哪个配置值
     */
    private String puid;
    /**
     * 用于标识是哪张table的
     */
    private String _table;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
    }

    public String getRelevanceDesc() {
        return relevanceDesc;
    }

    public void setRelevanceDesc(String relevanceDesc) {
        this.relevanceDesc = relevanceDesc;
    }

    public String getRelevanceCode() {
        return relevanceCode;
    }

    public void setRelevanceCode(String relevanceCode) {
        this.relevanceCode = relevanceCode;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String get_table() {
        return _table;
    }

    public void set_table(String _table) {
        this._table = _table;
    }
}
