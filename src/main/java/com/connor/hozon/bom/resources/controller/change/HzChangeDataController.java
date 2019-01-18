package com.connor.hozon.bom.resources.controller.change;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.BomBackReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.*;
import com.connor.hozon.bom.resources.domain.query.HzChangeDataQuery;
import com.connor.hozon.bom.resources.mybatis.change.HzAttachmentRecordDao;
import com.connor.hozon.bom.resources.mybatis.change.HzChangeListDAO;
import com.connor.hozon.bom.resources.service.bom.HzSingleVehiclesServices;
import com.connor.hozon.bom.resources.service.change.HzChangeDataService;
import com.connor.hozon.bom.resources.util.FileUtils;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.change.HzAttachmentRecord;
import sql.pojo.change.HzChangeListRecord;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/11/15
 * @Description:
 */
@Controller
@RequestMapping("change/data")
public class HzChangeDataController extends BaseController {

    @Autowired
    private HzChangeDataService hzChangeDataService;

    @Autowired
    private HzSingleVehiclesServices hzSingleVehiclesServices;

    @Autowired
    private HzChangeListDAO hzChangeListDAO;


    @Autowired
    private HzAttachmentRecordDao hzAttachmentRecordDao;

    @RequestMapping(value = "ebom/title",method = RequestMethod.GET)
    public void getEbomTitle(String projectId,HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
//        tableTitle.put("state","状态");
        tableTitle.put("changeType","变更类型");
        tableTitle.put("lineId","零件号" );
        tableTitle.put("pBomLinePartName","名称" );
        tableTitle.put("level","层级" );
        tableTitle.put("pBomOfWhichDept","专业" );
        tableTitle.put("pBomLinePartEnName","英文名称");
        tableTitle.put("pUnit","单位");
        tableTitle.put("pPictureNo","图号");
        tableTitle.put("pPictureSheet","图幅" );
        tableTitle.put("pMaterialHigh","料厚" );
        tableTitle.put("pMaterial1","材料1");
        tableTitle.put("pMaterial2","材料2" );
        tableTitle.put("pMaterial3","材料3" );
        tableTitle.put("pDensity","密度");
        tableTitle.put("pMaterialStandard","材料标准");
        tableTitle.put("pSurfaceTreat","表面处理" );
        tableTitle.put("pTextureColorNum","纹理编号/色彩编号");
        tableTitle.put("pManuProcess","制造工艺");
        tableTitle.put("pSymmetry","对称" );
        tableTitle.put("pImportance","重要度");
        tableTitle.put("pRegulationFlag","是否法规件");
        tableTitle.put("p3cpartFlag","是否3C件" );
        tableTitle.put("pRegulationCode","法规件型号");
        tableTitle.put("pBwgBoxPart","黑白灰匣子件" );
        tableTitle.put("pDevelopType","开发类别");
        tableTitle.put("pDataVersion","数据版本" );
        tableTitle.put("pTargetWeight","目标重量(kg)");
        tableTitle.put("pFeatureWeight","预估重量(kg)");
        tableTitle.put("pActualWeight","实际重量(kg)" );
        tableTitle.put("pFastener","紧固件");
        tableTitle.put("pFastenerStandard","紧固件规格");
        tableTitle.put("pFastenerLevel","紧固件性能等级");
        tableTitle.put("pTorque","扭矩" );
        tableTitle.put("pDutyEngineer","责任工程师");
        tableTitle.put("pSupply","供应商");
        tableTitle.put("pSupplyCode","供应商代码" );
        tableTitle.put("pBuyEngineer","采购工程师");
        tableTitle.put("pRemark","备注");
        tableTitle.put("pBomLinePartClass","零件分类" );
        tableTitle.put("pBomLinePartResource","零件来源");
        tableTitle.put("pInOutSideFlag","内外饰标识");
        tableTitle.put("pUpc","UPC");
        tableTitle.put("fna","FNA");
        tableTitle.put("pFnaDesc","FNA描述" );
        tableTitle.put("number","数量" );
        tableTitle.put("sparePart","备件");
        tableTitle.put("sparePartNum","备件编号");
        tableTitle.put("colorPart","是否颜色件");

        //获取该项目下的所有车型模型
        tableTitle.putAll(hzSingleVehiclesServices.singleVehDosageTitle(projectId));
        toJSONResponse(Result.build(tableTitle), response);
    }


