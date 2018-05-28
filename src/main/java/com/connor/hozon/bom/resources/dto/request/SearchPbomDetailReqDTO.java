package com.connor.hozon.bom.resources.dto.request;

/**
 * Created by haozt on 2018/5/28
 * 搜索功能 入参
 */
public class SearchPbomDetailReqDTO {
    /**
     * 层级
     */
    private String level;
    /**
     *专业
     */
    private String pBomOfWhichDept;
    /**
     * 零件号
     */
    private String lineId;
    /**
     * 名称
     */
    private String name;
    /**
     * 级别
     */
    private String rank;
    /**
     * 分组号
     */
    private String groupNum;

    /**
     * 零件分类
     */
    private String  itemType;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getpBomOfWhichDept() {
        return pBomOfWhichDept;
    }

    public void setpBomOfWhichDept(String pBomOfWhichDept) {
        this.pBomOfWhichDept = pBomOfWhichDept;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
