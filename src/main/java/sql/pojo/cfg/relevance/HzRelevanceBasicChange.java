package sql.pojo.cfg.relevance;

import java.math.BigDecimal;
import java.util.Date;

public class HzRelevanceBasicChange {
    private Long id;

    private String rbColorCode;

    private String rbColorUid;

    private String rbFeatureCode;

    private String rbFeatureUid;

    private String rbFeatureValueCode;

    private String rbFeatureValueUid;

    private String rbRelevance;

    private String rbRelevanceDesc;

    private String rbRelevanceCode;

    private Integer relevanceStatus;

    private Date relevanceCreateDate;

    private String relevanceCreator;

    private Date relevanceUpdateDate;

    private String relevanceUpdater;

    private String rbReserved1;

    private String rbReserved2;

    private String rbReserved3;

    private String rbReserved4;

    private String rbReserved5;

    private String rbReserved6;

    private String rbReserved7;

    private String rbReserved8;

    private String rbReserved9;

    private String rbReserved10;

    private String rbReserved11;

    private String rbReserved12;

    private String rbReserved13;

    private String rbReserved14;

    private String rbReserved15;

    private Long changeOrderId;

    private String rbProjectUid;

    private Integer isSent;

    private Long srcId;

    private Integer changeStatus;

    private Integer changeVersion;

    public HzRelevanceBasicChange(){

    }

