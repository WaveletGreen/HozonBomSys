/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.bom.controller.process;

import cn.net.connor.hozon.services.request.process.ProcessRequestDTO;
import com.connor.hozon.bom.service.process.IProcessManagerService;
import com.connor.hozon.bom.service.process.impl.ProcessManagerService;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 流程控制
 * @Date: Created in  2018/11/22 11:09
 * @Modified By:
 */
@Controller
@RequestMapping("process")
public class ProcessController {
    @Autowired
    IProcessManagerService processManagerService;

    /**
     * 获取选择审核人员的页面
     *
     * @return
     */
    @RequestMapping("getAuditorPage")
    public String getAuditorPage(@RequestParam Long orderId, Model model) {
        User user = null;
        if ((user = processManagerService.checkOrderAuditor(orderId)) != null) {
            model.addAttribute("msg", "该表单已选择审核人:" + user.getUsername());
            return "errorWithEntity";
        } else {
            return "change/changeForm/changeSelectAuditor";
        }
    }

    /**
     * 开始发起流程
     * 记录审核人信息
     * 更新表单的审核开始和审核人
     * 创建审核人任务，并启动任务
     *
     * @param processRequestDTO 前端传回的审核数据对象
     * @return
     */
    @RequestMapping(value = "doStartProcess", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doStartProcess(@RequestBody ProcessRequestDTO processRequestDTO) {
        return (Map<String, Object>) processManagerService.start(processRequestDTO.getOrderId(), processRequestDTO.getUserId());
    }

    /**
     * 审核人确定审核完毕
     * 根据入参，必定含有agreement字段，该字段标识审核人是否同意审核
     * 当agreement="1"，即表示审核通过，并根据表单的ID进行发布操作{@link ProcessManagerService#release(long, Object...)}
     * 当agreement="0"，即表示审核不通过，并根据表单的ID进行流程终止操作{@link ProcessManagerService#release(long, Object...)}，即表示流程终止，也表示该流程已完成
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "doCheck", method = RequestMethod.POST)
    @ResponseBody
    public Object doCheck(@RequestBody Map<String, String> params) {
        Map<String, Object> result = new HashMap<>();
        if (params == null || params.isEmpty() || !params.containsKey("agreement")) {
            result.put("status", false);
            result.put("msg", "入参无效");
            return result;
        }
        switch (params.get("agreement")) {
            case "1"://同意
                return processManagerService.release(Long.parseLong(params.get("id")), params.get("opiBomMngOpinion"));
            case "0"://不同意
                return processManagerService.interrupt(Long.parseLong(params.get("id")), params.get("opiBomMngOpinion"));
            default:
                result.put("status", false);
                result.put("msg", "审核失败");
                return result;
        }
    }
}
