package sql.pojo;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/16
 * @Description: 变更需要的字段
 */
public abstract class BaseChangeDO extends BaseDO {
    /**
     * 变更表单id
     */
    private Long orderId;

    /**
     * 生效时间
     */
    private Date effectTime;
    /**
     * 版本
     */
    private String revision;

    /**
     * 对应操作的表名
     */
    private String tableName;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
