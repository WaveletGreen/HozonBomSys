package sql.pojo.change;

import lombok.Data;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/13
 * @Description: 申请人申请变更历史记录
 */
@Data
public class HzApplicantChangeRecord {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 申请人id
     */
    private Long applicantId;
    /**
     * 申请时间
     */
    private Date applicantTime;
    /**
     * 变更表单id
     */
    private Long orderId;
    /**
     * 审核记录id
     */
    private Long auditRecordId;

    private String tableName;
}
