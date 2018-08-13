package sql.pojo.cfg.vwo;

import java.util.Date;

public class HzVwoInfo {
    /**
     * 主键
     */
    private Long id;
    /**
     * VWO编号
     */
    private String vwoNum;
    /**
     * 流程发起者
     */
    private String vwoCreator;
    /**
     * 流程创建时间
     */
    private Date vwoCreateDate;
    /**
     * 流程结束人
     */
    private String vwoFinisher;
    /**
     * 流程结束时间
     */
    private Date vwoFinishDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVwoNum() {
        return vwoNum;
    }

    public void setVwoNum(String vwoNum) {
        this.vwoNum = vwoNum == null ? null : vwoNum.trim();
    }

    public String getVwoCreator() {
        return vwoCreator;
    }

    public void setVwoCreator(String vwoCreator) {
        this.vwoCreator = vwoCreator;
    }

    public Date getVwoCreateDate() {
        return vwoCreateDate;
    }

    public void setVwoCreateDate(Date vwoCreateDate) {
        this.vwoCreateDate = vwoCreateDate;
    }

    public String getVwoFinisher() {
        return vwoFinisher;
    }

    public void setVwoFinisher(String vwoFinisher) {
        this.vwoFinisher = vwoFinisher;
    }

    public Date getVwoFinishDate() {
        return vwoFinishDate;
    }

    public void setVwoFinishDate(Date vwoFinishDate) {
        this.vwoFinishDate = vwoFinishDate;
    }
}