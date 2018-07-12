package integration.logic;

import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import integration.option.ActionFlagOption;
import integration.option.MRPControlOption;
import integration.option.MaterialOption;
import integration.option.PurchaseOption;
import integration.service.impl.masterMaterial1.TransMasterMaterialService;
import sql.pojo.project.HzMaterielRecord;

/**
 * 映射物料主数据
 */
public class ReflectMateriel {
    private MasterMaterial mm;
    /**
     * 工厂需要单独设置
     */
    private String factory;
    /**
     * 物料类型
     */
    private String materielType;
    /**
     * 采购类型
     */
    private String purchaseType;
    /**
     * 删除标识符
     */
    private String deleteFlag;
    /**
     * 公告号
     */
    private String announcement;

    /**
     * MRP控制者
     */
    private String MRPController;

    /**
     * 设置动作描述代码
     */
    private ActionFlagOption actionFlagOption;

    /**
     * 包号
     */
    private String packNo;
    /**
     * 行号，包号的前6位
     */
    private String lineNum;

    /**
     * 映射到中间bean
     *
     * @param hzMaterielRecord
     */
    public ReflectMateriel(HzMaterielRecord hzMaterielRecord) {
        mm = new MasterMaterial();

        //物料编码
        mm.setMaterialCode(hzMaterielRecord.getpMaterielCode());
        //物料中文描述
        mm.setMaterialName(hzMaterielRecord.getpMaterielDesc());
        //物料描述 英文
        mm.setMaterialEnglishName(hzMaterielRecord.getpMaterielDescEn() == null ? "" : hzMaterielRecord.getpMaterielDescEn());
        //基本计量单位
        mm.setUnit(hzMaterielRecord.getpBasicUnitMeasure() == null ? "EA" : hzMaterielRecord.getpBasicUnitMeasure());
        //虚拟件标识
        mm.setVertureFlag(hzMaterielRecord.getpInventedPart() == null ? "" : hzMaterielRecord.getpInventedPart().toString());
        //VIN前置号
        mm.setPerVIN(hzMaterielRecord.getpVinPerNo() == null ? "" : hzMaterielRecord.getpVinPerNo());
        //颜色件标识
        mm.setColorFlag(hzMaterielRecord.getpColorPart() == null ? "" : hzMaterielRecord.getpColorPart().toString());
        //毛重
        mm.setGrossWeight(hzMaterielRecord.getpHeight() == null ? "" : hzMaterielRecord.getpHeight());
        //内外饰标识
        mm.setInAndOutFlag(hzMaterielRecord.getpInOutSideFlag() == null ? "" : hzMaterielRecord.getpInOutSideFlag().toString());
        //3C件标识
        mm.setThreeCFlag(hzMaterielRecord.getP3cPartFlag() == null ? "" : hzMaterielRecord.getP3cPartFlag().toString());
        //零件重要度
        mm.setPartImportance(hzMaterielRecord.getpPartImportantDegree() == null ? "" : hzMaterielRecord.getpPartImportantDegree());
        //散件标志
        mm.setBulkFlag(hzMaterielRecord.getpLoosePartFlag() == null ? "" : hzMaterielRecord.getpLoosePartFlag().toString());
        //设置物料类型，默认是材料
        mm.setMaterialType(
                (hzMaterielRecord.getpMaterielType() == null ||
                        "".equalsIgnoreCase(hzMaterielRecord.getpMaterielType())) ?
                        MaterialOption.MATERIAL_ORG.GetDesc() : hzMaterielRecord.getpMaterielType());
        //备件+原材料双属性标识，只要有双属性标识，物料类型就是X
        if (hzMaterielRecord.getpSpareMaterial() != null) {
            mm.setDoubleAttribute("X");
            mm.setMaterialType("X");
        }
    }

    /**
     * 设置动作标识，新建传ADD;更新传"";删除传DELETE,删除会同步传""和删除标识D
     *
     * @param actionFlagOption
     */
    public void setActionFlagOption(ActionFlagOption actionFlagOption) {
        this.actionFlagOption = actionFlagOption;
        if (actionFlagOption == ActionFlagOption.DELETE) {
            //同步设置删除标志
            this.deleteFlag = "D";
            mm.setDelFlag(ActionFlagOption.DELETE.GetDesc());
            mm.setActionFlag(ActionFlagOption.UPDATE_EMPTY);
        } else
            mm.setActionFlag(actionFlagOption);
    }

