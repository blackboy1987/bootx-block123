
package com.bootx.service;

import com.bootx.entity.AccountLog;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.Member;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountService extends BaseService<BitCoinAccount, Long> {

    List<BitCoinAccount> findByUserId(Long userId);

    BitCoinAccount findByUserIdAndName(Long userId,String name);

    void initAccount(Member member);

    BitCoinAccount findByUserIdAndAssetType(Long userId, Integer assetType);

    BigDecimal getAmount(Long userId, Integer coinType);

    void addMoney(AccountLog accountLog);

    BitCoinAccount findByAddressIdAndAssetType(String addressId, Integer asset);

    boolean transaction(BitCoinAccount bitCoinAccount, BitCoinAccount toBitCoinAccount, BigDecimal money);

    void updateBitCoinMoney(BitCoinAccount bitCoinAccount,BigDecimal money,Member member);

    BitCoinAccount get(BitCoinAccount bitCoinAccount);

    boolean frozen(Long userId, BigDecimal frozenMoney, Integer assetType);

    boolean unFrozen(Long userId, BigDecimal frozenMoney, Integer assetType);
}