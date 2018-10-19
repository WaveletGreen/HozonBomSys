package sql.pojo.cfg.vwo;

import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import lombok.Data;

import java.util.Date;

/**
 * VWO主数据
 */
@Data
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
     * vwo名称，即标题
     */
    private String vwoName;
    /**
     * vwo状态，1：发起状态，可进行编辑，100：进入审核状态，等待人员审核/人员正在审核,，999：已发布；899：已终止
     */
    private Integer vwoStatus;
    /**
     * 状态名称
     */
    private String vwoStatusName;
    /**
     * 预计实施时间
     */
    private Date vwoExpectExecuteTime;
    /**
     * 类型，会有相应的数据库表对应
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
    /**
     *备注
     */
    private String vwoComment;

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
             * VWO名称
             */
            case "vwoName":
                return "vwo_Name";
            /**
             * 审批类型
             */
            case "approvalType":
                return "VWO_APPROVAL_TYPE";
            /**
             * vwo需求类型
             */
            case "demandType":
                return "VWO_DEMAND_TYPE";
            /**
             * vwo状态
             */
            case "vwoStatus":
                return "VWO_STATUS";
            /**
             * 预计实施时间
             */
            case "vwoExpectExecuteTime":
                return "VWO_EXPECT_EXECUTE_TIME";
            /**
             * vwo提出者
             */
            case "vwoCreator":
                return "VWO_CREATOR";
            /**
             * 提出时间
             */
            case "vwoCreateDate":
                return "VWO_CREATE_DATE";
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
            case 10:
                setVwoStatusName("编辑状态");
                break;
            //可能直接跳过该状态，直接进入100+
            case 100:
                setVwoStatusName("进入审核");
                break;
            case 101:
                setVwoStatusName("项目经理审核");
                break;
            case 102:
                setVwoStatusName("BOM经理审核");
                break;
            case 103:
                setVwoStatusName("专业PMT经理审核");
                break;
            case 899:
                setVwoStatusName("流程终止");
                break;
            case 999:
                setVwoStatusName("已发布");
                break;
            default:
                setVwoStatusName(null);
                break;
        }
    }

    private String projectUid;




    public void setVwoStatus(Integer vwoStatus) {
        this.vwoStatus = vwoStatus;
        reflectStatus(vwoStatus);
    }


    public void setVwoStartEffectiveTime(Date vwoStartEffectiveTime) {
        this.vwoStartEffectiveTime = vwoStartEffectiveTime;
        setStrVwoStartEffectiveTime(DateStringHelper.dateToString2(vwoStartEffectiveTime));
    }


    public void setVwoEndEffectiveTime(Date vwoEndEffectiveTime) {
        this.vwoEndEffectiveTime = vwoEndEffectiveTime;
        setStrVwoEndEffectiveTime(DateStringHelper.dateToString2(vwoEndEffectiveTime));
    }

}