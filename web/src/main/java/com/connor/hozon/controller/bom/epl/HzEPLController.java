package com.connor.hozon.controller.bom.epl;

import cn.net.connor.hozon.common.util.ListUtils;
import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeCode;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.controller.bom.BaseController;
import com.connor.hozon.resources.domain.dto.request.EditHzEPLReqDTO;
import com.connor.hozon.resources.domain.dto.response.HzEplRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.query.HzEPLByPageQuery;
import com.connor.hozon.resources.mybatis.change.HzChangeOrderDAO;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.epl.HzEPLService;
import com.connor.hozon.resources.util.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.net.connor.hozon.dao.pojo.change.change.HzChangeOrderRecord;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by haozt on 2018/06/05
 */
@Controller
@RequestMapping(value = "/epl")
public class HzEPLController extends BaseController {

    @Autowired
    private HzEPLService hzEPLService;
    @Autowired
    private HzChangeOrderDAO hzChangeOrderDAO;

    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void getEplTitle(HttpServletResponse response){
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No","序号");
        tableTitle.put("partId","零件号" );
        tableTitle.put("partName","名称" );
        tableTitle.put("partOfWhichDept","专业" );
        tableTitle.put("partEnName","英文名称");
        tableTitle.put("unit","单位");
        tableTitle.put("pictureNo","图号");
        tableTitle.put("pictureSheet","图幅" );
        tableTitle.put("materialHigh","料厚" );
        tableTitle.put("material1","材料1");
        tableTitle.put("material2","材料2" );
        tableTitle.put("material3","材料3" );
        tableTitle.put("density","密度");
        tableTitle.put("materialStandard","材料标准");
        tableTitle.put("surfaceTreat","表面处理" );
        tableTitle.put("textureColorNum","纹理编号/色彩编号");
        tableTitle.put("manuProcess","制造工艺");
        tableTitle.put("symmetry","对称" );
        tableTitle.put("importance","重要度");
        tableTitle.put("regulationFlag","是否法规件");
        tableTitle.put("is3cpart","是否3C件" );
        tableTitle.put("regulationCode","法规件型号");
        tableTitle.put("bwgBoxPart","黑白灰匣子件" );
        tableTitle.put("developType","开发类别");
        tableTitle.put("dataVersion","数据版本" );
        tableTitle.put("targetWeight","目标重量(kg)");
        tableTitle.put("featureWeight","预估重量(kg)");
        tableTitle.put("actualWeight","实际重量(kg)" );
        tableTitle.put("fastener","紧固件");
        tableTitle.put("fastenerStandard","紧固件规格");
        tableTitle.put("fastenerLevel","紧固件性能等级");
        tableTitle.put("torque","扭矩" );
        tableTitle.put("dutyEngineer","责任工程师");
        tableTitle.put("supply","供应商");
        tableTitle.put("supplyCode","供应商代码" );
        tableTitle.put("buyEngineer","采购工程师");
        tableTitle.put("remark","备注");
        tableTitle.put("partClass","零件分类" );
        tableTitle.put("partResource","零件来源");
        tableTitle.put("inOutSideFlag","内外饰标识");
        toJSONResponse(Result.build(tableTitle), response);
    }



