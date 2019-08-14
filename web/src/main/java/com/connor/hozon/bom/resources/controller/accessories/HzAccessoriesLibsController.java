package com.connor.hozon.bom.resources.controller.accessories;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.DeleteHzAccessoriesLibsDTO;
import com.connor.hozon.bom.resources.domain.query.HzAccessoriesLibsPageQuery;
import com.connor.hozon.bom.resources.mybatis.accessories.HzAccessoriesLibsDAO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.net.connor.hozon.dao.pojo.depository.accessories.HzAccessoriesLibs;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/9/27
 * Time: 15:43
 */
@Controller
@RequestMapping(value = "acce")
public class HzAccessoriesLibsController extends BaseController {
    @Autowired
    private HzAccessoriesLibsDAO hzAccessoriesLibsDAO;
    /**
     * 获工艺辅料标题
     *
     * @param response
     */
    @RequestMapping(value = "title", method = RequestMethod.GET)
    public void getHzSuperMBomTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> tableTitle = new LinkedHashMap<>();
        tableTitle.put("No", "序号");
        tableTitle.put("pMaterielCode", "物料号");
        tableTitle.put("pMaterielName", "材料名称");
        tableTitle.put("pTechnicalConditions", "技术条件/牌号规格");
        tableTitle.put("pMeasuringUnit", "计量单位");
        tableTitle.put("pMaterielPurpose", "材料用途");
        tableTitle.put("pDosageBicycle", "单车用量");
        tableTitle.put("pNote", "备注");
        toJSONResponse(Result.build(tableTitle), response);
    }  

    /**
     * 插入一条记录
     *
     * @param hzAccessoriesLibs
     * @param response
     */
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public void insert(@RequestBody HzAccessoriesLibs hzAccessoriesLibs, HttpServletResponse response) {
        int j = hzAccessoriesLibsDAO.selectHzAccessoriesLibsByCount(hzAccessoriesLibs.getpMaterielCode());
        if (j>0){
            toJSONResponse(Result.build(false, "对不起！您添加的物料号已存在！"), response);
            return;
        }
        String puid = UUID.randomUUID().toString();
        hzAccessoriesLibs.setPuid(puid);
        hzAccessoriesLibs.setpCreateName(UserInfo.getUser().getUsername());
        int i = hzAccessoriesLibsDAO.insert(hzAccessoriesLibs);
        if (i > 0) {
            toJSONResponse(Result.build(true, "操作成功！"), response);
            return;
        }
        toJSONResponse(Result.build(false, "操作失败！"), response);
    }


    /**
     * 编辑一条记录
     *
     * @param hzAccessoriesLibs
     * @param response
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public void update(@RequestBody HzAccessoriesLibs hzAccessoriesLibs, HttpServletResponse response) {
        int j = hzAccessoriesLibsDAO.selectHzAccessoriesLibsByCount(hzAccessoriesLibs.getpMaterielCode());
        HzAccessoriesLibs libs = hzAccessoriesLibsDAO.getHzAccessoriesLibsByCode(hzAccessoriesLibs.getpMaterielCode());
        if (j>1){
            toJSONResponse(Result.build(false, "对不起！您修改后的物料号已存在！"), response);
            return;
        }else if (j==1&&libs.getPuid().equals(hzAccessoriesLibs.getPuid())==false){
            toJSONResponse(Result.build(false, "对不起！您修改后的物料号已存在！"), response);
            return;
        }
        hzAccessoriesLibs.setpUpdateName(UserInfo.getUser().getUsername());
        int i = hzAccessoriesLibsDAO.update(hzAccessoriesLibs);
        if (i > 0) {
            toJSONResponse(Result.build(true, "操作成功！"), response);
            return;
        }
        toJSONResponse(Result.build(false, "操作失败！"), response);

    }

    /**
     * 批量删除
     *
     * @param deleteHzAccessoriesLibsDTO
     * @param response
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public void delete(@RequestBody DeleteHzAccessoriesLibsDTO deleteHzAccessoriesLibsDTO, HttpServletResponse response) {
        if (deleteHzAccessoriesLibsDTO.getPuids() == null || deleteHzAccessoriesLibsDTO.getPuids() == "") {
            toJSONResponse(Result.build(false, "非法参数！"), response);
            return;
        }
        List<DeleteHzAccessoriesLibsDTO> list = new ArrayList<>();
        String[] puids = deleteHzAccessoriesLibsDTO.getPuids().split(",");
        for (String puid : puids) {
            DeleteHzAccessoriesLibsDTO dto  = new DeleteHzAccessoriesLibsDTO();
            dto.setPuid(puid);
            list.add(dto);
        }
        try {
            hzAccessoriesLibsDAO.deleteList(list);
        } catch (Exception e) {
            toJSONResponse(Result.build(false, "操作失败！"), response);
            return;
        }
        toJSONResponse(Result.build(true, "操作成功！"), response);
    }

    /**
     * 分页查询辅料库的数据
     * @param query
     * @return
     */
    @RequestMapping(value = "get/lib", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getHzAccessories(HzAccessoriesLibsPageQuery query) {
        HzAccessoriesLibsPageQuery hzAccessoriesLibsPageQuery = query;
        hzAccessoriesLibsPageQuery.setPageSize(0);
        try {
            hzAccessoriesLibsPageQuery.setPageSize(Integer.valueOf(query.getLimit()));
        } catch (Exception e) {

        }
        Page<HzAccessoriesLibs> page = hzAccessoriesLibsDAO.getHzAccessoriesByPage(query);
        int i = 0;
        if (ListUtil.isEmpty(page.getResult())) {
            return new HashMap<>();
        }
        List<HzAccessoriesLibs> libs = page.getResult();
        Map<String, Object> ret = new HashMap<>();
        List<Map<String, Object>> _list = new ArrayList<>();
        for (HzAccessoriesLibs lib : libs) {
            Map<String, Object> _res = new HashMap<>();
            _res.put("No", ++i);
            _res.put("puid",lib.getPuid() );
            _res.put("pMaterielCode",lib.getpMaterielCode() );
            _res.put("pMaterielName",lib.getpMaterielName());
            _res.put("pTechnicalConditions",lib.getpTechnicalConditions() );
            _res.put("pMeasuringUnit",lib.getpMeasuringUnit() );
            _res.put("pMaterielPurpose",lib.getpMaterielPurpose() );
            _res.put("pDosageBicycle",lib.getpDosageBicycle() );
            _res.put("pNote",lib.getpNote() );
            _list.add(_res);
        }
        ret.put("totalCount", page.getTotalCount());
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
        return "resourcesLibrary/accessoriesLibrary/addAccessoriesLibrary";
    }


    /**
     * 跳转到工艺辅料的修改页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "updateAccessories", method = RequestMethod.GET)
    public String updateMbomToPage(String puid, Model model) {
        List<HzAccessoriesLibs> libs = hzAccessoriesLibsDAO.getHzAccessoriesLibs(puid);
        if (ListUtil.isEmpty(libs)) {
            return "";
        }
        model.addAttribute("data", libs.get(0));
        return "resourcesLibrary/accessoriesLibrary/updateAccessoriesLibrary";
    }

    /**
     * 跳转到工艺辅料库的导入页面
     * @return
     */
    @RequestMapping(value = "importExcel",method = RequestMethod.GET)
    public String getExcelImport() {
        return "resourcesLibrary/accessoriesLibrary/importExcel";
    }
}
