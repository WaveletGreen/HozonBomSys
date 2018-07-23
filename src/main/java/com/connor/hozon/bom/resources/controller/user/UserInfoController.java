package com.connor.hozon.bom.resources.controller.user;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.dto.request.UpdateUserPasswordReqDTO;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: haozt
 * @Date: 2018/7/23
 * @Description:
 */
@Controller
@RequestMapping("user")
public class UserInfoController extends BaseController {

    @RequestMapping(value = "update/password",method = RequestMethod.POST)
    public void updateUserPassWord(@RequestBody UpdateUserPasswordReqDTO reqDTO, HttpServletResponse response){
        User user = UserInfo.getUser();
        if(user ==null || user.getUserName() == null){
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"当前用户不存在！"),response);
            return;
        }
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String oldPassWord = reqDTO.getOldPassWord();
        String oldPassWordInDB = user.getPassword();
        if(!encoder.encodePassword(oldPassWord,"hyll").equals(oldPassWordInDB)){//加密 盐 最好还是随机产生
            writeAjaxJSONResponse(ResultMessageBuilder.build(false,"原始密码输入不正确！"),response);
            return;
        }

        String newPassWord = reqDTO.getNewPassWord();
        newPassWord = encoder.encodePassword(newPassWord,"hyll");
//        return encoder.encodePassword(password, "hyll");

    }

    public static void main(String[] a){
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String passWord = "dcproxy123";
        System.out.println(encoder.encodePassword(passWord,"hyll"));
    }
}