    @RequestMapping(value = "record",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHzEplRecord(HzEPLByPageQuery query){
        Page<HzEplRespDTO> recordRespDTOPage = hzEPLService.getEPLRecordByPage(query);
        Map<String, Object> ret = new HashMap<>();
        if(recordRespDTOPage == null || ListUtils.isEmpty(recordRespDTOPage.getResult())){
            return  ret;
        }
        List<HzEplRespDTO> list =  recordRespDTOPage.getResult();
        List<Map<String,Object>> list1 = new ArrayList<>();
        list.forEach(dto -> {
            Map<String, Object> map1 = new HashMap<>();
            map1.put("No",dto.getNo());
            map1.put("id",dto.getId());
            map1.put("status",dto.getStatus());
            map1.put("partOfWhichDept",dto.getPartOfWhichDept());
            map1.put("partId",dto.getPartId() );
            map1.put("partName",dto.getPartName() );
            map1.put("partEnName",dto.getPartEnName());
            map1.put("unit",dto.getUnit());
            map1.put("pictureNo",dto.getPictureNo());
            map1.put("pictureSheet",dto.getPictureSheet() );
            map1.put("materialHigh",dto.getMaterialHigh());
            map1.put("material1",dto.getMaterial1());
            map1.put("material2",dto.getMaterial2());
            map1.put("material3",dto.getMaterial3() );
            map1.put("density",dto.getDensity());
            map1.put("materialStandard",dto.getMaterialStandard());
            map1.put("surfaceTreat",dto.getSurfaceTreat() );
            map1.put("textureColorNum",dto.getTextureColorNum());

            map1.put("manuProcess",dto.getMaterialHigh());
            map1.put("symmetry",dto.getSymmetry());
            map1.put("importance",dto.getImportance());
            map1.put("regulationFlag",dto.getRegulationFlag());
            map1.put("is3cpart",dto.getIs3cpart() );
            map1.put("regulationCode",dto.getRegulationCode());
            map1.put("bwgBoxPart",dto.getBwgBoxPart() );
            map1.put("developType",dto.getDevelopType());
            map1.put("dataVersion",dto.getDataVersion() );
            map1.put("targetWeight",dto.getTargetWeight());
            map1.put("featureWeight",dto.getFeatureWeight());
            map1.put("actualWeight",dto.getActualWeight() );
            map1.put("fastener",dto.getFastener());
            map1.put("fastenerStandard",dto.getFastenerStandard());
            map1.put("fastenerLevel",dto.getFastenerLevel());
            map1.put("torque",dto.getTorque() );
            map1.put("dutyEngineer",dto.getDutyEngineer());
            map1.put("supply",dto.getSupply());
            map1.put("supplyCode",dto.getSupplyCode() );
            map1.put("buyEngineer",dto.getBuyEngineer());
            map1.put("remark",dto.getRemark());
            map1.put("partClass",dto.getPartClass() );
            map1.put("partResource",dto.getPartResource());
            map1.put("inOutSideFlag",dto.getInOutSideFlag());
            map1.put("effectTime",dto.getEffectTime());
            list1.add(map1);
        });
        ret.put("totalCount", recordRespDTOPage.getTotalCount());
        ret.put("result", list1);
        return ret;
        }


    /**
     * 跳转到新增EPL页面
     * @return
     */
    @RequestMapping(value = "add/page",method = RequestMethod.GET)
    public String toAddEplPage(Model model){
        List<HzLegislativeCode> recordList = hzEPLService.getLegislativeCode();
        if (recordList!=null){
            model.addAttribute("legislativeCode", recordList);
        }
        return "bomManage/epl/addEpl";
    }

    /**
     * 跳转到编辑EPL页面
     * @return
     */
    @RequestMapping(value = "update/page",method = RequestMethod.GET)
    public String toUpdatePage(Long id,Model model){
        HzEplRespDTO  respDTO = hzEPLService.getEplById(id);
        List<HzLegislativeCode> recordList = hzEPLService.getLegislativeCode();
        if (recordList!=null){
            model.addAttribute("legislativeCode", recordList);
        }
        if(respDTO != null){
            model.addAttribute("data",respDTO);
            model.addAttribute("id",id);
        }
        return "bomManage/epl/updateEpl";
    }

    /**
     * 跳转到EPL选择变更单
     * @return
     */
    @RequestMapping(value = "order/choose",method = RequestMethod.GET)
    public String getOrderChooseToPage(String projectId,String puids,Model model){
        List<HzChangeOrderRecord> records = hzChangeOrderDAO.findHzChangeOrderRecordByProjectId(projectId);
        if(ListUtils.isNotEmpty(records)){
            model.addAttribute("data",records);
            model.addAttribute("puids",puids);
        }
        return "bomManage/epl/eplSetChangeForm";
    }

    /**
     * 新增一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "add",method = RequestMethod.POST)
    public void addEpl(@RequestBody EditHzEPLReqDTO reqDTO,HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEPLService.addPartToEPL(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 编辑一条记录
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public void updateEpl(@RequestBody EditHzEPLReqDTO reqDTO, HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEPLService.updatePartFromEPLRecord(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    /**
     * 删除 批量删
     * @param ids
     * @param response
     */
    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    public void deleteEpl(String ids,HttpServletResponse response){
        WriteResultRespDTO respDTO = hzEPLService.deletePartFromEPLByIds(ids);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(respDTO),respDTO.getErrMsg()),response);
    }

    @RequestMapping("exist")
    @ResponseBody
    public Map<String, Object> deptExist(Long id,String partId,String projectId) {
        if(StringUtils.isNotBlank(partId)){
            partId = partId.trim();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("valid",!hzEPLService.partIdIsRepeat(id,partId,projectId));
        return result;
    }

    @RequestMapping("excelImportPage")
    public String excelImportPage(){
        return "bomManage/epl/eplExcelImport";
    }

    @RequestMapping("excelImport")
    @ResponseBody
    public JSONObject excelImport(@RequestParam("file") MultipartFile file, @RequestParam("projectId") String projectPuid){
        return hzEPLService.excelImport(file,projectPuid);
    }
}
