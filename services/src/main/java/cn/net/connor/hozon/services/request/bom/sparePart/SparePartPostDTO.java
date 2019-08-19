/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.request.bom.sparePart;

import cn.net.connor.hozon.dao.pojo.bom.sparePart.SparePartData;
import lombok.Data;

/**
 * 备件post提交的对象，增加一个项目ID，其余和备件SparePartData对象一致
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/16 14:56
 * @Modified By:
 */

@Data
public class SparePartPostDTO extends SparePartData {
    /**
     * 项目ID，无论是新增单个备件BOM数据还是子件备件BOM数据，都会出现这个字段的数据
     */
    private String projectId;

    /**
     * 父件ID，只有添加到子件才会出现这个字段的数据
     */
    private Long parentId;
}
