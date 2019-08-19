/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package integration.controller;

import net.sf.json.JSONObject;
import org.springframework.ui.Model;

/**
 *
 */
public class ExtraIntegrateController {
    /**
     * 添加进model中
     *
     * @param entities
     * @param model
     */
    public void addToModel(JSONObject entities, Model model) {
        model.addAttribute("msgOfSuccess", "发送成功项");
        model.addAttribute("msgOfFail", "发送失败项");
        model.addAttribute("success", entities.get("success"));
        model.addAttribute("fail", entities.get("fail"));
        model.addAttribute("total", entities.get("total"));
        model.addAttribute("totalOfSuccess", entities.get("totalOfSuccess"));
        model.addAttribute("totalOfFail", entities.get("totalOfFail"));
        model.addAttribute("totalOfOutOfParent", entities.get("totalOfOutOfParent"));
        model.addAttribute("totalOfUnknown", entities.get("totalOfUnknown"));
    }
}
