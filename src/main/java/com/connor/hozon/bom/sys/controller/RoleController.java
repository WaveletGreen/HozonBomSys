package com.connor.hozon.bom.sys.controller;


import com.connor.hozon.bom.common.base.constant.SystemStaticConst;
import com.connor.hozon.bom.common.base.controller.GenericController;
import com.connor.hozon.bom.common.base.service.GenericService;
import com.connor.hozon.bom.common.util.json.JsonHelper;
import com.connor.hozon.bom.common.util.user.UserInfo;
import com.connor.hozon.bom.resources.util.ListUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import com.connor.hozon.bom.resources.util.StringUtil;
import com.connor.hozon.bom.sys.entity.QueryUserRole;
import com.connor.hozon.bom.sys.entity.Tree;
import com.connor.hozon.bom.sys.entity.User;
import com.connor.hozon.bom.sys.entity.UserRole;
import com.connor.hozon.bom.sys.mapper.TreeMapper;
import com.connor.hozon.bom.sys.service.TreeService;
import com.connor.hozon.bom.sys.service.UserRoleService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 类描述：
* @auther linzf
* @create 2017/10/10 0010 
*/
@Controller
@RequestMapping("/role")
public class RoleController extends GenericController<UserRole,QueryUserRole> {

    @Inject
    private UserRoleService userRoleService;

    @Inject
    private TreeService treeService;

    @Inject
    private TreeMapper treeMapper;

    @Override
    protected GenericService<UserRole, QueryUserRole> getService() {
        return userRoleService;
    }

    /**
     * 功能描述：根据用户的权限去加载角色数据
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/loadRoleTree",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> loadRoleTree(UserRole entity){
        entity = userRoleService.getUserRoleAssociate(entity);
        List<Tree> treeList = treeService.query(null);
        if(entity!=null){
            for(Tree tree:entity.getTreeList()){
                treeList.stream().forEach(t->{
                    if(t.getId()==tree.getId()){
                        t.setChecked(true);
                    }
                });
            }
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put("data",treeMapper.treesToTressDTOs(treeList));
        return result;
    }




    /**
     * 功能描述：根据用户的权限去加载角色是否写权限树
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/loadRoleWriteTree",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> loadRoleWriteTree(UserRole entity){
        entity = userRoleService.getUserWriteRoleAssociate(entity);
        List<Tree> treeList = treeService.query(null);
        if(entity!=null){
            for(Tree tree:entity.getTreeList()){
                treeList.stream().forEach(t->{
                    if(t.getId() == tree.getId()){
                        t.setChecked(true);
                    }
                });
            }
        }
        Map<String,Object> result = new HashMap<>();
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put("data",treeMapper.treesToTressDTOs(treeList));
        return result;
    }

    /**
     * 功能描述：实现批量删除数据字典的记录
     * @param json
     * @return
     */
    @RequestMapping(value = "/removeBath",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> removeBath(String json) throws Exception{
        Map<String,Object> result = new HashMap<String, Object>();
        com.alibaba.fastjson.JSONArray array = com.alibaba.fastjson.JSONObject.parseArray(json);
        String id = "";
        for(int i=0;i<array.size();i++){
            com.alibaba.fastjson.JSONObject object = array.getJSONObject(i);
            if(object.containsKey("id")){
                id = object.getString("id");
                break;
            }
        }
        if(StringUtil.isEmpty(id)){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"非法参数！");
            return result;
        }

        Integer count = userRoleService.getUserRoleReferenceCount(Long.valueOf(id));
        if(count!=null){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"当前要删除的权限信息正在被用户所引用,无法删除！");
            return result;
        }
        getService().removeBath((List<UserRole>) JsonHelper.toList(json,(Class <UserRole>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]));
        result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG,"删除数据成功！");
        return result;
    }



    /**
     * 功能描述：跳转到开通用户写权限页面
     * @param entity
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/openEdit",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public String openEdit(UserRole entity, Model model) throws Exception {
        entity = getService().get(entity);
        model.addAttribute("entity",entity);
        String path=getPageBaseRoot()+"/roleEdit";
        return path;
    }


    /**
     * 功能描述：更新数据字典数据
     * @param entity
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> update(@RequestBody UserRole entity)  throws Exception{
        boolean success  = userRoleService.update(entity);
        Map<String,Object> result = new HashMap<>();
        if(success){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
            result.put(SystemStaticConst.MSG,"操作成功！");
        }else{
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"操作失败，请稍后重试！");
        }
        return result;
    }

    /**
     * 功能描述:保存用户权限信息
     * @param entity
     * @return
     */
    @RequestMapping(value = "/savePrivilege",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> saveWritePrivilege(@RequestBody UserRole entity){
        User user = UserInfo.getUser();
        Map<String,Object> result = new HashMap<>();
        if(!PrivilegeUtil.hasAdministratorPrivilege(user)){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"不具备系统管理员权限的用户不能进行此操作！");
        }
        boolean success  = userRoleService.saveRoleWritePrivilege(entity);
        if(success){
            result.put(SystemStaticConst.RESULT,SystemStaticConst.SUCCESS);
            result.put(SystemStaticConst.MSG,"操作成功！");
        }else{
            result.put(SystemStaticConst.RESULT,SystemStaticConst.FAIL);
            result.put(SystemStaticConst.MSG,"操作失败,请稍后重试！");
        }
        return result;
    }
}
