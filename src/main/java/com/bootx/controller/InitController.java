package com.bootx.controller;
import com.bootx.common.Result;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountWallet;
import com.bootx.entity.BitCoinType;
import com.bootx.entity.Member;
import com.bootx.eth.service.EthAdminService;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.BitCoinAccountWalletService;
import com.bootx.service.BitCoinTypeService;
import com.bootx.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;

import javax.annotation.Resource;
import java.math.BigDecimal;

@RestController
@RequestMapping("/icon/init")
public class InitController {

    @Resource
    private EthAdminService ethAdminService;
    @Resource
    private MemberService memberService;
    @Resource
    private BitCoinTypeService bitCoinTypeService;
    @Resource
    private BitCoinAccountService bitCoinAccountService;
    @Resource
    private BitCoinAccountWalletService bitCoinAccountWalletService;

    @GetMapping("/account")
   public Result account(Long userId){
        Member member = memberService.find(userId);
        NewAccountIdentifier newAccountIdentifier = ethAdminService.newAccountIdentifier(member.getPhone());
        String accountId = newAccountIdentifier.getAccountId();
        BitCoinType bitCoinType = bitCoinTypeService.findByName("JLB");
        if(bitCoinType!=null){
            BitCoinAccount bitCoinAccount = new BitCoinAccount();
            bitCoinAccount.setMoney(BigDecimal.ZERO);
            bitCoinAccount.setAssetType(bitCoinType.getAssetType());
            bitCoinAccount.setFrozenMoney(BigDecimal.ZERO);
            bitCoinAccount.setName(bitCoinAccount.getName());
            bitCoinAccount.setPrice(bitCoinType.getPrice());
            bitCoinAccount.setState(true);
            bitCoinAccount.setUserId(member.getId());
            bitCoinAccount.setAddressId(accountId);
            bitCoinAccount.setName(bitCoinType.getName());
            bitCoinAccount = bitCoinAccountService.save(bitCoinAccount);
            // 再创建钱包
            BitCoinAccountWallet bitCoinAccountWallet = new BitCoinAccountWallet();
            bitCoinAccountWallet.setUserId(bitCoinAccount.getUserId());
            bitCoinAccountWallet.setBitCoinAccountId(bitCoinAccount.getId());
            bitCoinAccountWallet.setAssetType(bitCoinAccount.getAssetType());
            bitCoinAccountWallet.setWalletAdd(bitCoinAccount.getAddressId());
            bitCoinAccountWallet.setMoney(BigDecimal.ZERO);
            bitCoinAccountWallet.setFrozenMoney(BigDecimal.ZERO);
            bitCoinAccountWallet.setState(0);
            bitCoinAccountWallet.setName(bitCoinAccount.getName());
            bitCoinAccountWallet.setMinLength(0);
            bitCoinAccountWallet.setPrice(bitCoinAccount.getPrice());
            bitCoinAccountWalletService.save(bitCoinAccountWallet);
        }
        return Result.success(accountId);
   }
}
