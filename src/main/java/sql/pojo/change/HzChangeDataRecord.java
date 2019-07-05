package sql.pojo.change;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/13
 * @Description: 变更表单数据记录
 */
@Data
public class HzChangeDataRecord implements Serializable {
    private static final long serialVersionUID = -4106849599610139955L;
    /**
     * 主键id
     */
    private Long id;
    /**
     *数据来源id
     */
    private String puid;
    /**
     *申请人id
     */
    private Long applicantId;
    /**
     *审核人id
     */
    private Long auditorId;
    /**
     *变更表单id
     */
    private Long orderId;
    /**
     *数据库表名
     */
    private String tableName;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 申请人
     */
    private String applicant;

    /**
     * 申请时间
     */
    private Date applicantTime;
}