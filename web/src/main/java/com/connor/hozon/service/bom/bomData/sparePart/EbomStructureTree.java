package com.connor.hozon.service.bom.bomData.sparePart;

import cn.net.connor.hozon.dao.pojo.bom.epl.EbomWithPbomData;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/23 16:05
 * @Modified By:
 */
@Data
public class EbomStructureTree {
    /**
     * 当前节点puid
     */
    private String puid;
    /**
     * 当前bomline
     */
    private EbomWithPbomData bomline;
    /**
     * 父节点
     */
    private TreeNode parent;
    /**
     * 节点
     */
    private List<TreeNode> children = new ArrayList<>();

    /**
     * 不允许使用setter设置子节点集合
     *
     * @param children
     */
    private void setChildren(List<TreeNode> children) {
    }
}
