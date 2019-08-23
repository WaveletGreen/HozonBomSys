/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.basic;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/16 16:22
 * @Modified By:
 */
public interface InsertListDao<T> {
    /**
     * 批量插入数据，注意，批量插入不会返回主键
     * @param list
     * @return
     */
    int insertList(List<T> list);
}
