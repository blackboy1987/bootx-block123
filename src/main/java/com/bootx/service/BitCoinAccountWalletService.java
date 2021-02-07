
package com.bootx.service;

import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountWallet;
import com.bootx.entity.Member;

import java.math.BigDecimal;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountWalletService extends BaseService<BitCoinAccountWallet, Long> {

    void init(Member member, BitCoinAccount bitCoinAccount);

    BitCoinAccountWallet findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId);

    BigDecimal sumMoney(Long userId);

    BitCoinAccountWallet findByUserIdAndAssetType(Long userId, Integer assetType);
}