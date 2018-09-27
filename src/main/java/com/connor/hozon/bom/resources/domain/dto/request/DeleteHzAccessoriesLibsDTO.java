package com.connor.hozon.bom.resources.domain.dto.request;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/27
 * Time: 15:15
 */
public class DeleteHzAccessoriesLibsDTO {

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
