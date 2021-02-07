
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
    public BitCoinAccountMoney init (Member member, BitCoinAccount bitCoinAccount) {
        BitCoinAccountMoney bitCoinAccountMoney = new BitCoinAccountMoney();
        bitCoinAccountMoney.setUserId(member.getId());
        bitCoinAccountMoney.setBitCoinAccountId(bitCoinAccount.getId());
        bitCoinAccountMoney.setAssetType(bitCoinAccount.getAssetType());
        bitCoinAccountMoney.setMoney(new BigDecimal("0"));
        bitCoinAccountMoney.setFrozenMoney(new BigDecimal("0"));
        bitCoinAccountMoney.setState(0);
        bitCoinAccountMoney.setName(bitCoinAccount.getName());
        bitCoinAccountMoney.setPrice(new BigDecimal("0"));
        bitCoinAccountMoney.setLockMoney(BigDecimal.ZERO);
        return super.save(bitCoinAccountMoney);
    }

    @Override
    public BitCoinAccountMoney findByBitCoinAccountIdAndUserId(BitCoinAccount bitCoinAccount, Member member) {
        BitCoinAccountMoney bitCoinAccountMoney = bitCoinAccountMoneyDao.findByBitCoinAccountIdAndUserId(bitCoinAccount, member.getId());
        if(bitCoinAccountMoney==null){
            bitCoinAccountMoney = init(member,bitCoinAccount);;
        }


        if(StringUtils.equalsIgnoreCase("JLB",bitCoinAccount.getName())){
            bitCoinAccountMoney.setPrice(BigDecimal.valueOf(Double.parseDouble(ethAdminService.ethGetBalance(bitCoinAccount.getAddressId()))));
            bitCoinAccountMoney.setMoney(bitCoinAccountMoney.getPrice().divide(new BigDecimal(2)));
        }
        return bitCoinAccountMoney;
    }

    @Override
    public BitCoinAccountMoney findByUserIdAndAssetType(Long userId, Integer assetType) {
        return bitCoinAccountMoneyDao.findByUserIdAndAssetType(userId,assetType);
    }
}