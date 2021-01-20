package com.bootx.job;

import com.bootx.service.BonusService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class BonusJob {

    @Resource
    private BonusService bonusService;

    @Scheduled(cron = "0 0/1 * * * ? ")
    public void run (){
        System.out.println(new Date()+"=====================");
        bonusService.bonus();
        System.out.println("====end");
    }

}
