package com.bootx.controller.api.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.ReceiptAccount;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import com.bootx.service.ReceiptAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/app/user/receipt")
public class ReceiptController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ReceiptAccountService receiptAccountService;

    @PostMapping("/receipt")
    public Result receipt(HttpServletRequest request, @CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        return Result.success(receiptAccountService.findList(member.getId(),null));
    }

    /**
     * 设置收款信息
     * @param request
     * @param member
     * @param bankCard
     * @param theirBank
     * @param area
     * @return
     */
    @PostMapping("/create")
    public Result receiptCreate(HttpServletRequest request, @CurrentUser Member member, String bankCard, String theirBank, String area, Integer type){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        List<ReceiptAccount> receiptAccounts = receiptAccountService.findList(member.getId(), type);
        ReceiptAccount receiptAccount = new ReceiptAccount();
        if(receiptAccounts!=null && receiptAccounts.size()>0){
            receiptAccount = receiptAccounts.get(0);
        }
        receiptAccount.setUserId(member.getId());
        receiptAccount.setBankCard(bankCard);
        receiptAccount.setName(member.getName());
        receiptAccount.setType(type);
        receiptAccount.setArea(area);
        receiptAccount.setTheirBank(theirBank);
        if(type==2){
            receiptAccount.setArea("支付宝");
            receiptAccount.setTheirBank("支付宝");
        }
        if(receiptAccount.isNew()){
            receiptAccountService.save(receiptAccount);
        }else{
            receiptAccountService.update(receiptAccount);
        }
        return Result.success("");
    }
}
