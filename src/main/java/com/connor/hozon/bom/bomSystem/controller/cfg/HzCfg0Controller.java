package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.controller.integrate.ExtraIntegrate;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0MainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0OptionFamilyService;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import com.connor.hozon.bom.bomSystem.service.integrate.SynMaterielService;
import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynFeatureService;
import integration.base.relevance.ZPPTCO004;
import integration.logic.Correlate;
import integration.option.ActionFlagOption;
import integration.option.CorrelateTypeOption;
import integration.service.impl.cfg2.TransCfgService;
import integration.service.impl.feature4.TransOptionsService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.HzCfg0MainRecord;
import sql.pojo.cfg.HzCfg0OptionFamily;
import sql.pojo.cfg.HzCfg0Record;

import java.util.*;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/6 16:55
 */
@Controller
@RequestMapping("/cfg0")
public class HzCfg0Controller extends ExtraIntegrate {
    private final HzCfg0Service hzCfg0Service;
    private final HzCfg0MainRecordDao hzCfg0MainRecordDao;
    private final HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;
    @Autowired
    ISynFeatureService iSynFeatureService;
    @Autowired
    HzCfg0OptionFamilyService cfg0OptionFamilyService;
    /**
     * 特性传输服务
     */
    @Autowired
    TransCfgService transCfgService;
    /***
     * 相关性传输服务
     */
    @Autowired
    TransOptionsService transOptionsService;
    /**
     * 日志记录
     */
    private final static Logger logger = LoggerFactory.getLogger(HzCfg0Controller.class);

    @Autowired
    public HzCfg0Controller(HzCfg0Service hzCfg0Service, HzCfg0MainRecordDao hzCfg0MainRecordDao, HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao) {
        this.hzCfg0Service = hzCfg0Service;
        this.hzCfg0MainRecordDao = hzCfg0MainRecordDao;
        this.hzCfg0OptionFamilyDao = hzCfg0OptionFamilyDao;
    }

    /******************************************特性表***********************************************/
    @RequestMapping("/loadFeature")
    @ResponseBody
    public Map<String, Object> loadCfg0(@RequestParam("projectPuid") String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<HzCfg0Record> records = hzCfg0Service.doLoadCfgListByProjectPuid(projectPuid);
        records.addAll(hzCfg0Service.doLoadAddedCfgListByProjectPuid(projectPuid));
        result.put("totalCount", records.size());
        result.put("result", records);
        return result;
    }

