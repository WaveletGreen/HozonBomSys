/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.config.init;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 *
 * @auther
 * @create 2017/8/24 0024
 */
@Component
@Order(value = 1)
public class InitDictCache implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitDictCache.class);

    @Override
    public void run(String... strings) {
        logger.warn("------init container success------");
    }
}
