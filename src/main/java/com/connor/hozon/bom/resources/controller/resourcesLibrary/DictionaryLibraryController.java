package com.connor.hozon.bom.resources.controller.resourcesLibrary;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.AddHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzDictionaryLibraryRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzDictionaryLibraryQuery;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryService;
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
 * Date: 2018/8/28
 * Time: 21:10
 */

@Controller
@RequestMapping(value = "dict")
public class DictionaryLibraryController extends BaseController {
    @Autowired
    private HzDictionaryLibraryService hzDictionaryLibraryService;
    /**
     * 跳转到字典库的添加页面
     * @return
     */
    @RequestMapping(value = "getAdd",method = RequestMethod.GET)
    public String getAddDictionaryLibrary(){
        return "resourcesLibrary/dictionaryLibrary/addDictionaryLibrary";
    }
    /**
     * 跳转到字典库的修改页面
     * @return
     */
    @RequestMapping(value = "getUpdate",method = RequestMethod.GET)
    public String getUpdateDictionaryLibrary(String puid, Model model){
        HzDictionaryLibraryRespDTO library = hzDictionaryLibraryService.findHzDictionaryLibraryByPuid(puid);
        if (library==null){
            return "";
        }
        model.addAttribute("data",library);
        return "resourcesLibrary/dictionaryLibrary/updateDictionaryLibrary";
    }

    /**
     * 添加一条数据
     * @param dto
     * @param response
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addDictionaryLibrary(@RequestBody AddHzDictionaryLibraryReqDTO dto, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzDictionaryLibraryService.insertHzDictionaryLibrary(dto);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 分页获取字典数据
     * @param query
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findDictionaryLibraryPage(HzDictionaryLibraryQuery query){
        HzDictionaryLibraryQuery dictionaryLibraryQuery = query;
        dictionaryLibraryQuery.setPageSize(0);
        try {
            dictionaryLibraryQuery.setPageSize(Integer.valueOf(query.getLimit()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Page<HzDictionaryLibraryRespDTO> page = hzDictionaryLibraryService.findHzDictionaryLibraryToPage(query);
        if (page ==null){
            return new HashMap<>();
        }
        List<HzDictionaryLibraryRespDTO> list = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        list.forEach(dto ->{
            Map<String,Object> map = new HashMap<>();
            map.put("puid",dto.getPuid());
            map.put("No",dto.getNo());
            map.put("professionCh",dto.getProfessionCh());
            map.put("professionEn",dto.getProfessionEn());
            map.put("classificationCh",dto.getClassificationCh());
            map.put("classificationEn",dto.getClassificationEn());
            map.put("groupCode",dto.getGroupCode());
            map.put("groupCh",dto.getGroupCh());
            map.put("groupEn",dto.getGroupEn());
            map.put("famillyCode",dto.getFamillyCode());
            map.put("famillyCh",dto.getFamillyCh());
            map.put("famillyEn",dto.getFamillyEn());
            map.put("eigenValue",dto.getEigenValue());
            map.put("valueDescCh",dto.getValueDescCh());
            map.put("valueDescEn",dto.getValueDescEn());
            map.put("type",dto.getType());
            map.put("valueSource",dto.getValueSource());
            map.put("effectTime",dto.getEffectTime());
            map.put("failureTime",dto.getFailureTime());
            map.put("note",dto.getNote());
            _list.add(map);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }

    /**
     * 修改一条数据
     * @param dto
     * @param response
     */
    @RequestMapping(value = "updateById",method = RequestMethod.POST)
    public void updateDiDictionaryLibrary(@RequestBody UpdateHzDictionaryLibraryReqDTO dto, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzDictionaryLibraryService.updateHzDictionaryLibrary(dto);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     *删除一条数据
     * @param puid
     * @param response
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void deleteDiDictionaryLibrary(String puid ,HttpServletResponse response){
        WriteResultRespDTO respDTO = hzDictionaryLibraryService.deleteHzDictionaryLibrary(puid);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 获取配置字典的标题
     * @param response
     */
    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getDiDictionaryLibraryTitel(HttpServletResponse response){
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No","序号");
        tableTitle.put("professionCh","专业部");
        tableTitle.put("professionEn","Profession");
        tableTitle.put("classificationCh","分类");
        tableTitle.put("classificationEn","classification");
        tableTitle.put("groupCode","特性组代码");
        tableTitle.put("groupCh","特性组");
        tableTitle.put("groupEn","group");
        tableTitle.put("famillyCode","特性族代码");
        tableTitle.put("famillyCh","特性族");
        tableTitle.put("famillyEn","familly");
        tableTitle.put("eigenValue","特性值");
        tableTitle.put("valueDescCh","特性值描述");
        tableTitle.put("valueDescEn","value description");
        tableTitle.put("type","类型");
        tableTitle.put("valueSource","特性值来源");
        tableTitle.put("effectTime","生效时间");
        tableTitle.put("failureTime","失效时间");
        tableTitle.put("note","备注");
        toJSONResponse(Result.build(tableTitle), response);
    }
}
