package sql.pojo.cfg.derivative;

import cn.net.connor.hozon.dao.pojo.configuration.derivative.HzDerivativeMaterielBasic;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class HzDMBasicChangeBean {

    /**
     * 主键
     */
    private Long id;
    /**
     * 变更表单主键
     */
    private Long formId;
    /**
     * 源数据ID
     */
    private Long dmbSrcId;
    /**
     * 基础模型外键
     */
    private String dmbModelUid;
    /**
     * 颜色车型外键
     */
    private String dmbColorModelUid;
    /**
     * 源数据创建者
     */
    private String dmbCreator;
    /**
     * 源数据创建日期
     */
    private Date dmbCreateDate;
    /**
     * 源数据更新者
     */
    private String dmbUpdater;
    /**
     * 源数据更新日期
     */
    private Date dmbUpdateDate;
    /**
     * 创建者
     */
    private String changeCreator;
    /**
     * 创建日期
     */
    private Date changeCreateDate;
    /**
     * 更新者
     */
    private String changeUpdater;
    /**
     * 更新日期
     */
    private Date changeUpdateDate;
    /**
     * 项目UID
     */
    private String dmbProjectUid;

    /**
     * 预留字段2
     */
    private String dmbReserved2;
    /**
     * 预留字段3
     */
    private String dmbReserved3;
    /**
     * 预留字段4
     */
    private String dmbReserved4;
    /**
     * 预留字段5
     */
    private String dmbReserved5;

    /**
     * 衍生物料的UID
     */
    private String dmbModelFeatureUid;

    /**
     * 特殊特性UID，特别是“车身颜色”
     */
    private String dmbSpecialFeatureUid;
    /**
     * 变更状态，0为流程中，1为生效
     */
    private Integer dmbChangeStatus;

    /**
     * 源数据状态
     */
    private Integer dmbSrcStatus;

    public void srcSetChange(HzDerivativeMaterielBasic hzDerivativeMaterielBasic){
        Date date = new Date();
        User user = UserInfo.getUser();
        this.setDmbSrcId(hzDerivativeMaterielBasic.getId());
        this.setDmbModelUid(hzDerivativeMaterielBasic.getDmbModelUid());
        this.setDmbColorModelUid(hzDerivativeMaterielBasic.getDmbColorModelUid());
        this.setDmbCreator(hzDerivativeMaterielBasic.getDmbCreator());
        this.setDmbCreateDate(hzDerivativeMaterielBasic.getDmbCreateDate());
        this.setDmbUpdater(hzDerivativeMaterielBasic.getDmbUpdater());
        this.setDmbUpdateDate(hzDerivativeMaterielBasic.getDmbUpdateDate());
        this.setChangeCreator(user.getLogin());
        this.setChangeCreateDate(date);
        this.setChangeUpdater(user.getLogin());
        this.setChangeUpdateDate(date);
        this.setDmbProjectUid(hzDerivativeMaterielBasic.getDmbProjectUid());
        this.setDmbReserved2(hzDerivativeMaterielBasic.getDmbReserved2());
        this.setDmbReserved3(hzDerivativeMaterielBasic.getDmbReserved3());
        this.setDmbReserved4(hzDerivativeMaterielBasic.getDmbReserved4());
        this.setDmbReserved5(hzDerivativeMaterielBasic.getDmbReserved5());
        this.setDmbModelFeatureUid(hzDerivativeMaterielBasic.getDmbModelFeatureUid());
        this.setDmbSpecialFeatureUid(hzDerivativeMaterielBasic.getDmbSpecialFeatureUid());
        this.setDmbSrcStatus(hzDerivativeMaterielBasic.getDmbStatus());
    }

    public HzDerivativeMaterielBasic getHzDerivativeMaterielBasic() {
        HzDerivativeMaterielBasic hzDerivativeMaterielBasic = new HzDerivativeMaterielBasic();
        hzDerivativeMaterielBasic.setId(this.getDmbSrcId());
        hzDerivativeMaterielBasic.setDmbModelUid(this.getDmbModelUid());
        hzDerivativeMaterielBasic.setDmbColorModelUid(this.getDmbColorModelUid());
        hzDerivativeMaterielBasic.setDmbSpecialFeatureUid(this.getDmbSpecialFeatureUid());
        hzDerivativeMaterielBasic.setDmbModelFeatureUid(this.getDmbModelFeatureUid());
        return hzDerivativeMaterielBasic;
    }
}