    @RequestMapping(value = "pbom/title",method = RequestMethod.GET)
    public void getPbomTitle(String projectId,HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
//        tableTitle.put("state","状态");
        tableTitle.put("changeType","变更类型");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("level", "层级");
        tableTitle.put("pBomOfWhichDept", "专业");
        tableTitle.put("rank", "级别");
        tableTitle.put("pBomLinePartEnName", "英文名称");
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
        //获取该项目下的所有车型模型
//        tableTitle.putAll(hzSingleVehiclesServices.singleVehDosageTitle(projectId));
        toJSONResponse(Result.build(tableTitle), response);
    }


    @RequestMapping(value = "mbom/title",method = RequestMethod.GET)
    public void getMbomTitle(String projectId,HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
//        tableTitle.put("state","状态");
        tableTitle.put("changeType","变更类型");
        tableTitle.put("lineId", "零件号");
        tableTitle.put("pBomLinePartName", "名称");
        tableTitle.put("level", "层级");
        tableTitle.put("rank", "级别");
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
        tableTitle.put("pBomType", "BOM类型");
        toJSONResponse(Result.build(tableTitle), response);
    }

    @RequestMapping(value = "materiel/title",method = RequestMethod.GET)
    public void getMaterielTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
//        tableTitle.put("state","状态");
        tableTitle.put("changeType","变更类型");
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

