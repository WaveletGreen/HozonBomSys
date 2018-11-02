package com.connor.hozon.bom.sys.controller;


import com.alibaba.fastjson.JSON;
import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.common.base.controller.GenericController;
import com.connor.hozon.bom.common.base.service.GenericService;
import com.connor.hozon.bom.common.util.json.JsonHelper;
import com.connor.hozon.bom.common.util.user.UserInfo;

import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.QueryUser;
import com.connor.hozon.bom.sys.entity.Tree;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.entity.UserRole;
import com.connor.hozon.bom.sys.service.TreeService;
import com.connor.hozon.bom.sys.service.UserAssociateRoleService;
import com.connor.hozon.bom.sys.service.UserRoleService;
import com.connor.hozon.bom.sys.service.UserService;
import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/*
* 类描述：用户维护controller
* @auther linzf
* @create 2017/9/7 0007
*/
@Controller
@RequestMapping("/user")
public class UserController extends GenericController<User,QueryUser> {

    @Inject
    private UserService userService;
    @Inject
    private TreeService treeService;
    @Inject
    private UserRoleService userRoleService;
    @SuppressWarnings("unused")
	@Inject
    private UserAssociateRoleService userAssociateRoleService;

    @Override
    protected GenericService<User, QueryUser> getService() {
        return userService;
    }

