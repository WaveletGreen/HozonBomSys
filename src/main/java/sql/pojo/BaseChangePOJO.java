package sql.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/11/16
 * @Description: 变更需要的字段
 */
@Data
public abstract class BaseChangePOJO extends BasePOJO{
    private Long orderId;

    private Date effectTime;

    private String revision;

    private String tableName;

}
