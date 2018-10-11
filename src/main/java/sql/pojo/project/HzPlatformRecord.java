package sql.pojo.project;

import com.connor.hozon.bom.bomSystem.service.project.IProject;

import java.util.Date;

/**
 * 平台
 */
public class HzPlatformRecord implements IProject{
    /**
     * puid
     */
    private String puid;
    /**
     * 归属品牌
     */
    private String pPertainToBrandPuid;
    /**
     * 平台代号
     */
    private String pPlatformCode;
    /**
     * 平台名称
     */
    private String pPlatformName;
    /**
     * 创建时间
     */
    private Date pPlatformCreateDate;
    /**
     * 最后修改时间
     */
    private Date pPlatformLastModDate;
    /**
     * 备注
     */
    private String pPlatformComment;
    /**
     * 最后一次修改人
     */
    private String pPlatformLastModifier;

    public String getPuid() {
        return puid;
    }

    @Override
    public String getCode() {
        return this.pPlatformCode;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpPertainToBrandPuid() {
        return pPertainToBrandPuid;
    }

    public void setpPertainToBrandPuid(String pPertainToBrandPuid) {
        this.pPertainToBrandPuid = pPertainToBrandPuid == null ? null : pPertainToBrandPuid.trim();
    }

    public String getpPlatformCode() {
        return pPlatformCode;
    }

    public void setpPlatformCode(String pPlatformCode) {
        this.pPlatformCode = pPlatformCode;
    }

    public String getpPlatformName() {
        return pPlatformName;
    }

    public void setpPlatformName(String pPlatformName) {
        this.pPlatformName = pPlatformName;
    }

    public Date getpPlatformCreateDate() {
        return pPlatformCreateDate;
    }

    public void setpPlatformCreateDate(Date pPlatformCreateDate) {
        this.pPlatformCreateDate = pPlatformCreateDate;
    }

    public Date getpPlatformLastModDate() {
        return pPlatformLastModDate;
    }

    public void setpPlatformLastModDate(Date pPlatformLastModDate) {
        this.pPlatformLastModDate = pPlatformLastModDate;
    }

    public String getPPlatformComment() {
        return pPlatformComment;
    }

    public void setPPlatformComment(String pPlatformComment) {
        this.pPlatformComment = pPlatformComment;
    }

    public String getpPlatformLastModifier() {
        return pPlatformLastModifier;
    }

    public void setpPlatformLastModifier(String pPlatformLastModifier) {
        this.pPlatformLastModifier = pPlatformLastModifier;
    }
}