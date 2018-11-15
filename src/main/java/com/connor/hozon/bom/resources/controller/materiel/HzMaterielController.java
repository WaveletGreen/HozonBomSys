package com.connor.hozon.bom.resources.controller.materiel;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.EditHzMaterielReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzMaterielRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.domain.query.HzMaterielByPageQuery;
import com.connor.hozon.bom.resources.domain.query.HzMaterielQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.materiel.HzMaterielService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.change.HzChangeOrderRecord;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/7/4
 * @Description: 物料
 */
@Controller
@RequestMapping(value = "materiel")
public class HzMaterielController  extends BaseController {

    @Autowired
    private HzMaterielService hzMaterielService;
    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;
    @RequestMapping("type")
    @ResponseBody
    public Map<String,Object> getMaterielType(){
        /**
         * 物料类型  严格按照注释来读写数据
         * （11 整车超级物料主数据
         * 21 整车衍生物料主数据
         * 31 虚拟件物料主数据
         * 41半成品物料主数据
         * 51 生产性外购物料主数据
         * 61自制备件物料主数据）
         */
        Map<String,Object> map = new HashMap<>();
        map.put("11","整车超级物料主数据");
        map.put("21","整车衍生物料主数据");
        map.put("31","虚拟件物料主数据");
        map.put("41","半成品物料主数据");
        map.put("51","生产性外购物料主数据");
        map.put("61","自制备件物料主数据");
        return map;
    }

    /**
     * 物料标题
     * @param response
     */
    @RequestMapping(value = "title", method = RequestMethod.GET)
    public void getPbomLineTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
//        tableTitle.put("puid", "puid");
        tableTitle.put("pMaterielCode", "物料编码");
        tableTitle.put("pMaterielDesc", "物料描述（中文）");
        tableTitle.put("pMaterielType", "物料类型");
        tableTitle.put("factoryCode","工厂");
        tableTitle.put("pMaterielDescEn", "物料描述（英文）");
        tableTitle.put("pBasicUnitMeasure", "单位");
        tableTitle.put("pInventedPart", "虚拟件标识");
        tableTitle.put("resource","采购类型");
        tableTitle.put("pSpareMaterial", "备件&原材料双属性标识");
        tableTitle.put("pVinPerNo", "VIN前置号");
        tableTitle.put("pColorPart", "颜色件标识");
        tableTitle.put("pHeight", "实际重量");
        tableTitle.put("pInOutSideFlag", "内外饰标识");
        tableTitle.put("p3cPartFlag", "3C件标识");
        tableTitle.put("pMrpController", "MRP控制者");
        tableTitle.put("pPartImportantDegree", "零件重要度");
        tableTitle.put("pLoosePartFlag", "散件标识");
        toJSONResponse(Result.build(tableTitle), response);
    }
    /**
     * 分页获取物料管理信息
     *
     * @param
     */
    @RequestMapping(value = "getMateriel", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPbomLineRecord(HzMaterielByPageQuery query) {
        HzMaterielByPageQuery ebomByPageQuery = query;
        ebomByPageQuery.setPageSize(0);
        try{
            ebomByPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        }catch (Exception e){

        }
        Page<HzMaterielRespDTO> respDTOPage = hzMaterielService.findHzMaterielForPage(query);
        List<HzMaterielRespDTO> respDTOS = respDTOPage.getResult();
        if(respDTOS == null){
            return new HashMap<>();
        }
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        respDTOS.forEach(dto -> {
            Map<String, Object> _res = new HashMap<>();
            _res.put("No",dto.getNo());
            _res.put("puid",dto.getPuid());
            _res.put("resource",dto.getResource());
            _res.put("pMaterielCode",dto.getpMaterielCode());
            _res.put("pMaterielType",dto.getpMaterielType());
            _res.put("pMaterielDesc",dto.getpMaterielDesc());
            _res.put("pMaterielDescEn",dto.getpMaterielDescEn());
            _res.put("pBasicUnitMeasure",dto.getpBasicUnitMeasure());
            _res.put("pInventedPart",dto.getpInventedPart());
            _res.put("pSpareMaterial",dto.getpSpareMaterial());
            _res.put("pVinPerNo",dto.getpVinPerNo());
            _res.put("pColorPart",dto.getpColorPart());
            _res.put("pHeight",dto.getpHeight());
            _res.put("pInOutSideFlag",dto.getpInOutSideFlag());
            _res.put("p3cPartFlag",dto.getP3cPartFlag());
            _res.put("pMrpController",dto.getpMrpController());
            _res.put("pPartImportantDegree",dto.getpPartImportantDegree());
            _res.put("pLoosePartFlag",dto.getpLoosePartFlag());
            _res.put("factoryCode",dto.getFactoryCode());
            _list.add(_res);
        });
        ret.put("totalCount", respDTOPage.getTotalCount());
        ret.put("result", _list);
        return ret;
    }

    /**
     * 跳转到物料数据的添加页面
     * @param query
     * @param model
     * @return
     */
    @RequestMapping(value = "addMateriel", method = RequestMethod.GET)
    public String addMaterielToPage(HzMaterielQuery query,Model model) {
        HzMaterielRespDTO respDTO = hzMaterielService.getHzMateriel(query);
        if(respDTO== null){
            return "";
        }
        model.addAttribute("data",respDTO);
        return"bomManage/mbom/materialData/addMaterialData";
    }

    /**
     * 跳转到物料数据的修改页面
     * @param query
     * @param model
     * @return
     */
    @RequestMapping(value = "updateMBom", method = RequestMethod.GET)
    public String updateMaterielToPage(HzMaterielQuery query, Model model) {
        HzMaterielRespDTO respDTO = hzMaterielService.getHzMateriel(query);
        if(respDTO== null){
            return "";
        }
        model.addAttribute("data",respDTO);
        return"bomManage/mbom/materialData/updateMaterialData";
    }


    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addMaterielToDB(@RequestBody EditHzMaterielReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzMaterielService.editHzMateriel(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 编辑一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateMaterielToDB(@RequestBody EditHzMaterielReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzMaterielService.editHzMateriel(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 删除一条记录
     * @param puid
     * @param response
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public void deleteMateriel(String puid,HttpServletResponse response){
        WriteResultRespDTO respDTO =  hzMaterielService.deleteHzMateriel(puid);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 跳转到物料数据选择变更单
     * @return
     */
    @RequestMapping(value = "order/choose",method = RequestMethod.GET)
    public String getOrderChooseToPage(String projectId,String puids,Model model){
        List<HzChangeOrderRecord> records = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(projectId);
        if(ListUtil.isNotEmpty(records)){
            model.addAttribute("data",records);
            model.addAttribute("puids",puids);
        }
        return "bomManage/mbom/materialData/materialSetChangeForm";
    }

}
