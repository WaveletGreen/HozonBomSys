package sql.pojo.cfg.modelColor;

import lombok.Data;

import java.util.Date;

@Deprecated
@Data
public class HzCmcrDetailBeforeChange {
    private Long cmcrDetailCgId;

    private Long cmcrDetailCgVwoId;

    private String cmcrDetailCgFeatureCode;

    private String cmcrDetailCgFeatureName;

    private String cmcrDetailCgColorCode;

    private String cmcrDetailCgColorName;

    private Date cmcrDetailCgCreateDate;

    private String cmcrDetailCgCreator;

    private Date cmcrDetailCgUpdateDate;

    private String cmcrDetailCgUpdater;

    private String cmcrDetailCgReverse1;

    private String cmcrDetailCgReverse2;

    private String cmcrDetailCgReverse3;

    private String cmcrDetailCgReverse4;

    private String cmcrDetailCgReverse5;

    private String cmcrDetailSrcPuid;

    private String cmcrDetailSrcCfgUid;

    private String cmcrDetailSrcColorUid;

    private String cmcrDetailSrcCfgMainUid;

    private Date cmcrDetailSrcCreateDate;

    private Date cmcrDetailSrcModifyDate;

    private String cmcrDetailSrcCreator;

    private String cmcrDetailSrcModifier;

    private String cmcrDetailSrcModelPuid;

}