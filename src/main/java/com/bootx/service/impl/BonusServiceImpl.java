package com.bootx.service.impl;

import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.service.BonusService;
import com.bootx.service.InvestService;
import com.bootx.service.MemberService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BonusServiceImpl implements BonusService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private MemberService memberService;
    @Resource
    private InvestService investService;

    @Override
    public void bonus() {
        /**
         * 所有有效的订单
         */
        String sql = "select id, coinType,profit,hourProfit,userId,productId,productName  from minemachineorder where (expireDate is null or expireDate> now())";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        for (Map<String,Object> map:list) {
            BigDecimal profit = new BigDecimal(map.get("profit")+"");
            if(map.get("hourProfit")==null){
                map.put("hourProfit", 0.12);
            }
            BigDecimal hourProfit = new BigDecimal(map.get("hourProfit")+"");
            Long userId = Long.valueOf(map.get("userId")+"");
            Member member = memberService.find(userId);
            if(member==null||!member.getIsEnabled()){
                continue;
            }
            Long id = Long.valueOf(map.get("id")+"");
            Long productId = Long.valueOf(map.get("productId")+"");
            String productName = map.get("productName")+"";
            Integer coinType = Integer.valueOf(map.get("coinType")+"");

            Invest invest = new Invest();
            invest.setInvest(hourProfit);
            invest.setInvestTime(new Date());
            invest.setCoinType(coinType);
            invest.setExcision(0);
            invest.setAllBtc(BigDecimal.ZERO);
            invest.setAllBtcPrice(BigDecimal.ZERO);
            invest.setAllEth(BigDecimal.ZERO);
            invest.setAllEthPrice(BigDecimal.ZERO);
            invest.setAllHpt(BigDecimal.ZERO);
            invest.setAllHptPrice(BigDecimal.ZERO);
            invest.setBtcDiscount(BigDecimal.ZERO);
            invest.setComeDate(new Date());
            invest.setExcision(0);
            invest.setElectric(BigDecimal.ZERO);
            invest.setExpirationDate(new Date());
            invest.setExpireDate(new Date());
            invest.setFrozenInvest(BigDecimal.ZERO);
            invest.setFrozenInvestTemp(BigDecimal.ZERO);
            invest.setFrozenTime(new Date());
            invest.setHbtDiscount(BigDecimal.ZERO);
            invest.setIsExpire(false);
            invest.setLastBtc(BigDecimal.ZERO);
            invest.setLastBtcPrice(BigDecimal.ZERO);
            invest.setLastEth(BigDecimal.ZERO);
            invest.setLastEthPrice(BigDecimal.ZERO);
            invest.setLastHpt(BigDecimal.ZERO);
            invest.setLastHptPrice(BigDecimal.ZERO);
            invest.setLastTime(null);
            invest.setManage(BigDecimal.ZERO);
            invest.setManageDiscount(BigDecimal.ZERO);
            invest.setOrderId(id);
            invest.setProductId(productId);
            invest.setProductName(productName);
            invest.setProfit(hourProfit);
            invest.setProfitYear(0L);
            invest.setReturnMoney(BigDecimal.ZERO);
            invest.setReturnDays(0);
            invest.setType(0);
            invest.setUserId(userId);
            invest.setUserId(member.getId());
            invest.setPhone(member.getPhone());
            invest.setUserName(member.getUsername());
            invest.setValidity(0L);
            investService.save(invest);
        }

    }
}
