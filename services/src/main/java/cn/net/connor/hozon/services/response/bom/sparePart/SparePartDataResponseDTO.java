/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.response.bom.sparePart;

import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * 备件零件响应对象
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/15 14:04
 * @Modified By:
 */
@Data
@ToString
public class SparePartDataResponseDTO extends SparePartData implements Serializable {
    private static final long serialVersionUID = 4235560016359300566L;
    private Map<String,String> vehicleUsage;
}