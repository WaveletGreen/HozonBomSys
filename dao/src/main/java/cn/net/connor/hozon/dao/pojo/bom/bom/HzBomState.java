/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.bom.bom;

/**
 * @Author: haozt
 * @Date: 2018/6/14
 * 废除 不在使用
 */
@Deprecated
public class HzBomState {

    private String puid;

    /**
     * bom id
     */
    private String pBomId;
    /**
     * bom状态 0 新增 1 修改 2 删除
     */
    private Integer pBomState;

    /**
     * 当前bom在流程中的状态 0待审核 1 审核中 2已通过 3未通过
     */
    private Integer pBomFlowState;

    /**
     * bom所在的流程id
     */
    private String pBomFlowId;

    /**
     * 当前bom所处于流程节点名称
     */
    private String pBomFlowPointName;


    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpBomId() {
        return pBomId;
    }

    public void setpBomId(String pBomId) {
        this.pBomId = pBomId;
    }

    public Integer getpBomState() {
        return pBomState;
    }

    public void setpBomState(Integer pBomState) {
        this.pBomState = pBomState;
    }

    public Integer getpBomFlowState() {
        return pBomFlowState;
    }

    public void setpBomFlowState(Integer pBomFlowState) {
        this.pBomFlowState = pBomFlowState;
    }

    public String getpBomFlowId() {
        return pBomFlowId;
    }

    public void setpBomFlowId(String pBomFlowId) {
        this.pBomFlowId = pBomFlowId;
    }

    public String getpBomFlowPointName() {
        return pBomFlowPointName;
    }

    public void setpBomFlowPointName(String pBomFlowPointName) {
        this.pBomFlowPointName = pBomFlowPointName;
    }

}