    public HzRelevanceBasicChange(HzRelevanceBasic hzRelevanceBasic) {
        this.rbColorCode = hzRelevanceBasic.getRbColorCode();
        this.rbColorUid = hzRelevanceBasic.getRbColorUid();
        this.rbFeatureCode = hzRelevanceBasic.getRbFeatureCode();
        this.rbFeatureUid = hzRelevanceBasic.getRbFeatureUid();
        this.rbFeatureValueCode = hzRelevanceBasic.getRbFeatureValueCode();
        this.rbFeatureValueUid = hzRelevanceBasic.getRbFeatureValueUid();
        this.rbRelevance = hzRelevanceBasic.getRbRelevance();
        this.rbRelevanceDesc = hzRelevanceBasic.getRbRelevanceDesc();
        this.rbRelevanceCode = hzRelevanceBasic.getRbRelevanceCode();
        this.relevanceStatus = hzRelevanceBasic.getRelevanceStatus();
        this.relevanceCreateDate = hzRelevanceBasic.getRelevanceCreateDate();
        this.relevanceCreator = hzRelevanceBasic.getRelevanceCreator();
        this.relevanceUpdateDate = hzRelevanceBasic.getRelevanceUpdateDate();
        this.relevanceUpdater = hzRelevanceBasic.getRelevanceUpdater();
        this.rbReserved1 = hzRelevanceBasic.getRbReserved1();
        this.rbReserved2 = hzRelevanceBasic.getRbReserved2();
        this.rbReserved3 = hzRelevanceBasic.getRbReserved3();
        this.rbReserved4 = hzRelevanceBasic.getRbReserved4();
        this.rbReserved5 = hzRelevanceBasic.getRbReserved5();
        this.rbReserved6 = hzRelevanceBasic.getRbReserved6();
        this.rbReserved7 = hzRelevanceBasic.getRbReserved7();
        this.rbReserved8 = hzRelevanceBasic.getRbReserved8();
        this.rbReserved9 = hzRelevanceBasic.getRbReserved9();
        this.rbReserved10 = hzRelevanceBasic.getRbReserved10();
        this.rbReserved11 = hzRelevanceBasic.getRbReserved11();
        this.rbReserved12 = hzRelevanceBasic.getRbReserved12();
        this.rbReserved13 = hzRelevanceBasic.getRbReserved13();
        this.rbReserved14 = hzRelevanceBasic.getRbReserved14();
        this.rbReserved15 = hzRelevanceBasic.getRbReserved15();
        this.changeOrderId = hzRelevanceBasic.getRbVwoId();
        this.rbProjectUid = hzRelevanceBasic.getRbProjectUid();
        this.isSent = hzRelevanceBasic.getIsSent();
        this.srcId = hzRelevanceBasic.getId();
        this.changeStatus = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRbColorCode() {
        return rbColorCode;
    }

    public void setRbColorCode(String rbColorCode) {
        this.rbColorCode = rbColorCode;
    }

    public String getRbColorUid() {
        return rbColorUid;
    }

    public void setRbColorUid(String rbColorUid) {
        this.rbColorUid = rbColorUid;
    }

    public String getRbFeatureCode() {
        return rbFeatureCode;
    }

    public void setRbFeatureCode(String rbFeatureCode) {
        this.rbFeatureCode = rbFeatureCode;
    }

    public String getRbFeatureUid() {
        return rbFeatureUid;
    }

    public void setRbFeatureUid(String rbFeatureUid) {
        this.rbFeatureUid = rbFeatureUid;
    }

    public String getRbFeatureValueCode() {
        return rbFeatureValueCode;
    }

    public void setRbFeatureValueCode(String rbFeatureValueCode) {
        this.rbFeatureValueCode = rbFeatureValueCode;
    }

    public String getRbFeatureValueUid() {
        return rbFeatureValueUid;
    }

    public void setRbFeatureValueUid(String rbFeatureValueUid) {
        this.rbFeatureValueUid = rbFeatureValueUid;
    }

    public String getRbRelevance() {
        return rbRelevance;
    }

    public void setRbRelevance(String rbRelevance) {
        this.rbRelevance = rbRelevance;
    }

    public String getRbRelevanceDesc() {
        return rbRelevanceDesc;
    }

    public void setRbRelevanceDesc(String rbRelevanceDesc) {
        this.rbRelevanceDesc = rbRelevanceDesc;
    }

    public String getRbRelevanceCode() {
        return rbRelevanceCode;
    }

    public void setRbRelevanceCode(String rbRelevanceCode) {
        this.rbRelevanceCode = rbRelevanceCode;
    }

    public Integer getRelevanceStatus() {
        return relevanceStatus;
    }

    public void setRelevanceStatus(Integer relevanceStatus) {
        this.relevanceStatus = relevanceStatus;
    }

    public Date getRelevanceCreateDate() {
        return relevanceCreateDate;
    }

    public void setRelevanceCreateDate(Date relevanceCreateDate) {
        this.relevanceCreateDate = relevanceCreateDate;
    }

    public String getRelevanceCreator() {
        return relevanceCreator;
    }

    public void setRelevanceCreator(String relevanceCreator) {
        this.relevanceCreator = relevanceCreator;
    }

    public Date getRelevanceUpdateDate() {
        return relevanceUpdateDate;
    }

    public void setRelevanceUpdateDate(Date relevanceUpdateDate) {
        this.relevanceUpdateDate = relevanceUpdateDate;
    }

    public String getRelevanceUpdater() {
        return relevanceUpdater;
    }

    public void setRelevanceUpdater(String relevanceUpdater) {
        this.relevanceUpdater = relevanceUpdater;
    }

    public String getRbReserved1() {
        return rbReserved1;
    }

    public void setRbReserved1(String rbReserved1) {
        this.rbReserved1 = rbReserved1;
    }

    public String getRbReserved2() {
        return rbReserved2;
    }

    public void setRbReserved2(String rbReserved2) {
        this.rbReserved2 = rbReserved2;
    }

    public String getRbReserved3() {
        return rbReserved3;
    }

    public void setRbReserved3(String rbReserved3) {
        this.rbReserved3 = rbReserved3;
    }

    public String getRbReserved4() {
        return rbReserved4;
    }

    public void setRbReserved4(String rbReserved4) {
        this.rbReserved4 = rbReserved4;
    }

    public String getRbReserved5() {
        return rbReserved5;
    }

    public void setRbReserved5(String rbReserved5) {
        this.rbReserved5 = rbReserved5;
    }

    public String getRbReserved6() {
        return rbReserved6;
    }

    public void setRbReserved6(String rbReserved6) {
        this.rbReserved6 = rbReserved6;
    }

    public String getRbReserved7() {
        return rbReserved7;
    }

    public void setRbReserved7(String rbReserved7) {
        this.rbReserved7 = rbReserved7;
    }

    public String getRbReserved8() {
        return rbReserved8;
    }

    public void setRbReserved8(String rbReserved8) {
        this.rbReserved8 = rbReserved8;
    }

    public String getRbReserved9() {
        return rbReserved9;
    }

    public void setRbReserved9(String rbReserved9) {
        this.rbReserved9 = rbReserved9;
    }

    public String getRbReserved10() {
        return rbReserved10;
    }

    public void setRbReserved10(String rbReserved10) {
        this.rbReserved10 = rbReserved10;
    }

    public String getRbReserved11() {
        return rbReserved11;
    }

    public void setRbReserved11(String rbReserved11) {
        this.rbReserved11 = rbReserved11;
    }

    public String getRbReserved12() {
        return rbReserved12;
    }

    public void setRbReserved12(String rbReserved12) {
        this.rbReserved12 = rbReserved12;
    }

    public String getRbReserved13() {
        return rbReserved13;
    }

    public void setRbReserved13(String rbReserved13) {
        this.rbReserved13 = rbReserved13;
    }

    public String getRbReserved14() {
        return rbReserved14;
    }

    public void setRbReserved14(String rbReserved14) {
        this.rbReserved14 = rbReserved14;
    }

    public String getRbReserved15() {
        return rbReserved15;
    }

    public void setRbReserved15(String rbReserved15) {
        this.rbReserved15 = rbReserved15;
    }

    public Long getChangeOrderId() {
        return changeOrderId;
    }

    public void setChangeOrderId(Long changeOrderId) {
        this.changeOrderId = changeOrderId;
    }

    public String getRbProjectUid() {
        return rbProjectUid;
    }

    public void setRbProjectUid(String rbProjectUid) {
        this.rbProjectUid = rbProjectUid;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    public Long getSrcId() {
        return srcId;
    }

    public void setSrcId(Long srcId) {
        this.srcId = srcId;
    }

    public Integer getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(Integer changeStatus) {
        this.changeStatus = changeStatus;
    }

    public Integer getChangeVersion() {
        return changeVersion;
    }

    public void setChangeVersion(Integer changeVersion) {
        this.changeVersion = changeVersion;
    }
}