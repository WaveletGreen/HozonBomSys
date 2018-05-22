package com.connor.hozon.bom.resources.dto.request;

/**
 * Created by haozt on 2018/5/22
 */
public class FindHzFeatureConfigReqDTO {
    /**
     * 主配置的puid，用这个可以找到主配置的对象
     */
    private String pCfg0MainItemPuid;

    public String getpCfg0MainItemPuid() {
        return pCfg0MainItemPuid;
    }

    public void setpCfg0MainItemPuid(String pCfg0MainItemPuid) {
        this.pCfg0MainItemPuid = pCfg0MainItemPuid;
    }
}
