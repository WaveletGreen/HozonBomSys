package sql.pojo.cfg.vwo;

import lombok.Data;

import java.util.Date;

@Data
public class HzVwoOpiBom {
    /**
     * 主键
     */
    private Long id;
    /**
     * vwo号
     */
    private Long opiVwoId;
    /**
     * BOM经理意见
     */
    private String opiBomMngOpinion;
    /**
     * BOM经理抉择
     */
    private Integer opiBomMngAgreement;
    /**
     * BOM经理抉择日期
     */
    private Date opiBomMngOptionDate;
    /**
     * BOM经理备注
     */
    private String opiBomMngComment;
    /**
     * 创建时间
     */
    private Date opiBomMngCreateDate;
    /**
     * 修改时间
     */
    private Date opiBomMngUpdateDate;
    /**
     * 主键opiBomMngCmcrCreator
     */
    private String opiBomMngCreator;
    /**
     * 主键
     */
    private String opiBomMngUpdater;
    /**
     * 主键
     */
    private Integer opiBomMngStage;

}