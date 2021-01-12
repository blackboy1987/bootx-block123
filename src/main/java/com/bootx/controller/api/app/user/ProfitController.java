package com.bootx.controller.api.app.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("/app/user/profit")
public class ProfitController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private InvestService investService;

    /**
     * 收益列表
     * @param request
     * @param member
     * @return
     */
    @PostMapping("/list")
    public Result profitList(HttpServletRequest request, @CurrentUser Member member, Integer coinType){
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
        data.put("allJLB",123);
        data.put("list", investService.findListByCoinType(member,coinType));

        return Result.success(data);
    }
}
