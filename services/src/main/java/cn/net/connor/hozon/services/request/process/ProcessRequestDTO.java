/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.request.process;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description:  前端回传的审核表单和审核人员数据
 * @Date: Created in  2018/11/22 11:14
 * @Modified By:
 */
@Data
public class ProcessRequestDTO implements Serializable {
    private static final long serialVersionUID = -8059932114340896509L;
    /***
     * 审核人ID
     */
    private Long userId;
    /**
     * 变更表单ID
     */
    private Long orderId;
    /**
     * 当前表单的Div ID
     */
    private Integer activeDivId;
}
