
package com.bootx.service.impl;


import com.bootx.dao.BitCoinAccountDao;
import com.bootx.entity.*;
import com.bootx.eth.service.EthAdminService;
import com.bootx.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;

import javax.annotation.Resource;
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
	private MemberService memberService;

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
	@Resource
	private EthAdminService ethAdminService;

	@Override
	@Transactional(readOnly = true)
	public List<BitCoinAccount> findByUserId(Long userId) {
		List<BitCoinAccount> bitCoinAccounts = bitCoinAccountDao.findByUserId(userId);
		for (BitCoinAccount bitCoinAccount:bitCoinAccounts) {
			if(StringUtils.equalsIgnoreCase("JLB",bitCoinAccount.getName())){
				bitCoinAccount.setPrice(BigDecimal.valueOf(Double.parseDouble(ethAdminService.ethGetBalance(bitCoinAccount.getAddressId()))));
				bitCoinAccount.setMoney(bitCoinAccount.getPrice().divide(new BigDecimal(2)));
			}
		}
		return bitCoinAccounts;
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

				if(StringUtils.equalsIgnoreCase("JLB",bitCoinAccount.getName())){
					NewAccountIdentifier newAccountIdentifier = ethAdminService.newAccountIdentifier(member.getUsername());
					if(newAccountIdentifier!=null){
						bitCoinAccount.setAddressId(newAccountIdentifier.getAccountId());
						member.setAccountId(bitCoinAccount.getAddressId());
						memberService.update(member);
					}
				}
				bitCoinAccount = super.save(bitCoinAccount);
				bitCoinAccountBankService.init(member,bitCoinAccount);
				bitCoinAccountMoneyService.init(member,bitCoinAccount);
				bitCoinAccountWalletService.init(member,bitCoinAccount);
				// bitCoinAccountRuleService.init(member,bitCoinAccount);
			}
		}
	}

	@Override
	public BitCoinAccount findByUserIdAndAssetType(Long userId, Integer assetType) {
		return bitCoinAccountDao.findByUserIdAndAssetType(userId,assetType);
	}

	@Override
	public BigDecimal getAmount(Long userId, Integer coinType) {
		BitCoinAccount bitCoinAccount = findByUserIdAndAssetType(userId,coinType);
		if(bitCoinAccount==null){
			return BigDecimal.ZERO;
		}
		return bitCoinAccount.getMoney().subtract(bitCoinAccount.getFrozenMoney());
	}

	@Override
	public void addMoney(AccountLog accountLog) {
		BitCoinAccount bitCoinAccount = findByUserIdAndAssetType(accountLog.getUserId(),accountLog.getAssetType());
		if(bitCoinAccount==null){
			bitCoinAccount = initAccount(memberService.find(accountLog.getId()),accountLog.getAssetType());
			if(bitCoinAccount!=null){
				BigDecimal money = accountLog.getMoney();
				if(accountLog.getDataType()!=1){
					money = money.multiply(new BigDecimal(-1));
				}
				bitCoinAccount.setMoney(bitCoinAccount.getMoney().add(money));
				super.save(bitCoinAccount);
			}
		}
	}

	@Override
	public BitCoinAccount findByAddressIdAndAssetType(String addressId, Integer asset) {
		return bitCoinAccountDao.findByAddressIdAndAssetType(addressId,asset);
	}

	@Override
	public boolean transaction(BitCoinAccount bitCoinAccount, BitCoinAccount toBitCoinAccount, BigDecimal money) {
		Member from = memberService.find(bitCoinAccount.getUserId());
		Member to = memberService.find(toBitCoinAccount.getUserId());
		if(StringUtils.equalsIgnoreCase("JLB",bitCoinAccount.getName())){
			from.setAccountId(bitCoinAccount.getAddressId());
			to.setAccountId(toBitCoinAccount.getAddressId());
			String result = ethAdminService.transferEther(from,to,money);
			System.out.println(result);
		}
		return true;
	}

	@Override
	public void updateBitCoinMoney(BitCoinAccount bitCoinAccount,BigDecimal money,Member member) {
		bitCoinAccount.setMoney(money);
		update(bitCoinAccount);
		BitCoinAccountMoney bitCoinAccountMoney = bitCoinAccountMoneyService.findByBitCoinAccountIdAndUserId(bitCoinAccount,member);
		bitCoinAccountMoney.setMoney(money);
		BitCoinAccountWallet bitCoinAccountWallet = bitCoinAccountWalletService.findByBitCoinAccountIdAndUserId(bitCoinAccount.getId(),member.getId());
		if(bitCoinAccountWallet!=null){
			bitCoinAccountWallet.setMoney(money);
			bitCoinAccountWalletService.update(bitCoinAccountWallet);
		}
	}

	private BitCoinAccount initAccount(Member member, Integer assetType) {
		BitCoinType bitCoinType = bitCoinTypeService.findByAssetType(assetType);
		BitCoinAccount bitCoinAccount = findByUserIdAndAssetType(member.getId(),assetType);
		if(bitCoinAccount==null||bitCoinAccount.getId()==null&&bitCoinType!=null){
			bitCoinAccount = new BitCoinAccount();
			bitCoinAccount.setAssetType(assetType);
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
			return bitCoinAccount;
		}
		return null;
	}
}