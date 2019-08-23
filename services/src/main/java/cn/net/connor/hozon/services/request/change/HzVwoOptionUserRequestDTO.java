/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services.request.change;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: 忘了
 * @Date: Created in 2018/8/27 21:17
 * @Modified By:
 */
@Data
public class HzVwoOptionUserRequestDTO implements Serializable {
    private static final long serialVersionUID = -2596745147912179037L;
    private Long selectedUserId;

    private String opiBomName;

    private String opiPmtName;

    private String opiProjName;

    private Long vwoId;

    private Integer selectId;

}
