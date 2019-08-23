package com.connor.hozon.bom.resources.controller.resourcesLibrary;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzLegislativeReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzLegislativeItemResDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzLegislativeItemQuery;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.resourcesLibrary.legislativeLibrary.HzLegislativeItemService;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @User zhangl
 * @Date 2019/8/20
 */
@Controller
@RequestMapping(value = "legislativeItem")
public class LegislativeItemsLibraryController extends BaseController {

    @Autowired
    private HzLegislativeItemService hzLegislativeItemService;

//    /**
//     * 获取法规件库整车级的标题
//     * @param response
//     */
//    @RequestMapping(value = "title",method = RequestMethod.GET)
//    public void getAutoType(HttpServletResponse response){
//        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
//        tableTitle.put("no","序号");
//        tableTitle.put("noticeNo","公告号");
//        tableTitle.put("autoType","车型");
//        tableTitle.put("vinNo","VIN码前8位");
//        tableTitle.put("batteryManufacturers","电池厂家");
//        tableTitle.put("batteryModel","储能装置电池包(箱)型号");
//        tableTitle.put("productionMode","生产方式(厂家)");
//        tableTitle.put("motorManufacturers","电机厂家");
//        tableTitle.put("motorModel","电机型号");
//        tableTitle.put("remarks","备注");
//        toJSONResponse(Result.build(tableTitle), response);
//    }

    @RequestMapping(value = "getDetail", method = RequestMethod.GET)
    public String getDetailItemToPage(String id, Model model) {
        model.addAttribute("data", id);
        return "resourcesLibrary/legislativeLibrary/legislativeItemLibrary/legislativeItemLibrary";
    }

    /**
     * 分页获取法规件库items的数据
     * @param query
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findItemPage(HzLegislativeItemQuery query){
        HzLegislativeItemQuery ItemQuery = query;
        ItemQuery.setPageSize(0);
        try {
            ItemQuery.setPageSize(Integer.valueOf(query.getLimit()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Page<HzLegislativeItemResDTO> page = hzLegislativeItemService.findHzLegislativeItemToPage(query);
        if (page == null){
            return  new HashMap<>();
        }
        List<HzLegislativeItemResDTO> list = page.getResult();
        Map<String,Object> ret = new HashMap<>();
        List<Map<String,Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("no",dto.getNo());
            map.put("puid",dto.getPuid());
            map.put("legislativeName",dto.getLegislativeName());
            map.put("legislativeNo",dto.getLegislativeNo());
            map.put("applicableModels",dto.getApplicableModels());
            map.put("noticeNo",dto.getNoticeNo());
            map.put("eplId",dto.getEplId());
            map.put("partId",dto.getPartId());
            map.put("partName",dto.getPartName());
            map.put("supplier",dto.getSupplier());
            map.put("supplierNo",dto.getSupplierNo());
            map.put("technologyDesc",dto.getTechnologyDesc());
            map.put("applyDepa",dto.getApplyDepa());
            map.put("isHaveTest",dto.getIsHaveTest());
            map.put("isHaveCcc",dto.getIsHaveCcc());
            map.put("dutyEngineer",dto.getDutyEngineer());
            map.put("remarks",dto.getRemarks());
            _list.add(map);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }


    @RequestMapping(value = "addLegislative",method = RequestMethod.GET)
    public String addItem(Model model) {

        return "resourcesLibrary/legislativeLibrary/legislativeItemLibrary/addLegislativeItemLibrary";
    }

    /**
     * 添加ebom信息
     * @param reqDTO
     * @param
     * @param response
     */
    @RequestMapping(value = "add/Legislative",method = RequestMethod.POST)
    public void addEbomToDB(@RequestBody AddHzLegislativeReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzLegislativeItemService.addHzLegislativeRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

}
