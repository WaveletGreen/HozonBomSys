package com.connor.hozon.bom.resources.domain.model;

import com.connor.hozon.bom.common.base.entity.QueryBase;

import java.math.BigDecimal;
import java.util.Date;

public class HzChosenSupplier  {
    private Long id;

    private Date csCreateDate;

    private String csCreator;

    private Date csUpdateDate;

    private String csUpdater;

    private String itemId;

    private String itemName;

    private BigDecimal eachCarQuantity;

    private String chosenSupplier;

    private String parts;

    private BigDecimal singleCarPrice;

    private BigDecimal moldsCostNotRevenue;

    private BigDecimal moldsCostHasRevenue;

    private String moldsCostDetails;

    private BigDecimal gaugeCost;

    private BigDecimal exploitationCost;

    private String aSamplePiece;

    private String bSamplePiece;

    private String cSamplePiece;

    private String csLowerCostingoPlan;

    private String remark;

    private String specialty;

    private String projectPuid;


    private String sort;
    /** 排序方式: desc \ asc */
    private String order = "";
    /** 获取一页行数 */
    private String limit;
    /** 获取的页码 */
    private Integer page;
    /** 起始记录 */
    private Integer offset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCsCreateDate() {
        return csCreateDate;
    }

    public void setCsCreateDate(Date csCreateDate) {
        this.csCreateDate = csCreateDate;
    }

    public String getCsCreator() {
        return csCreator;
    }

    public void setCsCreator(String csCreator) {
        this.csCreator = csCreator == null ? null : csCreator.trim();
    }

    public Date getCsUpdateDate() {
        return csUpdateDate;
    }

    public void setCsUpdateDate(Date csUpdateDate) {
        this.csUpdateDate = csUpdateDate;
    }

    public String getCsUpdater() {
        return csUpdater;
    }

    public void setCsUpdater(String csUpdater) {
        this.csUpdater = csUpdater == null ? null : csUpdater.trim();
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public BigDecimal getEachCarQuantity() {
        return eachCarQuantity;
    }

    public void setEachCarQuantity(BigDecimal eachCarQuantity) {
        this.eachCarQuantity = eachCarQuantity;
    }

    public String getChosenSupplier() {
        return chosenSupplier;
    }

    public void setChosenSupplier(String chosenSupplier) {
        this.chosenSupplier = chosenSupplier == null ? null : chosenSupplier.trim();
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts == null ? null : parts.trim();
    }

    public BigDecimal getSingleCarPrice() {
        return singleCarPrice;
    }

    public void setSingleCarPrice(BigDecimal singleCarPrice) {
        this.singleCarPrice = singleCarPrice;
    }

    public BigDecimal getMoldsCostNotRevenue() {
        return moldsCostNotRevenue;
    }

    public void setMoldsCostNotRevenue(BigDecimal moldsCostNotRevenue) {
        this.moldsCostNotRevenue = moldsCostNotRevenue;
    }

    public BigDecimal getMoldsCostHasRevenue() {
        return moldsCostHasRevenue;
    }

    public void setMoldsCostHasRevenue(BigDecimal moldsCostHasRevenue) {
        this.moldsCostHasRevenue = moldsCostHasRevenue;
    }

    public String getMoldsCostDetails() {
        return moldsCostDetails;
    }

    public void setMoldsCostDetails(String moldsCostDetails) {
        this.moldsCostDetails = moldsCostDetails == null ? null : moldsCostDetails.trim();
    }

    public BigDecimal getGaugeCost() {
        return gaugeCost;
    }

    public void setGaugeCost(BigDecimal gaugeCost) {
        this.gaugeCost = gaugeCost;
    }

    public BigDecimal getExploitationCost() {
        return exploitationCost;
    }

    public void setExploitationCost(BigDecimal exploitationCost) {
        this.exploitationCost = exploitationCost;
    }

    public String getaSamplePiece() {
        return aSamplePiece;
    }

    public void setaSamplePiece(String aSamplePiece) {
        this.aSamplePiece = aSamplePiece == null ? null : aSamplePiece.trim();
    }

    public String getbSamplePiece() {
        return bSamplePiece;
    }

    public void setbSamplePiece(String bSamplePiece) {
        this.bSamplePiece = bSamplePiece == null ? null : bSamplePiece.trim();
    }

    public String getcSamplePiece() {
        return cSamplePiece;
    }

    public void setcSamplePiece(String cSamplePiece) {
        this.cSamplePiece = cSamplePiece == null ? null : cSamplePiece.trim();
    }

    public String getCsLowerCostingoPlan() {
        return csLowerCostingoPlan;
    }

    public void setCsLowerCostingoPlan(String csLowerCostingoPlan) {
        this.csLowerCostingoPlan = csLowerCostingoPlan == null ? null : csLowerCostingoPlan.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    public String getProjectPuid() {
        return projectPuid;
    }

    public void setProjectPuid(String projectPuid) {
        this.projectPuid = projectPuid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}