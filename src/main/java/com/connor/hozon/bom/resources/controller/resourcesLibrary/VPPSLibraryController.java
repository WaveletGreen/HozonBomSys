package com.connor.hozon.bom.resources.controller.resourcesLibrary;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.AddHzVPPSLibraryReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzVPPSLibraryRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzVPPSLibraryQuery;
import com.connor.hozon.bom.resources.service.resourcesLibrary.VPPSLibrary.HzVPPSLibraryService;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
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
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }
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
        OperateResultMessageRespDTO respDTO = hzVPPSLibraryService.insertHzVPPSLibrary(dto);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
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
        OperateResultMessageRespDTO respDTO =hzVPPSLibraryService.updateHzVPPSLibrary(dto);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void deleteVPPSLibrary(String puid,HttpServletResponse response){
        OperateResultMessageRespDTO respDTO =hzVPPSLibraryService.deleteHzVPPSLibrary(puid);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }
}
