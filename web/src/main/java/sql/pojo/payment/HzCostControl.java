package sql.pojo.payment;

import lombok.Data;
import sql.pojo.BaseDO;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2019/2/15
 * @Description: 成本管理
 */
@Data
public class HzCostControl  extends BaseDO {

    private static final long serialVersionUID = 5227126603702580840L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 合同完工状态
     */
    private String contractFinishDetail;
    /**
     * 付款比例
     */
    private String ratioPayment;
    /**
     * 一级模块
     */
    private String level1Module;
    /**
     * 二级模块
     */
    private String level2Module;
    /**
     * 需求部门
     */
    private String needsGroup;
    /**
     * 项目费用描述
     */
    private String projectFeeDesc;
    /**
     * 合同信息-供应商
     */
    private Integer contractSupply;
    /**
     * 合同签订时间
     */
    private Date contractSignDate;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 合同金额
     */
    private Double contractAmount;
    /**
     * 付款条款
     */
    private String contractPaymentTerms;
    /**
     * 已付金额
     */
    private Double paid;
    /**
     * 未付
     */
    private Double unpaid;
    /**
     * 备注
     */
    private String remark;

}
