/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.query.change.breakPoint;

import cn.net.connor.hozon.common.entity.QueryBase;
import lombok.Data;

/**
 * 断点信息查询对象
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/12 16:27
 * @Modified By:
 */
@Data
public class BreakPointQuery extends QueryBase {
    /**
     * TCECN号，前端关键字也是属性名
     */
    private String P_TCECN;
    /**
     * SAP ECN号
     */
    private String P_ECN;
    /**
     * 断点执行工号
     */
    private String P_USER;
    /**
     * 断点处理时间
     */
    private String P_CTIME;

}
