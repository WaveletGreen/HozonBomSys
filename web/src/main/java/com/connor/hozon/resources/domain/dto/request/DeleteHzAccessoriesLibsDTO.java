package com.connor.hozon.resources.domain.dto.request;

import cn.net.connor.hozon.common.entity.BaseDTO;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/27
 * Time: 15:15
 */
public class DeleteHzAccessoriesLibsDTO extends BaseDTO {

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
