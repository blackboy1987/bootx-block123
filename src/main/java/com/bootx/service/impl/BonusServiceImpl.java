package com.bootx.service.impl;

import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.entity.MineMachineOrder;
import com.bootx.service.*;
import com.bootx.util.DateUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BonusServiceImpl implements BonusService {

    @Resource
    private MineMachineService mineMachineService;

    @Resource
    private MemberService memberService;
    @Resource
    private InvestService investService;
    @Resource
    private MineMachineOrderService mineMachineOrderService;

    @Override
    public void bonus() {
        List<MineMachineOrder> mineMachineOrders = mineMachineOrderService.findAll();
        for (MineMachineOrder mineMachineOrder:mineMachineOrders) {
            MineMachine mineMachine = mineMachineService.find(mineMachineOrder.getProductId());
            investService.create(memberService.find(mineMachineOrder.getUserId()), mineMachineOrder,"矿机收益");
            mineMachineOrder.setInvestTime(DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            if(mineMachineOrder.getInvest()==null){
                mineMachineOrder.setInvest(BigDecimal.ZERO);
            }
            mineMachineOrder.setHourProfit(mineMachineOrder.getProfit().divide(new BigDecimal(24),10, RoundingMode.HALF_DOWN));
            mineMachineOrder.setInvest(mineMachineOrder.getInvest().add(mineMachineOrder.getHourProfit()));

            // 该比订单可以返点
            if(mineMachineOrder.getIsReward()){
                investService.create(memberService.find(mineMachineOrder.getUserId()), mineMachineOrder,"返点收益");
                if(mineMachineOrder.getReturnMoney()==null){
                    mineMachineOrder.setReturnMoney(BigDecimal.ZERO);
                }
                if(mineMachine.getReturnRate()==null){
                    mineMachine.setReturnRate(new BigDecimal(0.05));
                }
                mineMachineOrder.setReturnMoney(mineMachineOrder.getReturnMoney().add(mineMachine.getReturnRate().multiply(mineMachineOrder.getHourProfit())));
            }
            mineMachineOrderService.update(mineMachineOrder);

        }
    }
}
