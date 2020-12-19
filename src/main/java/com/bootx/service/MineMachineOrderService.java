
package com.bootx.service;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.entity.MineMachineOrder;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface MineMachineOrderService extends BaseService<MineMachineOrder, Long> {

    MineMachineOrder create(Member member, MineMachine mineMachine, Integer quantity, Integer day, Integer excision,Integer orderType);

    Page<MineMachineOrder> findPage(Pageable pageable, Member member, Integer excision, String orderType, String coinType);
}