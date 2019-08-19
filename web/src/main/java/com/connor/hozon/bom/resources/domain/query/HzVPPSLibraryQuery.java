/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.resources.domain.query;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/4
 * Time: 17:14
 */
public class HzVPPSLibraryQuery extends DefaultPageQuery {
    /**
     *VPPS层级
     */
    private String vppsLevel;
    /**
     *VSG代码
     */
    private String vsgCode;
    /**
     *VPPS代码
     */
    private String vppsCode;
    /**
     *UPC
     */
    private String upc;
    /**
     *FNA
     */
    private String fna;
    /**
     *标准零件编号
     */
    private String standardPartCode;

    public String getVppsLevel() {
        return vppsLevel;
    }

    public void setVppsLevel(String vppsLevel) {
        this.vppsLevel = vppsLevel;
    }

    public String getVsgCode() {
        return vsgCode;
    }

    public void setVsgCode(String vsgCode) {
        this.vsgCode = vsgCode;
    }

    public String getVppsCode() {
        return vppsCode;
    }

    public void setVppsCode(String vppsCode) {
        this.vppsCode = vppsCode;
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

    public String getStandardPartCode() {
        return standardPartCode;
    }

    public void setStandardPartCode(String standardPartCode) {
        this.standardPartCode = standardPartCode;
    }
}
