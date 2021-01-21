
package com.bootx.service.impl;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.InvestDao;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.entity.MineMachineOrder;
import com.bootx.eth.service.EthAdminService;
import com.bootx.service.InvestService;
import com.bootx.service.MemberService;
import com.bootx.service.MineMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class InvestServiceImpl extends BaseServiceImpl<Invest, Long> implements InvestService {

    @Resource
    private InvestDao investDao;
    @Resource
    private MineMachineService mineMachineService;
    @Resource
    private EthAdminService ethAdminService;
    @Resource
    private MemberService memberService;

    @Override
    public List<Invest> findListByCoinType(Member member, Integer coinType) {
        return investDao.findListByCoinType(member,coinType);
    }

    @Override
    public BigDecimal sum(Member member, Integer coinType) {
        return jdbcTemplate.queryForObject("select sum(profit) from invest where userId="+member.getId()+" and coinType="+coinType,BigDecimal.class);
    }

    @Override
    public Page<Invest> findPage(Pageable pageable, Member member, Integer excision, Long productId, Integer type,Integer coinType, Date beginDate, Date endDate) {
        return investDao.findPage(pageable,member,excision,mineMachineService.find(productId),type,coinType,beginDate,endDate);
    }

    @Override
    public List<Invest> findList1(Member member, Integer excision, Long productId, Integer type, Integer coinType, Date beginDate, Date endDate) {
        return investDao.findList1(member,excision,mineMachineService.find(productId),type,coinType,beginDate,endDate);
    }

    @Override
    public void create(Member member, MineMachineOrder mineMachineOrder,String memo) {
        Invest invest = new Invest();
        invest.setInvest(mineMachineOrder.getHourProfit());
        invest.setInvestTime(new Date());
        invest.setCoinType(mineMachineOrder.getCoinType());
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
        invest.setOrderId(mineMachineOrder.getId());
        invest.setProductId(mineMachineOrder.getProductId());
        invest.setProductName(mineMachineOrder.getProductName());
        invest.setProfit(mineMachineOrder.getHourProfit());
        invest.setProfitYear(0L);
        invest.setReturnMoney(BigDecimal.ZERO);
        invest.setReturnDays(0);
        invest.setType(0);
        invest.setUserId(member.getId());
        invest.setUserId(member.getId());
        invest.setPhone(member.getPhone());
        invest.setUserName(member.getUsername());
        invest.setValidity(0L);
        invest.setMemo(memo);
        super.save(invest);
        ethAdminService.transferEther(memberService.find(1L), member, invest.getInvest());
    }
}