
package com.bootx.service.impl;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.AccountLogDao;
import com.bootx.entity.AccountLog;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.Member;
import com.bootx.service.AccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class AccountLogServiceImpl extends BaseServiceImpl<AccountLog, Long> implements AccountLogService {

    @Autowired
    private AccountLogDao accountLogDao;

    @Override
    public Page<AccountLog> findPage(Pageable pageable, Long userId, Integer dataType) {
        return accountLogDao.findPage(pageable, userId, dataType);
    }

    @Override
    public AccountLog create(Member member, BitCoinAccount bitCoinAccount, BigDecimal money, Integer dataType,String title) {
        if(member!=null&&bitCoinAccount!=null&&money!=null&&money.compareTo(BigDecimal.ZERO)>0&&dataType!=null){
            AccountLog accountLog = new AccountLog();
            accountLog.setTitle(title);
            accountLog.setAssetType(bitCoinAccount.getAssetType());
            accountLog.setMoney(money);
            accountLog.setBeforeMoney(bitCoinAccount.getMoney());
            accountLog.setAfterMoney(BigDecimal.ZERO);
            accountLog.setState(0);
            accountLog.setDataType(dataType);
            accountLog.setUserId(member.getId());
            accountLog.setMobile(member.getMobile());
            accountLog.setName(member.getName());
            return super.save(accountLog);
        }

        return null;
    }
}