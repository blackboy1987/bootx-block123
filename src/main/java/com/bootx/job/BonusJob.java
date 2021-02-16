package com.bootx.job;

import com.bootx.eth.service.EthAdminService;
import com.bootx.service.BonusService;
import com.bootx.service.InvestService;
import com.bootx.service.MemberService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Component
public class BonusJob {

    @Resource
    private BonusService bonusService;

    @Resource
    private EthAdminService ethAdminService;
    @Resource
    private MemberService memberService;
    @Resource
    private InvestService investService;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0/5 * * * ? ")
    public void run (){
        System.out.println(new Date()+"=====================");
        bonusService.bonus();
        System.out.println("===------------------------------------------------------------------=================================================end");
    }

    @Scheduled(cron = "0/2 * * * * ? ")
    public void run1 (){
        Map<String, Object> map = investService.findOne(0);
        if(map!=null&&map.get("id")!=null&&map.get("userId")!=null&&map.get("invest")!=null){
            BigDecimal invest = new BigDecimal(map.get("invest")+"");
            if(invest.compareTo(BigDecimal.ZERO)>0){
                String transactionHash = ethAdminService.transferEther(memberService.find(1L), memberService.find(Long.valueOf(map.get("userId") + "")), invest.divide(new BigDecimal("100")));
                System.out.println("result:"+transactionHash);
                if(StringUtils.isNotBlank(transactionHash)){
                    System.out.println("chengg");
                    jdbcTemplate.update("update invest set status=1,version=version+1,lastModifiedDate=now(),transactionHash='"+transactionHash+"' where type1=0 and id="+map.get("id"));
                }else{
                    System.out.println("shibai");
                    jdbcTemplate.update("update invest set status=0,version=version+1,lastModifiedDate=now() where type1=0 and id="+map.get("id"));
                }
            }
        }
    }


}
