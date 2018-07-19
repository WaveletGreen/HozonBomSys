package integration.logic;

import integration.base.bom.ZPPTCI005;
import integration.option.ActionFlagOption;
import integration.option.BomOption;
import sql.pojo.bom.HzMBomToERPBean;
import sql.redis.SerializeUtil;

import java.util.LinkedHashMap;

/**
 * 从MBOM中数据映射到SAP传输的DTO上，父层的物料编号需要单独设置，预料字段没有做对应
 */
public class ReflectBom {
    /**
     * 对应的SAP接口DTO
     */
    private ZPPTCI005 zpptci005;

    /**
     * 数据包号，随机数产生，ZPACKNO
     */
    private String packNo;
    /**
     * 行号，ZITEM
     */
    private String lineNum;

    /**
     * 动作描述代码,ZACTIONID
     */
    private String actionFlag;

    /**
     * 更改编号，ZAENNR,创建BOM可不输入，删除（D）和修改（U）必须有ECN（更改编号）
     */
    private String modifyCode;
    /**
     * 工程更改号描述
     */
    private String modifyDesc;
    /**
     * TC系统更改号
     */
    private String TCModifyCode;

    /**
     * 工厂,ZWERKS
     */
    private String factory;
    /**
     * BOM类型
     */
    private String bomType;
    /**
     * bomline首行,ZMATNR
     */
    private String headOfBomLine;
    /**
     * 表头的基本数量，默认是1
     */
    private String baseNumOfHead;

    /**
     * bomline下的子行,ZMATNR_C
     */
    private String childOfBomLine;
    /**
     * BOM顺序,ZSORTF
     */
    private String orderOfBomLine;
    /**
     * 数量,ZMENGE
     */
    private String number;

    /**
     * 单位,ZMEINS
     */
    private String unit;
    /**
     * 发料库存地点，Zlocation
     */
    private String stockLocation;
    /**
     * 相关性（选配条件）
     */
    private String relevance;
    /**
     * 采购件下级件标识，TC系统传输02或空，SAP系统会将02转化为L
     */
    private String purchaseFlag;
    /**
     * 装配位置，ZZPWZ
     */
    private String assemblyPoint;

    /**
     * 使用车间,ZWORKS
     */
    private String useWorkshop;

    /**
     * 工位,P_STATION
     */
    private String station;

    /***
     * 对应数据库的puid
     */
    private String puid;

    private HzMBomToERPBean bomToERPBean;

    public ReflectBom(HzMBomToERPBean bomToERPBean) {
        this.bomToERPBean = bomToERPBean;
        zpptci005 = new ZPPTCI005();
        this.puid = bomToERPBean.getPuid();
        //更改编号
        setModifyCode(bomToERPBean.getChangeNum());
        //工程更改号描述
        setModifyDesc(bomToERPBean.getChange());
        //TC系统更改号
        setTCModifyCode(bomToERPBean.getChangeNum());
        //工厂
        setFactory(bomToERPBean.getFactoryCode());
        //BOM类型,默认设置为生产
        if (bomToERPBean.getBomType() == null || "".equalsIgnoreCase(bomToERPBean.getBomType())) {
            setBomType(BomOption.BOM_TYPE_PRODUCTION);
        } else {
            setBomType(bomToERPBean.getBomType());
        }
        //表头物料编码单独获取

        //基本数量，默认是1
        setBaseNumOfHead("1");
        //BOM序号,按10进制
        Integer index = Integer.parseInt(bomToERPBean.getLineIndex().substring(bomToERPBean.getLineIndex().lastIndexOf(".") + 1)) * 10;
        String str = "";
        if (index < 100) {
            str = "00" + String.valueOf(index);
        }
        if (index > 100) {
            str = "0" + String.valueOf(index);
        } else if (index > 1000 && index < 10000) {
            str = String.valueOf(index);
        }
        setOrderOfBomLine(str);
        //子物料编码
        setChildOfBomLine(bomToERPBean.getBomLineId());
        //数量，也默认1条条传
        setNumber(bomToERPBean.getNumber() == null ? "1" : bomToERPBean.getNumber().toString());
        //单位，如果没有则默认设为EA
        Object obj = SerializeUtil.unserialize(bomToERPBean.getBomLineBlock());
        if (obj instanceof LinkedHashMap) {
            String unit = (String) ((LinkedHashMap) obj).get("h9_Gross_Unit");
            setUnit((unit == null || "".equals(unit) ? "EA" : unit));
        } else {
            setUnit("EA");
        }
        //发料库存地点，默认传个空格
        setStockLocation(bomToERPBean.getStockLocation() == null ? " " : bomToERPBean.getStockLocation());
        //相关性（选配条件）
        setRelevance(bomToERPBean.getCfg0Relevance());
        //采购件下级件标识,TC系统传输02或空，SAP系统会将02转化为L
        setPurchaseFlag("");
        //装配位置
        setAssemblyPoint(bomToERPBean.getFNAInfo() == null ? "" : bomToERPBean.getFNAInfo());
        //使用车间
        setUseWorkshop(bomToERPBean.getWorkShop() == null ? "" : bomToERPBean.getWorkShop());
        //工位
        setStation(bomToERPBean.getStation() == null ? "" : bomToERPBean.getStation());

    }


