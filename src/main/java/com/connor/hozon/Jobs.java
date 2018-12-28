package com.connor.hozon;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Jobs {
    public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Scheduled(cron="0 55 13 * * ?")
    public void cronJob(){
        System.out.println(sdf.format(new Date())+" >>cron执行....");
    }
}
