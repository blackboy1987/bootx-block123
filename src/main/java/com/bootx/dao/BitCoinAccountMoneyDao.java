
package com.bootx.dao;

import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountMoney;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountMoneyDao extends BaseDao<BitCoinAccountMoney, Long> {

    BitCoinAccountMoney findByBitCoinAccountIdAndUserId(BitCoinAccount bitCoinAccount, Long userId);

    BitCoinAccountMoney findByUserIdAndAssetType(Long userId, Integer assetType);
}