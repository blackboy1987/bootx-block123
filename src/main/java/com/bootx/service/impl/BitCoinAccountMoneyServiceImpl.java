
package com.bootx.service.impl;
import com.bootx.dao.BitCoinAccountMoneyDao;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountMoney;
import com.bootx.entity.Member;
import com.bootx.eth.service.EthAdminService;
import com.bootx.service.BitCoinAccountMoneyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class BitCoinAccountMoneyServiceImpl extends BaseServiceImpl<BitCoinAccountMoney, Long> implements BitCoinAccountMoneyService {

    @Autowired
    private BitCoinAccountMoneyDao bitCoinAccountMoneyDao;
    @Resource
    private EthAdminService ethAdminService;

    @Override
    public void init(Member member, BitCoinAccount bitCoinAccount) {
        BitCoinAccountMoney bitCoinAccountMoney = new BitCoinAccountMoney();
        bitCoinAccountMoney.setUserId(member.getId());
        bitCoinAccountMoney.setBitCoinAccountId(bitCoinAccount.getId());
        bitCoinAccountMoney.setAssetType(bitCoinAccount.getAssetType());
        bitCoinAccountMoney.setMoney(new BigDecimal("0"));
        bitCoinAccountMoney.setFrozenMoney(new BigDecimal("0"));
        bitCoinAccountMoney.setState(0);
        bitCoinAccountMoney.setName(bitCoinAccount.getName());
        bitCoinAccountMoney.setPrice(new BigDecimal("0"));
        super.save(bitCoinAccountMoney);
    }

    @Override
    @Transactional(readOnly = true)
    public BitCoinAccountMoney findByBitCoinAccountIdAndUserId(BitCoinAccount bitCoinAccount, Long userId) {
        BitCoinAccountMoney bitCoinAccountMoney = bitCoinAccountMoneyDao.findByBitCoinAccountIdAndUserId(bitCoinAccount, userId);
        if(StringUtils.equalsIgnoreCase("JLB",bitCoinAccount.getName())){
            bitCoinAccountMoney.setPrice(BigDecimal.valueOf(Double.parseDouble(ethAdminService.ethGetBalance(bitCoinAccount.getAddressId()))));
            bitCoinAccountMoney.setMoney(bitCoinAccountMoney.getPrice().divide(new BigDecimal(2)));
        }
        return bitCoinAccountMoney;
    }
}