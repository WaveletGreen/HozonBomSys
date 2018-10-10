package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVWOManagerService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVwoInfoService;
import com.connor.hozon.bom.bomSystem.service.project.HzPlatformService;
import com.connor.hozon.bom.bomSystem.service.project.HzProjectLibsService;
import com.connor.hozon.bom.bomSystem.service.project.HzVehicleService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.dao.OrgGroupDao;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;
import sql.pojo.cfg.vwo.HzVwoInfo;
import sql.pojo.project.HzPlatformRecord;
import sql.pojo.project.HzProjectLibs;
import sql.pojo.project.HzVehicleRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description:
 * @Date: Created in 2018/8/9 11:13
 * @Modified By:
 */
@Controller
@RequestMapping("/vwoProcess")
public class HzVWOProecrssController {

    @Autowired
    IHzVWOManagerService iHzVWOManagerService;

    /**
     * 特性进入VWO流程Controller
     *
     * @param projectUid 项目UID
     * @param beans      特性值对象
     * @return
     */
    @RequestMapping("/featureGetIntoVWO")
    @ResponseBody
    public JSONObject featureGetIntoVWO(@RequestParam String projectUid, @RequestBody List<HzCfg0Record> beans) {
        return iHzVWOManagerService.featureGetIntoVWO(projectUid, beans);
    }


}
