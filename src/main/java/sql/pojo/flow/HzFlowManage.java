package sql.pojo.flow;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/6/14
 * @Description:流程管理
 */
public class HzFlowManage {
    private String puid;

    /**
     * 流程名字
     */
    private String pFlowName;

    /**
     * 流程创建者
     */
    private String pFlowCreator;

    /**
     * 流程创建时间
     */
    private Date pFlowCreateTime;

    /**
     * 流程更新时间
     */
    private Date pFlowUpdateTime;

    /**
     * 流程更改者
     */
    private String pFlowUpdater;

    /**
     * 项目id
     */
    private String pProjectId;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpFlowName() {
        return pFlowName;
    }

    public void setpFlowName(String pFlowName) {
        this.pFlowName = pFlowName;
    }

    public String getpFlowCreator() {
        return pFlowCreator;
    }

    public void setpFlowCreator(String pFlowCreator) {
        this.pFlowCreator = pFlowCreator;
    }

    public Date getpFlowCreateTime() {
        return pFlowCreateTime;
    }

    public void setpFlowCreateTime(Date pFlowCreateTime) {
        this.pFlowCreateTime = pFlowCreateTime;
    }

    public Date getpFlowUpdateTime() {
        return pFlowUpdateTime;
    }

    public void setpFlowUpdateTime(Date pFlowUpdateTime) {
        this.pFlowUpdateTime = pFlowUpdateTime;
    }

    public String getpFlowUpdater() {
        return pFlowUpdater;
    }

    public void setpFlowUpdater(String pFlowUpdater) {
        this.pFlowUpdater = pFlowUpdater;
    }

    public String getpProjectId() {
        return pProjectId;
    }

    public void setpProjectId(String pProjectId) {
        this.pProjectId = pProjectId;
    }
}
