package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0ModelGroupDao;
import com.connor.hozon.bom.bomSystem.dto.HzMaterielFeatureBean;
import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0MainService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFamilyService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.IHzCfg0ModelFeatureService;
import com.connor.hozon.bom.bomSystem.service.project.HzSuperMaterielService;
import integration.logic.ConfigurableMaterialAllocation;
import integration.option.ActionFlagOption;
import integration.service.impl.cfg2.TransCfgService;
import integration.service.impl.classify3.TransClassifyService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0MainRecord;
import sql.pojo.cfg.HzCfg0ModelFeature;
import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.cfg.HzMaterielCfgBean;
import sql.pojo.project.HzMaterielRecord;

import java.util.*;

@Service("synConfigurableMaterialService")
public class SynConfigurableMaterialService {

    @Autowired
    private HzCfg0MainService hzCfg0MainService;
    @Autowired
    private  HzSuperMaterielService hzSuperMaterielService;
    @Autowired
    private HzCfg0OptionFamilyService hzCfg0OptionFamilyService;
    @Autowired
    private HzCfg0Service hzCfg0Service;
    @Autowired
    TransClassifyService transClassifyService;
    /**
     * 模型族dao层
     */
    @Autowired
    HzCfg0ModelGroupDao hzCfg0ModelGroupDao;
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(SynFeatureService.class);


    public JSONObject addConfigurableMaterial(String[] puid, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid) {
        return execute(puid, cfg0MainPuids, modeBasiceDetails, projectPuid, ActionFlagOption.ADD);
    }

    public JSONObject deleteConfigurableMaterial(String[] puid, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid) {
        return execute(puid, cfg0MainPuids, modeBasiceDetails, projectPuid, ActionFlagOption.DELETE);
    }

    private JSONObject execute(String[] puids, String[] cfg0MainPuids, String[] modeBasiceDetails, String projectPuid, ActionFlagOption option) {
        JSONObject result = new JSONObject();
        /**
         * 成功项
         */
        List<IntegrateMsgDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgDTO> fail = new ArrayList<>();

        List<String> _forDelete = new ArrayList<>();
        /***
         * 计数
         */
        int total = 0;
        int totalOfSuccess = 0;
        int totalOfFail = 0;
        int totalOfOutOfParent = 0;
        int totalOfUnknown = 0;

        if (puids == null || puids.length <= 0 || cfg0MainPuids == null || cfg0MainPuids.length<=0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }
        if(puids.length != cfg0MainPuids.length){
            result.put("status", false);
            result.put("msg", "前端数据传输错误");
            return result;
        }
        Map<String, String> packNumOfFeature = new HashMap<>();
        String packnum = UUIDHelper.generateUpperUid();
        /**
         * 获取到所有puid对应的所有的“特性编码”和其对应的"特性编码值"
         */
        for(int i=0;i<puids.length;i++){
            //没有父层的puid
            if (!packNumOfFeature.containsKey(puids[i])) {
                //添加父层puid和包号的对应关系
                packNumOfFeature.put(puids[i], packnum);
                packnum = UUIDHelper.generateUpperUid();
            }
            //存取编码特性与编码特性值
            Map<String, String> pCfg0ObjectMap = new HashMap<String, String>();
            List<String> column = hzCfg0OptionFamilyService.doGetColumnDef(projectPuid, "<br/>");
            List<HzMaterielFeatureBean> hzMaterielFeatureBeans = hzCfg0Service.doSelectMaterielFeatureByProjectPuid(projectPuid);

            HzMaterielRecord superMateriel = hzSuperMaterielService.doSelectByProjectPuid(projectPuid);

            Map<String, HzMaterielFeatureBean> sortedBean = new HashMap<>();

            hzMaterielFeatureBeans.stream().filter(_b -> _b.getpCfg0ModelRecord() != null).forEach(_b -> sortedBean.put(_b.getpCfg0ModelRecord() + "=" + _b.getpCfg0FamilyDesc() + "<br/>" + _b.getpCfg0FamilyName(), _b));

            if (hzMaterielFeatureBeans == null || column == null || column.size() == 0) {
                continue;
            } else {
                //往map中添加当前puid的特性编码和对应值
                for(HzMaterielFeatureBean hzMaterielFeatureBean :hzMaterielFeatureBeans){
                    if(hzMaterielFeatureBean.getpCfg0ModelRecord()!=null && hzMaterielFeatureBean.getpCfg0ModelRecord().equals(puids[i])){
                        pCfg0ObjectMap.put(hzMaterielFeatureBean.getpCfg0FamilyName(),hzMaterielFeatureBean.getpCfg0ObjectId());
                    }
                }
                //新建组映射对象，其中对特性编码，特性编码值和超级物料编码赋值
                List<ConfigurableMaterialAllocation> configurableMaterialAllocations = ConfigurableMaterialAllocation.getConfigurableMaterialAllocationList(cfg0MainPuids[i], pCfg0ObjectMap, hzCfg0MainService, hzSuperMaterielService);
                //获取族
                String groupName = hzCfg0ModelGroupDao.selectGroupNameByMainUid(cfg0MainPuids[i]);
                //循环list对其中映射对象的包号、动作描述赋值
                for(ConfigurableMaterialAllocation configurableMaterialAllocation : configurableMaterialAllocations){
                    //数据包号
                    configurableMaterialAllocation.setPackNo(packnum);
                    //配置物料编码
                    configurableMaterialAllocation.setConfigurableMaterialEncoding(modeBasiceDetails[i]);
                    //添加族
                    configurableMaterialAllocation.setModelClass(groupName);
                    if (option == ActionFlagOption.ADD) {
                            configurableMaterialAllocation.setActionFlag("A");
                    }
                    //执行更新或删除
                    else {
                        configurableMaterialAllocation.setActionFlag("D");
                    }
//                    transClassifyService.getInput().getItem().add(configurableMaterialAllocation.getZpptci003());
                }
            }
        }



        return result;
    }
}
