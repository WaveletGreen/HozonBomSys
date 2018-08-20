package com.connor.hozon.bom.resources.controller.dept;

import com.connor.hozon.bom.resources.controller.BaseController;
import com.connor.hozon.bom.resources.util.ResultMessageBuilder;
import com.connor.hozon.bom.sys.dao.OrgGroupDao;
import com.connor.hozon.bom.sys.dao.UserDao;
import com.connor.hozon.bom.sys.entity.OrgGroup;
import com.connor.hozon.bom.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.prefs.BackingStoreException;

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
        writeAjaxJSONResponse(ResultMessageBuilder.build(list),response);
    }

    /**
     * 查询部门下面的人员信息
     * @param response
     */
    @RequestMapping(value = "user",method = RequestMethod.GET)
    public void getUserByGroupId(HttpServletResponse response){
        List<User> list = userDao.findAllUser();
        writeAjaxJSONResponse(ResultMessageBuilder.build(list),response);
    }

}
