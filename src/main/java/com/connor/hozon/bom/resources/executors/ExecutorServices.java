package com.connor.hozon.bom.resources.executors;

import java.util.List;
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
    private ExecutorService pool;


    public ExecutorServices(){

    }

    public ExecutorServices(int parallelNumber){
        setParallelNumber(parallelNumber);
        this.parallelNumber = parallelNumber;
        this.pool = Executors.newFixedThreadPool(parallelNumber);
    }


    public int getParallelNumber() {
        return parallelNumber;
    }

    public void setParallelNumber(int parallelNumber) {
        this.parallelNumber = parallelNumber;
    }

    public  void execute(){
        //execute 无返回值 无法判断线程的执行情况 submit方法 有返回值 可以判断每个线程执行结果
        pool.execute(new Runnable() {
            @Override
            public void run() {
                action();
            }
        });

        if(pool != null){
            pool.shutdown();
        }
    }

    public  void action(){

    }
}
