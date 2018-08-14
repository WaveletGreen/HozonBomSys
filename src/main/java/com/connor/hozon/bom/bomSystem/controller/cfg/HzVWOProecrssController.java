package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzFeatureChangeService;
import com.connor.hozon.bom.bomSystem.service.iservice.cfg.vwo.IHzVwoInfoService;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSON;
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
    public JSONObject featureGetIntoVWO(@RequestParam String projectUid, @RequestBody List<HzCfg0Record> beans) {
        User user = UserInfo.getUser();
        Date now = new Date();
        JSONObject result = new JSONObject();
        if (beans != null && beans.size() > 0) {
            List<String> puids = new ArrayList<>();
            beans.forEach(bean -> puids.add(bean.getPuid()));
            List<HzCfg0Record> lists = hzCfg0Service.doLoadListByPuids(puids);
            List<HzCfg0Record> localParams = lists.stream().filter(l -> l != null).collect(Collectors.toList());
            Long id = -1L;
            if (beans.size() != localParams.size()) {
                logger.error("搜索出的特性值总数与发起VWO流程的特性值的总数不一致，请检查数据核对数据是否被删除");
                result.put("status", false);
                result.put("msg", "搜索出的特性值总数与发起VWO流程的特性值的总数不一致，请检查数据核对数据是否被删除");
                return result;
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
                }
                hzVwoInfo.setVwoCreator(user.getUserName());
                hzVwoInfo.setVwoCreateDate(now);
                hzVwoInfo.setProjectUid(projectUid);
                hzVwoInfo.setVwoType(1);
                hzVwoInfo.setVwoStatus(1);
                if ((id = iHzVwoInfoService.doInsert(hzVwoInfo)) <= 0) {
                    logger.error("创建新的VWO号失败，请联系系统管理员");
                    result.put("status", false);
                    result.put("msg", "创建新的VWO号失败，请联系系统管理员");
                }

                hzVwoInfo.setId(id);
                HzFeatureChangeBean after = new HzFeatureChangeBean();
                HzFeatureChangeBean before = new HzFeatureChangeBean();
                HzCfg0Record record = new HzCfg0Record();
                Long afterId = -1L, beforeId = -1L;
                for (int i = 0; i < localParams.size(); i++) {

                    if (1 == localParams.get(i).getCfgIsInProcess()) {
                        result.put("status", false);
                        result.put("msg", localParams.get(i).getpCfg0ObjectId() + "已在VWO变更流程中，不允许重复发起流程");
                        return result;
                    }

                    after.setCfgPuid(localParams.get(i).getPuid());
                    before.setCfgPuid(localParams.get(i).getPuid());
//                    after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
//                    before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");

                    after = iHzFeatureChangeService.doFindNewestChangeFromAfter(after);
                    before = iHzFeatureChangeService.doFindNewestChangeFromBefore(before);


                    if (after == null) {
                        after = new HzFeatureChangeBean();
                        if ((afterId = iHzFeatureChangeService.insertByCfgAfter(localParams.get(i))) <= 0) {
                            logger.error("创建后自动同步变更后记录值失败，请联系管理员");
                            result.put("status", false);
                            result.put("msg", localParams.get(i).getpCfg0ObjectId() + "创建后自动同步变更后记录值失败，请联系管理员");
                            return result;
                        }
//                        after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
                        /*这一段估计有bug*/
                        after.setId(afterId);
                        after = iHzFeatureChangeService.doSelectAfterByPk(after);
                    }

                    if (before == null) {
                        before = new HzFeatureChangeBean();
                        HzCfg0Record localRecord = new HzCfg0Record();
                        localRecord.setPuid(localParams.get(i).getPuid());
                        if ((beforeId = iHzFeatureChangeService.insertByCfgBefore(localRecord)) <= 0) {
                            logger.error("创建后自动同步变更前记录值失败，请联系管理员");
                            result.put("status", false);
                            result.put("msg", localParams.get(i).getpCfg0ObjectId() + "创建后自动同步变更前记录值失败，请联系管理员");
                            return result;
                        }
                        //before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");
                        /*这一段估计有bug*/
                        before.setId(beforeId);
                        before = iHzFeatureChangeService.doSelectBeforeByPk(before);
                    }

//                    after.setTableName("HZ_CFG0_AFTER_CHANGE_RECORD");
//                    before.setTableName("HZ_CFG0_BEFORE_CHANGE_RECORD");


                    setVwo(after, hzVwoInfo.getId(), now, user);
                    setVwo(before, hzVwoInfo.getId(), now, user);

//                    before.setVwoId(hzVwoInfo.getId());
//                    before.setCfgIsInProcess(1);
//                    before.setProcessStartDate(now);
//                    before.setProcessStarter(user.getUserName());

                    if (!iHzFeatureChangeService.doUpdateAfterByPk(after)) {
                        logger.error("更新" + localParams.get(i).getpCfg0ObjectId() + "的更新后VWO号失败，请联系系统管理员");
                    }
                    if (!iHzFeatureChangeService.doUpdateBeforeByPk(before)) {
                        logger.error("更新" + localParams.get(i).getpCfg0ObjectId() + "的更新前VWO号失败，请联系系统管理员");
                    }

                    record.setPuid(localParams.get(i).getPuid());
                    record.setCfgIsInProcess(1);
                    record.setVwoId(hzVwoInfo.getId());
                    record.setCfgStatus(0);
                    if (!hzCfg0Service.doUpdate(record)) {
                        logger.error("更新" + record.getpCfg0ObjectId() + "VWO号失败，请联系系统管理员");
                    }
                }
            }
            result.put("status", true);
            result.put("msg", "发起VWO流程成功");
            return result;
        } else {
            result.put("status", false);
            result.put("msg", "请至少选择1个特性值发起流程");
            return result;
        }
    }

    private void setVwo(HzFeatureChangeBean bean, Long id, Date now, User user) {
        bean.setVwoId(id);
        bean.setCfgIsInProcess(1);
        bean.setProcessStartDate(now);
        bean.setProcessStarter(user.getUserName());
        bean.setProcessStatus(1);
    }
}
