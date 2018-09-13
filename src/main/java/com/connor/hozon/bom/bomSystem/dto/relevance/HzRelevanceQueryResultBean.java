package com.connor.hozon.bom.bomSystem.dto.relevance;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/9/7 15:16
 * @Modified By:
 */
public class HzRelevanceQueryResultBean {
    /**
     * 主键
     */
    private Long id;
    /**
     * 序号
     */
    private Integer index;
    /**
     * 相关性
     */
    private String relevance;
    /**
     * 相关性描述
     */
    private String relevanceDesc;
    /**
     * 相关性代码
     */
    private String relevanceCode;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
