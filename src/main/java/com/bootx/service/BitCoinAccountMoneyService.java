
package com.bootx.service;

import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountMoney;
import com.bootx.entity.Member;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountMoneyService extends BaseService<BitCoinAccountMoney, Long> {

    BitCoinAccountMoney init(Member member, BitCoinAccount bitCoinAccount);

    BitCoinAccountMoney findByBitCoinAccountIdAndUserId(BitCoinAccount bitCoinAccount, Member member);

    BitCoinAccountMoney findByUserIdAndAssetType(Long userId, Integer assetType);

}