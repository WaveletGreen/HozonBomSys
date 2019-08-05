/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.derivative;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: x
 * @Date: Created in 2018/9/4 15:57
 * @Modified By:
 */
public class HzComposeDelDto {
    /**
     * 基本信息ID
     */
    private Long basicId;
    /**
     * 配置物料模型UID
     */
    private String puidOfModelFeature;
    /**
     * 基本车型UID
     */
    private String puid;
    /**
     * 基本信息
     */
    private String modeBasicDetail;
    /**
     * 基本信息描述
     */
    private String modeBasicDetailDesc;

    public Long getBasicId() {
        return basicId;
    }

    public void setBasicId(Long basicId) {
        this.basicId = basicId;
    }

    public String getPuidOfModelFeature() {
        return puidOfModelFeature;
    }

    public void setPuidOfModelFeature(String puidOfModelFeature) {
        this.puidOfModelFeature = puidOfModelFeature;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getModeBasicDetail() {
        return modeBasicDetail;
    }

    public void setModeBasicDetail(String modeBasicDetail) {
        this.modeBasicDetail = modeBasicDetail;
    }

    public String getModeBasicDetailDesc() {
        return modeBasicDetailDesc;
    }

    public void setModeBasicDetailDesc(String modeBasicDetailDesc) {
        this.modeBasicDetailDesc = modeBasicDetailDesc;
    }
}
