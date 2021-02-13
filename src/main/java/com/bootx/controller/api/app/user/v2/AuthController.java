package com.bootx.controller.api.app.user.v2;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Certification;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.CertificationService;
import com.bootx.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("appUserV2AuthController")
@RequestMapping("/app/user/v2/auth")
public class AuthController extends BaseController {

    @Resource
    private MemberService memberService;
    @Resource
    private CertificationService certificationService;

    @PostMapping("/info")
    public Result info(@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期，请重新登录");
        }
        // data:{"type":200,"content":"","date":{"id":27310,"user":218776,"name":"张三","certNo":"429***********1130","certPhoto":"[\"upload/auth/20200927/160cf88c-801d-46e9-bdcd-56d16f356840.jpg\",\"upload/auth/20200927/e58b3849-a7b8-461c-b0ab-0d49f6534da2.jpg\"]","state":1,"alipayInfo":null,"bankInfo":null,"userName":null,"phone":null,"shopName":null,"shopType":null},"code":null,"message":null}

        Certification certification = certificationService.findByUserId(member.getId());
        return Result.success(certification);
    }

    @PostMapping("/create")
    public Result create(@CurrentUser Member member, HttpServletRequest request,String zheng,String hand,String name,String card){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期，请重新登录");
        }
        certificationService.create(member,zheng,hand,name,card);
        return Result.success("ok");
    }

}
