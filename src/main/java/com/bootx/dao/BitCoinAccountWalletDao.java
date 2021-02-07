
package com.bootx.dao;

import com.bootx.entity.BitCoinAccountWallet;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountWalletDao extends BaseDao<BitCoinAccountWallet, Long> {

    BitCoinAccountWallet findByBitCoinAccountIdAndUserId(Long bitCoinAccountId, Long userId);

    BitCoinAccountWallet findByUserIdAndAssetType(Long userId, Integer assetType);
}