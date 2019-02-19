package sql.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: haozt
 * @Date: 2018/10/19
 * @Description: 基本属性 数据库建表有该字段时 可直接继承
 */
public abstract class BaseDO implements Serializable,Cloneable {


    private static final long serialVersionUID = -5896598481134328123L;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 更改人
     */
    private String updateName;

    /**
     * 创建人ID
     */
    private Long createId;

    /**
     * 更新人ID
     */
    private  Long updateId;



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    @Override
    public Object  clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

}
