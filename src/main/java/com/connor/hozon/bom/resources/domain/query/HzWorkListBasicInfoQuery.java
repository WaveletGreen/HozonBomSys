package com.connor.hozon.bom.resources.domain.query;

public class HzWorkListBasicInfoQuery extends DefaultQuery{
    private Long id;//根据ID查找一条记录

    private String changeNum;

    private String projectId;//项目ID

    private int flag;//标识：1-待办事项，2-已处理事项，3-我的申请

    private String launcher;//发起人

    private String launchTimeStart;//发起日期的起始
    private String launchTimeEnd;//发起日期的结束

    //private String auditer;//接口人（审批人）


    public String getLauncher() {
        return launcher;
    }

    public void setLauncher(String launcher) {
        this.launcher = launcher;
    }

    public String getLaunchTimeStart() {
        return launchTimeStart;
    }

    public void setLaunchTimeStart(String launchTimeStart) {
        this.launchTimeStart = launchTimeStart;
    }

    public String getLaunchTimeEnd() {
        return launchTimeEnd;
    }

    public void setLaunchTimeEnd(String launchTimeEnd) {
        this.launchTimeEnd = launchTimeEnd;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(String changeNum) {
        this.changeNum = changeNum;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
