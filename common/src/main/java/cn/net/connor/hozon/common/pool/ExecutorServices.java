/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: haozt
 * @Date: 2018/11/14
 * @Description: 线程池
 */
public  class ExecutorServices{
    /**
     * 并行数量
     */
    private int parallelNumber;

    /**
     * 线程池
     */
    private ExecutorService pool ;


    public ExecutorServices(){

    }

    public ExecutorServices(int parallelNumber){
        setParallelNumber(parallelNumber);
        this.parallelNumber = parallelNumber;
        this.pool = Executors.newFixedThreadPool(parallelNumber);
    }

    public ExecutorService getPool() {
        return pool;
    }

    public int getParallelNumber() {
        return parallelNumber;
    }

    public void setParallelNumber(int parallelNumber) {
        this.parallelNumber = parallelNumber;
    }


    public  void execute(){
        //execute 无返回值 无法判断线程的执行情况
        pool.execute(()-> {
            action();
        });
        if(pool != null){
            pool.shutdown();
        }
    }

    public  void action(){

    }
}
