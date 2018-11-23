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
     * 变更流程表单的变更号（change_no）或TC里的item_id
     */
    private String formId;
    /**
     * TC里的流程变更单引用的对象ID
     */
    private String itemId;
    /**
     * TC里的流程变更单引用的对象版本
     */
    private String itemRevision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemRevision() {
        return itemRevision;
    }

    public void setItemRevision(String itemRevision) {
        this.itemRevision = itemRevision;
    }
}
