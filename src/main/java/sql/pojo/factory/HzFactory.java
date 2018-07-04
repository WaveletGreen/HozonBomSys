package sql.pojo.factory;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/6/29
 * @Description: 加工工厂
 */
public class HzFactory {
    private String puid;
    /**
     * 工厂代码
     */
    private String pFactoryCode;

    /**
     * 工厂描述
     */
    private String pFactoryDesc;

    /**
     * 创建时间
     */
    private Date pCreateTime;
    /**
     * 更新时间
     */
    private Date pUpdateTime;

    /**
     * 创建人
     */
    private String pCreateName;

    /**
     * 更改人
     */
    private String pUpdateName;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpFactoryCode() {
        return pFactoryCode;
    }

    public void setpFactoryCode(String pFactoryCode) {
        this.pFactoryCode = pFactoryCode;
    }

    public String getpFactoryDesc() {
        return pFactoryDesc;
    }

    public void setpFactoryDesc(String pFactoryDesc) {
        this.pFactoryDesc = pFactoryDesc;
    }

    public Date getpCreateTime() {
        return pCreateTime;
    }

    public void setpCreateTime(Date pCreateTime) {
        this.pCreateTime = pCreateTime;
    }

    public Date getpUpdateTime() {
        return pUpdateTime;
    }

    public void setpUpdateTime(Date pUpdateTime) {
        this.pUpdateTime = pUpdateTime;
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
}
