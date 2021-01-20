package com.bootx.controller.api.app;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
@RequestMapping("/app/prize")
public class PrizeController extends BaseController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/coupon")
    public Result coupon(HttpServletRequest request, @CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        return Result.success(Collections.emptyList());
    }

}
