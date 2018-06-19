package sql.pojo.project;

import java.util.Calendar;
import java.util.Date;

public class HzVehicleRecord {
    /**
     * puid
     */
    private String puid;
    /**
     * 车型名称
     */
    private String pVehicleName;
    /**
     * 归属平台
     */
    private String pVehiclePertainToPlatform;
    /**
     * 创建时间
     */
    private Date pVehicleCreateDate;
    /**
     * 上一次修改时间
     */
    private Date pVehicleLastModDate;
    /**
     * 车型代码
     */
    private String pVehicleCode;
    /**
     * 车身形式代码
     */
    private String pVehicleShapeCode;
    /**
     * 车身变形名称
     */
    private String pVehicleTranName;
    /**
     * 车型年代码
     */
    private String pVehicleAnnualCode;
    /**
     * 车型年
     */
    private String pVehicleAnnual;
    /**
     * 最后修改者
     */
    private String pVehicleLastModifier;


    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid == null ? null : puid.trim();
    }

    public String getpVehicleName() {
        return pVehicleName;
    }

    public void setpVehicleName(String pVehicleName) {
        this.pVehicleName = pVehicleName;
    }

    public String getpVehiclePertainToPlatform() {
        return pVehiclePertainToPlatform;
    }

    public void setpVehiclePertainToPlatform(String pVehiclePertainToPlatform) {
        this.pVehiclePertainToPlatform = pVehiclePertainToPlatform == null ? null : pVehiclePertainToPlatform.trim();
    }

    public Date getpVehicleCreateDate() {
        return pVehicleCreateDate;
    }

    public void setpVehicleCreateDate(Date pVehicleCreateDate) {
        this.pVehicleCreateDate = pVehicleCreateDate;
    }

    public Date getpVehicleLastModDate() {
        return pVehicleLastModDate;
    }

    public void setpVehicleLastModDate(Date pVehicleLastModDate) {

        if (pVehicleLastModDate == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(9998, 12, 31, 23, 59, 59);
            this.pVehicleLastModDate = calendar.getTime();
        } else
            this.pVehicleLastModDate = pVehicleLastModDate;
    }

    public String getpVehicleCode() {
        return pVehicleCode;
    }

    public void setpVehicleCode(String pVehicleCode) {
        this.pVehicleCode = pVehicleCode == null ? null : pVehicleCode.trim();
    }

    public String getpVehicleShapeCode() {
        return pVehicleShapeCode;
    }

    public void setpVehicleShapeCode(String pVehicleShapeCode) {
        this.pVehicleShapeCode = pVehicleShapeCode;
    }

    public String getpVehicleTranName() {
        return pVehicleTranName;
    }

    public void setpVehicleTranName(String pVehicleTranName) {
        this.pVehicleTranName = pVehicleTranName;
    }

    public String getpVehicleAnnualCode() {
        return pVehicleAnnualCode;
    }

    public void setpVehicleAnnualCode(String pVehicleAnnualCode) {
        this.pVehicleAnnualCode = pVehicleAnnualCode == null ? null : pVehicleAnnualCode.trim();
    }

    public String getpVehicleAnnual() {
        return pVehicleAnnual;
    }

    public void setpVehicleAnnual(String pVehicleAnnual) {
        this.pVehicleAnnual = pVehicleAnnual == null ? null : pVehicleAnnual.trim();
    }

    public String getpVehicleLastModifier() {
        return pVehicleLastModifier;
    }

    public void setpVehicleLastModifier(String pVehicleLastModifier) {
        this.pVehicleLastModifier = pVehicleLastModifier;
    }
}