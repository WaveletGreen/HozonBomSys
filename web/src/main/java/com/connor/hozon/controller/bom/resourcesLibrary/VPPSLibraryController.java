/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.bom.resourcesLibrary;

import com.connor.hozon.controller.bom.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzVPPSLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzVPPSLibraryRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzVPPSLibraryQuery;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.service.bom.resourcesLibrary.VPPSLibrary.HzVPPSLibraryService;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/5
 * Time: 10:51
 */
@Controller
@RequestMapping("vpps")
public class VPPSLibraryController extends BaseController {
    @Autowired
    private HzVPPSLibraryService hzVPPSLibraryService;

    /**
     * 获取VPPS库的标题
     * @param response
     */
    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getVPPSLibraryTitel(HttpServletResponse response){
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No","序号");
        tableTitle.put("vppsLevel","VPPS层级");
        tableTitle.put("vsgCode","VSG代码");
        tableTitle.put("vppsCode","VPPS代码");
        tableTitle.put("vppsEnDesc","VPPS英文描述");
        tableTitle.put("vppsChDesc","VPPS中文描述");
        tableTitle.put("upc","UPC");
        tableTitle.put("fna","FNA");
        tableTitle.put("fnaChDesc","FNA中文描述");
        tableTitle.put("standardPartCode","零件标准代码");
        toJSONResponse(Result.build(tableTitle), response);
    }

    /**
     * 分页获取VPPS库的数据
     * @param query
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findVPPSLibraryPage(HzVPPSLibraryQuery query){
        HzVPPSLibraryQuery vppsLibraryQuery = query;
        vppsLibraryQuery.setPageSize(0);
        try {
            vppsLibraryQuery.setPageSize(Integer.valueOf(query.getLimit()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Page<HzVPPSLibraryRespDTO> page = hzVPPSLibraryService.findHzVPPSLibraryToPage(query);
        if (page == null){
            return  new HashMap<>();
        }
        List<HzVPPSLibraryRespDTO> list = page.getResult();
        Map<String,Object> ret = new HashMap<>();
        List<Map<String,Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("No",dto.getNo());
            map.put("puid",dto.getPuid());
            map.put("vppsLevel",dto.getVppsLevel());
            map.put("vsgCode",dto.getVsgCode());
            map.put("vppsCode",dto.getVppsCode());
            map.put("vppsEnDesc",dto.getVppsEnDesc());
            map.put("vppsChDesc",dto.getVppsChDesc());
            map.put("upc",dto.getUpc());
            map.put("fna",dto.getFna());
            map.put("fnaChDesc",dto.getFnaChDesc());
            map.put("standardPartCode",dto.getStandardPartCode());
            _list.add(map);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping(value = "getAdd",method = RequestMethod.GET)
    public String getAddVPPSLibraryToPage (){
        return "resourcesLibrary/VPPSLibrary/addVPPSLibrary";
    }

    /**
     * 添加一条数据
     * @param dto
     * @param response
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addVPPSLibrary(@RequestBody AddHzVPPSLibraryReqDTO dto, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzVPPSLibraryService.insertHzVPPSLibrary(dto);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }
    /**
     * 跳转到快速添加的页面
     * @param puid
     * @param model
     * @return
     */
    @RequestMapping(value = "getQuickAdd",method = RequestMethod.GET)
    public String getQuickAddVPPSLibraryToPage(String puid, Model model){
        HzVPPSLibraryRespDTO respDTO = hzVPPSLibraryService.findHzVPPSLibraryById(puid);
        if (respDTO == null){
            return null;
        }
        model.addAttribute("data",respDTO);
        return "resourcesLibrary/VPPSLibrary/QuickAddVPPSLibrary";
    }
    /**
     * 跳转到修改页面
     * @param puid
     * @param model
     * @return
     */
    @RequestMapping(value = "getUpdate",method = RequestMethod.GET)
    public String getUpdateVPPSLibraryToPage(String puid, Model model){
        HzVPPSLibraryRespDTO respDTO = hzVPPSLibraryService.findHzVPPSLibraryById(puid);
        if (respDTO == null){
            return null;
        }
        model.addAttribute("data",respDTO);
        return "resourcesLibrary/VPPSLibrary/updateVPPSLibrary";
    }

    /**
     * 编辑一条数据
     * @param dto
     * @param response
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateVPPSLibrary(@RequestBody AddHzVPPSLibraryReqDTO dto,HttpServletResponse response){
        WriteResultRespDTO respDTO =hzVPPSLibraryService.updateHzVPPSLibrary(dto);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 删除一条数据
     * @param puid
     * @param response
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void deleteVPPSLibrary(String puid,HttpServletResponse response){
        WriteResultRespDTO respDTO =hzVPPSLibraryService.deleteHzVPPSLibrary(puid);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }
}
