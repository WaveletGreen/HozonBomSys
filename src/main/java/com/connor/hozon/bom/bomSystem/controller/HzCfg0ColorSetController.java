/*
 * Copyright (c) 2018.
 * This file was wrote by fancyears·milos·maywas @connor. Any question/bug you can post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.controller;


import com.connor.hozon.bom.bomSystem.helper.DateStringHelper;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.service.color.HzCfg0ColorSetService;
import com.connor.hozon.bom.common.base.entity.QueryBase;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.sys.commen.Error;
import com.connor.hozon.bom.sys.entity.User;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.color.HzCfg0ColorSet;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.connor.hozon.bom.bomSystem.helper.StringHelper.checkString;


/**
 * @Author: Fancyears·Maylos·Maywas
 * @Description: 颜色库
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/colorSet")
public class HzCfg0ColorSetController {

    @Autowired
    HzCfg0ColorSetService colorSerService;

    private final static Logger logger = LoggerFactory.getLogger(HzCfg0ColorSetController.class);

    /**
     * @param
     * @return 颜色对象map
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 搜索出所有的颜色对象
     * @Date: 2018/5/21 15:53
     */
    @RequestMapping(value = "/queryAll2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryAll2(QueryBase queryBase) {
        System.out.println();
        return colorSerService.queryAll2(queryBase);
    }

    @RequestMapping(value = "/getAllColorSet", method = RequestMethod.GET)
    @ResponseBody
    public List<HzCfg0ColorSet> getAllColorSet() {
        return colorSerService.doGetAll();
    }

    /**
     * @param entity 颜色对象
     * @param model  spring的model
     * @return java.lang.String
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 先判断是否有颜色信息，有则返回页面给予更新，没有则返回错误页面
     * @Date: 2018/5/21 15:52
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HzCfg0ColorSet entity, Model model) {
        if ((entity = colorSerService.getById(entity)) != null) {
           /* entity.setStrColorAbolishDate(DateStringHelper.dateToString2(entity.getpColorAbolishDate()));
            entity.setStrColorEffectedDate(DateStringHelper.dateToString2(entity.getpColorEffectedDate()));*/
            model.addAttribute("entity", entity);
            return "cfg/color/colorUpdate";
        } else {
            Error error = new Error();
            error.setMsg("查找不到颜色信息:" + entity.getpColorName());
            model.addAttribute("entity", error);
            return "error";
        }
    }

    /**
     * @return 一个添加颜色信息的前端页面
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 获取添加颜色信息的页面
     * @Date: 2018/5/21 15:51
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "cfg/color/addColor";
    }

    /**
     * @param set 颜色对象
     * @return java.lang.Boolean
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 更新一个颜色对象的信息
     * @Date: 2018/5/21 15:51
     */
    @RequestMapping(value = "/updateWithEntity", method = RequestMethod.POST)
    @ResponseBody
    public Boolean update(@RequestBody HzCfg0ColorSet set) {
        Date now = new Date();
        User user = UserInfo.getUser();
        /*try
        {
            set.setpColorAbolishDate(DateStringHelper.stringToDate2(set.getStrColorAbolishDate()));
            set.setpColorEffectedDate(DateStringHelper.stringToDate2(set.getStrColorEffectedDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("从字符串解析时间失败", e);
        }
        if (now.before(set.getpColorAbolishDate())) {
            set.setpColorStatus(1);
        } else {
            set.setpColorStatus(0);
        }*/
        set.setpColorModifier(user.getUserName());
        set.setpColorModifyDate(now);
        return colorSerService.doUpdate(set);
    }

    /**
     * @param set
     * @return net.sf.json.JSONObject
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 添加一个颜色信息
     * @Date: 2018/5/21 15:50
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(@RequestBody HzCfg0ColorSet set) {
        JSONObject result = new JSONObject();
        Date now = new Date();
        User user = UserInfo.getUser();
        boolean resultFromDB;
        if (set.getPuid() == null || "".equals(set.getPuid())) {
            set.setPuid(UUIDHelper.generateUpperUid());
        }
        /*try {

            set.setpColorEffectedDate(DateStringHelper.stringToDate2(set.getStrColorEffectedDate()));
            if (set.getpColorAbolishDate() == null) {
                set.setpColorAbolishDate(new Date());
            }
            if (set.getpColorEffectedDate().after(set.getpColorAbolishDate())) {
                set.setpColorStatus(0);
            } else {
                set.setpColorStatus(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("从字符串解析时间失败", e);
        }*/
        //生效时间由审核完成只有更新
        set.setpColorModifyDate(now);
        //失效时间设置为9999
        set.setpColorAbolishDate(DateStringHelper.forever());
        //创建时间由数据库默认值定义
        //  set.setpColorCreateDate(now);
        set.setpColorModifier(user.getUserName());
        set.setpColorStatus(0);

        while (true) {
            if (colorSerService.getById(set) == null) {
                resultFromDB = colorSerService.doAddOne(set);
                break;
            } else {
                set.setPuid(UUIDHelper.generateUpperUid());
            }
        }
        if (resultFromDB) {
            result.put("status", resultFromDB);
            result.put("msg", "添加颜色信息:" + set.getpColorName() + "成功");
        } else {
            result.put("status", resultFromDB);
            result.put("msg", "添加颜色信息:" + set.getpColorName() + "失败,请联系系统管理员");
        }
        return result;
    }

    /**
     * @param set 颜色信息集合
     * @return net.sf.json.JSONObject
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 删除一组颜色信息
     * @Date: 2018/5/21 15:50
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestBody List<HzCfg0ColorSet> set) {
        JSONObject result = new JSONObject();
        StringBuilder sb = new StringBuilder();
        set.forEach(_set ->
                sb.append(_set.getpColorName() + ", ")
        );
        if (colorSerService.doDeleteByList(set)) {
            result.put("status", true);
            result.put("msg", "删除颜色信息:" + sb + "成功");
        } else {
            result.put("status", false);
            result.put("msg", "删除颜色信息:" + sb + "失败");
        }
        return result;
    }


    @RequestMapping(value = "/validateCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validateCodeWithPuid(HzCfg0ColorSet set) {
        JSONObject result = new JSONObject();
        result.put("valid", true);
        HzCfg0ColorSet _set = null;
        //有puid，则时更新，没有则为新增
        if (checkString(set.getPuid())) {
            _set = colorSerService.getById(set);
            //根据puid查出来的同名，代表自身，则验证通过
            if (_set.getpColorCode().equals(set.getpColorCode())) {
                result.put("valid", true);
            } else if ((_set = colorSerService.doGetByColorCode(set)) != null) {
                //不是自身，更换了代号，检查是否有同代号的，有同代号则不允许验证通过
                result.put("valid", false);
            }
        } else if ((_set = colorSerService.doGetByColorCode(set)) != null) {
            result.put("valid", false);
        }
        return result;
    }
}
