package sql.pojo.cfg.vwo;

import lombok.Data;

import java.util.Date;
@Data
public class HzVwoOpiProj {
    private Long id;

    private Long opiVwoId;

    private String opiProjMngOpinion;

    private Integer opiProjMngAgreement;

    private Date opiProjMngOptionDate;

    private String opiProjMngComment;

    private Date opiProjMngCreateDate;

    private Date opiProjMngUpdateDate;

    private String opiProjMngCreator;

    private String opiProjMngUpdater;

    private Integer opiProjMngStage;

    public Date getOpiProjMngOptionDate() {
        return opiProjMngOptionDate;
    }
}