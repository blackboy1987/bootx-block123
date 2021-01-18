package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.entity.*;
import com.bootx.service.BitCoinAccountMoneyService;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.BitCoinAccountWalletService;
import com.bootx.service.MemberService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author black
 */
@RestController("adminMemberController")
@RequestMapping("/block/admin/member")
public class MemberController extends BaseController {

    @Resource
    private MemberService memberService;
    @Resource
    private BitCoinAccountService bitCoinAccountService;
    @Resource
    private BitCoinAccountMoneyService bitCoinAccountMoneyService;
    @Resource
    private BitCoinAccountWalletService bitCoinAccountWalletService;

    @PostMapping("/page")
    @JsonView(BaseEntity.PageView.class)
    public Message page(Pageable pageable, String username, String name, Date beginDate, Date endDate){
        Page<Member> page = memberService.findPage(pageable,username,name,beginDate,endDate);
        for (Member member: page.getContent()) {
            member.setBitCoinAccounts(bitCoinAccountService.findByUserId(member.getId()));
        }
        return Message.success(page);
    }



    @PostMapping("/updateBitCoinMoney")
    public Message updateBitCoinMoney(BigDecimal money,Long bitCoinAccountId,Long memberId){
        BitCoinAccount bitCoinAccount = bitCoinAccountService.find(bitCoinAccountId);
        Member member = memberService.find(memberId);
        if(bitCoinAccount.getUserId().compareTo(memberId)!=0){
            Message.error("修改钱包的用户跟当前用户不一致");
        }
        bitCoinAccountService.updateBitCoinMoney(bitCoinAccount,money,member);

        return Message.success("操作成功");
    }


}
