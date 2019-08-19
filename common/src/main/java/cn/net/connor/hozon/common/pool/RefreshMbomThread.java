/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.pool;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: haozt
 * @Date: 2018/9/21
 * @Description:
 */
public abstract class RefreshMbomThread implements Runnable{

    private CountDownLatch latch;

    public RefreshMbomThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            refreshMbom();
        }catch (Exception e){

        }finally {
            if(latch !=null){
                latch.countDown();
            }
        }
    }

    public abstract void refreshMbom();
}
