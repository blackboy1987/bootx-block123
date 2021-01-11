package com.bootx.controller.api.app.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BitCoinAccount;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.AccountLogService;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.CompanyBankService;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author black
 */
@RestController("apiUserPayController")
@RequestMapping("/app/user/pay")
public class PayController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private BitCoinAccountService bitCoinAccountService;

    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private CompanyBankService companyBankService;

    /**
     * 充值
     * @param money
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/request")
    public Result request(BigDecimal money, @CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        if(money!=null&&money.compareTo(BigDecimal.ZERO)>0){
            // member.setBalance(member.getBalance().add(money));
            // memberService.update(member);
            BitCoinAccount bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(), 3);
            if(bitCoinAccount!=null&&!bitCoinAccount.isNew()){
                //bitCoinAccount.setMoney(bitCoinAccount.getMoney().add(money));
                //bitCoinAccountService.update(bitCoinAccount);
                accountLogService.create(member,bitCoinAccount,money,1,bitCoinAccount.getName()+"充值");
            }

            return Result.success("充值成功,等待系统审核");
        }else{
            return Result.error("充值金额有误");
        }
    }

    @PostMapping("/getCompanyBank")
    public Result getCompanyBank(@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",198);
        data.put("userId",-1);
        data.put("bankCard","陈顺心");
        data.put("theirBank",null);
        data.put("area","陈顺心");
        data.put("name","《官方账号：283205226@qq.com》");
        data.put("img","https://boox-mall.oss-cn-hangzhou.aliyuncs.com/20201222090955.jpg");
        return Result.success(companyBankService.get());
    }
}
