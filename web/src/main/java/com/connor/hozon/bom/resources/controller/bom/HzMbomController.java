package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.bomSystem.service.derivative.HzComposeMFService;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.HzMbomRecordRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzMbomByIdQuery;
import com.connor.hozon.bom.resources.domain.query.HzMbomByPageQuery;
import com.connor.hozon.bom.resources.mybatis.bom.HzPbomRecordDAO;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzMbomService;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sql.pojo.change.HzChangeOrderRecord;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.hibernate.jpa.internal.QueryImpl.LOG;

/**
 * \* User: xulf
 * \* Date: 2018/6/6
 * \* Time: 12:58
 * \
 */

@Controller
@RequestMapping(value = "/mbom")
public class HzMbomController extends BaseController {

    @Autowired
    private HzMbomService hzMbomService;

    @Autowired
    HzComposeMFService hzComposeMFService;
    @Autowired
    HzSingleVehiclesServices hzSingleVehiclesServices;
    @Autowired
    HzPbomRecordDAO hzPbomRecordDAO;
    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    private LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();

    private Map<String, Object> orderDataObject = new HashMap<>();

    /**
     * MBOM管理标题
     *
     * @param response
     */
    @RequestMapping(value = "manage/title", method = RequestMethod.GET)
    public void mbomTitle(String projectId, HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("level", "层级");
        tableTitle.put("rank", "级别");
        tableTitle.put("pBomOfWhichDept", "专业");
//        tableTitle.put("lineNo","查找编号");
        tableTitle.put("pLouaFlag", "LOU/LOA");
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
        tableTitle.put("pBomType", "BOM类型");
        tableTitle.put("effectTime", "生效时间");
        tableTitle.putAll(hzSingleVehiclesServices.singleVehDosageTitle(projectId));
        this.tableTitle = tableTitle;
        toJSONResponse(Result.build(tableTitle), response);

    }

    @RequestMapping(value = "refreshMBOM", method = RequestMethod.GET)
    @ResponseBody
    public String refreshMBOM(@RequestParam String projectId, HttpServletResponse response) {
        refreshMbom(projectId, response);
        return "测试成功";
    }

    /**
     * 分页获取MBOM 记录
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "record", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMbomLineRecord(HzMbomByPageQuery query) {
        Page<HzMbomRecordRespDTO> page = hzMbomService.findHzMbomForPage(query);
        if (page == null) {
            return new HashMap<>();
        }
        List<HzMbomRecordRespDTO> list = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("eBomPuid", dto.geteBomPuid());
            _res.put("puid", dto.getPuid());
            _res.put("colorId", dto.getColorId());
            _res.put("No", dto.getNo());
            _res.put("rank", dto.getRank());
            _res.put("level", dto.getLevel());
//            _res.put("lineNo",ProcessReceiveDto.getLineNo());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("lineId", dto.getLineId());
            _res.put("pBomLinePartName", dto.getpBomLinePartName());
            _res.put("pBomLinePartClass", dto.getpBomLinePartClass());
            _res.put("pBomLinePartResource", dto.getpBomLinePartResource());
            _res.put("sparePart", dto.getSparePart());
            _res.put("sparePartNum", dto.getSparePartNum());
            _res.put("processRoute", dto.getProcessRoute());
            _res.put("laborHour", dto.getLaborHour());
            _res.put("rhythm", dto.getRhythm());
            _res.put("pLouaFlag", dto.getpLouaFlag());
            _res.put("solderJoint", dto.getSolderJoint());
            _res.put("machineMaterial", dto.getMachineMaterial());
            _res.put("standardPart", dto.getStandardPart());
            _res.put("tools", dto.getTools());
            _res.put("wasterProduct", dto.getWasterProduct());
            _res.put("change", dto.getChange());
            _res.put("changeNum", dto.getChangeNum());
            _res.put("pFactoryCode", dto.getpFactoryCode());
            _res.put("pStockLocation", dto.getpStockLocation());
            _res.put("pBomType", dto.getpBomType());
            _res.put("status", dto.getStatus());
            _res.put("effectTime", dto.getEffectTime());
            if (null != dto.getVehNum()) {
                _res.putAll(dto.getVehNum());
            }
            _list.add(_res);
        });
        ret.put("totalCount", page.getTotalCount());
        ret.put("result", _list);
        return ret;
    }

    /**
     * 跳转到MBOM管理的添加页面
     *
     * @return
     */
    @RequestMapping(value = "addMBom", method = RequestMethod.GET)
    public String addMBomToPage(String projectId, String eBomPuid, Model model) {
        HzMbomByIdQuery query = new HzMbomByIdQuery();
        query.setProjectId(projectId);
        query.setPuid(eBomPuid);
        HzMbomRecordRespDTO respDTO = hzMbomService.findHzMbomByPuid(query);
        if (respDTO == null) {
            return "";
        }
        HzMbomRecordRespDTO hzMbomRecordRespDTO = new HzMbomRecordRespDTO();
        hzMbomRecordRespDTO.seteBomPuid(respDTO.geteBomPuid());
        hzMbomRecordRespDTO.setObject_name(respDTO.getpBomLinePartName());
        hzMbomRecordRespDTO.setLevel(respDTO.getLevel());
        hzMbomRecordRespDTO.setLineId(respDTO.getLineId());
        hzMbomRecordRespDTO.setpBomOfWhichDept(respDTO.getpBomOfWhichDept());
        model.addAttribute("data", hzMbomRecordRespDTO);
        return "bomManage/mbom/mbomMaintenance/addMbomMaintenance";
    }

