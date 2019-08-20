package com.connor.hozon.service.bom.bomData.sparePart;

import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartBomStructure;
import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/20 15:24
 * @Modified By:
 */
@Data
@AllArgsConstructor
public class SparePartStructureCache {
    /**
     * 子层结构
     */
    private List<SparePartBomStructure> structures;
    /**
     * 子层零件
     */
    private List<SparePartData> parts;
    /**
     * 父层自身
     */
    private SparePartData self;
}
