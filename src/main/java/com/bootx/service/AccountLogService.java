
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.AccountLog;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.Member;

import java.math.BigDecimal;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface AccountLogService extends BaseService<AccountLog, Long> {

    Page<AccountLog> findPage(Pageable pageable, Long userId, Integer dataType);

    AccountLog create(Member member, BitCoinAccount bitCoinAccount, BigDecimal money, Integer dataType,String title);
}