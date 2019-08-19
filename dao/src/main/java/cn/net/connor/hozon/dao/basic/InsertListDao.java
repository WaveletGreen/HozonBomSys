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
    int insertList(List<T> list);
}