    /**
     * 功能描述：加载首页菜单节点的数据
     * @return
     */
    @RequestMapping(value="/mainTree",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> mainTree(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<Tree> trees = UserInfo.loadUserTree(treeService);
        result.put("data",trees);
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        return result;
    }

    /**
     * 功能描述：更新用户状态为禁用/启用
     * @param entity
     * @return
     */
    @RequestMapping(value="/userControl",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> userControl(User entity){
        Map<String,Object> result = new HashMap<String, Object>();
        User user = UserInfo.getUser();
        User loginUser = userService.findByLogin(user.getLogin());
        if(StringUtil.isEmpty(entity.getState())){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"非法参数！");
            return result;
        }
        User u = null;
        if(entity.getState().equals("1")){//启用账户
            u= userService.findByUserId(Long.valueOf(entity.getId()),"0");//系统中存在用户

        }else {//禁用账户
            u= userService.findByUserId(Long.valueOf(entity.getId()),"1");//系统中存在用户
        }
        if(null == u){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"用户不存在！");
            return result;
        }
        if("dcproxy".equals(u.getLogin())){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"dcproxy账号无法禁用！");
            return result;
        }
        if(!PrivilegeUtil.hasAdministratorPrivilege(loginUser)){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"只有系统管理员权限账户才能禁用/启用员工账号！");
            return result;
        }
        if(userService.userControl(entity)){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
            result.put(SystemStaticConst.MSG,"更新用户状态成功！");
//            result.put("entity",entity);
        }else{
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"更新用户状态失败！");
        }
        return result;
    }

    /**
     * 功能描述：跳转到更新用户的页面
     * @param entity
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public String updatePage(User entity, Model model) throws Exception {
        entity = getService().get(entity);
        entity.setRoleArray(JsonHelper.list2json( Optional.ofNullable(userService.findByLogin(entity.getLogin())).filter(u->u!=null).orElse(new User()).getRoles()));
        model.addAttribute("entity",entity);
        String path=getPageBaseRoot()+UPDATEPAGE;
        return path;
    }

    /**
     * 功能描述：加载所有的权限数据
     * @return
     */
    @RequestMapping(value = "/loadRoles",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> loadRoles(){
        Map<String,Object> result = new HashMap<String, Object>();
        List<UserRole> userRoleList = userRoleService.query(null);
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put("list",userRoleList);
        return result;
    }

    /**
     * 功能描述：跳转到选择组织架构页面
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/pickGroup")
    public String pickGroup() throws Exception{
        return getPageBaseRoot() + "/group";
    }



    /**
     * @Author haozt on 2018/10/26
     * 功能描述:重写父类的update方法  这里进行权限判断
     * @param entity
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> update(User entity)  throws Exception{
        User user = userService.findByLogin(entity.getLogin());//编辑的用户
        if(null != user){
            List<UserRole> userRoles = user.getRoles();//系统已存在的用户角色
            User loginUser = userService.findByLogin(UserInfo.getUser().getLogin());//当前登录用户
            if(!loginUser.getLogin().equals(user.getLogin())){
                if(!PrivilegeUtil.hasAdministratorPrivilege(loginUser)){
                    Map<String, Object> map = new HashMap<>();
                    map.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
                    map.put(SystemStaticConst.MSG, "不具备系统管理员权限的用户不能编辑其他员工信息！");
                    return map;
                }else {//A系统管理员 修改  B系统管理员权限
                    if(!SystemStaticConst.DCPROXY.equals(loginUser.getLogin())){
                        for(UserRole role:userRoles){
                            if(PrivilegeUtil.ROLE_ADMIN.equals(role.getName())){
                                String id = String.valueOf(role.getId());
                                if(null !=entity.getRoleArray()){
                                    List<String> list = Lists.newArrayList(entity.getRoleArray().split(","));
                                    if(!list.contains(id)){
                                        Map<String, Object> map = new HashMap<>();
                                        map.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
                                        map.put(SystemStaticConst.MSG, "只有dcproxy账户才能移除其他用户的系统管理员权限！");
                                        return map;
                                    }
                                }else {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
                                    map.put(SystemStaticConst.MSG, "只有dcproxy账户才能移除其他用户的系统管理员权限！");
                                    return map;
                                }
                            }
                        }
                    }
                }
            }

            if(SystemStaticConst.DCPROXY.equals(entity.getLogin())){
                for(UserRole role :userRoles){
                    if(PrivilegeUtil.ROLE_ADMIN.equals(role.getName())){
                        if(StringUtil.isEmpty(entity.getRoleArray()) ||!entity.getRoleArray().contains(String.valueOf(role.getId()))){
                            Map<String, Object> map = new HashMap<>();
                            map.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
                            map.put(SystemStaticConst.MSG, "dcproxy的系统管理员权限不能删除！");
                            return map;
                        }
                    }
                }
            }
            String ids = entity.getRoleArray();
            boolean write = true;
            if(!PrivilegeUtil.administrator()) {
                if (StringUtil.isEmpty(ids) && ListUtil.isEmpty(userRoles)) {
                    write = true;
                } else if (StringUtil.isEmpty(ids) && ListUtil.isNotEmpty(userRoles)) {
                    write = false;
                }else if(!StringUtil.isEmpty(ids)&&ListUtil.isEmpty(userRoles)){
                    write = false;
                }
                else if (ListUtil.isNotEmpty(userRoles) && !StringUtil.isEmpty(ids)) {
                    List<String> list = Lists.newArrayList(ids.split(","));
                    if(list.size() != userRoles.size()){
                        write = false;
                    }else {
                        for(String s :list){
                            boolean find = false;
                            for(UserRole role :userRoles){
                                if(s.equals(String.valueOf(role.getId()))){
                                    find = true;
                                    break;
                                }
                            }
                            if(!find){
                                write = false;
                                break;
                            }
                        }
                    }

                }
                if (!write) {
                    Map<String, Object> map = new HashMap<>();
                    map.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
                    map.put(SystemStaticConst.MSG, "只有系统管理员权限账户才能修改员工所属权限信息！");
                    return map;
                }
            }
        }
        boolean success  = userService.update(entity);
        Map<String,Object> result = new HashMap<>();
        if(success==true){
                result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
            result.put(SystemStaticConst.MSG,"更新数据成功！");
        }else{
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"更新数据失败！");
        }
        return result;
    }


    /**
     * 功能描述：保存数据字典数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> save(User entity) throws Exception{
        //新增用户时，初始密码全部为123456
        User user = UserInfo.getUser();
        User u = userService.findByLogin(user.getLogin());
        if(!PrivilegeUtil.hasAdministratorPrivilege(u)){
            Map<String,Object> map = new HashMap<>();
            map.put(SystemStaticConst.RESULT, SystemStaticConst.FAIL);
            map.put(SystemStaticConst.MSG,"只有系统管理员权限账户才能增加员工信息！");
        }
        boolean success = getService().save(entity);
        Map<String,Object> result = new HashMap<>();
        if(success==true){
            result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
            result.put(SystemStaticConst.MSG,"增加数据成功！");
        }else{
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"增加数据失败！");
        }
        return result;
    }


    /**
     * 功能描述：删除用户
     * @param json
     * @return
     */
    @RequestMapping(value = "/removeBath",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> removeBath(String json) throws Exception{
        Map<String,Object> result = new HashMap<String, Object>();
        com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONObject.parseArray(json);
        String login = "";
        for(int i=0;i<array.size();i++){
            com.alibaba.fastjson.JSONObject object = array.getJSONObject(i);
            if(object.containsKey("login")){
                login = object.getString("login");
                break;
            }
        }

        if(SystemStaticConst.DCPROXY.equals(login)){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"无法删除"+SystemStaticConst.DCPROXY+"账户！");
            return result;
        }
        userService.removeBath((List<User>) JsonHelper.toList(json,(Class <User>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG,"删除数据成功！");
        return result;
    }
}
