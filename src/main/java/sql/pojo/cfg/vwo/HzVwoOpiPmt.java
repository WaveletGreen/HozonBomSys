package sql.pojo.cfg.vwo;

import lombok.Data;

import java.util.Date;
@Data
public class HzVwoOpiPmt {
    /**
     * 主键
     */
    private Long id;
    /**
     * 主键
     */
    private Long opiVwoId;
    /**
     * 主键
     */
    private String opiPmtMngOpinion;
    /**
     * 主键
     */
    private Integer opiPmtMngAgreement;
    /**
     * 主键
     */
    private Date opiPmtMngOptionDate;
    /**
     * 主键
     */
    private String opiPmtMngComment;
    /**
     * 主键
     */
    private Date opiPmtMngCreateDate;
    /**
     * 主键
     */
    private Date opiPmtMngUpdateDate;
    /**
     * 主键
     */
    private String opiPmtMngCreator;
    /**
     * 主键
     */
    private String opiPmtMngUpdater;
    /**
     * 主键
     */
    private Integer opiPmtMngStage;

}