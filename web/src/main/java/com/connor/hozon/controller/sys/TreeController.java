/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package com.connor.hozon.controller.sys;


import cn.net.connor.hozon.common.constant.SystemStaticConst;
import cn.net.connor.hozon.services.service.sys.GenericService;
import cn.net.connor.hozon.dao.query.sys.QueryTree;
import cn.net.connor.hozon.dao.pojo.sys.Tree;
import cn.net.connor.hozon.services.beanMapper.sys.TreeMapper;
import cn.net.connor.hozon.services.service.sys.TreeService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 类描述：菜单操作controller
 * @auther linzf
 * @create 2017/10/10 0010
 */
@Controller
@RequestMapping("/tree")
public class TreeController extends GenericController<Tree, QueryTree> {

    @Inject
    private TreeService treeService;
    @Inject
    private TreeMapper treeMapper;

    @Override
    protected GenericService<Tree, QueryTree> getService() {
        return treeService;
    }

    /**
     * 功能描述：跳转到修改菜单节点的页面
     *
     * @param entity
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateTreePage")
    public String updateTreePage(Tree entity, Model model) throws Exception {
        entity = treeService.get(entity);
        Tree pTree = null;
        if (entity.getpId() == 0l) {
            pTree = new Tree();
            pTree.setId(0l);
            pTree.setName("顶层节点");
        } else {
            pTree = treeService.get(new Tree(entity.getpId()));
        }
        entity.setTree(pTree);
        model.addAttribute("entity", entity);
        return getPageBaseRoot() + UPDATEPAGE;
    }

    /**
     * 功能描述：跳转到增加菜单节点的页面
     *
     * @param entity
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addTreePage")
    public String addPage(Tree entity, Model model) throws Exception {
        if (entity.getId() == 0) {
            entity = new Tree();
            entity.setId(0l);
            entity.setName("顶层节点");
        } else {
            entity = treeService.get(entity);
        }
        model.addAttribute("entity", entity);
        String page = getPageBaseRoot() + ADDPAGE;
        return page;
    }

    /**
     * 功能描述：直接加载整个菜单树的数据
     * <p>
     * 必须要有管理员权限才可以加载该菜单树的数据，如果没有管理员权限会返回403错误到前端
     *
     * @return
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/loadUserTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> loadUserTree() {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Tree> treeList = treeService.query(null);
        result.put(SystemStaticConst.RESULT, SystemStaticConst.SUCCESS);
        result.put(SystemStaticConst.MSG, "加载菜单数据成功！");
        result.put("data", treeMapper.treesToTressDTOs(treeList));
        return result;
    }


}
