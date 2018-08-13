package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVwoInfoService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0Record;
import sql.pojo.cfg.vwo.HzFeatureChangeBean;
import sql.pojo.cfg.vwo.HzVwoInfo;

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
    HzCfg0Service hzCfg0Service;

    @Autowired
    IHzVwoInfoService iHzVwoInfoService;
    @Autowired
    IHzFeatureChangeService iHzFeatureChangeService;

    private Logger logger = LoggerFactory.getLogger(HzVWOProecrssController.class);

    @RequestMapping("/featureGetIntoVWO")
    @ResponseBody
    public boolean featureGetIntoVWO(@RequestBody List<HzCfg0Record> beans) {
        User user = UserInfo.getUser();
        Date now = new Date();

        if (beans != null && beans.size() > 0) {
            List<String> puids = new ArrayList<>();
            beans.forEach(bean -> puids.add(bean.getPuid()));
            List<HzCfg0Record> lists = hzCfg0Service.doLoadListByPuids(puids);
            List<HzCfg0Record> localParams = lists.stream().filter(l -> l != null).collect(Collectors.toList());
            int id = -1;
            if (beans.size() != localParams.size()) {
                logger.error("搜索出的特性值总数与发起VWO流程的特性值的总数不一致，请检查数据核对数据是否被删除");
                return false;
            } else {
                //hzCfg0Service.doSetToProcess(localParams);
                System.out.println("总数一致");
                HzVwoInfo hzVwoInfo = iHzVwoInfoService.doFindMaxAreaVwoNum();

                if (hzVwoInfo == null || hzVwoInfo.getVwoNum() == null) {
                    hzVwoInfo = new HzVwoInfo();
                    //当月第一位vwo号
                    hzVwoInfo.setVwoNum(DateStringHelper.dateToString4(now) + "0001");
                } else {
                    hzVwoInfo.setVwoNum(String.valueOf(Long.parseLong(hzVwoInfo.getVwoNum()) + 1));
                    hzVwoInfo.setVwoCreator(user.getUserName());
                    hzVwoInfo.setVwoCreateDate(now);
                }
                if ((id = iHzVwoInfoService.doInsert(hzVwoInfo)) <= 0) {
                    logger.error("创建新的VWO号失败，请联系系统管理员");
                    return false;
                }

                hzVwoInfo.setId(Long.valueOf(id));
                HzFeatureChangeBean after = new HzFeatureChangeBean();
                HzFeatureChangeBean before = new HzFeatureChangeBean();
                HzCfg0Record record = new HzCfg0Record();
                int afterId = -1, beforeId = -1;
                for (int i = 0; i < localParams.size(); i++) {

                    after.setCfgPuid(localParams.get(i).getPuid());
                    before.setCfgPuid(localParams.get(i).getPuid());
                    after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
                    before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");


                    after = iHzFeatureChangeService.doFindNewestChange(after);
                    before = iHzFeatureChangeService.doFindNewestChange(before);


                    if (after == null) {
                        after = new HzFeatureChangeBean();
                        if ((afterId = iHzFeatureChangeService.insertByCfg(localParams.get(i), "HZ_CFG0_AFTER_CHANGE_RECORD", "SEQ_HZ_FEATURE_AFTER_CHANGE")) <= 0) {
                            logger.error("创建后自动同步变更后记录值失败，请联系管理员");
                            return false;
                        }
                        after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
                        after.setId(Long.valueOf(afterId));
                        after = iHzFeatureChangeService.doSelectByPrimaryKey(after);
                    }

                    if (before == null) {
                        before = new HzFeatureChangeBean();
                        HzCfg0Record localRecord = new HzCfg0Record();
                        localRecord.setPuid(localParams.get(i).getPuid());
                        if ((beforeId = iHzFeatureChangeService.insertByCfg(localRecord, "HZ_CFG0_BEFORE_CHANGE_RECORD", "SEQ_HZ_FEATURE_BEFORE_CHANGE")) <= 0) {
                            logger.error("创建后自动同步变更前记录值失败，请联系管理员");
                            return false;
                        }
                        before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");
                        before.setId(Long.valueOf(beforeId));
                        before = iHzFeatureChangeService.doSelectByPrimaryKey(before);
                    }

//                    after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
//                    before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");

                    after.setVwoId(hzVwoInfo.getId());
                    after.setCfgIsInProcess(1);

                    before.setVwoId(hzVwoInfo.getId());
                    before.setCfgIsInProcess(1);

                    if (!iHzFeatureChangeService.doUpdateAfterByPk(after)) {
                        logger.error("更新" + localParams.get(i).getpCfg0ObjectId() + "的更新后VWO号失败，请联系系统管理员");
                    }
                    if (!iHzFeatureChangeService.doUpdateBeforeByPk(before)) {
                        logger.error("更新" + localParams.get(i).getpCfg0ObjectId() + "的更新前VWO号失败，请联系系统管理员");
                    }

                    record.setPuid(localParams.get(i).getPuid());
                    record.setCfgIsInProcess(1);
                    record.setVwoId(hzVwoInfo.getId());

                    if (!hzCfg0Service.doUpdate(record)) {
                        logger.error("更新" + record.getpCfg0ObjectId() + "VWO号失败，请联系系统管理员");
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
