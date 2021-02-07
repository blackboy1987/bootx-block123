package com.bootx.controller.api.user;

import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.*;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/app/user/ad")
public class MemberAdController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Resource
    private MemberAdService memberAdService;

    @Resource
    private BitCoinAccountService bitCoinAccountService;
    @Resource
    private MoneyFlowLogService moneyFlowLogService;
    @Resource
    private MemberAdReadLogService memberAdReadLogService;

    @PostMapping("/save")
    public Result save(HttpServletRequest request, @CurrentUser Member member, MemberAd memberAd){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        memberAd.setMember(member);

        // TODO 冻结金额
        Integer coinType = memberAd.getCoinType();
        Integer assetType = 6;
        if(coinType==0){
            assetType = 6;
        }else if(coinType==1){
            assetType = 3;
        }else{
            assetType = 3;
        }
        boolean flag = bitCoinAccountService.frozen(member.getId(),memberAd.getPrice().multiply(new BigDecimal(memberAd.getTimes())),assetType);
        if(flag){
            memberAdService.save(memberAd);
            return Result.success("ok");
        }else{
            return Result.error("账户余额不足，发布失败");
        }

    }

    @PostMapping("/list")
    public Result list(HttpServletRequest request, @CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        return Result.success(memberAdService.findList1(true,1));
    }

    @PostMapping("/page")
    public Result page(HttpServletRequest request, @CurrentUser Member member, Pageable pageable){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        return Result.success(memberAdService.findPage1(pageable,true));
    }

    @PostMapping("/detail")
    public Result detail(HttpServletRequest request,Long id, @CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        MemberAd memberAd = memberAdService.find(id);
        if(memberAd==null||!memberAd.getIsShow()||memberAd.getRemainTimes()<=0){
            return Result.error("动态已删除");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",memberAd.getId());
        data.put("content",memberAd.getContent());
        data.put("image",memberAd.getImages());
        data.put("username",memberAd.getMember().getUsername());
        data.put("name",memberAd.getMember().getMemberRank().getName());
        data.put("createdDate",memberAd.getCreatedDate());
        return Result.success(data);
    }

    @PostMapping("/log")
    public Result log(HttpServletRequest request,Long id, @CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        MemberAd memberAd = memberAdService.find(id);
        if(memberAd==null||!memberAd.getIsShow()||memberAd.getRemainTimes()<=0){
            return Result.error("动态已删除");
        }
        memberAd.setRemainTimes(memberAd.getRemainTimes()-1);
        if(memberAd.getRemainTimes()<=0){
            memberAd.setIsShow(false);
        }
        MemberAdStatistics memberAdStatistics = memberAd.getStatistics();
        memberAdStatistics.setReadCount(memberAdStatistics.getReadCount()+1);
        memberAdService.update(memberAd);
        // 当前广告，当前会员 当天有没有看过记录
        boolean flag = memberAdReadLogService.create(member,memberAd);
        if(!flag){
            // 扣除浏览金额
            BigDecimal price = memberAd.getPrice();
            Integer coinType = memberAd.getCoinType();
            BitCoinAccount bitCoinAccount = null;
            if(coinType==0){
                bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(), 6);
            }else if(coinType==1){
                bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(),3);
            }else{
                bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(),3);
            }
            bitCoinAccountService.unFrozen(member.getId(),price,bitCoinAccount.getAssetType());
            moneyFlowLogService.create(price,bitCoinAccount.getMoney(),bitCoinAccount.getFrozenMoney(),bitCoinAccount.getAssetType(),bitCoinAccount.getName(),"浏览广告扣除",member.getId());

        }


        return Result.success("");
    }

}
