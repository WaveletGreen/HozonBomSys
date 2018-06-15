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

}
