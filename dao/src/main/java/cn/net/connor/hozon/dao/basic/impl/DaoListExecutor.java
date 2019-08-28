/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.dao.basic.impl;

import cn.net.connor.hozon.dao.basic.DeleteListDao;
import cn.net.connor.hozon.dao.basic.InsertListDao;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/8/16 16:16
 * @Modified By:
 */
@Component
public class DaoListExecutor {

    public <T> int insertList(int total, int max, List<T> list, InsertListDao<T> dao) {
        int count = (int) Math.ceil(total * 1.0 / max);
        int index = -1;
        int currentIndex = -1;
        int updateCount = 0;
        for (int i = 0; i < count; i++) {
            index = i + 1;//下一个节点
            currentIndex = index * max;//终末位置
            currentIndex = currentIndex > total ? total : currentIndex;//如果大于最大长度，则取最大长度
            if ((updateCount += dao.insertList(list.subList(i * max, currentIndex))) <= 0) {
                throw new RuntimeException("批量插入数据失败");
            }
        }
        return updateCount;
    }

    public <T> int deleteList(int total, int max, List<T> list, DeleteListDao<T> dao) {
        int count = (int) Math.ceil(total * 1.0 / max);
        int index = -1;
        int currentIndex = -1;
        int updateCount = 0;
        for (int i = 0; i < count; i++) {
            index = i + 1;//下一个节点
            currentIndex = index * max;//终末位置
            currentIndex = currentIndex > total ? total : currentIndex;//如果大于最大长度，则取最大长度
            if ((updateCount += dao.deleteList(list.subList(i * max, currentIndex))) <= 0) {
                throw new RuntimeException("批量删除数据失败");
            }
        }
        return updateCount;
    }

}
