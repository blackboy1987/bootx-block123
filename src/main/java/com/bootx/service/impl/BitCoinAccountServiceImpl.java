
package com.bootx.service.impl;


import com.bootx.dao.BitCoinAccountDao;
import com.bootx.entity.*;
import com.bootx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service - 广告
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class BitCoinAccountServiceImpl extends BaseServiceImpl<BitCoinAccount, Long> implements BitCoinAccountService {

	@Autowired
	private BitCoinAccountDao bitCoinAccountDao;

	@Autowired
	private BitCoinTypeService bitCoinTypeService;
	@Autowired
	private BitCoinAccountRuleService bitCoinAccountRuleService;
	@Autowired
	private BitCoinAccountMoneyService bitCoinAccountMoneyService;
	@Autowired
	private BitCoinAccountWalletService bitCoinAccountWalletService;
	@Autowired
	private BitCoinAccountBankService bitCoinAccountBankService;

	@Override
	public List<BitCoinAccount> findByUserId(Long userId) {
		return bitCoinAccountDao.findByUserId(userId);
	}

	@Override
	public BitCoinAccount findByUserIdAndName(Long userId, String name) {
		return bitCoinAccountDao.findByUserIdAndName(userId,name);
	}

	@Override
	public void initAccount(Member member) {
		List<BitCoinType> bitCoinTypes = bitCoinTypeService.findAll();
		for (BitCoinType bitCoinType:bitCoinTypes) {
			BitCoinAccount bitCoinAccount = findByUserIdAndName(member.getId(),bitCoinType.getName());
			if(bitCoinAccount==null||bitCoinAccount.getId()==null){
				bitCoinAccount = new BitCoinAccount();
				bitCoinAccount.setAssetType(bitCoinType.getAssetType());
				bitCoinAccount.setFrozenMoney(BigDecimal.ZERO);
				bitCoinAccount.setMoney(BigDecimal.ZERO);
				bitCoinAccount.setName(bitCoinType.getName());
				bitCoinAccount.setPrice(bitCoinType.getPrice());
				bitCoinAccount.setState(true);
				bitCoinAccount.setUserId(member.getId());
				bitCoinAccount = super.save(bitCoinAccount);
				bitCoinAccountBankService.init(member,bitCoinAccount);
				bitCoinAccountMoneyService.init(member,bitCoinAccount);
				bitCoinAccountWalletService.init(member,bitCoinAccount);
				bitCoinAccountRuleService.init(member,bitCoinAccount);
			}
		}
	}

	@Override
	public BitCoinAccount findByUserIdAndAssetType(Long userId, Integer assetType) {
		return bitCoinAccountDao.findByUserIdAndAssetType(userId,assetType);
	}
}