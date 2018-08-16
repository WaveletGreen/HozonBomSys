package integration.logic;

import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.project.HzSuperMaterielService;
import integration.base.classify.ZPPTCI003;
import integration.option.ActionFlagOption;
import sql.pojo.cfg.HzCfg0MainRecord;
import sql.pojo.project.HzMaterielRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 可配置物料分配特性接口映射类
 */
public class ConfigurableMaterialAllocation {
    /**
     * 数据包号,P_PACKNO
     */
    private String packNo;
    /**
     * 超级物料,P_ITEM
     */
    private String pitem;
    /**
     *动作描述代码,P_ACTIONID
     */
    private String actionFlag;
    /**
     * 可配置物料编码(?),P_MATNR
     */
    private String configurableMaterialEncoding;
    /**
     * 特性编码,P_ATNAM
     */
    private String peculiarityEncoding;
    /**
     * 特性值编码,P_ATWRT
     */
    private String peculiarityValueEncoding;
    /**
     * 模型族-分类,P_CLASS
     */
    private String modelClass;
    /**
     * 接口成员
     */
    private ZPPTCI003 zpptci003;

    /**
     * 无参构造函数
     */
    public ConfigurableMaterialAllocation(){
        zpptci003 = new ZPPTCI003();
    }

    public static List<ConfigurableMaterialAllocation> getConfigurableMaterialAllocationList(String puid, Map<String, String> pCfg0ObjectMap, HzCfg0MainService hzCfg0MainService, HzSuperMaterielService hzSuperMaterielService) {
        List<ConfigurableMaterialAllocation> configurableMaterialAllocations = new ArrayList<ConfigurableMaterialAllocation>();
        HzCfg0MainRecord mainRecord = hzCfg0MainService.doGetByPrimaryKey(puid);
        HzMaterielRecord superMateriel = null;
        if (mainRecord != null) {
            superMateriel = hzSuperMaterielService.doSelectByProjectPuid(mainRecord.getpCfg0OfWhichProjectPuid());
            if(superMateriel==null){
                return null;
            }
        }
        Set<String> keySet = pCfg0ObjectMap.keySet();
        for(String key : keySet){
            ConfigurableMaterialAllocation configurableMaterialAllocation = new ConfigurableMaterialAllocation();
            configurableMaterialAllocation.setPitem(superMateriel.getpMaterielCode());
            configurableMaterialAllocation.setPeculiarityEncoding(key);
            configurableMaterialAllocation.setPeculiarityValueEncoding(pCfg0ObjectMap.get(key));

            configurableMaterialAllocations.add(configurableMaterialAllocation);
        }
        return  configurableMaterialAllocations;
    }
    /////////////////////////setter//////////////////////////

    public void setPackNo(String packNo) {
        zpptci003.setPPACKNO(packNo);
        this.packNo = packNo;
    }

    public void setPitem(String pitem) {
        zpptci003.setPITEM(pitem);
        this.pitem = pitem;
    }

    public void setActionFlag(String actionFlag) {
        zpptci003.setPACTIONID(actionFlag);
        this.actionFlag = actionFlag;
    }

    public void setConfigurableMaterialEncoding(String configurableMaterialEncoding) {
        zpptci003.setPMATNR(configurableMaterialEncoding);
        this.configurableMaterialEncoding = configurableMaterialEncoding;
    }

    public void setPeculiarityEncoding(String peculiarityEncoding) {
        zpptci003.setPATNAM(peculiarityEncoding);
        this.peculiarityEncoding = peculiarityEncoding;
    }

    public void setPeculiarityValueEncoding(String peculiarityValueEncoding) {
        zpptci003.setPATWRT(peculiarityValueEncoding);
        this.peculiarityValueEncoding = peculiarityValueEncoding;
    }

    public void setModelClass(String modelClass) {
        zpptci003.setPCLASS(modelClass);
        this.modelClass = modelClass;
    }

    public void setZpptci003(ZPPTCI003 zpptci003) {
        this.zpptci003 = zpptci003;
    }

    ////////////////////////getter/////////////////

    public String getPackNo() { return packNo; }

    public String getPitem() { return pitem; }

    public String getActionFlag() { return actionFlag; }

    public String getConfigurableMaterialEncoding() { return configurableMaterialEncoding; }

    public String getPeculiarityEncoding() { return peculiarityEncoding; }

    public String getPeculiarityValueEncoding() { return peculiarityValueEncoding; }

    public String getModelClass() { return modelClass; }

    public ZPPTCI003 getZpptci003() { return zpptci003; }
}
