
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.TransactionRecord;

import java.util.Date;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface TransactionRecordService extends BaseService<TransactionRecord, Long> {
    Page<TransactionRecord> findPage(Member seller, Member buyer, Integer type, Integer status, Date beginDate, Date endDate, Pageable pageable);
}