    @RequestMapping("/addPage")
    public String add(@RequestParam("projectPuid") String projectPuid, Model model) {
        HzCfg0MainRecord mainRecord = hzCfg0MainRecordDao.selectByProjectPuid(projectPuid);
        if (mainRecord == null) {
            return "error";
        }
        model.addAttribute("entity", mainRecord);
        return "cfg/feature/addFeature";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(@RequestBody HzCfg0Record record) throws Exception {
        JSONObject result = new JSONObject();

        /**生成自身的puid*/
        record.setpCfg0FamilyName(record.getpCfg0FamilyName().toUpperCase());
        record.setpCfg0ObjectId(record.getpCfg0ObjectId().toUpperCase());


        if (!hzCfg0Service.preCheck(record)) {
            result.put("status", false);
            result.put("msg", "<p style='color:red;'>特性值已存在</p>");
            return result;
        }
        record.setPuid(UUIDHelper.generateUpperUid());

        HzCfg0OptionFamily family = new HzCfg0OptionFamily();
        family.setpOfCfg0Main(record.getpCfg0MainItemPuid());
        family.setpOptionfamilyName(record.getpCfg0FamilyName());
        family.setpOptionfamilyDesc(record.getpCfg0FamilyDesc());

        HzCfg0OptionFamily _family = cfg0OptionFamilyService.doGetByCodeAndDescWithMain(family);
        if (_family == null) {
            family.setPuid(UUIDHelper.generateUpperUid());
            if (!cfg0OptionFamilyService.doInsert(family)) {
                result.put("status", false);
                result.put("msg", "添加特性" + family.getpOptionfamilyName() + "失败，请联系系统管理员");
                return result;
            }
        } else {
            family = _family;
        }

        record.setpCfg0FamilyPuid(family.getPuid());
        if (!checkString(record.getpCfg0Relevance())) {
            record.setpCfg0Relevance("$ROOT." + record.getpCfg0FamilyName() + " = '" + record.getpCfg0ObjectId() + "'");
        }
        if (hzCfg0Service.doInsertOne(record)) {
            result.put("status", true);
            result.put("msg", "添加特性值" + record.getpCfg0ObjectId() + "成功");
//            //发送到SAP,走流程
//            if (!SynMaterielService.debug) {
//                iSynFeatureService.addFeature(Collections.singletonList(record));
//            }
        } else {
            result.put("status", false);
            result.put("msg", "添加特性值" + record.getpCfg0ObjectId() + "失败，请联系系统管理员");
        }
        return result;
    }

    @RequestMapping("/modifyPage")
    public String modifyPage(@RequestParam("projectPuid") String puid, Model model) {
        HzCfg0Record record = hzCfg0Service.doSelectOneByPuid(puid);
        if (record == null) {
            record = hzCfg0Service.doSelectOneAddedCfgByPuid(puid);
            if (record == null) {
                model.addAttribute("msg", "没有找到对应的特性数据，请重试或联系系统管理员");
                return "errorWithEntity";
            }
        }
        model.addAttribute("entity", record);
        model.addAttribute("action", "./cfg0/modify");
        return "cfg/feature/modFeature";
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject modify(@RequestBody HzCfg0Record record) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        if (record == null || "".equalsIgnoreCase(record.getPuid()) || null == record.getPuid()) {
            result.put("status", false);
            result.put("msg", "没有选择任何1条数据，请选择1条数据");
            return result;
        }
        record.setpCfg0FamilyName(record.getpCfg0FamilyName().toUpperCase());
        record.setpCfg0ObjectId(record.getpCfg0ObjectId().toUpperCase());
        if (!hzCfg0Service.preCheck(record)) {
            result.put("status", false);
            result.put("msg", "已存在的特性值");
            return result;
        }

        if (!checkString(record.getpCfg0Relevance())) {
            record.setpCfg0Relevance("$ROOT." + record.getpCfg0FamilyName() + " = '" + record.getpCfg0ObjectId() + "'");
        }
        if (hzCfg0Service.doSelectOneByPuid(record.getPuid()) != null) {
            if (hzCfg0Service.doUpdate(record)) {
                result.put("msg", "更新特性值" + record.getpCfg0ObjectId() + "成功");
            } else {
                result.put("msg", "更新特性值" + record.getpCfg0ObjectId() + "失败");
            }
        } else if (hzCfg0Service.doSelectOneAddedCfgByPuid(record.getPuid()) != null) {
            if (hzCfg0Service.doUpdateAddedCfg(record)) {
                result.put("msg", "更新特性值" + record.getpCfg0ObjectId() + "成功");
            } else {
                result.put("msg", "更新特性值" + record.getpCfg0ObjectId() + "失败");
            }
        } else {
            result.put("msg", "更新特性值" + record.getpCfg0ObjectId() + "时发生错误，请联系系统管理员");
        }
        return result;
    }

    @RequestMapping(value = "/deleteByPuid", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteByPuid(@RequestBody List<HzCfg0Record> records) throws Exception {
        List<HzCfg0Record> toDelete = new ArrayList<>();
        Map<String, HzCfg0Record> mapOfDelete = new HashMap<>();
        JSONObject result = new JSONObject();
        if (records == null || records.size() <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列做删除");
            return result;
        }
        for (HzCfg0Record record : records) {
            if (record == null || "".equalsIgnoreCase(record.getPuid()) || null == record.getPuid()) {
                result.put("status", false);
                result.put("msg", "找不到需要删除的数据，请重试或联系系统管理员");
                return result;
            }
            //原始配置先不给删除，只能删除新加的配置项
            else {
                /*if (hzCfg0Service.doSelectOneAddedCfgByPuid(record.getPuid()) != null) {
                    toDelete.add(record);
                }
                else*/
                if (hzCfg0Service.doSelectOneByPuid(record.getPuid()) != null) {
                    //如果需要删除原数据
                    toDelete.add(record);
                    mapOfDelete.put(record.getpCfg0FamilyName() + "-" + record.getpCfg0FamilyDesc() + "-" + record.getpCfg0ObjectId() + "-" + record.getpCfg0FamilyDesc(), record);
//                    result.put("status", false);
//                    result.put("msg", "目前不允许删除原数据，请重试或联系系统管理员");
//                    return result;
                } else {
                    result.put("status", false);
                    result.put("msg", "找不到需要删除的数据，请重试或联系系统管理员");
//                    result.put("msg", "目前不允许删除原数据，请重试或联系系统管理员");
                    return result;
                }
            }
        }
        JSONObject resultFromSap = iSynFeatureService.deleteFeature(toDelete);

        Object obj = resultFromSap.get("_forDelete");
        List<HzCfg0Record> _toDelete = new ArrayList<>();

        if (obj != null && obj instanceof List) {
            if (((List) obj).size() > 0) {
                for (int i = 0; i < ((List<String>) obj).size(); i++) {
                    if (mapOfDelete.containsKey(((List<String>) obj).get(i))) {
                        _toDelete.add(mapOfDelete.get(((List) obj).get(i)));
                        logger.warn("---------------同步在SAP中删除特性:" + (mapOfDelete.get(((List) obj).get(i)).getpCfg0ObjectId()));
                    }
                }
            }
        } else {
            _toDelete.addAll(records);
        }
        if (_toDelete.size() > 0 && hzCfg0Service.doDeleteCfgByList(_toDelete)) {
            result.put("status", true);
            result.put("msg", "删除成功");
        }
        return result;
    }

    @RequestMapping(value = "/sendToERP", method = RequestMethod.POST)
    public String sendToERP(@RequestBody List<HzCfg0Record> records, Model model) throws Exception {
        JSONObject result = iSynFeatureService.addFeature(records);
        addToModel(result, model);
        return "stage/templateOfIntegrate";
    }


    /******************************************相关性**********************************************/
    @RequestMapping("/loadRelevance")
    @ResponseBody
    public Map<String, Object> loadRelevance(@RequestParam("projectPuid") String projectPuid) {
        Map<String, Object> result = new HashMap<>();
        List<HzRelevanceBean> _list = new ArrayList<>();
        int _index = 0;
        _index = hzCfg0Service.doLoadRelevance(projectPuid, _list, _index, "HZ_CFG0_RECORD");
        hzCfg0Service.doLoadRelevance(projectPuid, _list, _index, "HZ_CFG0_ADD_CFG_RECORD");
        result.put("totalCount", _list.size());
        result.put("result", _list);
        return result;
    }

    @RequestMapping(value = "/relModify", method = RequestMethod.POST)
    @ResponseBody
    public boolean relOption(@RequestBody HzRelevanceBean bean) {
        if (bean == null || "".equalsIgnoreCase(bean.getPuid()) || null == bean.getPuid()) {
            return false;
        }
        HzCfg0Record record;

        record = hzCfg0Service.doSelectOneByPuid(bean.getPuid());
        if (record != null) {
            record = hzCfg0Service.doSelectOneByPuid(bean.getPuid());
            record.setpCfg0Relevance(bean.getRelevanceCode());
            return hzCfg0Service.doUpdate(record);
        } else if ((record = hzCfg0Service.doSelectOneAddedCfgByPuid(bean.getPuid())) != null) {
            record.setpCfg0Relevance(bean.getRelevanceCode());
            return hzCfg0Service.doUpdateAddedCfg(record);
        } else {
            return false;
        }
    }

    /**
     * 相关性获取页面。根据<strong>page</strong>的值来进行判断，以此进行相关页面的返回
     *
     * @param uid   可以是项目的puid，或者配置值的puid
     * @param page  相关页面，addPage或者modifyPage
     * @param model
     * @return 返回下对应的前端页面
     */
    @RequestMapping("/relModifyPage")
    public String relPage(@RequestParam("uid") String uid, @RequestParam("page") String page, Model model) {
        HzRelevanceBean bean = new HzRelevanceBean();
        //其实没有添加的页面
        if ("addPage".equals(page)) {
            HzCfg0MainRecord mainRecord = hzCfg0MainRecordDao.selectByProjectPuid(uid);
            if (mainRecord == null) {
                return "error";
            }
            model.addAttribute("action", "./cfg0/relModify");
        } else if ("modifyPage".equals(page)) {
            HzCfg0Record record = hzCfg0Service.doSelectOneByPuid(uid);
            if (record == null) {
                record = hzCfg0Service.doSelectOneAddedCfgByPuid(uid);
                if (record == null) {
                    model.addAttribute("msg", "没有找到对应的特性数据，请重试或联系系统管理员!");
                    return "errorWithEntity";
                }
                record.setWhichTable("HZ_CFG0_ADD_CFG_RECORD");
            } else {
                record.setWhichTable("HZ_CFG0_RECORD");
            }

            bean.setPuid(record.getPuid());
            bean.setRelevance(record.getpCfg0FamilyName() + "-" + record.getpCfg0ObjectId());
            bean.setRelevanceDesc((record.getpCfg0FamilyDesc() == null ? "" : record.getpCfg0FamilyDesc()) + "-" + (record.getpCfg0Desc() == null ? "" : record.getpCfg0Desc()));
            bean.setRelevanceCode(record.getpCfg0Relevance());
            bean.set_table(record.getWhichTable());

            model.addAttribute("action", "./cfg0/relModify");
        }
        model.addAttribute("entity", bean);
        return "cfg/relevance/mergeRelevance";
    }

    @RequestMapping(value = "/sendRelToERP", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendRelToERP(@RequestBody List<HzRelevanceBean> beans) throws Exception {
        //清空上次传输的内容
        transOptionsService.setClearInputEachTime(true);
        Map<String, HzRelevanceBean> _mapCoach = new HashMap<>();
        JSONObject result = new JSONObject();
        StringBuilder sbs = new StringBuilder();
        sbs.append("发送成功:<br/>");
        StringBuilder sbf = new StringBuilder();
        sbf.append("发送失败:<br/>");

        boolean hasFail = false;
        if (beans == null || beans.size() <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }


        beans.forEach(bean -> {
            Correlate correlate = new Correlate();
            String packnum = UUIDHelper.generateUpperUid();
            correlate.setPackNo(packnum);
            correlate.setLineNum(bean.getPuid().substring(0, 5));
            //动作描述代码
            correlate.setActionFlag(ActionFlagOption.ADD);
            //相关性
            correlate.setCorrelate(bean.getRelevance());
            //相关性描述
            correlate.setCorrelateDescript(bean.getRelevanceDesc());
            //相关性状态
            correlate.setCorrelateState("5");
            //创建日期
            correlate.setCreateDate(new Date());
            //相关性类型
            correlate.setCorrelateType(CorrelateTypeOption.CorrelateType_1);
            //相关性代码
            correlate.setCorrelateCode(bean.getRelevanceCode());
            _mapCoach.put(packnum, bean);
            transOptionsService.getInput().getItem().add(correlate.getZpptci004());
        });
        if (!SynMaterielService.debug)
            transOptionsService.execute();
        List<ZPPTCO004> list = transOptionsService.getOut().getItem();
        if (list != null && list.size() > 0) {
            result.put("status", true);
            for (ZPPTCO004 _l : list) {
                if ("S".equalsIgnoreCase(_l.getPTYPE())) {
                    sbs.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(_l.getPPACKNO()).getRelevance() + "<br/>");
                } else {
                    if (_mapCoach.get(_l.getPPACKNO()) == null) {
                        continue;
                    }
                    sbf.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(_l.getPPACKNO()).getRelevance() + "(" + _l.getPMESSAGE() + ")<br/>");
                    hasFail = true;
                }
            }
        }
        if (hasFail) {
            result.put("msg", sbs.append("<br/>" + sbf).toString());
        } else {
            result.put("msg", sbs.toString());
        }
        return result;
    }


}
