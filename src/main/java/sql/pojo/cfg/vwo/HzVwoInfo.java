package sql.pojo.cfg.vwo;

import java.util.Date;

public class HzVwoInfo {
    /**
     * 主键
     */
    private Long id;
    /**
     * VWO编号
     */
    private String vwoNum;
    /**
     * 流程发起者
     */
    private String vwoCreator;
    /**
     * 流程创建时间
     */
    private Date vwoCreateDate;
    /**
     * 流程结束人
     */
    private String vwoFinisher;
    /**
     * 流程结束时间
     */
    private Date vwoFinishDate;

    /**
     * 审批类型
     */
    private String approvalType;
    /**
     * 需求类型
     */
    private String demandType;
    /**
     * vwo名称
     */
    private String vwoName;
    /**
     * vwo状态
     */
    private Integer vwoStatus;
    /**
     *
     */
    private String vwoStatusName;
    /**
     * 预计实施时间
     */
    private Date vwoExpectExecuteTime;
    /**
     * 类型，对应的表
     */
    private Integer vwoType;


    public static String reflectToDBField(String code) {
        switch (code) {
            /**
             * 主键
             */
            case "id":

                return "id";
            /**
             * VWO编号
             */
            case "vwoNum":
                return "vwo_Num";
            /**
             * 流程创建时间
             */
            case "vwoCreateDate":
                return "vwo_Create_Date";
            default:
                return null;
        }
    }

    /**
     * 将状态码转为前端可看的显示值
     *
     * @param code
     * @return
     */
    public void reflectStatus(Integer code) {
        switch (code) {
            case 1:
                setVwoStatusName("DRAFF");
                break;
            case 2:
                setVwoStatusName("EDIT");
                break;
            case 3:
                setVwoStatusName("PROCESS");
                break;
            case 4:
                setVwoStatusName("IMPL");
                break;
            case 5:
                setVwoStatusName("INFO");
                break;
            default:
                setVwoStatusName(null);
                break;
        }
    }

    private String projectUid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVwoNum() {
        return vwoNum;
    }

    public void setVwoNum(String vwoNum) {
        this.vwoNum = vwoNum == null ? null : vwoNum.trim();
    }

    public String getVwoCreator() {
        return vwoCreator;
    }

    public void setVwoCreator(String vwoCreator) {
        this.vwoCreator = vwoCreator;
    }

    public Date getVwoCreateDate() {
        return vwoCreateDate;
    }

    public void setVwoCreateDate(Date vwoCreateDate) {
        this.vwoCreateDate = vwoCreateDate;
    }

    public String getVwoFinisher() {
        return vwoFinisher;
    }

    public void setVwoFinisher(String vwoFinisher) {
        this.vwoFinisher = vwoFinisher;
    }

    public Date getVwoFinishDate() {
        return vwoFinishDate;
    }

    public void setVwoFinishDate(Date vwoFinishDate) {
        this.vwoFinishDate = vwoFinishDate;
    }

    public String getProjectUid() {
        return projectUid;
    }

    public void setProjectUid(String projectUid) {
        this.projectUid = projectUid;
    }

    public String getApprovalType() {
        return approvalType;
    }

    public void setApprovalType(String approvalType) {
        this.approvalType = approvalType;
    }

    public String getDemandType() {
        return demandType;
    }

    public void setDemandType(String demandType) {
        this.demandType = demandType;
    }

    public String getVwoName() {
        return vwoName;
    }

    public void setVwoName(String vwoName) {
        this.vwoName = vwoName;
    }

    public Integer getVwoStatus() {
        return vwoStatus;
    }

    public void setVwoStatus(Integer vwoStatus) {
        this.vwoStatus = vwoStatus;
        reflectStatus(vwoStatus);
    }

    public Date getVwoExpectExecuteTime() {
        return vwoExpectExecuteTime;
    }

    public void setVwoExpectExecuteTime(Date vwoExpectExecuteTime) {
        this.vwoExpectExecuteTime = vwoExpectExecuteTime;
    }

    public Integer getVwoType() {
        return vwoType;
    }

    public void setVwoType(Integer vwoType) {
        this.vwoType = vwoType;
    }

    public String getVwoStatusName() {
        return vwoStatusName;
    }

    public void setVwoStatusName(String vwoStatusName) {
        this.vwoStatusName = vwoStatusName;
    }
}