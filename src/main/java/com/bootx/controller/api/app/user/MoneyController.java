
package com.bootx.controller.api.app.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountMoney;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController("apiUserMoneyController")
@RequestMapping({"/app/user/money"})
public class MoneyController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BitCoinAccountService bitCoinAccountService;
    @Autowired
    private BitCoinAccountBankService bitCoinAccountBankService;
    @Autowired
    private BitCoinAccountMoneyService bitCoinAccountMoneyService;
    @Autowired
    private BitCoinAccountWalletService bitCoinAccountWalletService;
    @Autowired
    private BitCoinAccountRuleService bitCoinAccountRuleService;

    public MoneyController() {
    }

    @PostMapping({"/details"})
    public Result details(Integer asset, @CurrentUser Member member, HttpServletRequest request) {
        if (member == null) {
            member = memberService.getCurrent(request);
        }

        if (member == null) {
            return Result.error("登录信息已过期，请重新登录");
        } else {
            BitCoinAccount bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(), asset);
            BitCoinAccountMoney bitCoinAccountMoney =  bitCoinAccountMoneyService.findByBitCoinAccountIdAndUserId(bitCoinAccount, member.getId());
            Map<String, Object> data = new HashMap();
            data.put("coinNum", 1);
            data.put("withdraw", bitCoinAccountMoney.getMoney().subtract(bitCoinAccountMoney.getFrozenMoney()));
            data.put("bank", bitCoinAccountBankService.findByBitCoinAccountIdAndUserId(bitCoinAccount.getId(), member.getId()));
            data.put("wallet", bitCoinAccountWalletService.findByBitCoinAccountIdAndUserId(bitCoinAccount.getId(),member.getId()));
            data.put("money",bitCoinAccountMoney);
            data.put("rule", bitCoinAccountRuleService.findByAssetType((bitCoinAccount.getAssetType())));
            return Result.success(data);
        }
    }

    @RequestMapping({"/list"})
    public Result moneyList(@CurrentUser Member member, HttpServletRequest request) {
        Map<String, Object> data = new HashMap();
        if (member == null) {
            member = memberService.getCurrent(request);
        }

        if (member == null) {
            return Result.error("登录信息已过期，请重新登录");
        } else {
            // 所有币的账户余额
            BigDecimal total = bitCoinAccountWalletService.sumMoney(member.getId());
            data.put("totalEarnings", 127.29D);
            data.put("totalMoney",total);
            data.put("list", bitCoinAccountService.findByUserId(member.getId()));
            return Result.success(data);
        }
    }
}
