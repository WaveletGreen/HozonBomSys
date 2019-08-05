package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.*;
import com.connor.hozon.bom.resources.domain.dto.response.HzPbomLineRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzPbomByPageQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzPbomService;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.util.ExcelUtil;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static org.hibernate.jpa.internal.QueryImpl.LOG;

/**
 * Created by haozt on 2018/5/24
 */
@Controller
@RequestMapping(value = "/pbom")
public class HzPbomController extends BaseController {

    @Autowired
    private HzPbomService hzPbomService;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    private LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();

    private Map<String, Object> orderDataObject = new HashMap<>();

    @RequestMapping(value = "manage/title", method = RequestMethod.GET)
    public void getPbomLineTitle(String projectId, HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("rank", "级别");
        tableTitle.put("groupNum", "分组号");
        tableTitle.put("lineNo", "查找编号");
        tableTitle.put("pBomLinePartEnName", "英文名称");
        tableTitle.put("pLouaFlag", "LOU/LOA");
        tableTitle.put("pBomLinePartClass", "零件分类");
        tableTitle.put("pBomLinePartResource", "零部件来源");
        tableTitle.put("resource", "自制/采购");
        tableTitle.put("type", "焊接/装配");
        tableTitle.put("buyUnit", "采购单元");
        tableTitle.put("workShop1", "车间1");
        tableTitle.put("workShop2", "车间2");
        tableTitle.put("productLine", "生产线");
        tableTitle.put("mouldType", "模具类别");
        tableTitle.put("outerPart", "外委件");
        tableTitle.put("station", "工位");
        tableTitle.put("effectTime", "生效时间");
        //获取该项目下的所有车型模型
        tableTitle.putAll(hzSingleVehiclesServices.singleVehDosageTitle(projectId));
        this.tableTitle = tableTitle;
        toJSONResponse(Result.build(tableTitle), response);
    }

