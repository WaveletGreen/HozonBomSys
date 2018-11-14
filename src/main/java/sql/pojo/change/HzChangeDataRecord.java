package sql.pojo.change;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/13
 * @Description: 变更表单数据记录
 */
@Data
public class HzChangeDataRecord {
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

}