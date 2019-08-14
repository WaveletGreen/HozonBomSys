package com.connor.hozon.bom.resources.controller.dept;

import com.alibaba.fastjson.JSONArray;
import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.util.Result;
import cn.net.connor.hozon.dao.dao.sys.OrgGroupDao;
import cn.net.connor.hozon.dao.dao.sys.UserDao;
import cn.net.connor.hozon.dao.pojo.sys.OrgGroup;
import cn.net.connor.hozon.dao.pojo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: haozt
 * @Date: 2018/8/17
 * @Description:
 */
@Controller
@RequestMapping(value = "/dept")
public class DeptController extends BaseController {

    @Autowired
    private OrgGroupDao orgGroupDao;

    @Autowired
    private UserDao userDao;

    /**
     * 获取全部的部门
     * @param response
     */
    @RequestMapping(value = "all",method = RequestMethod.GET)
    public void getAllDept(HttpServletResponse response){
        List<OrgGroup> list = orgGroupDao.queryAllOrgGroup();
        toJSONResponse(Result.build(list),response);
    }

    /**
     * 查询全部的人员信息
     * @param response
     */
    @RequestMapping(value = "user",method = RequestMethod.GET)
    @ResponseBody
    public JSONArray getUserByGroupId(HttpServletResponse response){
        List<User> list = userDao.findAllUser();
        JSONArray array = new JSONArray();
        list.forEach(user -> {
            array.add(user);
        });
        return array;
    }

}
