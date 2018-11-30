package sql.pojo.change;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/13
 * @Description: 审核人审核变更表单历史记录
 */
@Data
public class HzAuditorChangeRecord {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 审核人
     */
    private Long auditorId;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 审核结果（1 通过 0退回）
     */
    private Integer auditResult;
    /**
     * 审核意见
     */
    private String auditSugg;
    /**
     * 变更表单id
     */
    private Long orderId;
    /**
     * 数据库表名
     */
    private String tableName;
    /**
     * 是否为变更接口人,TC端同步的表单 需要通知到接口人
     * 1:TC同步获取，0：BOM端发起
     */
    private Integer changeAccepter;
}