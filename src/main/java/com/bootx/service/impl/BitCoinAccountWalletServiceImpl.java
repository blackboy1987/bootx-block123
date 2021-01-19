
package com.bootx.service.impl;

import com.bootx.dao.BitCoinAccountWalletDao;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountWallet;
import com.bootx.entity.Member;
import com.bootx.eth.service.EthAdminService;
import com.bootx.service.BitCoinAccountWalletService;
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
public class BitCoinAccountWalletServiceImpl extends BaseServiceImpl<BitCoinAccountWallet, Long> implements BitCoinAccountWalletService {
    @Autowired
    private BitCoinAccountWalletDao bitCoinAccountWalletDao;

    @Resource
    private EthAdminService ethAdminService;

    @Override
    public void init(Member member, BitCoinAccount bitCoinAccount) {
        BitCoinAccountWallet bitCoinAccountWallet = new BitCoinAccountWallet();
        bitCoinAccountWallet.setUserId(member.getId());
        bitCoinAccountWallet.setBitCoinAccountId(bitCoinAccount.getId());
        bitCoinAccountWallet.setAssetType(bitCoinAccount.getAssetType());
        bitCoinAccountWallet.setWalletAdd("");
        bitCoinAccountWallet.setMoney(new BigDecimal("0"));
        bitCoinAccountWallet.setFrozenMoney(new BigDecimal("0"));
        bitCoinAccountWallet.setState(0);
        bitCoinAccountWallet.setName(bitCoinAccount.getName());
        bitCoinAccountWallet.setMinLength(0);
        bitCoinAccountWallet.setWalletAdd(bitCoinAccount.getAddressId());
        super.save(bitCoinAccountWallet);

    }

    @Override
    @Transactional(readOnly = true)
    public BitCoinAccountWallet findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId) {

        BitCoinAccountWallet bitCoinAccountWallet = bitCoinAccountWalletDao.findByBitCoinAccountIdAndUserId( bitCoinAccountId, userId);
        if(bitCoinAccountWallet==null){
            return null;
        }
        if(StringUtils.equalsIgnoreCase("JLB",bitCoinAccountWallet.getName())){
            bitCoinAccountWallet.setPrice(BigDecimal.valueOf(Double.parseDouble(ethAdminService.ethGetBalance(bitCoinAccountWallet.getWalletAdd()))));
            bitCoinAccountWallet.setMoney(bitCoinAccountWallet.getPrice().divide(new BigDecimal(2)));
        }
        return bitCoinAccountWallet;
    }

    @Override
    public BigDecimal sumMoney(Long userId) {
        return jdbcTemplate.queryForObject("select sum((bitcoinaccountwallet.money-bitcoinaccountwallet.frozenMoney)*bitcoinaccountwallet.price) from bitcoinaccountwallet as bitcoinaccountwallet where bitcoinaccountwallet.userId="+userId,BigDecimal.class);
    }
}