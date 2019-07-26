/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.services;

import cn.net.connor.hozon.dao.dao.TestDao;
import cn.net.connor.hozon.dao.pojo.Test;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 示例service层
 *
 * @Author: Fancyears Milos Malvis
 * @Date: Created in  2019/7/25 14:45
 * @Modified By:
 */
@Service
//有一些特殊的异常无法回滚，需要从xml里面配置或者设置Transaction的rollBackFor
//@Transactional(rollbackFor = SQLIntegrityConstraintViolationException.class)
public class TestService {
    @Autowired
    TestDao testDao;

    /**
     * 这个方法会自动回滚，应为advice已经有对应的insert* AOP设置
     * @param type
     * @param id
     * @param name
     * @return
     */
    public JSONObject insert(@RequestParam int type, @RequestParam long id, @RequestParam String name) {
        Test test = new Test();
        test.setId(id);
        test.setName(name);

        switch (type) {
            case 1: {
                testDao.insert(test);
                break;
            }
            case 2: {
                testDao.updateByPrimaryKey(test);
                break;
            }
            case 3: {
                testDao.updateByPrimaryKey(test);
                throw new RuntimeException("手动跑出异常，请查看是否发生回滚");
            }
            case 4: {
                testDao.insert(test);
                throw new RuntimeException("手动跑出异常，请查看是否发生回滚");
            }
            case 5: {
                testDao.insert(test);
                testDao.insert(test);
                throw new RuntimeException("手动跑出异常，请查看是否发生回滚");
            }
        }
        return null;
    }

    /**
     * 下面的方法不会在异常发生之后进行回滚，因为没有相关的advice，需要自定义 @Transactional(rollbackFor)，dao层没有对应的advice，也不会发生回滚
     *
     * @param type
     * @param id
     * @param name
     * @return
     */
    //    @Transactional(rollbackFor = Exception.class)
    public JSONObject test(@RequestParam int type, @RequestParam long id, @RequestParam String name) {
        Test test = new Test();
        test.setId(id);
        test.setName(name);

        switch (type) {
            case 1: {
                testDao.insert(test);
                break;
            }
            case 2: {
                testDao.updateByPrimaryKey(test);
                break;
            }
            case 3: {
                testDao.updateByPrimaryKey(test);
                throw new RuntimeException("手动跑出异常，请查看是否发生回滚");
            }
            case 4: {
                testDao.insert(test);
                throw new RuntimeException("手动跑出异常，请查看是否发生回滚");
            }
            case 5: {
                testDao.insert(test);
                testDao.insert(test);
                throw new RuntimeException("手动跑出异常，请查看是否发生回滚");
            }
        }
        return null;
    }
}
