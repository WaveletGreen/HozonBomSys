/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package sql.pojo.cfg.vwo;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: VWO影响部门
 * @Date: Created in 2018/10/10 13:29
 * @Modified By:
 */

public class HzVwoInfluenceDept {
    /**
     * 主键
     */
    private Long id;
    /**
     * 智能驾驶研究院，Intelligent driving Research Institute
     */
    private Integer vwoIdri;
    /**
     * 智能座舱事业部,Intelligent cockpit business unit
     */
    private Integer vwoIcbu;
    /**
     * 电动化底盘中心,
     * <p>
     * Motorized chassis Center
     */
    private Integer vwoMcc;
    /**
     * 电子电器中心
     * <p>
     * Electronic and electrical Center
     */
    private Integer vwoEec;
    /**
     * 内外饰中心,Interior decoration Center
     */
    private Integer vwoIdc;
    /**
     * 动总集成中心 Dynamic integration center
     */
    private Integer vwoDic;
    /**
     * 车身中心 Body center
     */
    private Integer vwoVbc;
    /**
     * VWO的ID，外键
     */
    private Long vwoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVwoIdri() {
        return vwoIdri;
    }

    public void setVwoIdri(Integer vwoIdri) {
        this.vwoIdri = vwoIdri;
    }

    public Integer getVwoIcbu() {
        return vwoIcbu;
    }

    public void setVwoIcbu(Integer vwoIcbu) {
        this.vwoIcbu = vwoIcbu;
    }

    public Integer getVwoMcc() {
        return vwoMcc;
    }

    public void setVwoMcc(Integer vwoMcc) {
        this.vwoMcc = vwoMcc;
    }

    public Integer getVwoEec() {
        return vwoEec;
    }

    public void setVwoEec(Integer vwoEec) {
        this.vwoEec = vwoEec;
    }

    public Integer getVwoIdc() {
        return vwoIdc;
    }

    public void setVwoIdc(Integer vwoIdc) {
        this.vwoIdc = vwoIdc;
    }

    public Integer getVwoDic() {
        return vwoDic;
    }

    public void setVwoDic(Integer vwoDic) {
        this.vwoDic = vwoDic;
    }

    public Integer getVwoVbc() {
        return vwoVbc;
    }

    public void setVwoVbc(Integer vwoVbc) {
        this.vwoVbc = vwoVbc;
    }

    public Long getVwoId() {
        return vwoId;
    }

    public void setVwoId(Long vwoId) {
        this.vwoId = vwoId;
    }
}