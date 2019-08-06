/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.bom.bom;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: haozt
 * @Date: 2018/8/23
 * @Description: excel导入EBOM专用
 */
public class HzImportEbomRecord  implements Serializable {

    private static final long serialVersionUID = -6559274579058619219L;
    private Integer No;

    private String puid;

    private String parentUid;

    private String level;

    private String lineIndex;

//    private String linePuid;
    /**
     * 树的高度（深度）
     */
    private int high;

    /**
     * 排序号
     */
    private String sortNum;

    private String bomDigifaxId;

    private String lineId;

    private Integer isHas;

    /**
     * 是否2Y层
     */
    private Integer is2Y;
    /**
     * 是否零件
     */
//    private Integer isPart;

    private String pBomOfWhichDept;


    /**
     * Bom行对应的零件名
     */
    private String pBomLinePartName;
    /**
     * Bom行对应的零件类别
     */
    private String pBomLinePartClass;

    private String pBomLinePartEnName;

    private String pBomLinePartResource;

    private String pFnaInfo;

//    private String pFastener;
//
//    private Integer p3cpartFlag;
//
//    private Integer pInOutSideFlag;

    private String pUpc;

    private String pFnaDesc;

    private String pCreateName;

    private String pUpdateName;
//
//    private String pUnit;
//
//    private String pPictureNo;
//
//    private String pPictureSheet;
//
//    private String pMaterialHigh;
//    /**
//     * 材料1
//     */
//    private String pMaterial1;
//    /**
//     * 材料2
//     */
//    private String pMaterial2;
//    /**
//     * 材料3
//     */
//    private String pMaterial3;
//    /**
//     * 密度
//     */
//    private String pDensity;
//    /**
//     * 材料标准
//     */
//    private String pMaterialStandard;
//    /**
//     * 表面处理
//     */
//    private String pSurfaceTreat;
//    /**
//     * 纹理编号
//     */
//    private String pTextureColorNum;
//
//    /**
//     * 制造工艺
//     */
//    private String pManuProcess;
//    /**
//     *  对称
//     */
//    private String pSymmetry;
//    /**
//     * 重要度
//     */
//    private String pImportance;
//    /**
//     * 法规件标识
//     */
//    private Integer pRegulationFlag;
//    /**
//     * 黑白匣子类型
//     */
//    private String pBwgBoxPart;
//    /**
//     * 开发类型
//     */
//    private String pDevelopType;
//    /**
//     * 数据版本
//     */
//    private String pDataVersion;
//    /**
//     * 实际重量
//     */
//    private String pTargetWeight;
//    /**
//     * 预估重量
//     */
//    private String pFeatureWeight;
//    /**
//     * 实际重量
//     */
//    private String pActualWeight;
//    /**
//     * 紧固件标准
//     */
//    private String pFastenerStandard;
//    /**
//     * 紧固件等级
//     */
//    private String pFastenerLevel;
//    /**
//     * 转矩
//     */
//    private String pTorque;
//    /**
//     * 责任工程师
//     */
//    private String pDutyEngineer;
//    /**
//     * 供应商
//     */
//    private String pSupply;
//    /**
//     * 供应商代码
//     */
//    private String pSupplyCode;
//    /**
//     * 备注
//     */
//    private String pRemark;
//    /**
//     * 法规件代码
//     */
//    private String pRegulationCode;

    /**
     * 数量
     */
    private String number;
    /**
     * 采购工程师
     */
//    private String pBuyEngineer;

    private Integer colorPart;

    private Long eplId;

    private Integer status;

    /**
     * 备件
     */
    private String sparePart;
    /**
     * 备件编号
     */
    private String sparePartNum;

    public String getSparePart() {
        return sparePart;
    }

    public void setSparePart(String sparePart) {
        this.sparePart = sparePart;
    }

    public String getSparePartNum() {
        return sparePartNum;
    }

    public void setSparePartNum(String sparePartNum) {
        this.sparePartNum = sparePartNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getColorPart() {
        return colorPart;
    }

    public void setColorPart(Integer colorPart) {
        this.colorPart = colorPart;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        if(o instanceof HzImportEbomRecord){
            HzImportEbomRecord record = (HzImportEbomRecord) o;
            return this.getPuid().equals(record.getPuid());
        }
        return false;
    }

    @Override
    public int hashCode() {

        return Objects.hash( puid);
    }


    public Integer getNo() {
        return No;
    }

    public void setNo(Integer no) {
        No = no;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getParentUid() {
        return parentUid;
    }

    public void setParentUid(String parentUid) {
        this.parentUid = parentUid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(String lineIndex) {
        this.lineIndex = lineIndex;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public String getSortNum() {
        return sortNum;
    }

    public void setSortNum(String sortNum) {
        this.sortNum = sortNum;
    }

    public String getBomDigifaxId() {
        return bomDigifaxId;
    }

    public void setBomDigifaxId(String bomDigifaxId) {
        this.bomDigifaxId = bomDigifaxId;
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

    public String getpFnaInfo() {
        return pFnaInfo;
    }

    public void setpFnaInfo(String pFnaInfo) {
        this.pFnaInfo = pFnaInfo;
    }

    public String getpUpc() {
        return pUpc;
    }

    public void setpUpc(String pUpc) {
        this.pUpc = pUpc;
    }

    public String getpFnaDesc() {
        return pFnaDesc;
    }

    public void setpFnaDesc(String pFnaDesc) {
        this.pFnaDesc = pFnaDesc;
    }

    public String getpCreateName() {
        return pCreateName;
    }

    public void setpCreateName(String pCreateName) {
        this.pCreateName = pCreateName;
    }

    public String getpUpdateName() {
        return pUpdateName;
    }

    public void setpUpdateName(String pUpdateName) {
        this.pUpdateName = pUpdateName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getEplId() {
        return eplId;
    }

    public void setEplId(Long eplId) {
        this.eplId = eplId;
    }

    public String getpBomLinePartResource() {
        return pBomLinePartResource;
    }

    public void setpBomLinePartResource(String pBomLinePartResource) {
        this.pBomLinePartResource = pBomLinePartResource;
    }


    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
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

    public String getpBomLinePartEnName() {
        return pBomLinePartEnName;
    }

    public void setpBomLinePartEnName(String pBomLinePartEnName) {
        this.pBomLinePartEnName = pBomLinePartEnName;
    }
}
