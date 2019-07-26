package sql.pojo.flow;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/6/14
 * @Description: 流程节点信息
 */
@Deprecated
public class HzFlowPointInfo {
    private String puid;
    /**
     * 流程id
     */
    private String pFlowId;

    /**
     * 流程节点名字
     */
    private String pPointName;

    /**
     * 流程责任人
     */
    private String pPointDuty;

    /**
     * 流程节点开始时间
     */
    private Date pPointStartTime;

    /**
     * 流程节点结束时间
     */
    private Date pPointEndTime;

    /**
     * 流程节点状态 0待审核 1已通过 2 未通过
     */
    private Integer pState;

    /**
     * 是否为第一个节点(1 是 0 否)
     */
    private Integer pIsFirstPoint;

    /**
     * 是否为最后一个节点（1 是 0 否）
     */
    private Integer pIsLastPoint;

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpFlowId() {
        return pFlowId;
    }

    public void setpFlowId(String pFlowId) {
        this.pFlowId = pFlowId;
    }

    public String getpPointName() {
        return pPointName;
    }

    public void setpPointName(String pPointName) {
        this.pPointName = pPointName;
    }

    public String getpPointDuty() {
        return pPointDuty;
    }

    public void setpPointDuty(String pPointDuty) {
        this.pPointDuty = pPointDuty;
    }

    public Date getpPointStartTime() {
        return pPointStartTime;
    }

    public void setpPointStartTime(Date pPointStartTime) {
        this.pPointStartTime = pPointStartTime;
    }

    public Date getpPointEndTime() {
        return pPointEndTime;
    }

    public void setpPointEndTime(Date pPointEndTime) {
        this.pPointEndTime = pPointEndTime;
    }

    public Integer getpState() {
        return pState;
    }

    public void setpState(Integer pState) {
        this.pState = pState;
    }

    public Integer getpIsFirstPoint() {
        return pIsFirstPoint;
    }

    public void setpIsFirstPoint(Integer pIsFirstPoint) {
        this.pIsFirstPoint = pIsFirstPoint;
    }

    public Integer getpIsLastPoint() {
        return pIsLastPoint;
    }

    public void setpIsLastPoint(Integer pIsLastPoint) {
        this.pIsLastPoint = pIsLastPoint;
    }
}