    /**
     * 查询PBOM管理信息
     *
     * @param
     */
    @RequestMapping(value = "getBomManage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPbomLineRecord(HzPbomByPageQuery query) {
        Page<HzPbomLineRespDTO> respDTOPage = hzPbomService.getHzPbomRecordPage(query);
        List<HzPbomLineRespDTO> respDTOS = respDTOPage.getResult();
        if (respDTOS == null) {
            return new HashMap<>();
        }
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        respDTOS.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("puid", dto.getPuid());
            _res.put("eBomPuid", dto.geteBomPuid());
            _res.put("No", dto.getNo());
            _res.put("level", dto.getLevel());
            _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
            _res.put("rank", dto.getRank());
            _res.put("groupNum", dto.getGroupNum());
            _res.put("lineId", dto.getLineId());
            _res.put("lineNo", dto.getLineNo());
            _res.put("pBomLinePartName", dto.getpBomLinePartName());
            _res.put("pBomLinePartEnName", dto.getpBomLinePartEnName());
            _res.put("pBomLinePartClass", dto.getpBomLinePartClass());
            _res.put("pBomLinePartResource", dto.getpBomLinePartResource());
            _res.put("pLouaFlag", dto.getpLouaFlag());
            _res.put("resource", dto.getResource());
            _res.put("type", dto.getType());
            _res.put("buyUnit", dto.getBuyUnit());
            _res.put("workShop1", dto.getWorkShop1());
            _res.put("workShop2", dto.getWorkShop2());
            _res.put("productLine", dto.getProductLine());
            _res.put("mouldType", dto.getMouldType());
            _res.put("outerPart", dto.getOuterPart());
            _res.put("station", dto.getStation());
            _res.put("status", dto.getStatus());
            _res.put("effectTime", dto.getEffectTime());
            if (null != dto.getObject()) {
                _res.putAll(dto.getObject());
            }
            _list.add(_res);
        });
        ret.put("totalCount", respDTOPage.getTotalCount());
        ret.put("result", _list);
        return ret;
    }


    /**
     * 跳转到PBOM管理添加页面
     *
     * @return
     */
    @RequestMapping(value = "addPbomManage", method = RequestMethod.GET)
    public String addPbomManageRecordToPage(String eBomPuid, String projectId, Model model) {
        HzPbomLineRespDTO respDTO = hzPbomService.getHzPbomByPuid(projectId, eBomPuid);
        if (respDTO == null) {
            return "";
        }
        HzPbomLineRespDTO hzPbomLineRespDTO = new HzPbomLineRespDTO();
        hzPbomLineRespDTO.setLineId(respDTO.getLineId());
        hzPbomLineRespDTO.seteBomPuid(respDTO.geteBomPuid());
        hzPbomLineRespDTO.setGroupNum(respDTO.getGroupNum());
        hzPbomLineRespDTO.setpBomOfWhichDept(respDTO.getpBomOfWhichDept());
        hzPbomLineRespDTO.setRank(respDTO.getRank());
        hzPbomLineRespDTO.setLevel(respDTO.getLevel());
        hzPbomLineRespDTO.setpBomLinePartClass(respDTO.getpBomLinePartClass());
        hzPbomLineRespDTO.setpBomLinePartResource(respDTO.getpBomLinePartResource());
        model.addAttribute("data", hzPbomLineRespDTO);
        return "bomManage/pbom/pbomManage/addPbomManage";
    }

    /**
     * 跳转到PBOM管理修改页面
     *
     * @return
     */
    @RequestMapping(value = "updatePbomManage", method = RequestMethod.GET)
    public String updatePbomManageRecordToPage(String projectId, String eBomPuid, String puid, Integer updateType, Model model) {
        HzPbomLineRespDTO respDTO = hzPbomService.getHzPbomByPuid(projectId, eBomPuid);
        if (respDTO == null) {
            return "";
        }
        model.addAttribute("data", respDTO);
        return "bomManage/pbom/pbomManage/updatePbomManage";
    }


    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void updatePbomRecord(@RequestBody UpdateHzPbomRecordReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzPbomService.updateHzPbomRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void deletePbomRecord(@RequestBody DeleteHzPbomReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzPbomService.deleteHzPbomRecordByForeignId(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }


    /**
     * 获取PBOM结构树
     * 	S00-5000000BBYY0;S00-5000200
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "processComposeTree", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject findProcessComposeTree(HzPbomProcessComposeReqDTO reqDTO, HttpServletResponse response) {
        return hzPbomService.buildCraftSourceTree(reqDTO,response);
    }

    /**
     * 获取一条PBOM 信息
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public void findPbomDetail(HzPbomProcessComposeReqDTO reqDTO, HttpServletResponse response) {
        if (reqDTO == null) {
            toJSONResponse(Result.build(false, "非法参数！"), response);
            return;
        }
        if (reqDTO.getProjectId() == null || reqDTO.getPuid() == null) {
            toJSONResponse(Result.build(false, "非法参数！"), response);
            return;
        }

        JSONArray object = hzPbomService.getPbomByLineId(reqDTO);
        toJSONResponse(Result.build(object), response);
    }

    @RequestMapping(value = "updataProcessOfFitting", method = RequestMethod.GET)
    public String updateRecordToPage(String puids, Model model) {
        HzPbomLineRespDTO respDTO = new HzPbomLineRespDTO();
        respDTO.setPuids(puids);
        model.addAttribute("data", respDTO);
        return "bomManage/pbom/processOfFitting/updateProcessOfFitting";
    }

    /**
     * 获取合成新件界面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "updataProcessOfFitting2", method = RequestMethod.GET)
    public String updateRecordToPage2(Model model) {
        return "bomManage/pbom/processOfFitting/updateProcessOfFitting2";
    }

    /**
     * 合成工艺合件
     *
     * @param
     * @param param
     * @Autor Fancyears·Malos
     */
    @RequestMapping(value = "/add/processCompose2", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addProcessCompose2(@RequestBody Map<String, Object> param) {
        return hzPbomService.simulateCraftingPart(param);
    }

    @RequestMapping(value = "update/accessories", method = RequestMethod.GET)
    public String getupdate(String projectId, String eBomPuid, Model model) {
        if (eBomPuid == null) {
            return "";
        }
        model.addAttribute("data", eBomPuid);
        return "bomManage/pbom/pbomManage/updateAccessoriesLibrary";
    }


    /**
     * 合成工艺合件操作
     *
     * @param
     * @param param
     * @Autor Fancyears·Malos
     * @ImportantDesc DO NOT ERASE THIS METHOD
     */
    @RequestMapping(value = "/doGenerateProcessCompose", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject doGenerateProcessCompose(@RequestBody Map<String, Object> param) {
        return hzPbomService.doGenerateProcessCompose(param);
    }

    /**
     * 下载PBOM
     */
    @RequestMapping(value = "excelExport", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listDownLoad(
            @RequestBody List<HzPbomLineRespDTO> dtos, HttpServletRequest request
    ) {
        boolean flag = true;
        JSONObject result = new JSONObject();
        try {
            String fileName = "tableExport.xlsx";//文件名-tableExport
            Object[] temp = tableTitle.values().toArray();
            //表头
            String[] title = new String[temp.length];
            for (int i = 0; i < temp.length; i++) {
                title[i] = temp[i].toString();
            }
            //当前页的数据
            List<String[]> dataList = new ArrayList<String[]>();
            int index = 1;
            for (HzPbomLineRespDTO ebomRespDTO : dtos) {
                String[] cellArr = new String[title.length];
                cellArr[0] = index + "";
                index++;
                cellArr[1] = ebomRespDTO.getLineId();
                cellArr[2] = ebomRespDTO.getpBomLinePartName();
                cellArr[3] = ebomRespDTO.getLevel();
                cellArr[4] = ebomRespDTO.getpBomOfWhichDept();
                cellArr[5] = ebomRespDTO.getRank();
                cellArr[6] = ebomRespDTO.getGroupNum();
                cellArr[7] = ebomRespDTO.getLineNo();
                cellArr[8] = ebomRespDTO.getpBomLinePartEnName();
                cellArr[9] = ebomRespDTO.getpLouaFlag();
                cellArr[10] = ebomRespDTO.getpBomLinePartClass();
                cellArr[11] = ebomRespDTO.getpBomLinePartResource();
                cellArr[12] = ebomRespDTO.getResource();
                cellArr[13] = ebomRespDTO.getType();
                cellArr[14] = ebomRespDTO.getBuyUnit();
                cellArr[15] = ebomRespDTO.getWorkShop1();
                cellArr[16] = ebomRespDTO.getWorkShop2();
                cellArr[17] = ebomRespDTO.getProductLine();
                cellArr[18] = ebomRespDTO.getMouldType();
                cellArr[19] = ebomRespDTO.getOuterPart();
                cellArr[20] = ebomRespDTO.getStation();
                dataList.add(cellArr);
            }
            flag = ExcelUtil.writeExcel(fileName, title, dataList, "pbom", request);

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
     * 跳转到Excel导入页面
     *
     * @return
     */
    @RequestMapping(value = "importExcel", method = RequestMethod.GET)
    public String getExcelImport() {
        return "bomManage/pbom/pbomManage/excelImport";
    }

    @RequestMapping("/query/accessories")
    @ResponseBody
    public JSONObject queryAccessories(String materielCode) {
        return hzPbomService.queryAccessories(materielCode);
    }

    @RequestMapping("/add/accessories")
    @ResponseBody
    public JSONObject addAccessories(String materielCode, String puid, String projectId) {
        return hzPbomService.addAccessories(puid, materielCode, projectId);
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
        toJSONResponse(Result.build(orderDataObject), response);
        toJSONResponse(Result.build(orderDataObject), response);
    }

    /**
     * 跳转到PBOM选择变更单
     *
     * @return
     */
    @RequestMapping(value = "order/choose", method = RequestMethod.GET)
    public String getOrderChooseToPage(Model model) {
        model.addAttribute("data", this.orderDataObject.get("data"));
        model.addAttribute("puids", this.orderDataObject.get("puids"));
        return "bomManage/pbom/pbomManage/pbomSetChangeForm";
    }

    /**
     * PBOM发起变更数据到变更单
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "data/change", method = RequestMethod.POST)
    public void pbomDataToChangeOrder(@RequestBody AddDataToChangeOrderReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzPbomService.dataToChangeOrder(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

    /**
     * PBOM撤销
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    public void pbomCancel(@RequestBody BomBackReqDTO reqDTO, HttpServletResponse response) {
        WriteResultRespDTO respDTO = hzPbomService.backBomUtilLastValidState(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO), respDTO.getErrMsg()), response);
    }

}
