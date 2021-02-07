
package com.bootx.service;

import com.bootx.entity.MoneyFlowLog;

import java.math.BigDecimal;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MoneyFlowLogService extends BaseService<MoneyFlowLog, Long> {

    MoneyFlowLog create(BigDecimal money, BigDecimal balance,BigDecimal frozenMoney,Integer coinType,String coinName, String memo, Long userId);
}