    public MasterMaterial getMm() {
        return mm;
    }

    /**
     * 设置工厂
     *
     * @param factory
     */
    public void setFactory(String factory) {
        this.factory = factory;
        mm.setFactory(factory);
    }

    /**
     * 设置包号
     */
    public void setPackNo(String packNo) {
        this.packNo = packNo;
        mm.setGUID(packNo);
    }

    /**
     * 设置行号
     */
    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
        mm.setLineNum(lineNum);
    }

    /**
     * 设置MRP控制者，公告号和采购类型
     */
    public void setMRPAndPurchase(String mrp, String purchase, String announcement) {
        //默认时采购件
        if (mrp == null) {
            mrp = "Z10";
        }
        this.MRPController = mrp;
        //设置采购类型
        switch (mrp) {
            //整车
            case "Z02":
                //虚拟件
            case "Z04":
                //冲压
            case "Z07":
                //自制备件
            case "Z08":
                //采购件
            case "Z10":
                if (PurchaseOption.PURCHASE_X.GetDesc().equalsIgnoreCase(purchase) ||
                        "".equalsIgnoreCase(purchase) ||
                        null == purchase) {
                    //如果乱填了X，则默认时采购
                    mm.setPurchaseType(PurchaseOption.PURCHASE_BUY);
                }
                //采购
                else if (PurchaseOption.PURCHASE_BUY.GetDesc().equalsIgnoreCase(purchase)) {
                    mm.setPurchaseType(PurchaseOption.PURCHASE_BUY);
                }
                //自制
                else {
                    mm.setPurchaseType(PurchaseOption.PURCHASE_MAKE);
                }
                break;
            //总成/分总成，对应的采购类型才有X
            case "Z06":
                //设置采购类型
                mm.setPurchaseType(purchase);
                //设置公告，只有MRP=整车（Z02）才有公告，否则留空或不传
                if (announcement == null) {
                    mm.setAnnouncement("");
                } else {
                    mm.setAnnouncement(announcement);
                }
                break;
            default:
                break;
        }
        this.purchaseType = mm.getPurchaseType();
        this.MRPController = mrp;
        //设置MRP
        mm.setMRPController(mrp);
    }


    public static void main(String[] args) {
        HzMaterielRecord materiel = new HzMaterielRecord();
        materiel.setPuid("2123132131");
        materiel.setpFactoryPuid("22133");
        //物料编号
        materiel.setpMaterielCode("S00-5206010");
        //中文描述
        materiel.setpMaterielDesc("前风挡总成");
        //英文描述
        materiel.setpMaterielDescEn("QFDZC");
        //基本计量单位
        materiel.setpBasicUnitMeasure("EA");
        //设置物料类型
        materiel.setpMaterielType(MaterialOption.MATERIAL_ORG.GetDesc());
        //设置采购类型
        //设置VIN前置号
        materiel.setpVinPerNo("11111111111");
        //设置颜色标识
        materiel.setpColorPart(0);
        //设置毛重
        materiel.setpHeight("12.5");
        //设置内外饰标识
        materiel.setpInOutSideFlag(0);
        //设置3C件标识
        materiel.setP3cPartFlag(0);
        //设置零件重要度
        materiel.setpPartImportantDegree("1");
        //设置散件标识
        materiel.setpLoosePartFlag(0);
        //设置虚拟件标识
        materiel.setpInventedPart(0);

        ReflectMateriel materiel1 = new ReflectMateriel(materiel);
        //设置工厂
        materiel1.setFactory("1001");
        //设置动作描述代码
        materiel1.setActionFlagOption(ActionFlagOption.ADD);
        //设置MRP控制者，公告号和采购类型
        materiel1.setMRPAndPurchase(MRPControlOption.MRP_FICTITIOUS.GetDesc(),
                PurchaseOption.PURCHASE_MAKE.GetDesc(),
                "公告号");
        String uuid = UUIDHelper.generateUpperUid();
        String lineNum = uuid.substring(0, 5);
        materiel1.setPackNo(uuid);
        materiel1.setLineNum(lineNum);

        TransMasterMaterialService service = new TransMasterMaterialService();
        service.getInput().getItem().add(materiel1.getMm().getZpptci001());
        service.execute();
        service.getOut();
        System.out.println();
    }
}
