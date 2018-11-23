package sql.pojo.change;

import lombok.Data;

/**
 * @Author: haozt
 * @Date: 2018/11/22
 * @Description: TC端同步过来的零件号 与版本号
 */
@Data
public class HzChangeListRecord {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 表单号
     */
    private String formId;

    /**
     * 零件号
     */
    private String itemId;

    /**
     * 零件版本
     */
    private String itemRevision;
}
