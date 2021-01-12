package com.bootx.controller.api.app.user;

import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("iconApiUserController")
@RequestMapping("/app/user")
public class IndexController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ArticleService articleService;

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
        data.put("addPass",member.getEncodedPassword1()==null);
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
        data.put("addPass",member.getEncodedPassword1()==null);
        data.put(  "userWallet",null);
        data.put(  "userMoney",null);
        data.put( "shopName",null);
        data.put( "shopType",null);
        data.put( "typeName",null);
        return Result.success(data);
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

}
