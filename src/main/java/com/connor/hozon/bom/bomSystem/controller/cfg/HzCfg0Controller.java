package com.connor.hozon.bom.bomSystem.controller.cfg;

import com.connor.hozon.bom.bomSystem.dto.HzRelevanceBean;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0MainRecordDao;
import com.connor.hozon.bom.bomSystem.dao.cfg.HzCfg0OptionFamilyDao;
import com.connor.hozon.bom.bomSystem.service.cfg.HzCfg0Service;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.cfg.HzCfg0MainRecord;
import sql.pojo.cfg.HzCfg0Record;
import webservice.base.cfg.ZPPTCO002;
import webservice.base.options.ZPPTCO004;
import webservice.logic.Correlate;
import webservice.logic.Features;
import webservice.option.ActionFlagOption;
import webservice.option.CorrelateTypeOption;
import webservice.service.impl.cfg2.TransCfgService;
import webservice.service.impl.options4.TransOptionsService;

import java.util.*;

/**
 * Created by Fancyears·Maylos·Mayways
 * Description:
 * Date: 2018/6/6 16:55
 */
@Controller
@RequestMapping("/cfg0")
public class HzCfg0Controller {
    private final HzCfg0Service hzCfg0Service;
    private final HzCfg0MainRecordDao hzCfg0MainRecordDao;
    private final HzCfg0OptionFamilyDao hzCfg0OptionFamilyDao;
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
    public boolean add(@RequestBody HzCfg0Record record) {
        /**因为没有关联组，所以要新生成组的puid*/
        record.setpCfg0FamilyPuid(UUID.randomUUID().toString());
        /**生成自身的puid*/
        record.setPuid(UUID.randomUUID().toString());
        return hzCfg0Service.doInsertAddCfg(record);
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
    public boolean modify(@RequestBody HzCfg0Record record) {
        if (record == null || "".equalsIgnoreCase(record.getPuid()) || null == record.getPuid())
            return false;
        if (hzCfg0Service.doSelectOneByPuid(record.getPuid()) != null) {
            return hzCfg0Service.doUpdate(record);
        } else if (hzCfg0Service.doSelectOneAddedCfgByPuid(record.getPuid()) != null) {
            return hzCfg0Service.doUpdateAddedCfg(record);
        } else {
            return false;
        }
    }

    @RequestMapping(value = "/deleteByPuid", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteByPuid(@RequestBody List<HzCfg0Record> records) {
        List<HzCfg0Record> toDelete = new ArrayList<>();
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
                if (hzCfg0Service.doSelectOneAddedCfgByPuid(record.getPuid()) != null) {
                    toDelete.add(record);
                }
//                else if(hzCfg0Service.doSelectOneByPuid(record.getPuid()) != null){
//                    //如果需要删除原数据
//                    result.put("status", false);
//                    result.put("msg", "目前不允许删除原数据，请重试或联系系统管理员");
//                    return result;
//                }
                else {
                    result.put("status", false);
                    result.put("msg", "目前不允许删除原数据，请重试或联系系统管理员");
                    return result;
                }
            }
        }
        result.put("status", hzCfg0Service.doDeleteAddedCfgByList(toDelete));
        result.put("msg", "删除成功");
        return result;
    }

    @RequestMapping(value = "/sendToERP", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject sendToERP(@RequestBody List<HzCfg0Record> records) throws Exception {
        transCfgService.setClearInputEachTime(true);
        List<HzCfg0Record> toSend = new ArrayList<>();
        List<Features> featuresList = new ArrayList<>();
        Map<String, HzCfg0Record> _mapCoach = new HashMap<>();
        JSONObject result = new JSONObject();
        StringBuilder sbs = new StringBuilder();
        sbs.append("发送成功:<br/>");
        StringBuilder sbf = new StringBuilder();
        sbf.append("发送失败:<br/>");

        boolean hasFail = false;

        if (records == null || records.size() <= 0) {
            result.put("status", false);
            result.put("msg", "选择的列为空，请至少选择1列发送");
            return result;
        }
        records.forEach(_r -> {
            HzCfg0Record record = null;
            if ((record = hzCfg0Service.doSelectOneByPuid(_r.getPuid())) != null
                    || (record = hzCfg0Service.doSelectOneAddedCfgByPuid(_r.getPuid())) != null) {
                toSend.add(record);
            }
        });

        toSend.forEach(_s -> {
            Features features = new Features();
            String packnum = UUID.randomUUID().toString().replaceAll("-", "");
            //数据包号
            features.setPackNo(packnum);
            //行号
            features.setLineNum(packnum.substring(0, 5));
            //动作描述代码
            features.setActionFlag(ActionFlagOption.ADD);
            //特性编码
            features.setFeaturesCode(_s.getpCfg0FamilyName());
            //特性描述
            features.setFeaturesDescribe(_s.getpCfg0FamilyDesc());
            //特性值编码
            features.setPropertyValuesCode(_s.getpCfg0ObjectId());
            //特性值描述
            features.setPropertyValuesDescribe(_s.getpCfg0Desc());
            //            featuresList.add(features);
            transCfgService.getInput().getItem().add(features.getZpptci002());
            _mapCoach.put(packnum, _s);
        });
        transCfgService.execute();
        List<ZPPTCO002> list = transCfgService.getOut().getItem();
        if (list != null && list.size() > 0) {
            result.put("status", true);
            for (ZPPTCO002 _l : list) {
                if ("S".equalsIgnoreCase(_l.getTYPE())) {
                    sbs.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(_l.getZPACKNO()).getpCfg0ObjectId() + "<br/>");
                } else {
                    sbf.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(_l.getZPACKNO()).getpCfg0ObjectId() + "(" + _l.getMESSAGE() + ")<br/>");
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
        HzCfg0Record record = null;

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
            String packnum = UUID.randomUUID().toString().replaceAll("-", "");
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
        transOptionsService.execute();
        List<ZPPTCO004> list = transOptionsService.getOut().getItem();
        if (list != null && list.size() > 0) {
            result.put("status", true);
            for (ZPPTCO004 _l : list) {
                if ("S".equalsIgnoreCase(_l.getTYPE())) {
                    sbs.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(_l.getZPACKNO()).getRelevance() + "<br/>");
                } else {
                    if (_mapCoach.get(_l.getZPACKNO()) == null) {
                        continue;
                    }
                    sbf.append("&emsp;&emsp;&emsp;&emsp;" + _mapCoach.get(_l.getZPACKNO()).getRelevance() + "(" + _l.getMESSAGE() + ")<br/>");
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
