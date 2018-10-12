package sql.pojo.cfg.vwo;

import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;

import java.util.Date;

/**
 * VWO主数据
 *
 */
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
    /**
     * 用户部门名
     */
    private String userDeptName;
    /**
     * 平台代码
     */
    private String platformCode;
    /**
     * 车型代码
     */
    private String projectCode;
    /**
     * 项目代码
     */
    private String vehicleCode;


    /**
     * 联系电话
     */
    private String contactPhoneNum;
    /**
     * 要求完成时间
     */
    private Date vwoDemandFinishTime;
    /**
     * 变更类型
     */
    private Integer vwoChangeType;
    /**
     * 费用承担部门
     */
    private String vwoCostBearingDept;
    /**
     * 上市类型
     */
    private Integer vwoListedType;
    /**
     * 关联的VWO号
     */
    private String vwoConnectedVwo;
    /**
     * 启动生效时间
     */
    private Date vwoStartEffectiveTime;
    /**
     * 启动生效时间,字符串显示形式
     */
    private String strVwoStartEffectiveTime;
    /**
     * 终止生效时间
     */
    private Date vwoEndEffectiveTime;
    /**
     * 终止生效时间,字符串显示形式
     */
    private String strVwoEndEffectiveTime;
    /**
     * 项目所属阶段
     */
    private String vwoProjectStage;
    /**
     * 关联工程师姓名
     */
    private String vwoConnectedUser;
    /**
     * 所属部门
     */
    private String vwoConnectedUserDept;
    /**
     * 关联零件号
     */
    private String vwoConnectedPart;
    /**
     * 关联零件名称
     */
    private String vwoConnectedPartName;
    /**
     * 原因描述
     */
    private String vwoChangeReason;


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

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getVehicleCode() {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode) {
        this.vehicleCode = vehicleCode;
    }

    public String getContactPhoneNum() {
        return contactPhoneNum;
    }

    public void setContactPhoneNum(String contactPhoneNum) {
        this.contactPhoneNum = contactPhoneNum;
    }

    public Date getVwoDemandFinishTime() {
        return vwoDemandFinishTime;
    }

    public void setVwoDemandFinishTime(Date vwoDemandFinishTime) {
        this.vwoDemandFinishTime = vwoDemandFinishTime;
    }

    public Integer getVwoChangeType() {
        return vwoChangeType;
    }

    public void setVwoChangeType(Integer vwoChangeType) {
        this.vwoChangeType = vwoChangeType;
    }

    public String getVwoCostBearingDept() {
        return vwoCostBearingDept;
    }

    public void setVwoCostBearingDept(String vwoCostBearingDept) {
        this.vwoCostBearingDept = vwoCostBearingDept;
    }

    public Integer getVwoListedType() {
        return vwoListedType;
    }

    public void setVwoListedType(Integer vwoListedType) {
        this.vwoListedType = vwoListedType;
    }

    public String getVwoConnectedVwo() {
        return vwoConnectedVwo;
    }

    public void setVwoConnectedVwo(String vwoConnectedVwo) {
        this.vwoConnectedVwo = vwoConnectedVwo;
    }

    public Date getVwoStartEffectiveTime() {
        return vwoStartEffectiveTime;
    }

    public void setVwoStartEffectiveTime(Date vwoStartEffectiveTime) {
        this.vwoStartEffectiveTime = vwoStartEffectiveTime;
        setStrVwoStartEffectiveTime(DateStringHelper.dateToString2(vwoStartEffectiveTime));
    }

    public Date getVwoEndEffectiveTime() {
        return vwoEndEffectiveTime;
    }

    public void setVwoEndEffectiveTime(Date vwoEndEffectiveTime) {
        this.vwoEndEffectiveTime = vwoEndEffectiveTime;
        setStrVwoEndEffectiveTime(DateStringHelper.dateToString2(vwoEndEffectiveTime));
    }

    public String getVwoProjectStage() {
        return vwoProjectStage;
    }

    public void setVwoProjectStage(String vwoProjectStage) {
        this.vwoProjectStage = vwoProjectStage;
    }

    public String getVwoConnectedUser() {
        return vwoConnectedUser;
    }

    public void setVwoConnectedUser(String vwoConnectedUser) {
        this.vwoConnectedUser = vwoConnectedUser;
    }

    public String getVwoConnectedUserDept() {
        return vwoConnectedUserDept;
    }

    public void setVwoConnectedUserDept(String vwoConnectedUserDept) {
        this.vwoConnectedUserDept = vwoConnectedUserDept;
    }

    public String getVwoConnectedPart() {
        return vwoConnectedPart;
    }

    public void setVwoConnectedPart(String vwoConnectedPart) {
        this.vwoConnectedPart = vwoConnectedPart;
    }

    public String getVwoConnectedPartName() {
        return vwoConnectedPartName;
    }

    public void setVwoConnectedPartName(String vwoConnectedPartName) {
        this.vwoConnectedPartName = vwoConnectedPartName;
    }

    public String getVwoChangeReason() {
        return vwoChangeReason;
    }

    public void setVwoChangeReason(String vwoChangeReason) {
        this.vwoChangeReason = vwoChangeReason;
    }

    public String getStrVwoStartEffectiveTime() {
        return strVwoStartEffectiveTime;
    }

    public void setStrVwoStartEffectiveTime(String strVwoStartEffectiveTime) {
        this.strVwoStartEffectiveTime = strVwoStartEffectiveTime;
    }

    public String getStrVwoEndEffectiveTime() {
        return strVwoEndEffectiveTime;
    }

    public void setStrVwoEndEffectiveTime(String strVwoEndEffectiveTime) {
        this.strVwoEndEffectiveTime = strVwoEndEffectiveTime;
    }
}