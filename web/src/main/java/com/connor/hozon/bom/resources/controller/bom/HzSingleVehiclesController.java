package com.connor.hozon.bom.resources.controller.bom;

import cn.net.connor.hozon.common.setting.CommonSetting;
import cn.net.connor.hozon.dao.pojo.interaction.HzSingleVehicles;
import cn.net.connor.hozon.dao.pojo.interaction.SingleVehicleBomRelation;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateHzSingleVehiclesReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesBomRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzSingleVehiclesRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzSingleVehiclesBomByPageQuery;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesBomServices;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.bom.resources.util.Result;
import com.connor.hozon.bom.resources.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.hibernate.jpa.internal.QueryImpl.LOG;

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

    //String code="";

    @RequestMapping(value = "title", method = RequestMethod.GET)
    public void singleVehiclesTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> title = new LinkedHashMap<>();
        //物料编码-svlMaterialCode
        //基本信息-svlMaterialBasicInfo
        title.put("brandCode", "品牌代码");
        title.put("brandName", "中文品牌");
        title.put("colorCode", "颜色代码");
        title.put("colorName", "颜色名称");
        title.put("platformCode", "平台代码");
        title.put("platformName", "平台名称");
        title.put("svlBatteryCode", "电池型号");
        title.put("svlInnerColorCode", "内饰颜色代码");
        title.put("svlInnerColorName", "内饰颜色名称");
        title.put("svlMaterialBasicInfo", "基本信息");
        title.put("vehicleName", "车型名称");
        title.put("vehicleCode", "车型代码");
        title.put("svlMaterialCode", "物料编号");
        title.put("svlMotorCode", "电机型号");
        toJSONResponse(Result.build(title), response);
    }


    @RequestMapping(value = "getDetail", method = RequestMethod.GET)
    public String getDetailBikeBomToPage(String id, Model model) {
        model.addAttribute("data", id);
        return "bikeBom/detailBikeBom";
    }

    @RequestMapping(value = "update/page", method = RequestMethod.GET)
    public String toUpdateSingleVehiclesPage(String projectId, Long id, Model model) {
        HzSingleVehiclesRespDTO respDTO = hzSingleVehiclesServices.getSingleVehiclesById(projectId, id);
        model.addAttribute("data", respDTO);
        return "bikeBom/updateBikeBom";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void updateSingleVehicles(@RequestBody UpdateHzSingleVehiclesReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzSingleVehiclesServices.updateSingleVehicle(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO)), response);
    }

    @RequestMapping(value = "refresh", method = RequestMethod.POST)
    public void refreshSingleVehicles(String projectId, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzSingleVehiclesServices.refreshSingleVehicle(projectId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO)), response);
    }

    /**
     * 获取单车清单信息
     *
     * @return
     */
    @RequestMapping(value = "record", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getSingleVehiclesRecord(String projectId) {

        List<HzSingleVehiclesRespDTO> respDTOS = hzSingleVehiclesServices.singleVehiclesList(projectId);
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        if (ListUtils.isNotEmpty(respDTOS)) {
            respDTOS.forEach(dto -> {
                Map<String, Object> _res = new HashMap<>();
                _res.put("id", dto.getId());
                _res.put("brandCode", dto.getBrandCode());
                _res.put("brandName", dto.getBrandName());
                _res.put("colorCode", dto.getColorCode());
                _res.put("colorName", dto.getColorName());
                _res.put("platformCode", dto.getPlatformCode());
                _res.put("platformName", dto.getPlatformName());
                _res.put("svlBatteryCode", dto.getSvlBatteryCode());
                _res.put("svlInnerColorCode", dto.getSvlInnerColorCode());
                _res.put("svlInnerColorName", dto.getSvlInnerColorName());
                _res.put("svlMaterialBasicInfo", dto.getSvlMaterialBasicInfo());
                _res.put("checkStatus", dto.getCheckStatus());//检查状态
                _res.put("vehicleName", dto.getVehicleName());
                _res.put("vehicleCode", dto.getVehicleCode());
                _res.put("svlMaterialCode", dto.getSvlMaterialCode());
                _res.put("svlMotorCode", dto.getSvlMotorCode());
                _list.add(_res);
            });
            ret.put("totalCount", respDTOS.size());
        } else {
            ret.put("totalCount", 0);
        }
        ret.put("result", _list);

        return ret;
    }


    /**
     * 单车BOM标题
     *
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
        toJSONResponse(Result.build(tableTitle), response);
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
        if (StringUtil.isEmpty(query.getProjectId())) {
            return new HashMap<>();
        }
        if (null == query.getSingleVehiclesId()) {
            return new HashMap<>();
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
            _res.put("eBomPuid", dto.getEBomPuid());
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
            _res.put("pStockLocation", dto.getPStockLocation());
            _res.put("singleVehiclesId", dto.getSingleVehiclesId());
            _list.add(_res);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }


    @RequestMapping(value = "bom/refresh", method = RequestMethod.GET)
    public void refreshSingleVehiclesBOM(String projectId, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzSingleVehiclesBomServices.analysisSingleVehicles(projectId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * 下载单车BOM
     */
    @RequestMapping(value = "excelExport", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listDownLoad(@RequestBody List<HzSingleVehiclesRespDTO> dtos, HttpServletRequest request) {
        boolean flag = true;
        JSONObject result = new JSONObject();
        try {
            String fileName = "tableExport.xlsx";//文件名-tableExport
            String[] title = {
                    "物料编号", "基本信息", "品牌代码", "中文品牌", "平台代码", "平台名称",
                    "车型代码", "车型名称", "内饰颜色代码", "内饰颜色名称",
                    "颜色代码", "颜色名称", "电池型号", "电机型号"
            };//表头
            //当前页的数据
            List<String[]> dataList = new ArrayList<String[]>();
            //int index=1;
            for (HzSingleVehiclesRespDTO bomRespDTO : dtos) {
                String[] cellArr = new String[title.length];
                //cellArr[0] = index+"";
                //index++;
                cellArr[0] = bomRespDTO.getSvlMaterialCode();
                cellArr[1] = bomRespDTO.getSvlMaterialBasicInfo();
                cellArr[2] = bomRespDTO.getBrandCode();
                cellArr[3] = bomRespDTO.getBrandName();
                cellArr[4] = bomRespDTO.getPlatformCode();
                cellArr[5] = bomRespDTO.getPlatformName();
                cellArr[6] = bomRespDTO.getVehicleCode();
                cellArr[7] = bomRespDTO.getVehicleName();
                cellArr[8] = bomRespDTO.getSvlInnerColorCode();
                cellArr[9] = bomRespDTO.getSvlInnerColorName();
                cellArr[10] = bomRespDTO.getColorCode();
                cellArr[11] = bomRespDTO.getColorName();
                cellArr[12] = bomRespDTO.getSvlBatteryCode();
                cellArr[13] = bomRespDTO.getSvlMotorCode();
                dataList.add(cellArr);
            }
            flag = ExcelUtil.writeExcel(fileName, title, dataList, "", request);

            if (flag) {
                LOG.info(fileName + ",文件创建成功");
                result.put("status", flag);
                result.put("msg", "成功");
                result.put("path", "./files/" + fileName);
            } else {
                LOG.info(fileName + ",文件创建失败");
                result.put("status", flag);
                result.put("msg", "失败");
            }
        } catch (Exception e) {
            if (LOG.isTraceEnabled())//isErrorEnabled()
                LOG.error(e.getMessage());
        }
        return result;
    }

    /**
     * 下载单车BOM具体信息
     */
    @RequestMapping(value = "excelExport2", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listDownLoad2(@RequestBody List<HzSingleVehiclesBomRespDTO> dtos, HttpServletRequest request) {
        boolean flag = true;
        JSONObject result = new JSONObject();
        try {
            String fileName = "tableExport.xlsx";//文件名-tableExport
            String[] title = {
                    "序号", "零件号", "名称", "层级", "专业", "零件分类", "零部件来源", "备件", "备件编号", "工艺路线",
                    "人工工时", "节拍", "焊点", "机物料", "标准件", "工具", "废品", "变更", "变更号", "工厂代码", "发货料库存地点"
            };//表头
            //当前页的数据
            List<String[]> dataList = new ArrayList<String[]>();
            int index = 1;
            for (HzSingleVehiclesBomRespDTO bomRespDTO : dtos) {
                String[] cellArr = new String[title.length];
                cellArr[0] = index + "";
                index++;
                cellArr[1] = bomRespDTO.getLineId();
                cellArr[2] = bomRespDTO.getPBomLinePartName();
                cellArr[3] = bomRespDTO.getLevel();
                cellArr[4] = bomRespDTO.getPBomOfWhichDept();
                cellArr[5] = bomRespDTO.getPBomLinePartClass();
                cellArr[6] = bomRespDTO.getPBomLinePartResource();
                cellArr[7] = bomRespDTO.getSparePart();
                cellArr[8] = bomRespDTO.getSparePartNum();
                cellArr[9] = bomRespDTO.getProcessRoute();
                cellArr[10] = bomRespDTO.getLaborHour();
                cellArr[11] = bomRespDTO.getRhythm();
                cellArr[12] = bomRespDTO.getSolderJoint();
                cellArr[13] = bomRespDTO.getMachineMaterial();
                cellArr[14] = bomRespDTO.getStandardPart();
                cellArr[15] = bomRespDTO.getTools();
                cellArr[16] = bomRespDTO.getWasterProduct();
                cellArr[17] = bomRespDTO.getChange();
                cellArr[18] = bomRespDTO.getChangeNum();
                cellArr[19] = bomRespDTO.getPFactoryCode();
                cellArr[20] = bomRespDTO.getPStockLocation();
                dataList.add(cellArr);
            }
            flag = ExcelUtil.writeExcel(fileName, title, dataList, "", request);

            if (flag) {
                LOG.info(fileName + ",文件创建成功");
                result.put("status", flag);
                result.put("msg", "成功");
                result.put("path", "/files/" + fileName);
            } else {
                LOG.info(fileName + ",文件创建失败");
                result.put("status", flag);
                result.put("msg", "失败");
            }
        } catch (Exception e) {
            if (LOG.isTraceEnabled())//isErrorEnabled()
                LOG.error(e.getMessage());
        }
        return result;
    }


    @RequestMapping("sendSap")
    @ResponseBody
    public JSONObject sendSap(@RequestBody List<HzSingleVehicles> hzSingleVehicles) {
        return hzSingleVehiclesServices.sendSap(hzSingleVehicles);
    }

    /**
     * 从删除操作通知到SAP
     * @param hzSingleVehicles
     * @return
     */
    @RequestMapping("deleteSap")
    @ResponseBody
    public JSONObject deleteSap(@RequestBody List<HzSingleVehicles> hzSingleVehicles) {
        return hzSingleVehiclesServices.deleteSap(hzSingleVehicles);
    }

    /**
     * 检查单车2Y与对应的特性值的对应关系
     *
     * @param projectId
     * @param vehiclesId
     * @param model
     * @return
     */
    @RequestMapping("checkStatus")
    public String checkStatus(String projectId, Long vehiclesId, Model model) {
        List<SingleVehicleBomRelation> list = hzSingleVehiclesServices.checkStatus(projectId, vehiclesId).getRel();
        if (org.thymeleaf.util.ListUtils.isEmpty(list)) {
            model.addAttribute(CommonSetting.STATUS_FIELD, false);
            model.addAttribute(CommonSetting.ERROR_FIELD, "查询不到单车配置信息，需要重新生成单车数据");
        } else {
            model.addAttribute(CommonSetting.STATUS_FIELD, true);
            model.addAttribute(CommonSetting.RESULT_DATA, list);
        }
        return "bikeBom/checkStatus";
    }

    /**
     * 方便重新检查某个项目的所有单车状态的前端调用API
     * @param projectId
     * @return
     */
    @RequestMapping("refreshProjectSingleVehicleStatus")
    @ResponseBody
    public String refreshProjectSingleStatus(String projectId) {
        //单车信息
        List<HzSingleVehicles> hzSingleVehicles = hzSingleVehiclesServices.selectByProjectUid(projectId);
        hzSingleVehiclesServices.postCheck(hzSingleVehicles,projectId);
        return "重新检查成功";
    }
}
