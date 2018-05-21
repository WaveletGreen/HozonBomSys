package com.connor.hozon.bom.banchOfFancyears.controller.cfg;


import com.connor.hozon.bom.banchOfFancyears.service.cfg.HzCfg0ColorSerService;
import com.connor.hozon.bom.sys.commen.Error;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.cfg.HzCfg0ColorSet;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/colorSet")
public class HzCfg0ColorSetController {

    @Autowired
    HzCfg0ColorSerService colorSerService;

//    @RequestMapping(value = "/queryAll", method = RequestMethod.GET)
//    @ResponseBody
//    public JSONArray queryAll() {
//        if (baseSQLUtil == null) {
//            baseSQLUtil = new BaseSQLUtil();
//        }
//        JSONArray result = new JSONArray();
//        String[] orgName = new String[]{"colorOfSet", "colorName", "colorCode", "comment", "hide"};
//        String[] localName = new String[]{"colorOfSet", "colorName", "colorCode", "comment", "hide"};
//        result.add(0, orgName);
//        result.add(1, localName);
//
//        List<HzCfg0ColorSet> colorSet = baseSQLUtil.executeQuery(new HzCfg0ColorSet(), "sql.mapper.cfg.i.HzCfg0ColorSetMapper.selectAll");
//        colorSet.forEach((set) -> {
//            JSONObject object = new JSONObject();
//            object.put(orgName[0], set.getpColorOfSet());
//            object.put(orgName[1], set.getpColorName());
//            object.put(orgName[2], set.getpColorCode());
//            object.put(orgName[3], set.getpColorComment());
//            object.put("hide", set.getPuid());
//            result.add(object);
//        });
//        return result;
//    }

    /**
     * @param
     * @return 颜色对象map
     * @Author: Fancyears·Maylos·Mayways
     * @Description: 搜索出所有的颜色对象
     * @Date: 2018/5/21 15:53
     */
    @RequestMapping(value = "/queryAll2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> queryAll2() {
        return colorSerService.queryAll2();
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
        boolean resultFromDB = false;
        if (set.getPuid() == null || "".equals(set.getPuid())) {
            set.setPuid(UUID.randomUUID().toString());
        }
        while (true) {
            if (colorSerService.getById(set) == null) {
                resultFromDB = colorSerService.doAddOne(set);
                break;
            } else {
                set.setPuid(UUID.randomUUID().toString());
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
}
