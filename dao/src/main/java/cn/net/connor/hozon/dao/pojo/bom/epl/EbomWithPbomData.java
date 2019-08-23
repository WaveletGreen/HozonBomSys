package cn.net.connor.hozon.dao.pojo.bom.epl;

import lombok.Data;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/23 11:50
 * @Modified By:
 */
@Data
public class EbomWithPbomData extends HzEPLManageRecord{
    /**
     * 车间1
     */
    private String workshop1;
    /**
     * 车间2
     */
    private String workshop2;
}
