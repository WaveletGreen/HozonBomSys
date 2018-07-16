package com.connor.hozon.bom.resources.dto.response;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by haozt on 2018/06/06
 */
public class HzEbomRespDTO {

    private JSONArray jsonArray;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }
    /**不要吐槽为什么要这么写 实在没办法 一会这样的 一会那样的 代码都让改乱了*/
    /**把参数提出来 单独写实体类*/

    private String puid;

    /**
     * 层级
     */
    private String level;//1

    /**
     * 零件分类
     */
    private String pBomLinePartClass;

    /**
     * 零件来源
     */
    private String pBomLinePartResource;

    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;

    /**
     * 英文名称
     */
    private String pBomLinePartEnName;


	private String p3cPartFlag;
    private String inOutSideFlag;
    private String upc;
    private String fna ;
    private String fnaDesc;
    private String h9_Dimension;
    private String h9_Gross_Unit;
    private String h9_Drawingno;
    private String h9_Draw_size;
    private String h9_Thickness;
    private String h9_Repalced;
    private String h9_Gross;
    private String h9_Configure;
    private String h9_Density;
    private String h9_MatType;
    private String h9_Surface_treatment;
    private String h9_Model_numberPaint_colour;
    private String h9_Manufacture_method;
    private String h9_Symmetrical_Part;
    private String h9_I_Part;
    private String h9_S_Part;
    private String h9_Legal_Part;
    private String h9_SupplyType;
    private String h9_DevelopType;
    private String item_revision_id;
    private String  h9_Target_Weight;
    private String  h9_Estimate_Weight;
    private String h9_Actual_Weight;
    private String h9_Specification;
    private String h9_PerformanceLevel;
    private String h9_Torque;
    private String owning_user;
    private String h9_SupplyInfor;
    private String h9_Memo;
    private String fastener;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getpBomLinePartClass() {
        return pBomLinePartClass;
    }

    public void setpBomLinePartClass(String pBomLinePartClass) {
        this.pBomLinePartClass = pBomLinePartClass;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
    }

    public String getpBomLinePartName() {
        return pBomLinePartName;
    }

    public void setpBomLinePartName(String pBomLinePartName) {
        this.pBomLinePartName = pBomLinePartName;
    }

    public String getpBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setpBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }

    public String getP3cPartFlag() {
        return p3cPartFlag;
    }

    public void setP3cPartFlag(String p3cPartFlag) {
        this.p3cPartFlag = p3cPartFlag;
    }

    public String getInOutSideFlag() {
        return inOutSideFlag;
    }

    public void setInOutSideFlag(String inOutSideFlag) {
        this.inOutSideFlag = inOutSideFlag;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getFna() {
        return fna;
    }

    public void setFna(String fna) {
        this.fna = fna;
    }

    public String getFnaDesc() {
        return fnaDesc;
    }

    public void setFnaDesc(String fnaDesc) {
        this.fnaDesc = fnaDesc;
    }

    public String getH9_Dimension() {
        return h9_Dimension;
    }

    public void setH9_Dimension(String h9_Dimension) {
        this.h9_Dimension = h9_Dimension;
    }

    public String getH9_Gross_Unit() {
        return h9_Gross_Unit;
    }

    public void setH9_Gross_Unit(String h9_Gross_Unit) {
        this.h9_Gross_Unit = h9_Gross_Unit;
    }

    public String getH9_Drawingno() {
        return h9_Drawingno;
    }

    public void setH9_Drawingno(String h9_Drawingno) {
        this.h9_Drawingno = h9_Drawingno;
    }

    public String getH9_Draw_size() {
        return h9_Draw_size;
    }

    public void setH9_Draw_size(String h9_Draw_size) {
        this.h9_Draw_size = h9_Draw_size;
    }

    public String getH9_Thickness() {
        return h9_Thickness;
    }

    public void setH9_Thickness(String h9_Thickness) {
        this.h9_Thickness = h9_Thickness;
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

    public String getH9_Density() {
        return h9_Density;
    }

    public void setH9_Density(String h9_Density) {
        this.h9_Density = h9_Density;
    }

    public String getH9_MatType() {
        return h9_MatType;
    }

    public void setH9_MatType(String h9_MatType) {
        this.h9_MatType = h9_MatType;
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

    public String getItem_revision_id() {
        return item_revision_id;
    }

    public void setItem_revision_id(String item_revision_id) {
        this.item_revision_id = item_revision_id;
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

    public String getH9_Actual_Weight() {
        return h9_Actual_Weight;
    }

    public void setH9_Actual_Weight(String h9_Actual_Weight) {
        this.h9_Actual_Weight = h9_Actual_Weight;
    }

    public String getH9_Specification() {
        return h9_Specification;
    }

    public void setH9_Specification(String h9_Specification) {
        this.h9_Specification = h9_Specification;
    }

    public String getH9_PerformanceLevel() {
        return h9_PerformanceLevel;
    }

    public void setH9_PerformanceLevel(String h9_PerformanceLevel) {
        this.h9_PerformanceLevel = h9_PerformanceLevel;
    }

    public String getH9_Torque() {
        return h9_Torque;
    }

    public void setH9_Torque(String h9_Torque) {
        this.h9_Torque = h9_Torque;
    }

    public String getOwning_user() {
        return owning_user;
    }

    public void setOwning_user(String owning_user) {
        this.owning_user = owning_user;
    }

    public String getH9_SupplyInfor() {
        return h9_SupplyInfor;
    }

    public void setH9_SupplyInfor(String h9_SupplyInfor) {
        this.h9_SupplyInfor = h9_SupplyInfor;
    }

    public String getH9_Memo() {
        return h9_Memo;
    }

    public void setH9_Memo(String h9_Memo) {
        this.h9_Memo = h9_Memo;
    }

    public String getFastener() {
        return fastener;
    }

    public void setFastener(String fastener) {
        this.fastener = fastener;
    }
}
