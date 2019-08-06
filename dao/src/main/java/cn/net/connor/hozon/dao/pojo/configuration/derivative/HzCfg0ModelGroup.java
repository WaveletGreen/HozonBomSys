/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.pojo.configuration.derivative;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 模型组，同步到SAP用的模型组，在BOM系统中没有与之对应的操作入口
 * @Date: Created in 2018/8/10 13:55
 * @Modified By:
 */
@Data
@ToString
public class HzCfg0ModelGroup {
    private String id;

    private String tcUid;

    private String groupDesc;

    private String groupName;

    private String mainUid;
}