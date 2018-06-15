package com.connor.hozon.bom.resources.dto.request;


public class AddProcessComposeReqDTO {

    private String parentUid;
    private String isDept;
    private String bomDigifaxId;
    private String lineIndex;
    private String linePuid;
    private String lineId;
    private Integer isHas;
    private Integer is2Y;
    private Integer isPart;
    private Integer orderNum;
    private String pBomOfWhichDept;
    private String projectPuid;
    private String pBomLinePartName;
    private String pBomLinePartClass;

    private String rank;

    /**
     * 数据版本
     */
    private String item_revision_id;
    /**
     *英文名称
     */
    private String h9_Dimension;
    /**
     *图号
     */
    private String h9_Drawingno;
    /**
     *料厚
     */
    private String h9_Thickness;
    /**
     *单位
     */
    private String h9_Gross_Unit;
    /**
     *材料1
     */
    private String h9_Repalced;
    /**
     *材料2
     */
    private String h9_Gross;
    /**
     *材料3
     */
    private String h9_Configure;
    /**
     *材料标准-材料牌号
     */
    private String h9_MatType;
    /**
     *对称
     */
    private String h9_Symmetrical_Part;
    /**
     *重要度
     */
    private String h9_I_Part;
    /**
     *是否法规件
     */
    private String h9_S_Part;
    /**
     *法规件型号
     */
    private String h9_Legal_Part;
    /**
     *黑白灰匣子件
     */
    private String h9_SupplyType;
    /**
     *开发类型
     */
    private String h9_DevelopType;
    /**
     *实际重量
     */
    private String h9_Actual_Weight;
    /**
     *扭矩
     */
    private String h9_Torque;
    /**
     *零件分类
     */
    private String h9_IsCommon;
    /**
     *备注
     */
    private String h9_Memo;
    /**
     *供应商
     */
    private String h9_SupplyInfor;
    /**
     *所有者
     */
    private String owning_user;
    /**
     *目标重量
     */
    private String h9_Target_Weight;
    /**
     *预估重量
     */
    private String h9_Estimate_Weight;
    /**
     *表面处理
     */
    private String h9_Surface_treatment;
    /**
     *色彩编号
     */
    private String h9_Model_numberPaint_colour;
    /**
     *制造工艺
     */
    private String h9_Manufacture_method;
    /**
     *紧固件规格
     */
    private String h9_Specification;
    /**
     *紧固件产品类别
     */
    private String h9_ProdcutType;
    /**
     *紧固件性能等级
     */
    private String h9_PerformanceLevel;


    /**
     * 来源（自制/采购）
     */
    private String resource;
    /**
     * 类型 是否为焊接/装配（0是  1不是 2不明确）
     */
    private Integer type;
    /**
     * 是否采购单元 （0是  1不是 2不明确）
     */
    private Integer buyUnit;
    /**
     *车间1
     */
    private String workShop1;
    /**
     *车间2
     */
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
    private Integer colorPart;
    /**
     * 外键id 对应EBOM表的id
     */
    private String puid;

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getIsDept() {
        return isDept;
    }

    public void setIsDept(String isDept) {
        this.isDept = isDept;
    }

    public String getBomDigifaxId() {
        return bomDigifaxId;
    }