    /**
     * 跳转到MBOM管理的修改页面
     *
     * @param projectId
     * @param eBomPuid
     * @param model
     * @return
     */
    @RequestMapping(value = "updateMBom", method = RequestMethod.GET)
    public String updateMbomToPage(String projectId, String eBomPuid, Model model) {
        HzMbomByIdQuery query = new HzMbomByIdQuery();
        query.setProjectId(projectId);
        query.setPuid(eBomPuid);
        HzMbomRecordRespDTO respDTO = hzMbomService.findHzMbomByPuid(query);
        if (respDTO == null) {
            return "";
        }
        model.addAttribute("data", respDTO);
        return "bomManage/mbom/mbomMaintenance/updateMbomMaintenance";
    }

    /**
     * 插入一条记录
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public void addMbomToDB(@RequestBody AddMbomReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzMbomService.insertMbomRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * 编辑一条记录
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void updateMbomToDB(@RequestBody UpdateMbomReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzMbomService.updateMbomRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * 批量删除记录
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void deleteMbom(@RequestBody DeleteHzMbomReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzMbomService.deleteMbomRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    @RequestMapping(value = "refresh", method = RequestMethod.POST)
    public void refreshMbom(String projectId, HttpServletResponse response) {
        WriteResultRespDTO resultMessageRespDTO = hzMbomService.refreshHzMbom(projectId);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultMessageRespDTO), resultMessageRespDTO.getErrMsg()), response);
    }

    /**
     * 跳转到白车身生产的修改页面
     *
     * @return
     */
    @RequestMapping(value = "updateProduction", method = RequestMethod.GET)
    public String updateWhiteBodyProduction(String projectId, String eBomPuid, Integer type, Integer updateType, Model model) {
        HzMbomByIdQuery query = new HzMbomByIdQuery();
        query.setProjectId(projectId);
        query.setType(type);
        query.setPuid(eBomPuid);
        HzMbomRecordRespDTO respDTO = hzMbomService.findHzMbomByPuid(query);
        if (respDTO == null) {
            return "";
        }
        model.addAttribute("data", respDTO);
        return "bomManage/mbom/mbomMaintenance/updateProduction";
    }

    /**
     * 跳转到白车身财务的修改页面
     *
     * @return
     */
    @RequestMapping(value = "updateFinancial", method = RequestMethod.GET)
    public String updateWhiteBodyFinancial(String projectId, String eBomPuid, Integer type, Integer updateType, Model model) {
        HzMbomByIdQuery query = new HzMbomByIdQuery();
        query.setProjectId(projectId);
        query.setType(type);
        query.setPuid(eBomPuid);
        HzMbomRecordRespDTO respDTO = hzMbomService.findHzMbomByPuid(query);
        if (respDTO == null) {
            return "";
        }
        model.addAttribute("data", respDTO);
        return "bomManage/mbom/mbomMaintenance/updateFinancial";
    }