    /////////////////////////////////////////setter/////////////////////////////

    public void setPackNo(String packNo) {
        zpptci005.setPPACKNO(packNo);
        this.packNo = packNo;
    }

    public void setLineNum(String lineNum) {
        zpptci005.setPITEM(lineNum);
        this.lineNum = lineNum;
    }

    public void setActionFlag(ActionFlagOption flag) {
        zpptci005.setPACTIONID(flag.GetDesc());
        this.actionFlag = flag.GetDesc();
    }

    public void setModifyCode(String modifyCode) {
        this.modifyCode = modifyCode;
        zpptci005.setPAENNR(modifyCode);
    }

    public void setFactory(String factory) {
        zpptci005.setPWERKS(factory);
        this.factory = factory;
    }

    public void setBomType(BomOption bomType) {
        this.bomType = bomType.GetDesc();
        zpptci005.setPUSE(bomType.GetDesc());
    }

    public void setBomType(String bomType) {
        this.bomType = bomType;
        zpptci005.setPUSE(bomType);

    }

    public void setHeadOfBomLine(String headOfBomLine) {
        zpptci005.setPMATNR(headOfBomLine);
        this.headOfBomLine = headOfBomLine;
    }

    public void setBaseNumOfHead(String baseNumOfHead) {
        zpptci005.setPBASEQ(baseNumOfHead);
        this.baseNumOfHead = baseNumOfHead;
    }

    public void setChildOfBomLine(String childOfBomLine) {
        zpptci005.setPMATNRC(childOfBomLine);
        this.childOfBomLine = childOfBomLine;
    }

    public void setOrderOfBomLine(String orderOfBomLine) {
        zpptci005.setPSORTF(orderOfBomLine);
        this.orderOfBomLine = orderOfBomLine;
    }

    public void setNumber(String number) {
        zpptci005.setPMENGE(number);
        this.number = number;
    }

    public void setUnit(String unit) {
        zpptci005.setPMEINS(unit);
        this.unit = unit;
    }

    public void setStockLocation(String stockLocation) {
        zpptci005.setPLOCATION(stockLocation);
        this.stockLocation = stockLocation;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance;
        zpptci005.setPKNBLK(relevance);
    }

    public void setPurchaseFlag(String purchaseFlag) {
        this.purchaseFlag = purchaseFlag;
    }

    public void setAssemblyPoint(String assemblyPoint) {
        zpptci005.setPZPWZ(assemblyPoint);
        this.assemblyPoint = assemblyPoint;
    }

    public void setUseWorkshop(String useWorkshop) {
        zpptci005.setPWORKS(useWorkshop);
        this.useWorkshop = useWorkshop;
    }

    public void setStation(String station) {
        zpptci005.setPSTATION(station);
        this.station = station;
    }

    public void setModifyDesc(String modifyDesc) {
        zpptci005.setPAETXT(modifyDesc);
        this.modifyDesc = modifyDesc;
    }

    public void setTCModifyCode(String TCModifyCode) {
        this.TCModifyCode = TCModifyCode;
        zpptci005.setPPCNNO(TCModifyCode);
    }

    /////////////////////////////////////////getter/////////////////////////////

    public ZPPTCI005 getZpptci005() {
        return zpptci005;
    }

    public String getPackNo() {
        return packNo;
    }

    public String getLineNum() {
        return lineNum;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public String getModifyCode() {
        return modifyCode;
    }

    public String getFactory() {
        return factory;
    }

    public String getBomType() {
        return bomType;
    }

    public String getHeadOfBomLine() {
        return headOfBomLine;
    }

    public String getChildOfBomLine() {
        return childOfBomLine;
    }

    public String getOrderOfBomLine() {
        return orderOfBomLine;
    }

    public String getNumber() {
        return number;
    }

    public String getUnit() {
        return unit;
    }

    public String getStockLocation() {
        return stockLocation;
    }

    public String getRelevance() {
        return relevance;
    }

    public String getPurchaseFlag() {
        return purchaseFlag;
    }

    public String getAssemblyPoint() {
        return assemblyPoint;
    }

    public String getUseWorkshop() {
        return useWorkshop;
    }

    public String getStation() {
        return station;
    }

    public String getModifyDesc() {
        return modifyDesc;
    }

    public String getTCModifyCode() {
        return TCModifyCode;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }
}
