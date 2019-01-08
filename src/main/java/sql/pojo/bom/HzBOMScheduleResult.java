package sql.pojo.bom;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2019/1/3
 * @Description: BOM定时器解算结果
 */
@Data
public class HzBOMScheduleResult implements Serializable {
    private static final long serialVersionUID = -4854148382122484783L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 变更表单id
     */
    private Long orderId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 解算结果
     */
    private String result;
}
