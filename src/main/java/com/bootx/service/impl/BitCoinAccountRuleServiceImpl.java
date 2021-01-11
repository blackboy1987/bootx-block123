
package com.bootx.service.impl;
import com.bootx.dao.BitCoinAccountRuleDao;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountRule;
import com.bootx.entity.Member;
import com.bootx.service.BitCoinAccountRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class BitCoinAccountRuleServiceImpl extends BaseServiceImpl<BitCoinAccountRule, Long> implements BitCoinAccountRuleService {

    @Autowired
    private BitCoinAccountRuleDao bitCoinAccountRuleDao;

    @Override
    public void init(Member member, BitCoinAccount bitCoinAccount) {
        BitCoinAccountRule bitCoinAccountRule = new BitCoinAccountRule();
        bitCoinAccountRule.setAssetType(bitCoinAccount.getAssetType());
        bitCoinAccountRule.setCanRecharge(false);
        bitCoinAccountRule.setCanWithdraw(false);
        bitCoinAccountRule.setCanTrade(false);
        bitCoinAccountRule.setRechargeLimit(new BigDecimal("0"));
        bitCoinAccountRule.setRechargeRate(new BigDecimal("0"));
        bitCoinAccountRule.setRechargeMax(new BigDecimal("0"));
        bitCoinAccountRule.setWithdrawLimit(new BigDecimal("0"));
        bitCoinAccountRule.setWithdrawRate(new BigDecimal("0"));
        bitCoinAccountRule.setWithdrawMax(new BigDecimal("0"));
        bitCoinAccountRule.setActivation(false);
        bitCoinAccountRule.setWithdrawProduct(0L);
        bitCoinAccountRule.setWithdrawAuth(false);
        bitCoinAccountRule.setProductName("");
        bitCoinAccountRule.setName("");
        super.save(bitCoinAccountRule);
    }

    @Override
    public BitCoinAccountRule findByAssetType(Integer assetType) {
        return bitCoinAccountRuleDao.findByAssetType(assetType);
    }
}