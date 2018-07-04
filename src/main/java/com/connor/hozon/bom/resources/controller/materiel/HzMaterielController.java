package com.connor.hozon.bom.resources.controller.materiel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: haozt
 * @Date: 2018/7/4
 * @Description: 物料
 */
@Controller
@RequestMapping(value = "materiel")
public class HzMaterielController {

    @RequestMapping("type")
    @ResponseBody
    public Map<String,Object> getMaterielType(){
        /**
         * 物料类型  严格按照注释来读写数据
         * （11 整车超级物料主数据
         * 21 整车衍生物料主数据
         * 31 虚拟件物料主数据
         * 41半成品物料主数据
         * 51 生产性外购物料主数据
         * 61自制备件物料主数据）
         */
        Map<String,Object> map = new HashMap<>();
        map.put("11","整车超级物料主数据");
        map.put("21","整车衍生物料主数据");
        map.put("31","虚拟件物料主数据");
        map.put("41","半成品物料主数据");
        map.put("51","生产性外购物料主数据");
        map.put("61","自制备件物料主数据");
        return map;
    }

}
