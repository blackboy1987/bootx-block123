
package com.bootx.service;

import com.bootx.entity.ReceiptAccount;

import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface ReceiptAccountService extends BaseService<ReceiptAccount, Long> {
    List<ReceiptAccount> findList(Long userId, Integer type);

}