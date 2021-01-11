package com.bootx.controller.api.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.Member;
import com.bootx.entity.ReceiptAccount;
import com.bootx.security.CurrentUser;
import com.bootx.service.AccountLogService;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.MemberService;
import com.bootx.service.ReceiptAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("apiUserWithdrawalController")
@RequestMapping("/app/userWithdrawal")
public class WithdrawalController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ReceiptAccountService receiptAccountService;
    @Autowired
    private AccountLogService accountLogService;
    @Resource
    private BitCoinAccountService bitCoinAccountService;

    @PostMapping("/getBankCard")
    public Result getBankCard(@CurrentUser Member member,Long userId, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            member = memberService.find(userId);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        BitCoinAccount bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(), 3);
        List<ReceiptAccount> receiptAccounts = receiptAccountService.findList(member.getId(),null);
        Map<String,Object> map = new HashMap<>();
        map.put("money",bitCoinAccount.getMoney().subtract(bitCoinAccount.getFrozenMoney()));
        map.put("receiptAccounts",receiptAccounts);
        return Result.success(map);
    }

    /**
     * 提现
     * @param member
     * @param userId
     * @param amount
     * @param bankId
     * @param request
     * @return
     */
    @PostMapping("/requestWithdrawal")
    public Result requestWithdrawal(@CurrentUser Member member, Long userId, String amount,Integer type, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            member = memberService.find(userId);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        if(type==null){
            return Result.error("提现方式不存在");
        }
        List<ReceiptAccount> receiptAccounts = receiptAccountService.findList(member.getId(),type);
        if(receiptAccounts==null||receiptAccounts.size()==0){
            return Result.error("提现方式不存在");
        }

        Map<String,Object> data = new HashMap<>();
        BitCoinAccount bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(), 3);
        if((bitCoinAccount.getMoney().subtract(bitCoinAccount.getFrozenMoney())).compareTo(new BigDecimal(amount))<0){
            return Result.error("可提现余额不足");
        }

        // 写入提现记录
        accountLogService.create(member,bitCoinAccount,new BigDecimal(amount),bitCoinAccount.getAssetType(),"提现");
        bitCoinAccount.setMoney(bitCoinAccount.getMoney().subtract(new BigDecimal(amount)));
        bitCoinAccountService.update(bitCoinAccount);

        data.put("type",receiptAccounts.get(0).getType());
        data.put("bankCard",receiptAccounts.get(0).getBankCard());
        data.put("money",amount);
        return Result.success(data);
    }

}
