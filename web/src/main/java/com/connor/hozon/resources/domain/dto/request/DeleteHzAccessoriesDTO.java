package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;

/**
 * @Author: haozt
 * @Date: 2018/7/16
 * @Description:
 */
public class DeleteHzAccessoriesDTO extends BaseDTO {
    /**
     * 要删除的puid
     */
    private String puids;

    private String puid;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getPuids() {
        return puids;
    }

    public void setPuids(String puids) {
        this.puids = puids;
    }
}
