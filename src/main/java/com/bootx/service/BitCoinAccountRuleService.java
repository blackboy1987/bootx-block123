
package com.bootx.service;

import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountRule;
import com.bootx.entity.Member;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface BitCoinAccountRuleService extends BaseService<BitCoinAccountRule, Long> {

    void init(Member member, BitCoinAccount bitCoinAccount);

    BitCoinAccountRule findByAssetType(Integer assetType);
}