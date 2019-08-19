/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.request.change;

import lombok.Data;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 忘了
 * @Date: Created in 2018/10/16 15:58
 * @Modified By:
 */
@Data
public class HzVwoProcessRequestDTO {
    /**
     * VWO类型
     */
    private Integer vwoType;
    /**
     * 项目UID
     */
    private String projectUid;
    /**
     * VWO主键
     */
    private Long vwoId;

    /**
     * 表单ID，该ID目前在MySql中，需要从DIV里面获取ID
     */
    private Long formId;
}
