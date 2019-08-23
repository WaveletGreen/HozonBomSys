package cn.net.connor.hozon.dao.pojo.bom.sparePart;

import lombok.Data;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/22 17:48
 * @Modified By:
 */
@Data
public class SparePartStructureRecursion {
    private SparePartData part;
    private SparePartBomStructure structure;
}
