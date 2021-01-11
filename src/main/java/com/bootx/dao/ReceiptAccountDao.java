
package com.bootx.dao;

import com.bootx.entity.ReceiptAccount;

import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface ReceiptAccountDao extends BaseDao<ReceiptAccount, Long> {
    List<ReceiptAccount> findList(Long userId, Integer type);
}