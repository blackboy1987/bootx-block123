
package com.bootx.service.impl;

import com.bootx.dao.MemberRankDao;
import com.bootx.entity.MemberRank;
import com.bootx.service.MemberRankService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Service - 会员等级
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class MemberRankServiceImpl extends BaseServiceImpl<MemberRank, Long> implements MemberRankService {

	@Autowired
	private MemberRankDao memberRankDao;

	@Override
	@Transactional(readOnly = true)
	public boolean amountExists(BigDecimal amount) {
		return memberRankDao.exists("amount", amount);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean amountUnique(Long id, BigDecimal amount) {
		return memberRankDao.unique(id, "amount", amount);
	}

	@Override
	@Transactional(readOnly = true)
	public MemberRank findDefault() {
		return memberRankDao.findDefault();
	}

	@Override
	@Transactional(readOnly = true)
	public MemberRank findByAmount(BigDecimal amount) {
		return memberRankDao.findByAmount(amount);
	}

	@Override
	public MemberRank getLastest(Long orderCount) {
		if(orderCount==null){
			orderCount = 0L;
		}
		try {
			Map<String,Object> map = jdbcTemplate.queryForMap("select id,mineMachineCount from memberrank where mineMachineCount<"+orderCount+" order by mineMachineCount desc limit 1",new TypeReference<Map<String,Object>>(){

			});
			return find(Long.valueOf(map.get("id")+""));
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Transactional
	public MemberRank save(MemberRank memberRank) {
		Assert.notNull(memberRank, "[Assertion failed] - memberRank is required; it must not be null");

		if (BooleanUtils.isTrue(memberRank.getIsDefault())) {
			memberRankDao.clearDefault();
		}
		return super.save(memberRank);
	}

	@Override
	@Transactional
	public MemberRank update(MemberRank memberRank) {
		Assert.notNull(memberRank, "[Assertion failed] - memberRank is required; it must not be null");

		MemberRank pMemberRank = super.update(memberRank);
		if (BooleanUtils.isTrue(pMemberRank.getIsDefault())) {
			memberRankDao.clearDefault(pMemberRank);
		}
		return pMemberRank;
	}

	@Override
	public Integer countMember(MemberRank memberRank) {
		if(memberRank==null){
			return 0;
		}
		return jdbcTemplate.queryForObject("select count(1) from member where memberRank_id="+memberRank.getId(), Integer.class);
	}

	@Override
	public MemberRank getLastest(Long orderCount, Long adClickCount) {
		if(orderCount==null){
			orderCount = 0L;
		}
		if(adClickCount==null){
			adClickCount = 0L;
		}
		try {
			Map<String,Object> map = jdbcTemplate.queryForMap("select id,mineMachineCount from memberrank where mineMachineCount<="+orderCount+" and adClickCount<="+adClickCount+" order by mineMachineCount desc limit 1",new TypeReference<Map<String,Object>>(){

			});
			return find(Long.valueOf(map.get("id")+""));
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}