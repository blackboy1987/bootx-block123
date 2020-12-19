package com.bootx.controller.api;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("iconApiUserController")
@RequestMapping("/app/user")
public class UserController {

    @Autowired
    private BitCoinAccountService bitCoinAccountService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private CacheService cacheService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private InvestService investService;

    @PostMapping("/info/index")
    public Result infoIndex(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",member.getId());
        data.put("userName",member.getUsername());
        data.put("icon","/templates/images/head/010.png");
        data.put("phone",member.getMobile());
        data.put("email",member.getEmail());
        data.put("digest",null);
        data.put("password",null);
        data.put("tradePwd",null);
        data.put( "isActivation",false);
        data.put( "isLock",member.getIsLocked());
        data.put( "isEnable",member.getIsEnabled());
        data.put("extendCode","73161116");
        data.put( "child",12);
        data.put( "isAuth",false);
        data.put( "createDate",member.getCreatedDate());
        data.put("modifyDate",member.getLastModifiedDate());
        data.put("centerOpt",135);
        data.put( "centerMeb",228);
        data.put( "member",45);
        data.put( "memberChild",14);
        data.put( "bindShop",313);
        data.put( "bindTime",null);
        data.put( "umeng",null);
        data.put( "ip","220.189.220.65, 59.56.79.17");
        data.put("status",1);
        data.put( "internal",false);
        data.put( "online",false);
        data.put( "lastLoginDate",member.getLastLoginDate());
        data.put( "investNum",null);
        data.put( "parentName",null);
        data.put( "lastIp",member.getLastLoginIp());
        data.put( "mobile",member.getMobile());
        data.put(  "addPass",true);
        data.put(  "userWallet",null);
        data.put(  "userMoney",null);
        data.put( "shopName",null);
        data.put( "shopType",null);
        data.put( "typeName",null);
        
        
        

        return Result.success(data);
    }

    @PostMapping("/profit/list2")
    public Result profitIndex(@CurrentUser Member member,Integer coinType, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",member.getId());
        data.put("userName",member.getUsername());
        data.put("icon","/templates/images/head/010.png");
        data.put("phone",member.getMobile());
        data.put("email",member.getEmail());
        data.put("digest",null);
        data.put("password",null);
        data.put("tradePwd",null);
        data.put( "isActivation",false);
        data.put( "isLock",member.getIsLocked());
        data.put( "isEnable",member.getIsEnabled());
        data.put("extendCode","73161116");
        data.put( "child",12);
        data.put( "isAuth",false);
        data.put( "createDate",member.getCreatedDate());
        data.put("modifyDate",member.getLastModifiedDate());
        data.put("centerOpt",135);
        data.put( "centerMeb",228);
        data.put( "member",45);
        data.put( "memberChild",14);
        data.put( "bindShop",313);
        data.put( "bindTime",null);
        data.put( "umeng",null);
        data.put( "ip","220.189.220.65, 59.56.79.17");
        data.put("status",1);
        data.put( "internal",false);
        data.put( "online",false);
        data.put( "lastLoginDate",member.getLastLoginDate());
        data.put( "investNum",null);
        data.put( "parentName",null);
        data.put( "lastIp",member.getLastLoginIp());
        data.put( "mobile",member.getMobile());
        data.put(  "addPass",true);
        data.put(  "userWallet",null);
        data.put(  "userMoney",null);
        data.put( "shopName",null);
        data.put( "shopType",null);
        data.put( "typeName",null);




        return Result.success(data);
    }


    /**
     * 检查用户是否实名认证
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/v2/auth/info")
    public Result authInfo(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("请先登录");
        }
        if(member.getIsAuth()){
            return Result.success("ok");
        }
        return Result.success(null);
    }

    /**
     * 重置资金密码
     * @param oldPass
     * @param newPass
     * @param sourPass
     * @param request
     * @param member
     * @return
     */
    @PostMapping("/safe/recash")
    public Result recash(String oldPass,String newPass,String sourPass,HttpServletRequest request,@CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        if(!member.isValidCredentials1(oldPass)){
            return Result.error("原资金密码错误，请重新输入");
        }
        if(StringUtils.equalsIgnoreCase(newPass,sourPass)){
            return Result.error("两次密码输入不一致");
        }
        member.setPassword1(sourPass);
        memberService.update(member);
        return Result.success("修改成功");
    }

    /**
     * 忘记资金密码
     * @param oldPass
     * @param newPass
     * @param sourPass
     * @param request
     * @param member
     * @return
     */
    @PostMapping("/safe/forget_pass")
    public Result forgetPass(String oldPass,String newPass,String sourPass,HttpServletRequest request,@CurrentUser Member member,String code){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        if(StringUtils.isNotBlank(code)){
            if(!cacheService.smsCodeCacheValidate(member.getMobile(),code)){
                return Result.error("验证码校验失败");
            }
        }else{
            if(!member.isValidCredentials1(oldPass)){
                return Result.error("原资金密码错误，请重新输入");
            }
        }
        if(StringUtils.equalsIgnoreCase(newPass,sourPass)){
            return Result.error("两次密码输入不一致");
        }
        member.setPassword1(sourPass);
        memberService.update(member);
        return Result.success("修改成功");
    }

    @PostMapping("/safe/repass")
    public Result repass(String oldPass,String newPass,String sourPass,HttpServletRequest request,@CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        if(!member.isValidCredentials1(oldPass)){
            return Result.error("原登录密码错误，请重新输入");
        }
        if(StringUtils.equalsIgnoreCase(newPass,sourPass)){
            return Result.error("两次密码输入不一致");
        }
        member.setPassword(sourPass);
        memberService.update(member);
        return Result.success("修改成功");
    }

    @PostMapping("/receipt/receipt")
    public Result receipt(HttpServletRequest request,@CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("area",member.getBankArea());
        data.put("bankCard",member.getBankCard());
        data.put("theirBank",member.getBankName());
        return Result.success(data);
    }
    @PostMapping("/receipt/create")
    public Result receiptCreate(HttpServletRequest request,@CurrentUser Member member,String bankCard,String theirBank,String area){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        member.setBankArea(area);
        member.setBankName(theirBank);
        member.setBankCard(bankCard);
        memberService.update(member);
        return Result.success("");
    }
    @PostMapping("/v2/new/detail")
    @JsonView(BaseEntity.ViewView.class)
    public Result newsDetail(HttpServletRequest request, @CurrentUser Member member, Integer type){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        return Result.success(articleService.findLastest());
    }
    @PostMapping("/relation/list")
    public Result relationList(HttpServletRequest request, @CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        return Result.success(memberService.findListTeam(member));
    }

    /**
     * 收益列表
     * @param request
     * @param member
     * @return
     */
    @PostMapping("/profit/list")
    public Result profitList(HttpServletRequest request, @CurrentUser Member member,Integer coinType){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("allHpt",123.86);
        data.put("allHptPrice",82.11);
        data.put("allEth",0);
        data.put("allBtcPrice",45.18);
        data.put("allBtc",0.0003512);
        data.put("allEthPrice",0);
        data.put("list", investService.findListByCoinType(member,coinType));

        return Result.success(data);
    }

}
