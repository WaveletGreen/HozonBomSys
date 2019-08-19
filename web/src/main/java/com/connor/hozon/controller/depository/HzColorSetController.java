/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.depository;


import cn.net.connor.hozon.common.entity.QueryBase;
import cn.net.connor.hozon.common.util.DateStringUtils;
import cn.net.connor.hozon.common.util.UUIDHelper;
import cn.net.connor.hozon.dao.pojo.depository.accessories.HzAccessoriesLibs;
import cn.net.connor.hozon.dao.pojo.depository.color.HzCfg0ColorSet;
import cn.net.connor.hozon.dao.pojo.depository.color.HzColorSetQuery;
import cn.net.connor.hozon.dao.pojo.sys.User;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesLibsDAO;
import cn.net.connor.hozon.services.service.depository.color.HzColorSetService;
import cn.net.connor.hozon.services.service.sys.UserInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 颜色库controller
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 如发现不一致需要特殊处理
 * 已完成注释
 * @Date: Created in 2018/8/30 18:53
 * @Modified By:
 */
@Controller
@RequestMapping("/colorSet")
public class HzColorSetController {

    /***颜色库服务*/
    @Autowired
    HzColorSetService colorSerService;

    /***工艺辅料库dao*/
    @Autowired
    private HzAccessoriesLibsDAO hzAccessoriesLibsDAO;
    /**
     * 颜色库分页查询
     *
     * @param hzColorSetQuery 分页描述对象，采用bootstrap table自带的分页，从前端table定义分页对象。
     *                  采用POST方法回传的是普通JSON对象包含{@link QueryBase}的各个字段即可
     * @return 一组颜色对象
     */
    @RequestMapping(value = "/queryAll2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryAll2(HzColorSetQuery hzColorSetQuery) {
        return colorSerService.selectAll(hzColorSetQuery);
    }

    @RequestMapping(value = "/getAllColorSet", method = RequestMethod.GET)
    @ResponseBody
    public List<HzCfg0ColorSet> getAllColorSet() {
        return colorSerService.doGetAll();
    }