    /**
     * 下载MBOM
     */
    @RequestMapping(value = "excelExport", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listDownLoad(
            @RequestBody List<HzMbomRecordRespDTO> dtos
            , HttpServletRequest request) {
        boolean flag = true;
        JSONObject result = new JSONObject();
        try {
            String fileName = "tableExport.xlsx";//文件名-tableExport
            //表头
            Object[] temp = tableTitle.values().toArray();

            String[] title = new String[temp.length];
            for (int i = 0; i < temp.length; i++) {
                title[i] = temp[i].toString();
            }
            /*String[] title = {
                    "序号","零件号" ,"名称","层级" ,"专业" ,"查找编号" ,"LOU/LOA","零件分类","零部件来源",
                    "备件","备件编号","工艺路线","人工工时","节拍","焊点","机物料","标准件","工具","废品",
                    "变更","变更号","工厂代码","发货料库存地点","BOM类型"
            };*/
            //当前页的数据
            List<String[]> dataList = new ArrayList<String[]>();
            int index = 1;
            for (HzMbomRecordRespDTO ebomRespDTO : dtos) {
                String[] cellArr = new String[title.length];
                cellArr[0] = index + "";
                index++;
                cellArr[1] = ebomRespDTO.getLineId();
                cellArr[2] = ebomRespDTO.getpBomLinePartName();
                cellArr[3] = ebomRespDTO.getLevel();
                cellArr[4] = ebomRespDTO.getRank();
                cellArr[5] = ebomRespDTO.getpBomOfWhichDept();
                cellArr[6] = ebomRespDTO.getpLouaFlag();
                cellArr[7] = ebomRespDTO.getpBomLinePartClass();
                cellArr[8] = ebomRespDTO.getpBomLinePartResource();
                cellArr[9] = ebomRespDTO.getSparePart();
                cellArr[10] = ebomRespDTO.getSparePartNum();
                cellArr[11] = ebomRespDTO.getProcessRoute();
                cellArr[12] = ebomRespDTO.getLaborHour();
                cellArr[13] = ebomRespDTO.getRhythm();
                cellArr[14] = ebomRespDTO.getSolderJoint();
                cellArr[15] = ebomRespDTO.getMachineMaterial();
                cellArr[16] = ebomRespDTO.getStandardPart();
                cellArr[17] = ebomRespDTO.getTools();
                cellArr[18] = ebomRespDTO.getWasterProduct();
                cellArr[19] = ebomRespDTO.getChange();
                cellArr[20] = ebomRespDTO.getChangeNum();
                cellArr[21] = ebomRespDTO.getpFactoryCode();
                cellArr[22] = ebomRespDTO.getpStockLocation();
                cellArr[23] = ebomRespDTO.getpBomType();
                dataList.add(cellArr);
            }
            flag = ExcelUtil.writeExcel(fileName, title, dataList, "mbom", request);

            if (flag) {
                LOG.info(fileName + ",文件创建成功");
                result.put("status", flag);
                result.put("msg", "成功");
                result.put("path", "./files/tableExport.xlsx");
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
     * 跳转到Excel导入超级MBOM页面
     */
    @RequestMapping(value = "importExcel", method = RequestMethod.GET)
    public String getExcelImport() {
        return "bomManage/mbom/mbomMaintenance/excelImport";
    }

    /**
     * 跳转到Excel导入白车身生产MBOM页面
     */
    @RequestMapping(value = "importExcel2", method = RequestMethod.GET)
    public String getExcelImport2() {
        return "bomManage/mbom/mbomMaintenance/excelImport2";
    }

    /**
     * 跳转到Excel导入白车身财务MBOM页面
     */
    @RequestMapping(value = "importExcel3", method = RequestMethod.GET)
    public String getExcelImport3() {
        return "bomManage/mbom/mbomMaintenance/excelImport3";
    }

    /**
     * 获取变更表单
     *
     * @return
     */
    @RequestMapping(value = "find/choose", method = RequestMethod.POST)
    public void getOrderChooseToPage(@RequestBody AddDataToChangeOrderReqDTO reqDTO, HttpServletResponse response) {
        List<HzChangeOrderRecord> records = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(reqDTO.getProjectId());
        this.orderDataObject = new HashMap<>();
        this.orderDataObject.put("data", records);
        this.orderDataObject.put("puids", reqDTO.getPuids());
        this.orderDataObject.put("type", reqDTO.getType());
        toJSONResponse(Result.build(orderDataObject), response);
    }

    /**
     * 跳转到MBOM选择变更单
     *
     * @return
     */
    @RequestMapping(value = "order/choose", method = RequestMethod.GET)
    public String getOrderChooseToPage(Model model) {
        model.addAttribute("data", this.orderDataObject.get("data"));
        model.addAttribute("puids", this.orderDataObject.get("puids"));
        model.addAttribute("type", this.orderDataObject.get("type"));
        return "bomManage/mbom/mbomMaintenance/mbomSetChangeForm";
    }

    /**
     * MBOM发起变更数据到变更单
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "data/change", method = RequestMethod.POST)
    public void mbomDataToChangeOrder(@RequestBody AddDataToChangeOrderReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzMbomService.dataToChangeOrder(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * MBOM撤销
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    public void mbomCancel(@RequestBody BomBackReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzMbomService.backBomUtilLastValidState(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }
}