    @RequestMapping(value = "work/procedure/title",method = RequestMethod.GET)
    public void getworkProcedureTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> title = new LinkedHashMap<>();
        title.put("changeType","变更类型");
        title.put("pMaterielCode", "物料代码");//物料
        title.put("pMaterielDesc","物料名称");//物料名称
        title.put("factoryCode", "工厂");//工厂
        title.put("purpose", "用途");//用途
        title.put("state", "状态");//状态
        title.put("pProcedureCode", "工序号");//工序号
        title.put("pWorkCode", "工作中心");//工作中心
        title.put("pWorkDesc", "工作中心描述");//工作中心描述
        title.put("controlCode", "控制码");//控制码
        title.put("pProcedureDesc", "工序描述");//工序描述
        title.put("pCount", "基本数量 ");//基本数量
        title.put("pDirectLabor", "直接人工/机物料消耗");//直接人工时间
        title.put("pIndirectLabor", "间接人工/标准件消耗");//间接人工时间
        title.put("pMachineLabor", "折旧/工具消耗");//机器时间
        title.put("pBurn", "燃动费/废品损失");//燃动能
        title.put("pMachineMaterialLabor", "辅助人工/设备维修");//机物料消耗
        title.put("pOtherCost", "辅助折旧/辅助其他费用");//其他费用
        toJSONResponse(Result.build(title), response);
    }



    /**
     * 获取超链接
     * @param query
     * @param response
     */
    @RequestMapping(value = "order/hyper",method = RequestMethod.GET)
    public void getChangeOrderData(HzChangeDataQuery query,HttpServletResponse response){
        List<HzChangeDataRespDTO> respDTOS = hzChangeDataService.getChangeDataHyperRecord(query);
        toJSONResponse(Result.build(respDTOS),response);
    }


    @RequestMapping(value = "ebom/page")
    public String ebomDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changeEbomTable";
    }
    @RequestMapping(value = "pbom/page")
    public String pbomDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changePbomTable";
    }
    @RequestMapping(value = "mbom/page")
    public String mbomDataTOPage(Model model,Long orderId,Integer type){
        model.addAttribute("orderId",orderId);
        model.addAttribute("type",type);
        return "change/changeOrder/changeMbomTable";
    }
    @RequestMapping(value = "material/page")
    public String materialDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changeMaterialTable";
    }
    @RequestMapping(value = "routing/page")
    public String routingDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changeRoutingTable";
    }
    @RequestMapping(value = "feature/page")
    public String featureDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changeFeatureTable";
    }
    @RequestMapping(value = "modelColorCfg/page")
    public String modelColorCfgDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changeColorCfgTable";
    }
    @RequestMapping(value = "materielFeature/page")
    public String materielFeatureDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changeMaterielFeatureTable";
    }
    @RequestMapping(value = "bomCfg/page")
    public String bomCfgDataTOPage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changeBomCfgTable";
    }
    @RequestMapping(value = "relevance/page")
    public String relevancePage(Model model,Long orderId){
        model.addAttribute("orderId",orderId);
        return "change/changeOrder/changeRelevanceTable";
    }
    @RequestMapping(value = "tc",method = RequestMethod.GET)
    public void getDataDetailForTc(String formId,HttpServletResponse response){
        if(StringUtils.isBlank(formId)){
            toJSONResponse(Result.build(false,"表单信息不能为空!"),response);
            return;
        }
        List<HzChangeListRecord> list = hzChangeListDAO.findItemListByFormId(formId);
        toJSONResponse(Result.build(list),response);
    }


    @RequestMapping(value = "changeFile", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject changeFile(String changeNo){
        JSONObject result = new JSONObject();
        List<HzAttachmentRecord> hzAttachmentRecords = hzAttachmentRecordDao.selectByChangeNo(changeNo);
        result.put("files",hzAttachmentRecords);
        return result;
    }

//    @RequestMapping(value = "download")
//    public ResponseEntity<byte[]> export(Long filePath)  {
//
//        HzAttachmentRecord hzAttachmentRecord = hzAttachmentRecordDao.selectByPrimaryKey(filePath);
//
//
//        HttpHeaders headers = new HttpHeaders();
//        File file = new File(hzAttachmentRecord.getAttachmentUrl()+"\\"+hzAttachmentRecord.getRealName());
//        try {
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", new String(hzAttachmentRecord.getShowName().getBytes("utf-8"), "ISO-8859-1"));
//            if(!file.exists()){
//                return new ResponseEntity(null,headers,HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
//                    headers, HttpStatus.CREATED);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new ResponseEntity(null,headers,HttpStatus.NOT_FOUND);
//        }
//    }


    @RequestMapping(value = "download",method = RequestMethod.GET)
    public Object export(Long filePath,HttpServletResponse response)  {
        HzAttachmentRecord hzAttachmentRecord = hzAttachmentRecordDao.selectByPrimaryKey(filePath);
        if(hzAttachmentRecord == null){
            return "error";
        }
        File file = new File(hzAttachmentRecord.getAttachmentUrl()+"\\"+hzAttachmentRecord.getRealName());
        String fileName = "";
        try {
            //浏览器端默认编码为 ISO-8859-1 需要将编码转换为ISO-8859-1 否则出现乱码
            fileName = new String(hzAttachmentRecord.getShowName().getBytes("UTF-8"),"ISO-8859-1");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!file.exists()){
            return "error";
        }
        if(file.exists()){
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + fileName);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "ebom/data",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEBOMChangeDataDetail(HzChangeDataQuery query){
        List<HzEbomRespDTO> list = hzChangeDataService.getChangeDataRecordForEBOM(query);
        Map<String, Object> ret = new HashMap<>();
        if(ListUtil.isNotEmpty(list)){
            List<Map<String, Object>> _list = new ArrayList<>();
            list.forEach(dto -> {
                Map<String, Object> _res = new HashMap<>();
                _res.put("puid",dto.getPuid());
                _res.put("changeType",dto.getChangeType());
                _res.put("lineId",dto.getLineId() );
                _res.put("pBomLinePartName",dto.getpBomLinePartName() );
                _res.put("level",dto.getLevel() );
                _res.put("pBomOfWhichDept",dto.getpBomOfWhichDept() );
                _res.put("pBomLinePartEnName",dto.getpBomLinePartEnName());

                _res.put("pUnit",dto.getpUnit());
                _res.put("pPictureNo",dto.getpPictureNo());
                _res.put("pPictureSheet",dto.getpPictureSheet() );
                _res.put("pMaterialHigh",dto.getpMaterialHigh() );
                _res.put("pMaterial1",dto.getpMaterial1());
                _res.put("pMaterial2",dto.getpMaterial2() );
                _res.put("pMaterial3",dto.getpMaterial3());
                _res.put("pDensity",dto.getpDensity());
                _res.put("pMaterialStandard",dto.getpMaterialStandard());
                _res.put("pSurfaceTreat",dto.getpSurfaceTreat() );
                _res.put("pTextureColorNum",dto.getpTextureColorNum());
                _res.put("pManuProcess",dto.getpManuProcess());
                _res.put("pSymmetry",dto.getpSymmetry() );
                _res.put("pImportance",dto.getpImportance());
                _res.put("pRegulationFlag",dto.getpRegulationFlag());
                _res.put("p3cpartFlag",dto.getP3cpartFlag() );

                _res.put("pRegulationCode",dto.getpRegulationCode());
                _res.put("pBwgBoxPart",dto.getpBwgBoxPart() );
                _res.put("pDevelopType",dto.getpDevelopType());
                _res.put("pDataVersion",dto.getpDataVersion() );
                _res.put("pTargetWeight",dto.getpTargetWeight());
                _res.put("pFeatureWeight",dto.getpFeatureWeight());
                _res.put("pActualWeight",dto.getpActualWeight() );
                _res.put("pFastener",dto.getpFastener());
                _res.put("pFastenerStandard",dto.getpFastenerStandard());
                _res.put("pFastenerLevel",dto.getpFastenerLevel());
                _res.put("pTorque",dto.getpTorque() );
                _res.put("pDutyEngineer",dto.getpDutyEngineer());
                _res.put("pSupply",dto.getpSupply());
                _res.put("pSupplyCode",dto.getpSupplyCode() );

                _res.put("pBuyEngineer",dto.getpBuyEngineer());
                _res.put("pRemark",dto.getpRemark());
                _res.put("pBomLinePartClass",dto.getpBomLinePartClass() );
                _res.put("pBomLinePartResource",dto.getpBomLinePartResource());
                _res.put("pInOutSideFlag",dto.getpInOutSideFlag());
                _res.put("pUpc",dto.getpUpc());
                _res.put("fna",dto.getFna());
                _res.put("pFnaDesc",dto.getpFnaDesc() );
                _res.put("number",dto.getNumber() );
                _res.put("sparePart",dto.getSparePart());
                _res.put("sparePartNum",dto.getSparePartNum());
                _res.put("colorPart",dto.getColorPart());
                _res.putAll(dto.getMap());
                _list.add(_res);
            });
            ret.put("result", _list);
            return ret;
        }
        return null;
    }


    @RequestMapping(value = "pbom/data",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getPBOMChangeDataDetail(HzChangeDataQuery query){
        List<HzPbomLineRespDTO> list = hzChangeDataService.getChangeDataRecordForPBOM(query);
        Map<String, Object> ret = new HashMap<>();
        if(ListUtil.isNotEmpty(list)){
            List<Map<String, Object>> _list = new ArrayList<>();
            list.forEach(dto -> {
                Map<String, Object> _res = new HashMap<>();
//                _res.put("state",ProcessReceiveDto.getState());
                _res.put("puid",dto.getPuid());
                _res.put("changeType",dto.getChangeType());
                _res.put("level", dto.getLevel());
                _res.put("pBomOfWhichDept", dto.getpBomOfWhichDept());
                _res.put("rank", dto.getRank());
                _res.put("lineId", dto.getLineId());
                _res.put("pBomLinePartName", dto.getpBomLinePartName());
                _res.put("pBomLinePartEnName", dto.getpBomLinePartEnName());
                _res.put("pBomLinePartClass", dto.getpBomLinePartClass());
                _res.put("pBomLinePartResource", dto.getpBomLinePartResource());
                _res.put("resource", dto.getResource());
                _res.put("type", dto.getType());
                _res.put("buyUnit", dto.getBuyUnit());
                _res.put("workShop1", dto.getWorkShop1());
                _res.put("workShop2", dto.getWorkShop2());
                _res.put("productLine", dto.getProductLine());
                _res.put("mouldType", dto.getMouldType());
                _res.put("outerPart", dto.getOuterPart());
                _res.put("station", dto.getStation());
                _list.add(_res);
            });
            ret.put("result", _list);
            return ret;
        }
        return null;
    }


    @RequestMapping(value = "mbom/data",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMBOMChangeDataDetail(HzChangeDataQuery query){
        List<HzMbomRecordRespDTO> list = hzChangeDataService.getChangeDataRecordForMBOM(query);
        Map<String, Object> ret = new HashMap<>();
        if(ListUtil.isNotEmpty(list)){
            List<Map<String, Object>> _list = new ArrayList<>();
            list.forEach(dto -> {
                Map<String, Object> _res = new HashMap<>();
                _res.put("puid",dto.getPuid());
//                _res.put("state",ProcessReceiveDto.getState());
                _res.put("changeType",dto.getChangeType());
                _res.put("rank",dto.getRank());
                _res.put("level", dto.getLevel());
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
                _res.put("pLouaFlag",dto.getpLouaFlag());
                _res.put("solderJoint", dto.getSolderJoint());
                _res.put("machineMaterial", dto.getMachineMaterial());
                _res.put("standardPart", dto.getStandardPart());
                _res.put("tools", dto.getTools());
                _res.put("wasterProduct", dto.getWasterProduct());
                _res.put("change", dto.getChange());
                _res.put("changeNum", dto.getChangeNum());
                _res.put("pFactoryCode", dto.getpFactoryCode());
                _res.put("pStockLocation",dto.getpStockLocation());
                _res.put("pBomType", dto.getpBomType());
                _list.add(_res);
            });
            ret.put("result", _list);
            return ret;
        }
        return null;
    }


    @RequestMapping(value = "materiel/data",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMaterielChangeDataDetail(HzChangeDataQuery query){
        List<HzMaterielRespDTO> list = hzChangeDataService.getChangeDataRecordForMateriel(query);
        Map<String, Object> ret = new HashMap<>();
        if(ListUtil.isNotEmpty(list)){
            List<Map<String, Object>> _list = new ArrayList<>();
            list.forEach(dto -> {
                Map<String, Object> _res = new HashMap<>();
                _res.put("puid",dto.getPuid());
//                _res.put("state",ProcessReceiveDto.getState());
                _res.put("changeType",dto.getChangeType());
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
            ret.put("result", _list);
            return ret;
        }
        return null;
    }


    @RequestMapping(value = "work/procedure/data",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getWorkProcedureChangeDataDetail(HzChangeDataQuery query){
        List<HzWorkProcessRespDTO> list = hzChangeDataService.getChangeDataRecordForWorkProcedure(query);
        Map<String, Object> ret = new HashMap<>();
        if(ListUtil.isNotEmpty(list)){
            List<Map<String, Object>> _list = new ArrayList<>();
            list.forEach(dto -> {
                Map<String, Object> _res = new HashMap<>();
                _res.put("puid",dto.getPuid());
                _res.put("changeType",dto.getChangeType());
                _res.put("pMaterielCode", dto.getpMaterielCode());
                _res.put("pMaterielDesc", dto.getpMaterielDesc());
                _res.put("factoryCode", dto.getFactoryCode());
                _res.put("purpose", dto.getPurpose());
                _res.put("state", dto.getState());
                _res.put("pProcedureCode", dto.getpProcedureCode());
                _res.put("pWorkCode", dto.getpWorkCode());
                _res.put("pWorkDesc", dto.getpWorkDesc());
                _res.put("controlCode", dto.getControlCode());
                _res.put("pProcedureDesc", dto.getpProcedureDesc());
                _res.put("pCount", dto.getpCount());
                _res.put("pDirectLabor", dto.getpDirectLabor());
                _res.put("pIndirectLabor", dto.getpIndirectLabor());
                _res.put("pMachineLabor", dto.getpMachineLabor());
                _res.put("pBurn", dto.getpBurn());
                _res.put("pMachineMaterialLabor", dto.getpMachineMaterialLabor());
                _res.put("pOtherCost", dto.getpOtherCost());
                _list.add(_res);
            });
            ret.put("result", _list);
            return ret;
        }
        return null;
    }


    @RequestMapping(value = "ebom/delete",method = RequestMethod.DELETE)
    public void deleteEBOMChangeDataDetail(@RequestBody BomBackReqDTO reqDTO,HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeDataService.deleteEBOMChangeDataDetail(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }

    @RequestMapping(value = "pbom/delete",method = RequestMethod.DELETE)
    public void deletePBOMChangeDataDetail(@RequestBody BomBackReqDTO reqDTO,HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeDataService.deletePBOMChangeDataDetail(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }


    @RequestMapping(value = "mbom/delete",method = RequestMethod.DELETE)
    public void deleteMBOMChangeDataDetail(@RequestBody BomBackReqDTO reqDTO,HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeDataService.deleteMBOMChangeDataDetail(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }



    @RequestMapping(value = "materiel/delete",method = RequestMethod.DELETE)
    public void deleteMaterielChangeDataDetail(@RequestBody BomBackReqDTO reqDTO,HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeDataService.deleteMaterielChangeDataDetail(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }

    @RequestMapping(value = "work/procedure/delete",method = RequestMethod.DELETE)
    public void deleteWorkProcedureChangeDataDetail(@RequestBody BomBackReqDTO reqDTO,HttpServletResponse response){
        WriteResultRespDTO resultRespDTO = hzChangeDataService.deleteWorkProcedureChangeDataDetail(reqDTO);
        toJSONResponse(Result.build(WriteResultRespDTO.isSuccess(resultRespDTO),resultRespDTO.getErrMsg()),response);
    }

}
