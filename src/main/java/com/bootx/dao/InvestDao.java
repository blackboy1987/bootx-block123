
package com.bootx.dao;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;

import java.util.Date;
import java.util.List;

/**
 * Dao - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface InvestDao extends BaseDao<Invest, Long> {

    List<Invest> findListByCoinType(Member member, Integer coinType);

    Page<Invest> findPage(Pageable pageable, Member member, Integer excision, MineMachine mineMachine, Integer type, Integer coinType, Date beginDate, Date endDate);

    List<Invest> findList1(Member member, Integer excision, MineMachine mineMachine, Integer type, Integer coinType, Date beginDate, Date endDate);
}