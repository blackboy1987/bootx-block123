
package com.bootx.service.impl;

import com.bootx.entity.MoneyFlowLog;
import com.bootx.service.MoneyFlowLogService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class MoneyFlowLogServiceImpl extends BaseServiceImpl<MoneyFlowLog, Long> implements MoneyFlowLogService {

    @Override
    public MoneyFlowLog create(BigDecimal money,BigDecimal balance,BigDecimal frozenMoney,Integer coinType,String coinName,String memo,Long userId) {
        MoneyFlowLog moneyFlowLog = new MoneyFlowLog();
        moneyFlowLog.setMoney(money);
        moneyFlowLog.setFrozenMoney(frozenMoney);
        moneyFlowLog.setBeforeMoney(balance);
        moneyFlowLog.setCoinType(coinType);
        moneyFlowLog.setCoinName(coinName);
        moneyFlowLog.setAfterMoney(moneyFlowLog.getBeforeMoney().subtract(moneyFlowLog.getMoney()));
        moneyFlowLog.setMemo(memo);
        moneyFlowLog.setUserId(userId);
        return super.save(moneyFlowLog);
    }
}