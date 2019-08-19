package com.connor.hozon.bom.controller.bom.privilege;

import cn.net.connor.hozon.common.util.ListUtils;
import com.connor.hozon.bom.controller.bom.BaseController;
import com.connor.hozon.bom.resources.domain.dto.response.WriteResultRespDTO;
import com.connor.hozon.bom.resources.util.Result;
import cn.net.connor.hozon.config.filter.HzFilter;
import com.connor.hozon.bom.service.sys.impl.UserRolePrivilegeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/11/29
 * @Description: 简要操作权限判断
 */
@Controller
@RequestMapping("privilege")
public class HzPrivilegeController extends BaseController {
    @Autowired
    @Qualifier("userRolePrivilegeServiceImpl")
    private UserRolePrivilegeServiceImpl userRolePrivilegeService;

    @RequestMapping(value = "write",method = RequestMethod.GET)
    public void openWritePrivilege(String url,HttpServletResponse response){
        String uri ="/hozon/"+url;
        if(uri.contains("?")){
            uri= uri.split("\\?")[0];
        }
        List<String> urls = HzFilter.getUrlList();
        if(ListUtils.isNotEmpty(urls)){
            if(urls.contains(uri)){
                WriteResultRespDTO resultRespDTO = userRolePrivilegeService.hasPrivilege(uri,urls);
                if(WriteResultRespDTO.isSuccess(resultRespDTO)){
                    toJSONResponse(Result.build(true),response);
                }else {
                   toJSONResponse(Result.build(false,resultRespDTO.getErrMsg()),response);
                }
            }else {
                toJSONResponse(Result.build(true),response);
            }
        }else {
            toJSONResponse(Result.build(false,"服务器异常,请稍后重试!"),response);
        }
    }

}
