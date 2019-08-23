package com.connor.hozon.controller.bom.resourcesLibrary;

import com.connor.hozon.bom.resources.domain.dto.response.HzLegislativeAutoTypeResDTO;
import com.connor.hozon.controller.bom.BaseController;
import com.connor.hozon.resources.domain.query.HzLegislativeAutoTypeQuery;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.resourcesLibrary.legislativeLibrary.HzLegislativeAutoTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @User zhangl
 * @Date 2019/8/19
 */
@Controller
@RequestMapping(value = "legislative")
public class LegislativeLibraryController extends BaseController {

    @Autowired
    private HzLegislativeAutoTypeService hzLegislativeAutoTypeService;

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

    /**
     * 分页获取法规件库整车级的数据
     * @param query
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findAutoTypePage(HzLegislativeAutoTypeQuery query){
        HzLegislativeAutoTypeQuery autoTypeQuery = query;
        autoTypeQuery.setPageSize(0);
        try {
            autoTypeQuery.setPageSize(Integer.valueOf(query.getLimit()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Page<HzLegislativeAutoTypeResDTO> page = hzLegislativeAutoTypeService.findHzLegislativeMotorcycleTypeToPage(query);
        if (page == null){
            return  new HashMap<>();
        }
        List<HzLegislativeAutoTypeResDTO> list = page.getResult();
        Map<String,Object> ret = new HashMap<>();
        List<Map<String,Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("no",dto.getNo());
            map.put("puid",dto.getPuid());
            map.put("noticeNo",dto.getNoticeNo());
            map.put("autoType",dto.getAutoType());
            map.put("vinNo",dto.getVinNo());
            map.put("batteryManufacturers",dto.getBatteryManufacturers());
            map.put("batteryModel",dto.getBatteryModel());
            map.put("productionMode",dto.getProductionMode());
            map.put("motorManufacturers",dto.getMotorManufacturers());
            map.put("motorModel",dto.getMotorModel());
            map.put("remarks",dto.getRemarks());
            _list.add(map);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }



    @RequestMapping(value = "addAutoType",method = RequestMethod.GET)
    public String addAutoType(String puid,Model model) {

        return "resourcesLibrary/legislativeLibrary/legislativeItemLibrary/addLegislativeItemLibrary";
    }
}