    /**
     * 先判断是否有颜色信息，有则返回页面给予更新，没有则返回错误页面
     *
     * @param entity 颜色对象
     * @param model  不用传
     * @return 错误页面/更新页面
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(HzCfg0ColorSet entity, Model model) {
        if ((entity = colorSerService.getById(entity)) != null) {
           /* entity.setStrColorAbolishDate(DateStringUtils.dateToString2(entity.getpColorAbolishDate()));
            entity.setStrColorEffectedDate(DateStringUtils.dateToString2(entity.getpColorEffectedDate()));*/
            model.addAttribute("entity", entity);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("pColorEffectedDate",entity.getpColorEffectedDate()==null?"":sdf.format(entity.getpColorEffectedDate()));
            model.addAttribute("pColorAbolishDate",entity.getpColorAbolishDate()==null?"":sdf.format(entity.getpColorAbolishDate()));
            return "cfg/color/colorUpdate";
        } else {
            model.addAttribute("msg", "查找不到颜色信息:" + entity.getpColorName());
            return "errorWithEntity";
        }
    }

    /**
     * 获取添加颜色信息的页面
     *
     * @return 一个添加颜色信息的前端页面
     */
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "cfg/color/addColor";
    }



    /**
     * 更新一个颜色对象的信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateWithEntity2", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update2(@RequestBody Map<String, String> map) throws ParseException {
        JSONObject result = new JSONObject();
        Date now = new Date();
        User user = UserInfo.getUser();
        /*try
        {
            set.setpColorAbolishDate(DateStringUtils.stringToDate2(set.getStrColorAbolishDate()));
            set.setpColorEffectedDate(DateStringUtils.stringToDate2(set.getStrColorEffectedDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            logger.error("从字符串解析时间失败", e);
        }
        if (now.before(set.getpColorAbolishDate())) {
            set.setpColorStatus(1);
        } else {
            set.setpColorStatus(0);
        }*/

        HzCfg0ColorSet set = new HzCfg0ColorSet();
        set.setPuid(map.get("puid"));
        set.setpColorOfSet(map.get("pColorOfSet"));
        set.setpColorName(map.get("pColorName"));
        set.setpColorCode(map.get("pColorCode"));
        set.setpColorPlate(map.get("pColorPlate"));
        set.setpColorIsMultiply(map.get("pColorIsMultiply"));
        set.setpColorComment(map.get("pColorComment"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        set.setpColorEffectedDate(sdf.parse(map.get("strColorEffectedDate")));
        set.setpColorAbolishDate(sdf.parse(map.get("strColorAbolishDate")));
        String csPaintMaterielCodes = "";
        List<String> materielCodeList = new ArrayList<String>();
        for (int i = 0; i < (map.size() - 7); i++) {
            String materielCode = map.get("color_" + i);
            if (materielCode == null || "".equals(materielCode)) {
                continue;
            }
            materielCodeList.add(materielCode);
        }
        if (materielCodeList != null && materielCodeList.size() > 0) {
            List<String> csPaintMaterielUidList = hzAccessoriesLibsDAO.queryAccessoriesListByMaterielCode(materielCodeList);
            if (csPaintMaterielUidList.size() != materielCodeList.size()) {
                result.put("status", false);
                result.put("msg", "油漆物料编号重复或错误");
                return result;
            }
            Integer materielSize = csPaintMaterielUidList.size();
            String csPaintMaterielUids = "";
            for (int i = 0; i < materielSize; i++) {
                csPaintMaterielCodes += materielCodeList.get(i);
                csPaintMaterielUids += csPaintMaterielUidList.get(i);
                if (materielSize > 1 && i < (materielSize - 1)) {
                    csPaintMaterielCodes += "<br>";
                    csPaintMaterielUids += "|";
                }
            }
            set.setCsPaintMaterielCodes(csPaintMaterielCodes);
            set.setCsPaintMaterielUids(csPaintMaterielUids);
        }

        set.setpColorModifier(user.getUsername());
        set.setpColorModifyDate(now);
        if (colorSerService.doUpdate(set)) {
            result.put("status", true);
            result.put("msg", "颜色库修改成功");
        } else {
            result.put("status", false);
            result.put("msg", "颜色库修改失败");
        }
        return result;
    }


    /**
     * 添加一个颜色信息
     * @param map
     * @return
     */
    @RequestMapping(value = "/add2", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add2(@RequestBody Map<String, String> map) {
        JSONObject result = new JSONObject();
        Date now = new Date();
        User user = UserInfo.getUser();
        boolean resultFromDB;

        HzCfg0ColorSet set = new HzCfg0ColorSet();
        set.setpColorOfSet(map.get("pColorOfSet"));
        set.setpColorName(map.get("pColorName"));
        set.setpColorCode(map.get("pColorCode"));
        set.setpColorPlate(map.get("pColorPlate"));
        set.setpColorIsMultiply(map.get("pColorIsMultiply"));
        set.setpColorComment(map.get("pColorComment"));

        String csPaintMaterielCodes = "";
        List<String> materielCodeList = new ArrayList<String>();
        for (int i = 0; i < (map.size() - 6); i++) {
            String materielCode = map.get("color_" + i);
            if (materielCode == null || "".equals(materielCode)) {
                continue;
            }
            materielCodeList.add(materielCode);
        }
        if (materielCodeList != null && materielCodeList.size() > 0) {
            List<String> csPaintMaterielUidList = hzAccessoriesLibsDAO.queryAccessoriesListByMaterielCode(materielCodeList);
            if (csPaintMaterielUidList.size() != materielCodeList.size()) {
                result.put("status", false);
                result.put("msg", "油漆物料编号错误");
                return result;
            }
            Integer materielSize = csPaintMaterielUidList.size();
            String csPaintMaterielUids = "";
            for (int i = 0; i < materielSize; i++) {
                csPaintMaterielCodes += materielCodeList.get(i);
                csPaintMaterielUids += csPaintMaterielUidList.get(i);
                if (materielSize > 1 && i < (materielSize - 1)) {
                    csPaintMaterielCodes += "<br>";
                    csPaintMaterielUids += "|";
                }
            }
            set.setCsPaintMaterielCodes(csPaintMaterielCodes);
            set.setCsPaintMaterielUids(csPaintMaterielUids);
        }
        if (set.getPuid() == null || "".equals(set.getPuid())) {
            set.setPuid(UUIDHelper.generateUpperUid());
        }
        //生效时间由审核完成只有更新
        set.setpColorModifyDate(now);
        //失效时间设置为9999
        set.setpColorAbolishDate(DateStringUtils.forever());
        //创建时间由数据库默认值定义
        //  set.setpColorCreateDate(now);
        set.setpColorModifier(user.getUsername());
        set.setpColorStatus(0);
        set.setpColorEffectedDate(new Date());
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
     * 验证油漆物料号，每一个颜色都关联n个油漆物料号，每个油漆物料号{@code materielCode}都需要经过验证才允许通过验证
     *
     * @param materielCode 油漆物料号
     * @return 验证通过与否标识和消息
     */
    @RequestMapping("checkMaterielCode")
    @ResponseBody
    public JSONObject checkMaterielCode(String materielCode) {
        JSONObject result = new JSONObject();
        result.put("status", true);
        HzAccessoriesLibs hzAccessoriesLibs = hzAccessoriesLibsDAO.queryAccessoriesByMaterielCode(materielCode);
        if (hzAccessoriesLibs == null) {
            result.put("status", false);
            result.put("msg", "油漆物料编号错误");
            return result;
        }
        return result;

    }

    /**
     * 删除一组颜色信息
     *
     * @param set 颜色信息集合
     * @return 成功/失败标志和消息
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delete(@RequestBody List<HzCfg0ColorSet> set) {
        return colorSerService.delete(set);

    }

    /**
     * 验证颜色代码是否存在，当颜色代码存在时则前端验证不允许通过，反之验证通过
     *
     * @param set 颜色对象，该对象必须存在的颜色代码，否则验证不通过
     * @return 前端验证数据，依赖bootstrap验证规则，由bootstrap自动解析
     */
    @RequestMapping(value = "/validateCodeWithPuid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JSONObject validateCodeWithPuid(HzCfg0ColorSet set) {
        return colorSerService.validateCodeWithId(set);

    }

    /**********************************************废除方法****************************************/
    /**
     * 更新颜色对象，该方法已废除，因为无法更新油漆物料号在颜色对象上
     *
     * @param set 颜色对象
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/updateWithEntity", method = RequestMethod.POST)
    @ResponseBody
    public Boolean update(@RequestBody HzCfg0ColorSet set) {
        Date now = new Date();
        User user = UserInfo.getUser();
        set.setpColorModifier(user.getUsername());
        set.setpColorModifyDate(now);
        return colorSerService.doUpdate(set);
    }

    /**
     * 添加一个颜色信息，需要油漆物料号，该方法已废除
     *
     * @param set 颜色对象
     * @return 添加成功与否标志和消息
     */
    @Deprecated
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject add(@RequestBody HzCfg0ColorSet set) {
        return colorSerService.add(set);
    }
    /**********************************************废除方法****************************************/

}
