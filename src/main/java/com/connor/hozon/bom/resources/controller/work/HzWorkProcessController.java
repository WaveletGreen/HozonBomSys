package com.connor.hozon.bom.resources.controller.work;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;

/**
 * @Author: haozt
 * @Date: 2018/7/5
 * @Description:
 */
@Controller
@RequestMapping(value = "work/process")
public class HzWorkProcessController extends BaseController {
    /**
     * 工艺路线的标题
     * @param response
     */
    @RequestMapping(value = "title",method = RequestMethod.GET)
    public void HzWorkProcessTitle(HttpServletResponse response) {
        LinkedHashMap<String, String> title = new LinkedHashMap<>();
        title.put("pMaterielCode", "物料");
        title.put("pMaterielDesc","物料名称");
        title.put("factoryCode", "工厂");
        title.put("purpose", "用途");
        title.put("state", "状态");
        title.put("pProcedureCode", "工序号");
        title.put("workCenterCode", "工作中心");
        title.put("workCenterDesc", "工作中心描述");
        title.put("controlCode", "控制码");
        title.put("pProcedureDesc", "工序描述");
        title.put("pCount", "基本数量 ");
        title.put("pDirectLabor", "直接人工时间");
        title.put("pIndirectLabor", "间接人工时间");
        title.put("pMachineLabor", "机器时间");
        title.put("pBurn", "燃动能");
        title.put("pMachineMaterialLabor", "机物料消耗");
        title.put("pOtherCost", "其他费用");
        writeAjaxJSONResponse(ResultMessageBuilder.build(title), response);
    }




    /**
     * 跳转到工艺路线添加页面
     * @return
     */
    @RequestMapping(value = "addWorkProcess",method = RequestMethod.GET)
    public String addWorkProcessToPage(){
        return  "bomManage/mbom/routingData/addRoutingData";
    }
    /**
     * 跳转到工艺路线修改页面
     * @return
     */
    @RequestMapping(value = "updateWorkProcess",method = RequestMethod.GET)
    public String updateWorkProcessToPage(){
      return  "bomManage/mbom/routingData/updateRoutingData";
    }
}
