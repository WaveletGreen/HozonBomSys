package cn.net.connor.hozon.dao.pojo.workList;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的工作列表全字段
 */
@Data
public class HzWorkListRecord implements Serializable {
    private static final long serialVersionUID = -8481871072615198264L;
    /**
     * 主键
     */
    private Long id;
    /**
     *变更流程单号
     */
    private String changeNum;
    /**
     *发起人
     */
    private String launcher;
    /**
     *发起人工号
     */
    private String launcherId;
    /**
     *发起人部门
     */
    private String launcherDep;
    /**
     *发起人联系方式
     */
    private String launcherContact;
    /**
     *接口人（审批人）
     */
    private String auditer;
    /**
     *接口人工号
     */
    private String auditerId;
    /**
     *发起时间
     */
    private Date launchTime;
    /**
     *更新时间
     */
    private Date updateTime;
    /**
     *状态
     */
    private Integer status;
    /**
     *变更类型
     */
    private String changeType;
    /**
     *是否有关联变更
     */
    private String isRelevanceChange;
    /**
     *上市类型
     */
    private String marketType;
    /**
     *项目所属阶段
     */
    private String projectStage;
    /**
     *备注
     */
    private String note;
    /**
     * 项目ID
     */
    private String projectId;
    private String reserve2;
    private String reserve3;
    private String reserve4;
    private String reserve5;
    private String reserve6;
    private String reserve7;
    private String reserve8;
    private String reserve9;
    private String reserve10;


}
