package com.connor.hozon.bom.resources.controller.bom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.AddHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.DeleteHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.request.FindForPageReqDTO;
import com.connor.hozon.bom.resources.dto.request.UpdateHzEbomReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzEbomRespDTO;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.bom.HzEbomService;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import com.connor.hozon.bom.sys.entity.User;
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
 * \* User: xulf
 * \* Date: 2018/6/4
 * \* Time: 13:01
 * \
 */
@Controller
@RequestMapping(value = "/ebom")
public class HzEbomController extends BaseController {

    @Autowired
    private HzEbomService hzEbomService;

    @RequestMapping(value = "/ebomTitle",method = RequestMethod.GET)
    public void getEbomTitle(String projectId, HttpServletResponse response) {
        if(projectId==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
        }
        JSONArray array = hzEbomService.getEbomTitle(projectId);
        if(array==null){
        writeAjaxJSONResponse(ResultMessageBuilder.build(false,"网络错误！"), response);
    }
    writeAjaxJSONResponse(ResultMessageBuilder.build(array), response);
}

    @RequestMapping(value = "getEBom/list", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEbomList(FindForPageReqDTO reqDTO) {
        Page<HzEbomRespDTO> recordRespDTOPage = hzEbomService.getHzEbomPage(reqDTO);
        Map<String, Object> ret = new HashMap<>();
        if(recordRespDTOPage == null){
            return ret;
        }
        List<HzEbomRespDTO> recordRespDTOS =  recordRespDTOPage.getResult();
        if (ListUtil.isEmpty(recordRespDTOS)) {
            return ret;
        }
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        JSONArray array = recordRespDTOS.get(0).getJsonArray();
        for(int i =0;i<array.size();i++){
            JSONObject object = array.getJSONObject(i);
            map = object;
            list.add(map);
        }
        ret.put("totalCount", recordRespDTOPage.getTotalCount());
        ret.put("result", list);
        return ret;
    }

    @RequestMapping(value = "getEBom", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getEbomById(String puid,String projectId ) {
        Map<String, Object> ret = new HashMap<>();
        if(puid == null || projectId == null){
            return ret;
        }
        HzEbomRespDTO recordRespDTO = hzEbomService.fingEbomById(puid,projectId);
        if(recordRespDTO == null){
            return ret;
        }
        JSONArray array = recordRespDTO.getJsonArray();
        JSONObject object = array.getJSONObject(0);
        ret = object;
        return ret;
    }


    @RequestMapping(value = "addEbom",method = RequestMethod.GET)
    public String addEbom(String projectId,String puid,Model model) {
        if(projectId == null){
            return "";
        }
        JSONArray array = hzEbomService.getEbomTitle(projectId);
        if(array == null){
            return "";
        }

        //过滤掉没必要的标题
        JSONArray jsonArray = new JSONArray();
        String[] strings1 = (String[]) array.get(0);
        String[] strings2 = (String[]) array.get(1);

        List<String> list1 = Arrays.asList(strings1);
        List<String> list2 = Arrays.asList(strings2);
        List<String> arrayList1=new ArrayList<String>(list1);
        List<String> arrayList2=new ArrayList<String>(list2);
        if(list1.contains("puid")){
            arrayList1.remove("puid");
        }
        if(list1.contains("序号")){
            arrayList1.remove("序号");
        }
        if(list1.contains("级别")){
            arrayList1.remove("级别");
        }
        if(list1.contains("分组号")){
            arrayList1.remove("分组号");
        }
        if(list1.contains("层级")){
            arrayList1.remove("层级");
        }

        if(list2.contains("puid")){
            arrayList2.remove("puid");
        }
        if(list2.contains("No")){
            arrayList2.remove("No");
        }
        if(list2.contains("rank")){
            arrayList2.remove("rank");
        }
        if(list2.contains("groupNum")){
            arrayList2.remove("groupNum");
        }
        if(list2.contains("level")){
            arrayList2.remove("level");
        }
        if(puid!=null){
            HzEbomRespDTO recordRespDTO = hzEbomService.fingEbomById(puid,projectId);
            if(array == null ||recordRespDTO == null){
                return "";
            }
            JSONArray array1 = recordRespDTO.getJsonArray();
            JSONObject object = array1.getJSONObject(0);
            for(int i =0 ;i<strings2.length;i++){
                if("puid".equals(strings2[i])){
                    model.addAttribute("puid",object.getString(strings2[i]));

                }
            }
        }
        String[] strings3 = arrayList1.toArray(new String[0]);
        String[] strings4 = arrayList2.toArray(new String[0]);
        jsonArray.add(strings3);
        jsonArray.add(strings4);
        model.addAttribute("data",jsonArray);

        return "bomManage/ebom/ebomManage/addebomManage";
    }

    @RequestMapping(value = "updateEbom",method = RequestMethod.GET)
    public String updateEbom(String projectId,String puid,Model model) {
        if(projectId == null || puid == null){
            return "";
        }
        JSONArray array = hzEbomService.getEbomTitle(projectId);
        HzEbomRespDTO recordRespDTO = hzEbomService.fingEbomById(puid,projectId);
        if(array == null ||recordRespDTO == null){
            return "";
        }
        //过滤掉没必要的标题
        JSONArray jsonArray = new JSONArray();
        String[] strings1 = (String[]) array.get(0);
        String[] strings2 = (String[]) array.get(1);

        List<String> list1 = Arrays.asList(strings1);
        List<String> list2 = Arrays.asList(strings2);
        List<String> arrayList1=new ArrayList<String>(list1);
        List<String> arrayList2=new ArrayList<String>(list2);

        if(list1.contains("序号")){
            arrayList1.remove("序号");
        }
        if(list1.contains("级别")){
            arrayList1.remove("级别");
        }
        if(list1.contains("层级")){
            arrayList1.remove("层级");
        }
        if(list1.contains("分组号")){
            arrayList1.remove("分组号");
        }
        if(list1.contains("puid")){
            arrayList1.remove("puid");
        }
        if(list2.contains("No")){
            arrayList2.remove("No");
        }
        if(list2.contains("rank")){
            arrayList2.remove("rank");
        }
        if(list2.contains("level")){
            arrayList2.remove("level");
        }
        if(list2.contains("groupNum")){
            arrayList2.remove("groupNum");
        }
        if(list2.contains("puid")){
            arrayList2.remove("puid");
        }
        String[] strings3 =arrayList1.toArray(new String[0]);;
        String[] strings4 = arrayList2.toArray(new String[0]);
        jsonArray.add(strings3);
        jsonArray.add(strings4);


        JSONArray array1 = recordRespDTO.getJsonArray();
        JSONObject object = array1.getJSONObject(0);
        String[] strings5 = new String[strings2.length];
        for(int i =0 ;i<strings2.length;i++){
            if("puid".equals(strings2[i])){
                model.addAttribute("puid",object.getString(strings2[i]));
                continue;
            }
            strings5[i] = object.getString(strings2[i])==null?"":object.getString(strings2[i]);
        }
        jsonArray.add(strings5);
        model.addAttribute("data",jsonArray);

        return "bomManage/ebom/ebomManage/updateEbomManage";
    }
    /**
     * 添加ebom信息
     * @param reqDTO
     * @param map
     * @param response
     */
    @RequestMapping(value = "add/ebom",method = RequestMethod.POST)
    public void addEbomToDB(AddHzEbomReqDTO reqDTO, @RequestBody Map<String,Object> map, HttpServletResponse response){
        if(reqDTO.getProjectId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
        }
        User user = UserInfo.getUser();
        if(user.getGroupId()!=9){//管理员权限
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"您没有权限进行当前操作！"), response);
        }
        AddHzEbomReqDTO dto = new AddHzEbomReqDTO();
        dto.setMap(map);
        dto.setProjectId(reqDTO.getProjectId());
        int i = hzEbomService.addHzEbomRecord(dto);
        if(i==0){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"操作失败！"), response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(true,"操作成功！"), response);
    }


    /**
     * 更新ebom信息
     * @param reqDTO
     * @param map
     * @param response
     */
    @RequestMapping(value = "update/ebom",method = RequestMethod.POST)
    public void updateEbomToDB(UpdateHzEbomReqDTO reqDTO, @RequestBody Map<String,Object> map, HttpServletResponse response){
        if(reqDTO.getProjectId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
        }
        User user = UserInfo.getUser();
        if(user.getGroupId()!=9){//管理员权限
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"您没有权限进行当前操作！"), response);
        }
        UpdateHzEbomReqDTO dto = new UpdateHzEbomReqDTO();
        dto.setUpdateContent(map);
        dto.setProjectId(reqDTO.getProjectId());
        int i = hzEbomService.updateHzEbomRecord(dto);
        if(i==0){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"操作失败！"), response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(true,"操作成功！"), response);
    }


    /**
     * 删除ebom信息
     * @param reqDTO
     * @param
     * @param response
     */
    @RequestMapping(value = "delete/ebom",method = RequestMethod.POST)
    public void updateEbomToDB(DeleteHzEbomReqDTO reqDTO, HttpServletResponse response){
        if(reqDTO.getProjectId()==null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"非法参数！"), response);
        }
        User user = UserInfo.getUser();
        if(user.getGroupId()!=9){//管理员权限
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"您没有权限进行当前操作！"), response);
        }
        int i = hzEbomService.deleteHzEbomRecordById(reqDTO);
        if(i==0){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"操作失败！"), response);
        }
        writeAjaxJSONResponse(ResultMessageBuilder.build(true,"操作成功！"), response);
    }


    /**
     * 获取当前登录用户信息
     */
    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public void getUser(HttpServletResponse  response){
        User user = UserInfo.getUser();
        writeAjaxJSONResponse(ResultMessageBuilder.build(user),response);
        System.out.println(JSON.toJSONString(user));
    }
}