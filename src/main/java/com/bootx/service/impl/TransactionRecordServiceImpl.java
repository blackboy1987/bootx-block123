
package com.bootx.service.impl;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.dao.TransactionRecordDao;
import com.bootx.entity.Member;
import com.bootx.entity.TransactionRecord;
import com.bootx.service.TransactionRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class TransactionRecordServiceImpl extends BaseServiceImpl<TransactionRecord, Long> implements TransactionRecordService {

    @Resource
    private TransactionRecordDao transactionRecordDao;

    @Override
    public Page<TransactionRecord> findPage(Member seller, Member buyer, Integer type, Integer status, Date beginDate, Date endDate, Pageable pageable) {
        return transactionRecordDao.findPage(seller,buyer,type,status,beginDate,endDate,pageable);
    }
}