
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.MineMachineOrderDao;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.entity.MineMachineOrder;
import com.bootx.entity.Sn;
import com.bootx.service.MineMachineOrderService;
import com.bootx.service.SnService;
import com.bootx.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class MineMachineOrderServiceImpl extends BaseServiceImpl<MineMachineOrder, Long> implements MineMachineOrderService {

    @Autowired
    private SnService snService;
    @Autowired
    private MineMachineOrderDao mineMachineOrderDao;

    @Override
    public MineMachineOrder create(Member member, MineMachine mineMachine, Integer quantity, Integer day, Integer excision,Integer orderType,String memo) {
        MineMachineOrder mineMachineOrder = new MineMachineOrder();
        mineMachineOrder.init();
        mineMachineOrder.setUserId(member.getId());
        mineMachineOrder.setProductType(mineMachine.getType());
        mineMachineOrder.setProductId(mineMachine.getId());
        mineMachineOrder.setPrice(mineMachine.getPrice());
        mineMachineOrder.setAmount(mineMachine.getPrice().multiply(new BigDecimal(quantity)));
        mineMachineOrder.setQuantity(quantity);
        mineMachineOrder.setMemo(memo);
        mineMachineOrder.setState(0);
        mineMachineOrder.setPayType(3);
        mineMachineOrder.setElectricType(1);
        mineMachineOrder. setDay(day);
        mineMachineOrder.setAddElectric(BigDecimal.ZERO);
        mineMachineOrder.setElectricMoney(BigDecimal.valueOf(43.99));
        mineMachineOrder.setRmbPrice(setScale(mineMachine.getRmbPrice().multiply(new BigDecimal(quantity))));
        mineMachineOrder.setPayPrice(mineMachine.getRmbPrice()+" 元");
        mineMachineOrder.setCoinType(mineMachine.getCoinType());
        mineMachineOrder.setFromChannel("APP");
        mineMachineOrder.setExpirationDate(DateUtils.getNextDay(mineMachineOrder.getDay()));
        mineMachineOrder.setInvestTime("2021-01-01 00:00:00");
        mineMachineOrder.setSn(snService.generate(Sn.Type.ORDER));
        mineMachineOrder.setUserName(member.getUsername());
        mineMachineOrder.setPhone(member.getPhone());
        mineMachineOrder.setName(member.getName());
        mineMachineOrder.setProductName(mineMachine.getName());
        mineMachineOrder.setProductId(mineMachine.getId());
        mineMachineOrder.setProductIcon(mineMachine.getIcon());
        mineMachineOrder.setExcision(excision);
        mineMachineOrder.setOrderType(orderType);
        mineMachineOrder.setProfit(mineMachine.getProfit().multiply(new BigDecimal(quantity)));
        mineMachineOrder.setHourProfit(mineMachineOrder.getProfit().divide(new BigDecimal(24),12, RoundingMode.HALF_DOWN));
        return super.save(mineMachineOrder);
    }

    @Override
    public Page<MineMachineOrder> findPage(Pageable pageable, Member member, Integer excision, String orderType, String coinType) {
        return mineMachineOrderDao.findPage(pageable, member, excision, orderType, coinType);
    }

    @Override
    public Long count(Member member) {
        if(member==null){
            return 0L;
        }
        return jdbcTemplate.queryForObject("select count(id) from minemachineorder where userId= "+member.getId()+" and ( state=2 or state=3)", Long.class);
    }

    @Override
    public Long countTeam(Member member) {
        if(member==null){
            return 0L;
        }
        return jdbcTemplate.queryForObject("select count(id) from minemachineorder where userId in (select id from member where parent_id="+member.getId()+") and (state=2 or state=3)", Long.class);
    }
}