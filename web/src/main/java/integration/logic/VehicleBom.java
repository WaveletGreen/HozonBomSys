package integration.logic;

import com.connor.hozon.bom.bomSystem.iservice.cfg.IHzMaterielCfgService;
import integration.base.productAttributes.ZPPTCI007;
import integration.option.ActionFlagOption;
import sql.pojo.cfg.derivative.HzCfg0ModelFeature;
import sql.pojo.cfg.derivative.HzMaterielCfgBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 整车物料属性接口映射been
 */
public class VehicleBom {

    /**
     * 对应的SAP接口DTO
     */
    private ZPPTCI007 zpptci007;

    /**
     * 数据包号，随机数产生，ZPACKNO
     */
    private String packNo;

    /**
     * 行号，ZITEM
     */
    private String item;

    /**
     * 动作描述代码,ZACTIONID
     */
    private String actionFlag;

    /**
     * 工厂,ZWERKS
     */
    private String factory;

    /**
     * 整车编码
     */
    private String vehicleEncoding;

    /**
     * 属性编码
     */
    private String propertyEncoding;

    /**
     * 属性值
     */
    private String propertyValue;


    /***
     * 对应数据库的puid
     */
    private String puid;

    /**
     * 是否已发送过SAP
     */
    private Integer isSent;

    public VehicleBom() {
        zpptci007=new ZPPTCI007();
    }



    ///////////////setter////////////
    public void setPackNo(String packNo) {
        this.packNo = packNo;
        zpptci007.setPPACKNO(packNo);
    }

    public void setItem(String item) {
        this.item = item;
        zpptci007.setPITEM(item);
    }

    public void setActionFlag(ActionFlagOption actionFlag) {
        this.actionFlag = actionFlag.GetDesc();
        zpptci007.setPACTIONID(actionFlag.GetDesc());
    }

    public void setFactory(String factory) {
        this.factory = factory;
        zpptci007.setPWERKS(factory);
    }

    public void setVehicleEncoding(String vehicleEncoding) {
        this.vehicleEncoding = vehicleEncoding;
        zpptci007.setPMATNR(vehicleEncoding);
    }

    public void setPropertyEncoding(String propertyEncoding) {
        this.propertyEncoding = propertyEncoding;
        zpptci007.setPATNAM(propertyEncoding);
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
        zpptci007.setPATWRT(propertyValue);
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public void setIsSent(Integer isSent) { this.isSent = isSent; }

    /////////getter////////////

    public String getPackNo() {
        return packNo;
    }

    public String getItem() {
        return item;
    }

    public String getActionFlag() {
        return actionFlag;
    }

    public String getFactory() {
        return factory;
    }

    public String getVehicleEncoding() {
        return vehicleEncoding;
    }

    public String getPropertyEncoding() {
        return propertyEncoding;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public String getPuid() {
        return puid;
    }

    public ZPPTCI007 getZpptci007() {
        return zpptci007;
    }

    public void setZpptci007(ZPPTCI007 zpptci007) {
        this.zpptci007 = zpptci007;
    }

    public Integer getIsSent() { return isSent; }

    /**
     * DMS提交的车辆属性代码
     */
    public static String[] code={"SD_01","SD_02","SD_03","SD_04","SD_05","SD_06",
            "SD_07","SD_08", "SD_09","SD_10","SD_11","SD_12"};


    /**
     * 查询出puid对应的所有映射对象
     * @param puid
     * @param hzmcs
     * @return
     */
    public static List<VehicleBom> getVehicleBom(String puid,IHzMaterielCfgService hzmcs ){
        HzMaterielCfgBean hzmcb = new HzMaterielCfgBean();
        hzmcb.setPuid(puid);
        List<HzMaterielCfgBean> result = hzmcs.doSelectByDiff(hzmcb);

        List<VehicleBom> vehicleBomList = new ArrayList<VehicleBom>();
        for(int i=0;i<code.length;i++){
            VehicleBom vb = new VehicleBom();
            vb.setPuid(puid);
            vb.setFactory("1001");
            vb.setVehicleEncoding(result.get(0).getObjectName());
            vb.setPropertyEncoding(code[i]);
            vb.setPropertyValue(reflectCode(result.get(0),vb.getPropertyEncoding()));
            vb.setIsSent(result.get(0).getIsSent());
            vehicleBomList.add(vb);
        }
        return vehicleBomList;
    }

    public static List<VehicleBom> getVehicleBom(HzMaterielCfgBean record) {
        List<VehicleBom> vehicleBomList = new ArrayList<VehicleBom>();
        for(int i=0;i<code.length;i++){
            VehicleBom vb = new VehicleBom();
            vb.setPuid(record.getPuid());
            vb.setFactory("1001");
            vb.setVehicleEncoding(record.getObjectName());
            vb.setPropertyEncoding(code[i]);
            vb.setPropertyValue(reflectCode(record,vb.getPropertyEncoding()));
            vb.setIsSent(record.getIsSent());
            vehicleBomList.add(vb);
        }
        return vehicleBomList;
    }
    /**
     * 根据属性代码返回对应的值
     * @param bean
     * @param code
     * @return
     */
    public static String reflectCode(HzMaterielCfgBean bean, String code) {
        switch (code) {
            case "SD_01":
                return bean.getpBrandCode();
            case "SD_02":
                return bean.getpBrandName();
            case "SD_03":
                return bean.getpPlatformCode();
            case "SD_04":
                return bean.getpPlatformName();
            case "SD_05":
                return bean.getpVehicleCode();
            case "SD_06":
                return bean.getpVehicleName();
            case "SD_07":
                return bean.getpInColorCode();
            case "SD_08":
                return bean.getpInColorName();
            case "SD_09":
                return bean.getpColorCode();
            case "SD_10":
                return bean.getpColorName();
            case "SD_11":
                return bean.getpBatteryModel();
            case "SD_12":
                return bean.getpMotorModel();
            default:
                return null;
        }
    }
}
