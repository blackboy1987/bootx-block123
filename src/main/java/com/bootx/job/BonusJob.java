package com.bootx.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BonusJob {


    @Scheduled(cron = "0 55 10 * * ? ")
    public void run (){
        System.out.println(new Date()+"=====================");
    }

}
