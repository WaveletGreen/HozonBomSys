package com.connor.hozon.bom.bomSystem.service.cfg;

import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgMainDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzFullCfgModelDao;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.common.util.user.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.cfg.HzCfg0ModelRecord;
import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.cfg.HzFullCfgMain;
import sql.pojo.cfg.HzFullCfgModel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("hzBomAllCfgService")
public class HzBomAllCfgService {

    @Autowired
    private HzCfg0Service hzCfg0Service;
    @Autowired
    private HzCfg0ModelService hzCfg0ModelService;
    @Autowired
    private HzFullCfgMainDao hzFullCfgMainDao;
    @Autowired
    private HzFullCfgModelDao hzFullCfgModelDao;
    /**
     * @param projectPuid 项目puid
     * @return net.sf.json.JSONObject
     * Description: 根据数模层获取到bom的配置信息和车型模型信息
     */
    public JSONObject parse(String projectPuid) {
        JSONObject respond = new JSONObject();

        /**搜索全部特性，并经过P_CFG0_OBJECT_ID 升序排序*/
        QueryBase queryBase = new QueryBase();
        queryBase.setSort("P_CFG0_OBJECT_ID");
        List<HzCfg0Record> hzCfg0Records = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid, queryBase);

        /**
         * 获取该项目下的所有车型模型
         */
        List<HzCfg0ModelRecord> hzCfg0ModelRecords = hzCfg0ModelService.doSelectByProjectPuid(projectPuid);
        //
        Map<String, List<String>> modelCfgMap = new HashMap<String, List<String>>();
        for(HzCfg0ModelRecord hzCfg0ModelRecord : hzCfg0ModelRecords){
            List<String> cfgList = hzFullCfgModelDao.selectCfg(hzCfg0ModelRecord.getPuid());
            modelCfgMap.put(hzCfg0ModelRecord.getPuid(),cfgList);
        }
        HzFullCfgMain hzFullCfgMain = hzFullCfgMainDao.selectByPrimaryKey(BigDecimal.valueOf(Integer.valueOf(projectPuid)));
        if(hzFullCfgMain==null){
            HzFullCfgMain hzFullCfgMain1 = new HzFullCfgMain();
            hzFullCfgMain1.setProjectUid(projectPuid);
            hzFullCfgMain1.setStatus("编辑");
            hzFullCfgMain1.setVersion("1.0");
            hzFullCfgMain1.setCreator(UserInfo.getUser());
            hzFullCfgMain1.setUpdater(UserInfo.getUser());
            Integer mainPuid = hzFullCfgMainDao.insert(hzFullCfgMain1);
        }




        return respond;
    }
}
