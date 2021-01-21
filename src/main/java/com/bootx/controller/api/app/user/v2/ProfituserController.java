package com.bootx.controller.api.app.user.v2;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.BitCoinAccountMoney;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.BitCoinAccountMoneyService;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/user/v2/profituser")
public class ProfituserController extends BaseController {

    @Resource
    private MemberService memberService;
    @Resource
    private BitCoinAccountService bitCoinAccountService;
    @Resource
    private BitCoinAccountMoneyService bitCoinAccountMoneyService;

    @PostMapping("/detail")
    public Result detail (@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        Map<String,Object> data = new HashMap<>();
        BitCoinAccount bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(), 6);
        BitCoinAccountMoney bitCoinAccountMoney =  bitCoinAccountMoneyService.findByBitCoinAccountIdAndUserId(bitCoinAccount, member);

        data.put("id",member.getId());
        data.put("profitUsdt",0);
        data.put("profitHbt",0);
        data.put("profitBtc",0);
        data.put("profitJlb",bitCoinAccountMoney.getMoney());
        data.put("profitCny",0);
        return Result.success(data);
    }

}
