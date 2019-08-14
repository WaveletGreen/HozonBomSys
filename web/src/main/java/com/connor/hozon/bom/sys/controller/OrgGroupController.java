package com.connor.hozon.bom.sys.controller;


import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.common.base.controller.GenericController;
import cn.net.connor.hozon.common.entity.Page;
import com.connor.hozon.bom.common.base.service.GenericService;
import com.connor.hozon.bom.common.util.json.JsonHelper;

import cn.net.connor.hozon.dao.pojo.sys.OrgGroup;
import cn.net.connor.hozon.dao.query.sys.QueryOrgGroup;
import cn.net.connor.hozon.dao.query.sys.QueryUser;
import cn.net.connor.hozon.dao.pojo.sys.User;
import com.connor.hozon.bom.sys.service.OrgGroupService;
import com.connor.hozon.bom.sys.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * 类描述：组织架构的操作类
 * @auther linzf
 * @create 2017/9/30 0030
 */
@Controller
@RequestMapping("/group")
public class OrgGroupController extends GenericController<OrgGroup, QueryOrgGroup> {

    @Inject
    private OrgGroupService orgGroupService;
    @Inject
    private UserService userService;

    @Override
    protected GenericService<OrgGroup, QueryOrgGroup> getService() {
        return orgGroupService;
    }

    /**
     * 功能描述：跳转到更新用户的页面
     *
     * @param entity
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateUserPage")
    public String updateUserPage(User entity, Model model) throws Exception {
        entity = userService.get(entity);
        entity.setRoleArray(JsonHelper.list2json(Optional.ofNullable(userService.findByLogin(entity.getLogin())).filter(u -> u != null).orElse(new User()).getRoles()));
        model.addAttribute("entity", entity);
        String page = getPageBaseRoot() + "/addUser";
        return page;
    }

    /**
     * 跳转到添加用户的页面
     *
     * @throws Exception
     */
    @RequestMapping(value = "/addUserPage")
    public String addUserPage() throws Exception {
        String page = getPageBaseRoot() + "/addUser";
        return page;
    }

    @Override
    public Map<String, Object> update(OrgGroup entity) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        OrgGroup update = new OrgGroup();
        update.setGroupId(entity.getGroupId());
        update = orgGroupService.get(update);
        update.setName(entity.getName());
        update.setGroupCode(entity.getGroupCode());
        update.setNum(entity.getNum());
        boolean success = orgGroupService.update(update);
        if (success) {
            result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
            result.put(SystemStaticConst.MSG, "修改数据成功！");
            result.put("entity", entity);
        } else {
            result.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG, "修改数据失败！");
        }
        return result;
    }

    @Override
    public Map<String, Object> save(OrgGroup entity) throws Exception {
        String max_node = getMaxNode(orgGroupService.getMaxOrgGroup(entity.getOrgGroup().getNode()), entity.getOrgGroup().getNode());
        entity.setParentNode(entity.getOrgGroup().getNode());
        entity.setNode(max_node);
        return super.save(entity);
    }

    @RequestMapping(value = "/updateGroupPage")
    //@ResponseBody
    public String updateGroupPage(OrgGroup entity, Model model) throws Exception {
        entity = orgGroupService.get(entity);
        entity.setOrgGroup(orgGroupService.findByNode(entity.getParentNode()));
        model.addAttribute("entity", entity);
        //return getPageBaseRoot()+UPDATEPAGE;
        return "sys/orggroup/update";
    }

    @RequestMapping(value = "/addGroupPage")
    //@ResponseBody
    public String addPage(OrgGroup entity, Model model) throws Exception {
        entity = orgGroupService.get(entity);
        model.addAttribute("entity", entity);
        //return getPageBaseRoot()+ADDPAGE;
        return "sys/orggroup/add";
    }

    /**
     * 功能描述：获取组织架构底下的相应的用户
     *
     * @return
     */
    @RequestMapping(value = "/userList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> userList(QueryUser user) {
        Map<String, Object> result = new HashMap<String, Object>();
        user.setSort(User.reflectToDBField(user.getSort()));
        Page page = userService.findByGroupUserPage(user);
        result.put("totalCount", page.getTotal());
        result.put("result", page.getRows());
        return result;
    }

    /**
     * 功能描述：获取组织架构的整颗菜单树
     *
     * @return
     */
    @RequestMapping(value = "/loadGroupTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> loadGroupTree() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<OrgGroup> orgGroupList = orgGroupService.query(null);
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG, "加载组织机构数据成功！");
        result.put("data", orgGroupList);
        return result;
    }




    /**
     * 功能描述：查询部门名称是否存在
     *
     * @return
     */
    @RequestMapping(value = "/exist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> deptExist(String name) {
        Map<String, Object> result = new HashMap<>();
        if(orgGroupService.deptNameIsRepeat(name)){
            result.put("valid",false);
        }else {
            result.put("valid",true);
        }
        return result;
    }

    /**
     * 功能描述：实现批量删除数据字典的记录
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> remove(OrgGroup entity) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        if (null == Long.valueOf(entity.getGroupId())) {
            result.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG, "非法参数！");
            return result;
        }
        try {
            Integer count = orgGroupService.findOrgGroupUserCount(entity.getGroupId());
            if (count != null && count > 0) {
                result.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
                result.put(SystemStaticConst.MSG, "当前部门下存在人员信息,无法删除！");
                return result;
            }
            boolean b = getService().delete(entity);
            if (b) {
                result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
                result.put(SystemStaticConst.MSG, "删除数据成功！");
            } else {
                result.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
                result.put(SystemStaticConst.MSG, "删除数据失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG, "删除数据失败！");
        }
        return result;
    }


    private String getMaxNode(String node, String parentNode) {
        String max_node = "";
        if (node == null) {
            max_node = parentNode + "001";
        } else {
            String n = (Integer.parseInt(node.substring(node.length() - 3)) + 1) + "";
            switch (n.length()) {
                case 1:
                    max_node = parentNode + "00" + n;
                    break;
                case 2:
                    max_node = parentNode + "0" + n;
                    break;
                case 3:
                    max_node = parentNode + "" + n;
                    break;
            }
        }
        return max_node;
    }


}
