/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.config;

import cn.net.connor.hozon.dao.query.configuration.relevance.HzRelevanceQuery;
import com.connor.hozon.service.configuration.relevance.impl.HzRelevanceServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 相关性表controller
 * 配置管理controller的所有返回消息字段key都是msg
 * 配置管理controller的所有返回成功标志字段key都是status
 * 如发现不一致需要特殊处理
 * 已完成注释
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Controller
@RequestMapping("/relevance")
public class HzRelevanceController {
    /*** 相关性服务层，一个controller对应一个service*/
    @Autowired
    private HzRelevanceServiceImpl hzRelevanceService2;

    /**
     * 生成相关性代码，生成的时，将原有的历史相关性进行删除处理
     *
     * @param projectPuid 当前工作项目UID(主键)
     * @return 所有生成的相关性数据，前端只是显示一部分数据
     */
    @RequestMapping("/addRelevance")
    @ResponseBody
    public JSONObject addRelevance(@RequestParam String projectPuid) {
        return hzRelevanceService2.addRelevance(projectPuid);
    }

    /**
     * 查询当前项目下的相关性数据，采用封装的对象进行分页查询
     *
     * @param dto 一个封装相关性分页查询的对象，具体定义从{@link HzRelevanceQuery}查看各个字段与前端进行对应
     * @return 分页查询一组相关性数据
     */
    @RequestMapping(value = "/queryRelevance", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryRelevance(HzRelevanceQuery dto) {
        return hzRelevanceService2.queryRelevance(dto);
    }


    @RequestMapping(value = "/getChangePage")
    public String getChangePage(String projectUid, Model model) {
        hzRelevanceService2.getChangePageModel(projectUid, model);
        return "cfg/relevance/relevanceChangeFrom";
    }


    @RequestMapping(value = "/getChange")
    @ResponseBody
    public JSONObject getChange(String projectPuid, Long changeFromId) {
        return hzRelevanceService2.getChange(changeFromId, projectPuid);
    }

    @RequestMapping(value = "/goBackData")
    @ResponseBody
    public JSONObject goBackData(String projectPuid) {
        return hzRelevanceService2.goBackData(projectPuid);
    }
}
