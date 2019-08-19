/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.bom.bom;

import cn.net.connor.hozon.dao.query.bom.sparePart.SparePartOfProjectQuery;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartPostDTO;
import com.alibaba.fastjson.JSONObject;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartBomQueryResponse;
import com.connor.hozon.service.bom.bomData.sparePart.SparePartsBomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 备件Bom Controller
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 9:09
 * @Modified By:
 */
@Controller
@RequestMapping("sparePartsBom")
public class SparePartsBomController {
    @Autowired
    private SparePartsBomService sparePartsBomService;

    /**
     * 获取到增加页面
     *
     * @return
     */
    @RequestMapping("getPage")
    public String getPage(String type, Model model) {
        if("add".equals(type)){
            model.addAttribute("type","add");
            model.addAttribute("url","./sparePartsBom/addSparePart");
        }
        else if("update".equals(type)){
            model.addAttribute("type","update");
            model.addAttribute("url","./sparePartsBom/updateSparePart");
        }
        return "bomManage/sparePart/addOrUpdate";
    }


    /**
     * 获取到增加子件页面
     *
     * @return
     */
    @RequestMapping("addChildPage")
    public String addChildPage() {
        return "bomManage/sparePart/addChild";
    }


    /**
     * 分页查询出项目中的备件
     *
     * @param query
     * @return
     */
    @RequestMapping("selectPageByProjectId")
    @ResponseBody
    public SparePartBomQueryResponse selectPageByProjectId(SparePartOfProjectQuery query) {
        return sparePartsBomService.selectPageByProjectId(query);
    }

    /**
     * 获取到增加页面
     *
     * @return
     */
    @RequestMapping("addSparePart")
    @ResponseBody
    public JSONObject addSparePart(@RequestBody SparePartPostDTO data) {
        return sparePartsBomService.saveSparePart(data);
    }


    /**
     * 获取到增加页面
     *
     * @return
     */
    @RequestMapping("addSparePartChild")
    @ResponseBody
    public JSONObject addSparePartChild(@RequestBody SparePartPostDTO data) {
        return sparePartsBomService.addSparePartChild(data);
    }

    /**
     * 更新单条数据
     *
     * @return
     */
    @RequestMapping("updateSparePart")
    @ResponseBody
    public JSONObject updateSparePart(@RequestBody SparePartPostDTO data) {
        return sparePartsBomService.updateSparePart(data);
    }

    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping("deleteList")
    @ResponseBody
    public JSONObject deleteList(@RequestBody List<SparePartPostDTO> data) {
        return sparePartsBomService.deleteList(data);
    }
}
