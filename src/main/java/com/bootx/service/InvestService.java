
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface InvestService extends BaseService<Invest, Long> {

    List<Invest> findListByCoinType(Member member, Integer coinType);

    BigDecimal sum(Member member, Integer coinType);

    void create(Member member, MineMachine mineMachine, Integer type, Integer excision);

    Page<Invest> findPage(Pageable pageable, Member member, Integer excision, Long productId, Integer type,Integer coinType, Date beginDate, Date endDate);
    List<Invest> findList1(Member member, Integer excision, Long productId, Integer type,Integer coinType, Date beginDate, Date endDate);
}