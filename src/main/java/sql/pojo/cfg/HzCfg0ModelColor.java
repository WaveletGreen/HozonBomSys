package sql.pojo.cfg;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Fancyears·Maylos·Mayways
 * Date: 2018/5/22
 * Time: 11:23
 */
public class HzCfg0ModelColor {
    private String puid;
    private String pCfg0MainRecordOfMC;
    private String pCodeOfColorfulModel;
    private String pDescOfColorfulModel;
    private byte[] pColorfulMapBlock;
    private LinkedHashMap<String, String> mapOfCfg0;

    public HzCfg0ModelColor() {
        mapOfCfg0 = new LinkedHashMap<>();
    }

    public String getpCfg0MainRecordOfMC() {
        return pCfg0MainRecordOfMC;
    }

    public void setpCfg0MainRecordOfMC(String pCfg0MainRecordOfMC) {
        this.pCfg0MainRecordOfMC = pCfg0MainRecordOfMC;
    }

    public String getPuid() {
        return puid;
    }

    public void setPuid(String puid) {
        this.puid = puid;
    }

    public String getpCodeOfColorfulModel() {
        return pCodeOfColorfulModel;
    }

    public void setpCodeOfColorfulModel(String pCodeOfColorfulModel) {
        this.pCodeOfColorfulModel = pCodeOfColorfulModel;
    }

    public String getpDescOfColorfulModel() {
        return pDescOfColorfulModel;
    }

    public void setpDescOfColorfulModel(String pDescOfColorfulModel) {
        this.pDescOfColorfulModel = pDescOfColorfulModel;
    }

    public byte[] getpColorfulMapBlock() {
        return pColorfulMapBlock;
    }

    public void setpColorfulMapBlock(byte[] pColorfulMapBlock) {
        this.pColorfulMapBlock = pColorfulMapBlock;
    }

    public LinkedHashMap<String, String> getMapOfCfg0() {
        return mapOfCfg0;
    }

    public void setMapOfCfg0(LinkedHashMap<String, String> mapOfCfg0) {
        this.mapOfCfg0 = mapOfCfg0;
    }
}
