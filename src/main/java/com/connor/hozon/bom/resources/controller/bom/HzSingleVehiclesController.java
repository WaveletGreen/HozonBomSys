package com.connor.hozon.bom.resources.controller.bom;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesBomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesBomServices;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import com.connor.hozon.bom.resources.util.StringUtil;
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
 * Date: 2018/9/12
 * Time: 15:34
 */

@Controller
@RequestMapping(value = "singleVehicles")
public class HzSingleVehiclesController extends BaseController {

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzSingleVehiclesBomServices hzSingleVehiclesBomServices;

    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void singleVehiclesTitle(HttpServletResponse response){
        LinkedHashMap<String, String> title = new LinkedHashMap<>();
        title.put("brandCode","品牌代码");
        title.put("brandName","中文品牌");
        title.put("colorCode","颜色代码");
        title.put("colorName","颜色名称");
        title.put("platformCode","平台代码");
        title.put("platformName","平台名称");
        title.put("svlBatteryCode","电池型号");
        title.put("svlInnerColorCode","内饰颜色代码");
        title.put("svlInnerColorName","内饰颜色名称");
        title.put("svlMaterialBasicInfo","基本信息");
        title.put("vehicleName","车型名称");
        title.put("vehicleCode","车型代码");
        title.put("svlMaterialCode","物料编号");
        title.put("svlMotorCode","电机型号");
        writeAjaxJSONResponse(ResultMessageBuilder.build(title), response);
    }
    
    
    @RequestMapping(value = "get/detail",method = RequestMethod.GET)
    public String getDetailBikeBomToPage(String id,Model model){
        model.addAttribute("data",id);
        return "bikeBom/detailBikeBom";
    }

    @RequestMapping(value = "update/page",method = RequestMethod.GET)
    public String toUpdateSingleVehiclesPage(String projectId, Long id, Model model){
        HzSingleVehiclesRespDTO respDTO = hzSingleVehiclesServices.getSingleVehiclesById(projectId,id);
        model.addAttribute("data",respDTO);
        return "bikeBom/updateBikeBom";
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateSingleVehicles(@RequestBody UpdateHzSingleVehiclesReqDTO reqDTO,HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzSingleVehiclesServices.updateSingleVehicle(reqDTO);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO)),response);
    }

    @RequestMapping(value = "refresh",method = RequestMethod.POST)
    public void refreshSingleVehicles(String projectId,HttpServletResponse response){
        OperateResultMessageRespDTO respDTO = hzSingleVehiclesServices.refreshSingleVehicle(projectId);
        writeAjaxJSONResponse(ResultMessageBuilder.build(OperateResultMessageRespDTO.isSuccess(respDTO)),response);
    }

    /**
     * 获取单车清单信息
     * @return
     */
    @RequestMapping(value = "record", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSingleVehiclesRecord(String projectId) {
        
        List<HzSingleVehiclesRespDTO> respDTOS = hzSingleVehiclesServices.singleVehiclesList(projectId);
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        if(ListUtil.isNotEmpty(respDTOS)){
            respDTOS.forEach(dto -> {
                Map<String, Object> _res = new HashMap<>();
                _res.put("id", dto.getId());
                _res.put("brandCode", dto.getBrandCode());
                _res.put("brandName", dto.getBrandName());
                _res.put("colorCode", dto.getColorCode());
                _res.put("colorName",dto.getColorName());
                _res.put("platformCode", dto.getPlatformCode());
                _res.put("platformName", dto.getPlatformName());
                _res.put("svlBatteryCode", dto.getSvlBatteryCode());
                _res.put("svlInnerColorCode", dto.getSvlInnerColorCode());
                _res.put("svlInnerColorName", dto.getSvlInnerColorName());
                _res.put("svlMaterialBasicInfo", dto.getSvlMaterialBasicInfo());
                _res.put("vehicleName", dto.getVehicleName());
                _res.put("vehicleCode", dto.getVehicleCode());
                _res.put("svlMaterialCode", dto.getSvlMaterialCode());
                _res.put("svlMotorCode", dto.getSvlMotorCode());
                _list.add(_res);
            });
            ret.put("totalCount", respDTOS.size());
        }else {
            ret.put("totalCount",0);
        }
        ret.put("result", _list);

        return ret;
    }


    /**
     * 单车BOM标题
     * @param response
     */
    @RequestMapping(value = "bom/title", method = RequestMethod.GET)
    public void mbomTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("pBomLinePartClass", "零件分类");
        tableTitle.put("pBomLinePartResource", "零部件来源");
        tableTitle.put("sparePart", "备件");
        tableTitle.put("sparePartNum", "备件编号");
        tableTitle.put("processRoute", "工艺路线");
        tableTitle.put("laborHour", "人工工时");
        tableTitle.put("rhythm", "节拍");
        tableTitle.put("solderJoint", "焊点");
        tableTitle.put("machineMaterial", "机物料");
        tableTitle.put("standardPart", "标准件");
        tableTitle.put("tools", "工具");
        tableTitle.put("wasterProduct", "废品");
        tableTitle.put("change", "变更");
        tableTitle.put("changeNum", "变更号");
        tableTitle.put("pFactoryCode", "工厂代码");
        tableTitle.put("pStockLocation", "发货料库存地点");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }


    /**
     * 分页获取单车BOM 记录
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "bom/record", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSingleVehiclesBomRecordByPage(HzSingleVehiclesBomByPageQuery query) {
        if(StringUtil.isEmpty(query.getProjectId())){
            return new HashMap<>();
        }
        if(null == query.getSingleVehiclesId()){
            return new HashMap<>();
        }
        HzSingleVehiclesBomByPageQuery pageQuery = query;
        pageQuery.setPageSize(0);
        try{
            pageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){

        }
        Page<HzSingleVehiclesBomRespDTO> page = hzSingleVehiclesBomServices.getHzSingleVehiclesBomByPage(query);
        if (page == null) {
            return new HashMap<>();
        }
        List<HzSingleVehiclesBomRespDTO> list = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("No", dto.getNo());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getPBomOfWhichDept());
            _res.put("lineId", dto.getLineId());
            _res.put("pBomLinePartName", dto.getPBomLinePartName());
            _res.put("pBomLinePartClass", dto.getPBomLinePartClass());
            _res.put("pBomLinePartResource", dto.getPBomLinePartResource());
            _res.put("sparePart", dto.getSparePart());
            _res.put("sparePartNum", dto.getSparePartNum());
            _res.put("processRoute", dto.getProcessRoute());
            _res.put("laborHour", dto.getLaborHour());
            _res.put("rhythm", dto.getRhythm());
            _res.put("solderJoint", dto.getSolderJoint());
            _res.put("machineMaterial", dto.getMachineMaterial());
            _res.put("standardPart", dto.getStandardPart());
            _res.put("tools", dto.getTools());
            _res.put("wasterProduct", dto.getWasterProduct());
            _res.put("change", dto.getChange());
            _res.put("changeNum", dto.getChangeNum());
            _res.put("pFactoryCode", dto.getPFactoryCode());
            _res.put("pStockLocation",dto.getPStockLocation());
            _list.add(_res);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }


    @RequestMapping(value = "bom/refresh",method = RequestMethod.POST)
    public void refreshSingleVehiclesBOM(String projectId,HttpServletResponse response){
        hzSingleVehiclesBomServices.analysisSingleVehicles(projectId);
        writeAjaxJSONResponse(ResultMessageBuilder.build(true),response);
    }
}
