package com.connor.hozon.bom.resources.service;

import java.util.concurrent.CountDownLatch;

/**
 * @Author: haozt
 * @Date: 2018/9/21
 * @Description:
 */
public abstract class AddMbomThread implements Runnable{

    private CountDownLatch latch;

    public AddMbomThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
           insertMbom();
        }catch (Exception e){

        }finally {
            if(latch !=null){
                latch.countDown();
            }
        }
    }

    public abstract void insertMbom();
}