    public void setBomDigifaxId(String bomDigifaxId) {
        this.bomDigifaxId = bomDigifaxId;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public String getLinePuid() {
        return linePuid;
    }

    public void setLinePuid(String linePuid) {
        this.linePuid = linePuid;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public Integer getIsHas() {
        return isHas;
    }

    public void setIsHas(Integer isHas) {
        this.isHas = isHas;
    }

    public Integer getIs2Y() {
        return is2Y;
    }

    public void setIs2Y(Integer is2Y) {
        this.is2Y = is2Y;
    }

    public Integer getIsPart() {
        return isPart;
    }

    public void setIsPart(Integer isPart) {
        this.isPart = isPart;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getProjectPuid() {
        return projectPuid;
    }

    public void setProjectPuid(String projectPuid) {
        this.projectPuid = projectPuid;
    }

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public String getItem_revision_id() {
        return item_revision_id;
    }

    public void setItem_revision_id(String item_revision_id) {
        this.item_revision_id = item_revision_id;
    }

    public String getH9_Dimension() {
        return h9_Dimension;
    }

    public void setH9_Dimension(String h9_Dimension) {
        this.h9_Dimension = h9_Dimension;
    }

    public String getH9_Drawingno() {
        return h9_Drawingno;
    }

    public void setH9_Drawingno(String h9_Drawingno) {
        this.h9_Drawingno = h9_Drawingno;
    }

    public String getH9_Thickness() {
        return h9_Thickness;
    }

    public void setH9_Thickness(String h9_Thickness) {
        this.h9_Thickness = h9_Thickness;
    }

    public String getH9_Gross_Unit() {
        return h9_Gross_Unit;
    }

    public void setH9_Gross_Unit(String h9_Gross_Unit) {
        this.h9_Gross_Unit = h9_Gross_Unit;
    }

    public String getH9_Repalced() {
        return h9_Repalced;
    }

    public void setH9_Repalced(String h9_Repalced) {
        this.h9_Repalced = h9_Repalced;
    }

    public String getH9_Gross() {
        return h9_Gross;
    }

    public void setH9_Gross(String h9_Gross) {
        this.h9_Gross = h9_Gross;
    }

    public String getH9_Configure() {
        return h9_Configure;
    }

    public void setH9_Configure(String h9_Configure) {
        this.h9_Configure = h9_Configure;
    }

    public String getH9_MatType() {
        return h9_MatType;
    }

    public void setH9_MatType(String h9_MatType) {
        this.h9_MatType = h9_MatType;
    }

    public String getH9_Symmetrical_Part() {
        return h9_Symmetrical_Part;
    }

    public void setH9_Symmetrical_Part(String h9_Symmetrical_Part) {
        this.h9_Symmetrical_Part = h9_Symmetrical_Part;
    }

    public String getH9_I_Part() {
        return h9_I_Part;
    }

    public void setH9_I_Part(String h9_I_Part) {
        this.h9_I_Part = h9_I_Part;
    }

    public String getH9_S_Part() {
        return h9_S_Part;
    }

    public void setH9_S_Part(String h9_S_Part) {
        this.h9_S_Part = h9_S_Part;
    }

    public String getH9_Legal_Part() {
        return h9_Legal_Part;
    }

    public void setH9_Legal_Part(String h9_Legal_Part) {
        this.h9_Legal_Part = h9_Legal_Part;
    }

    public String getH9_SupplyType() {
        return h9_SupplyType;
    }

    public void setH9_SupplyType(String h9_SupplyType) {
        this.h9_SupplyType = h9_SupplyType;
    }

    public String getH9_DevelopType() {
        return h9_DevelopType;
    }

    public void setH9_DevelopType(String h9_DevelopType) {
        this.h9_DevelopType = h9_DevelopType;
    }

    public String getH9_Actual_Weight() {
        return h9_Actual_Weight;
    }

    public void setH9_Actual_Weight(String h9_Actual_Weight) {
        this.h9_Actual_Weight = h9_Actual_Weight;
    }

    public String getH9_Torque() {
        return h9_Torque;
    }

    public void setH9_Torque(String h9_Torque) {
        this.h9_Torque = h9_Torque;
    }

    public String getH9_IsCommon() {
        return h9_IsCommon;
    }

    public void setH9_IsCommon(String h9_IsCommon) {
        this.h9_IsCommon = h9_IsCommon;
    }

    public String getH9_Memo() {
        return h9_Memo;
    }

    public void setH9_Memo(String h9_Memo) {
        this.h9_Memo = h9_Memo;
    }

    public String getH9_SupplyInfor() {
        return h9_SupplyInfor;
    }

    public void setH9_SupplyInfor(String h9_SupplyInfor) {
        this.h9_SupplyInfor = h9_SupplyInfor;
    }

    public String getOwning_user() {
        return owning_user;
    }

    public void setOwning_user(String owning_user) {
        this.owning_user = owning_user;
    }

    public String getH9_Target_Weight() {
        return h9_Target_Weight;
    }

    public void setH9_Target_Weight(String h9_Target_Weight) {
        this.h9_Target_Weight = h9_Target_Weight;
    }

    public String getH9_Estimate_Weight() {
        return h9_Estimate_Weight;
    }

    public void setH9_Estimate_Weight(String h9_Estimate_Weight) {
        this.h9_Estimate_Weight = h9_Estimate_Weight;
    }

    public String getH9_Surface_treatment() {
        return h9_Surface_treatment;
    }

    public void setH9_Surface_treatment(String h9_Surface_treatment) {
        this.h9_Surface_treatment = h9_Surface_treatment;
    }

    public String getH9_Model_numberPaint_colour() {
        return h9_Model_numberPaint_colour;
    }

    public void setH9_Model_numberPaint_colour(String h9_Model_numberPaint_colour) {
        this.h9_Model_numberPaint_colour = h9_Model_numberPaint_colour;
    }

    public String getH9_Manufacture_method() {
        return h9_Manufacture_method;
    }

    public void setH9_Manufacture_method(String h9_Manufacture_method) {
        this.h9_Manufacture_method = h9_Manufacture_method;
    }

    public String getH9_Specification() {
        return h9_Specification;
    }

    public void setH9_Specification(String h9_Specification) {
        this.h9_Specification = h9_Specification;
    }

    public String getH9_ProdcutType() {
        return h9_ProdcutType;
    }

    public void setH9_ProdcutType(String h9_ProdcutType) {
        this.h9_ProdcutType = h9_ProdcutType;
    }

    public String getH9_PerformanceLevel() {
        return h9_PerformanceLevel;
    }

    public void setH9_PerformanceLevel(String h9_PerformanceLevel) {
        this.h9_PerformanceLevel = h9_PerformanceLevel;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getBuyUnit() {
        return buyUnit;
    }

    public void setBuyUnit(Integer buyUnit) {
        this.buyUnit = buyUnit;
    }

    public String getWorkShop1() {
        return workShop1;
    }

    public void setWorkShop1(String workShop1) {
        this.workShop1 = workShop1;
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

    public Integer getColorPart() {
        return colorPart;
    }

    public void setColorPart(Integer colorPart) {
        this.colorPart = colorPart;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
