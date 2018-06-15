package com.connor.hozon.bom.resources.dto.response;

/**
 * Created by haozt on 2018/06/08
 */
public class HzPbomComposeRespDTO {

    /**
     * 父id
     */
    private String parentPuid;
    /**
     * 自身id
     */
    private String puid;

    /**
     * 零件号
     */
    private String lineId;
    /**
     * 层级
     */
    private String level;

    /**
     * 中文名称
     */
    private String nameZh;

    /**
     * 是否有子层
     */
    private boolean hasChildren;

    private String workShop2;
    /**
     *生产线
     */
    private String productLine;
    /**
     * 模具类型
     */
    private String mouldType;
    /**
     * 外委件
     */
    private String outerPart;
    /**
     * 颜色件
     */
    private String colorPart;

    public String getParentPuid() {
        return parentPuid;
    }

    public void setParentPuid(String parentPuid) {
        this.parentPuid = parentPuid;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public String getWorkShop2() {
        return workShop2;
    }

    public void setWorkShop2(String workShop2) {
        this.workShop2 = workShop2;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getMouldType() {
        return mouldType;
    }

    public void setMouldType(String mouldType) {
        this.mouldType = mouldType;
    }

    public String getOuterPart() {
        return outerPart;
    }

    public void setOuterPart(String outerPart) {
        this.outerPart = outerPart;
    }

    public String getColorPart() {
        return colorPart;
    }

    public void setColorPart(String colorPart) {
        this.colorPart = colorPart;
    }
}
