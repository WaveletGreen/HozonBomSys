/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.bom.bom;

import cn.net.connor.hozon.services.request.bom.sparePart.SparePartOfProjectRequestQueryDTO;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartPostDTO;
import cn.net.connor.hozon.services.request.bom.sparePart.SparePartQuoteEbomLinesPostDTO;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartQueryEbomPageResponseDTO;
import com.alibaba.fastjson.JSONObject;
import cn.net.connor.hozon.services.response.bom.sparePart.SparePartBomQueryPageResponse;
import com.connor.hozon.resources.domain.query.HzEbomByPageQuery;
import com.connor.hozon.service.bom.bomData.sparePart.SparePartsBomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
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
        if ("add".equals(type)) {
            model.addAttribute("type", "add");
            model.addAttribute("url", "./sparePartsBom/addSparePart");
        } else if ("update".equals(type)) {
            model.addAttribute("type", "update");
            model.addAttribute("url", "./sparePartsBom/updateSparePart");
        }
        else if("clonePartData".equals(type)){
            model.addAttribute("type", "clonePartData");
            model.addAttribute("url", "./sparePartsBom/addSparePart");
        }
        return "bomManage/sparePart/addOrUpdate";
    }


    /**
     * 获取到增加子件页面
     *
     * @return
     */
    @RequestMapping("addChildPage")
    public String addChildPage(Integer type, Model model) {
        if (type != null) {
            switch (type) {
                case 1:
                    model.addAttribute("isAddChild", true);
                    break;
                case 2:
                    model.addAttribute("isAddChild", false);
                    break;
                default:
                    model.addAttribute("isAddChild", false);
                    break;
            }
        } else {
            model.addAttribute("isAddChild", false);
        }
        return "bomManage/sparePart/addChild";
    }

    @RequestMapping("jumpToEbom")
    public String jumpToEbom() {
        return "bomManage/sparePart/sparePartRelEbomIndex";
    }

    @RequestMapping("getQuoteTypePage")
    public String getQuoteTypePage() {
        return "bomManage/sparePart/quoteType";
    }


    /**
     * 分页查询出项目中的备件
     *
     * @param query 查询的对象，差不多和dao层对象的属性字段一样
     * @return
     */
    @RequestMapping("selectPageByProjectId")
    @ResponseBody

    public SparePartBomQueryPageResponse selectPageByProjectId(SparePartOfProjectRequestQueryDTO query) {
        return sparePartsBomService.selectPageByProjectId(query);
    }

    /**
     * 添加单个备件零件对象
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

    /*****************************************与EBOM有关联********************************************/

    @RequestMapping(value = "ebom/title", method = RequestMethod.GET)
    @ResponseBody
    public LinkedHashMap<String, String> getEbomTitle(String projectId) {
        return sparePartsBomService.createRelEbomTitle();
    }

    @RequestMapping(value = "ebom/getEBom/list", method = RequestMethod.GET)
    @ResponseBody
    public SparePartQueryEbomPageResponseDTO getEbomList(HzEbomByPageQuery query) {
        return sparePartsBomService.getEbomList(query);
    }

    @RequestMapping(value = "quoteEbomLines", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject quoteEbomLines(@RequestBody SparePartQuoteEbomLinesPostDTO dto) {
        return sparePartsBomService.quoteEbomLines(dto);
    }

}
