package com.connor.hozon.bom.resources.controller.accessories;

import com.connor.hozon.bom.bomSystem.dao.impl.bom.HzBomLineRecordDaoImpl;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.DeleteHzAccessoriesDTO;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzAccessoriesPageQuery;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sql.pojo.accessories.HzAccessoriesLib;
import sql.pojo.bom.HzBomLineRecord;
import sql.pojo.bom.HzMbomLineRecord;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author: haozt
 * @Date: 2018/7/16
 * @Description:
 */
@Controller
@RequestMapping(value = "accessories")
public class HzAccessoriesController extends BaseController {
    @Autowired
    private HzAccessoriesDAO hzAccessoriesDAO;

    /**
     * 获工艺辅料标题
     *
     * @param response
     */
    @RequestMapping(value = "title", method = RequestMethod.GET)
    public void getHzSuperMBomTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("pLineId", "零件号");
        tableTitle.put("pBomLinePartName", "零件名称");
        tableTitle.put("pBomLinePartEnName", "零件英文名称");
        tableTitle.put("pUnit", "单位");
        tableTitle.put("pMaterialHigh", "料厚");
        tableTitle.put("pMaterial1", "材料1");
        tableTitle.put("pMaterial2", "材料2");
        tableTitle.put("pMaterial3", "材料3");
        tableTitle.put("pDensity", "密度");
        tableTitle.put("pMaterialStandard", "材料标准");
        tableTitle.put("pSurfaceTreat", "表面处理");
        tableTitle.put("pTextureColorNum", "纹理编号/色彩编号");
        tableTitle.put("pManuProcess", "制造工艺");
        tableTitle.put("pTargetWeight", "目标重量(kg)");
        tableTitle.put("pFutureWeight", "预估重量(kg)");
        tableTitle.put("pActualWeight", "实际重量(kg)");
        tableTitle.put("pDutyEngineer", "责任工程师");
        tableTitle.put("pSupply", "供应商");
        tableTitle.put("pSupplyCode", "供应商代码");
        tableTitle.put("pRemark", "备注");
        writeAjaxJSONResponse(ResultMessageBuilder.build(tableTitle), response);
    }

    /**
     * 插入一条记录
     *
     * @param hzAccessoriesLib
     * @param response
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public void insert(@RequestBody HzAccessoriesLib hzAccessoriesLib, HttpServletResponse response) {
        String puid = UUID.randomUUID().toString();
        hzAccessoriesLib.setPuid(puid);
        int i = hzAccessoriesDAO.insert(hzAccessoriesLib);
        if (i > 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, "操作成功！"), response);
            return;
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "操作失败！"), response);

    }


    /**
     * 编辑一条记录
     *
     * @param hzAccessoriesLib
     * @param response
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void update(@RequestBody HzAccessoriesLib hzAccessoriesLib, HttpServletResponse response) {
        int i = hzAccessoriesDAO.update(hzAccessoriesLib);
        if (i > 0) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(true, "操作成功！"), response);
            return;
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(false, "操作失败！"), response);

    }


    /**
     * 批量删除
     *
     * @param reqDTO
     * @param response
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody DeleteHzAccessoriesDTO reqDTO, HttpServletResponse response) {
        if (reqDTO.getPuids() == null || reqDTO.getPuids() == "") {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, "非法参数！"), response);
            return;
        }
        List<DeleteHzAccessoriesDTO> list = new ArrayList<>();
        String[] puids = reqDTO.getPuids().split(",");
        for (String puid : puids) {
            DeleteHzAccessoriesDTO deleteHzAccessoriesDTO = new DeleteHzAccessoriesDTO();
            deleteHzAccessoriesDTO.setPuid(puid);
            list.add(deleteHzAccessoriesDTO);
        }
        try {
            hzAccessoriesDAO.deleteList(list);
        } catch (Exception e) {
            writeAjaxJSONResponse(ResultMessageBuilder.build(false, "操作失败！"), response);
            return;
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(true, "操作成功！"), response);

    }


    @RequestMapping(value = "get/lib", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHzAccessories(HzAccessoriesPageQuery query) {
        HzAccessoriesPageQuery hzAccessoriesPageQuery = query;
        hzAccessoriesPageQuery.setPageSize(0);
        try {
            hzAccessoriesPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        } catch (Exception e) {

        }
        Page<HzAccessoriesLib> page = hzAccessoriesDAO.getHzAccessoriesByPage(query);
        int i = 0;
        if (ListUtil.isEmpty(page.getResult())) {
            return new HashMap<>();
        }
        List<HzAccessoriesLib> libs = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        for (HzAccessoriesLib lib : libs) {
            Map<String, Object> _res = new HashMap<>();
            _res.put("No", ++i);
            _res.put("puid", lib.getPuid());
            _res.put("pBomLinePartName", lib.getpBomLinePartName());
            _res.put("pBomLinePartEnName", lib.getpBomLinePartEnName());
            _res.put("pLineId", lib.getpLineId());
            _res.put("pUnit", lib.getpUnit());
            _res.put("pMaterialHigh", lib.getpMaterialHigh());
            _res.put("pMaterial1", lib.getpMaterial1());
            _res.put("pMaterial2", lib.getpMaterial2());
            _res.put("pMaterial3", lib.getpMaterial3());
            _res.put("pDensity", lib.getpDensity());
            _res.put("pMaterialStandard", lib.getpMaterialStandard());
            _res.put("pSurfaceTreat", lib.getpSurfaceTreat());
            _res.put("pTextureColorNum", lib.getpTextureColorNum());
            _res.put("pManuProcess", lib.getpManuProcess());
            _res.put("pTargetWeight", lib.getpTargetWeight());
            _res.put("pFutureWeight", lib.getpFutureWeight());
            _res.put("pActualWeight", lib.getpActualWeight());
            _res.put("pDutyEngineer", lib.getpDutyEngineer());
            _res.put("pSupply", lib.getpSupply());
            _res.put("pSupplyCode", lib.getpSupplyCode());
            _res.put("pRemark", lib.getpRemark());

            _list.add(_res);
        }
        ret.put("totalCount", libs.size());
        ret.put("result", _list);
        return ret;
    }


    /**
     * 跳转到工艺辅料管理的添加页面
     *
     * @return
     */
    @RequestMapping(value = "addAccessories", method = RequestMethod.GET)
    public String addMBomToPage() {
        return "bomManage/pbom/processAids/addProcessAids";
    }

    /**
     * 跳转到工艺辅料的修改页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "updateAccessories", method = RequestMethod.GET)
    public String updateMbomToPage(String puid, Model model) {
        List<HzAccessoriesLib> libs = hzAccessoriesDAO.getHzAccessoriesLibs(puid);
        if (ListUtil.isEmpty(libs)) {
            return "";
        }
        model.addAttribute("data", libs.get(0));
        return "bomManage/pbom/processAids/updateProcessAids";
    }
}
