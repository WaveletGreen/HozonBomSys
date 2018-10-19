package com.connor.hozon.bom.resources.controller.user;

import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.domain.dto.request.UpdateUserPasswordReqDTO;
import com.connor.hozon.bom.resources.util.Result;
import com.connor.hozon.bom.sys.dao.UserDao;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: haozt
 * @Date: 2018/7/23
 * @Description:
 */
@Controller
@RequestMapping("user")
public class UserInfoController extends BaseController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "update/password",method = RequestMethod.POST)
    public void updateUserPassWord(@RequestBody UpdateUserPasswordReqDTO reqDTO, HttpServletResponse response){
        User user = UserInfo.getUser();
        if(user ==null || user.getUserName() == null){
            toJSONResponse(Result.build(false,"当前用户不存在！"),response);
            return;
        }
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        String oldPassWord = reqDTO.getOldPassWord();
        String oldPassWordInDB = user.getPassword();
        if(!encoder.encodePassword(oldPassWord,"hyll").equals(oldPassWordInDB)){//加密 盐 最好还是随机产生
            toJSONResponse(Result.build(false,"原始密码输入不正确！"),response);
            return;
        }

        String newPassWord = reqDTO.getNewPassWord();
        newPassWord = encoder.encodePassword(newPassWord,"hyll");
        User userInfo = new User();
        userInfo.setId(user.getId());
        userInfo.setPassword(newPassWord);
        int i = userDao.updatePassword(userInfo);
        if(i<=0){
            toJSONResponse(Result.build(false,"操作失败！"),response);
            return;
        }
        toJSONResponse(Result.build(true,"操作成功！"),response);

    }

    @RequestMapping(value = "/getUser",method = RequestMethod.GET)
    public String get(Model model){
        User user = UserInfo.getUser();
        model.addAttribute("data",user);
        return "updatePassword";
    }

}
