
package com.bootx.service.impl;

import com.bootx.dao.ReceiptAccountDao;
import com.bootx.entity.ReceiptAccount;
import com.bootx.service.ReceiptAccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class ReceiptAccountServiceImpl extends BaseServiceImpl<ReceiptAccount, Long> implements ReceiptAccountService {

    @Resource
    private ReceiptAccountDao receiptAccountDao;

    @Override
    public List<ReceiptAccount> findList(Long userId, Integer type) {
        return receiptAccountDao.findList(userId,type);
    }

    @Override
    public List<ReceiptAccount> findListByUserId(Long userId) {
        return receiptAccountDao.findListByUserId(userId);
    }
}