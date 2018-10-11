package sql.pojo.project;

import com.connor.hozon.bom.bomSystem.service.project.IProject;

import java.util.Date;

/**
 * 品牌
 */
public class HzBrandRecord implements IProject{
    /**
     * puid
     */
    private String puid;
    /**
     * 品牌代号
     */
    private String pBrandCode;
    /**
     * 品牌名称
     */
    private String pBrandName;
    /**
     * 创建时间
     */
    private Date pBrandCreateDate;
    /**
     * 最后一次修改时间
     */
    private Date pBrandLastModDate;
    /**
     * 品牌备注
     */
    private String pBrandComment;
    /**
     * 最后修改人
     */
    private String pBrandLastModifier;

    public String getPuid() {
        return puid;
    }

    @Override
    public String getCode() {
        return this.pBrandCode;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpBrandCode() {
        return pBrandCode;
    }

    public void setpBrandCode(String pBrandCode) {
        this.pBrandCode = pBrandCode;
    }

    public String getpBrandName() {
        return pBrandName;
    }

    public void setpBrandName(String pBrandName) {
        this.pBrandName = pBrandName;
    }

    public Date getpBrandCreateDate() {
        return pBrandCreateDate;
    }

    public void setpBrandCreateDate(Date pBrandCreateDate) {
        this.pBrandCreateDate = pBrandCreateDate;
    }

    public Date getpBrandLastModDate() {
        return pBrandLastModDate;
    }

    public void setpBrandLastModDate(Date pBrandLastModDate) {
        this.pBrandLastModDate = pBrandLastModDate;
    }

    public String getpBrandComment() {
        return pBrandComment;
    }

    public void setpBrandComment(String pBrandComment) {
        this.pBrandComment = pBrandComment;
    }

    public String getpBrandLastModifier() {
        return pBrandLastModifier;
    }

    public void setpBrandLastModifier(String pBrandLastModifier) {
        this.pBrandLastModifier = pBrandLastModifier;
    }
}