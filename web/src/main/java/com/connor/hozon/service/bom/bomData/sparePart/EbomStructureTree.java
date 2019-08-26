package com.connor.hozon.service.bom.bomData.sparePart;

import cn.net.connor.hozon.dao.pojo.bom.epl.EbomWithPbomData;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/23 16:05
 * @Modified By:
 */
@Data
public class EbomStructureTree {
    /**
     * 当前节点的bomline
     */
    private @NotNull EbomWithPbomData bomline;
    /**
     * 子节点树形结构
     */
    private List<EbomStructureTree> children = new ArrayList<>();

    /**
     * 不允许使用setter设置子节点集合
     *
     * @param children
     */
    private void setChildren(List<EbomStructureTree> children) {
    }

    @Override
    public String toString() {
        return bomline.getLineID();
    }
}
