package com.bootx.controller.api.app.user.v2;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Invest;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.InvestService;
import com.bootx.service.MemberService;
import com.bootx.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("userV2InvestController")
@RequestMapping("/app/user/v2/invest")
public class InvestController extends BaseController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private InvestService investService;

    @PostMapping("/page/byDate")
    public Result pageByDate(@CurrentUser Member member, HttpServletRequest request, Integer coinType, Integer limit, Integer page) {
        if (limit == null) {
            limit = 10;
        }
        Pageable pageable = new Pageable(page, limit);
        if (member == null) {
            member = memberService.getCurrent(request);
        }
        if (member == null) {
            return Result.error("请先登录");
        }
        Map<String, Object> data = new HashMap<>();
        Page<Invest> page1 = investService.findPage(pageable, member, null, null, null, coinType, null, null);
        data.put("list",page1.getContent());
        data.put("page",page1.getPageNumber());
        data.put("totalPage",page1.getTotalPages());
        return Result.success(data);
    }

    @PostMapping("/page")
    public Result page(@CurrentUser Member member, HttpServletRequest request, Integer excision, Long productId, Integer type, Integer page, Integer limit, Date createDate, Integer coinType) {
        if (limit == null) {
            limit = 10;
        }
        Date beginDate = null;
        Date endDate = null;
        if (createDate != null) {
            beginDate = DateUtils.getBeginDay(createDate);
            endDate = DateUtils.getEndDay(createDate);
        }
        Pageable pageable = new Pageable(page, limit);
        if (member == null) {
            member = memberService.getCurrent(request);
        }
        if (member == null) {
            return Result.error("请先登录");
        }
        Map<String, Object> data = new HashMap<>();
        Page<Invest> page1 = investService.findPage(pageable, member, excision, productId, type, coinType, beginDate, endDate);
        data.put("list",page1.getContent());
        data.put("excision",excision);
        data.put("page",page1.getPageNumber());
        data.put("totalPage",page1.getTotalPages());
        return Result.success(data);
    }


    @PostMapping("/invest")
    public Result invest(@CurrentUser Member member, HttpServletRequest request, Integer coinType) {
        if (member == null) {
            member = memberService.getCurrent(request);
        }
        if (member == null) {
            return Result.error("请先登录");
        }
        Map<String, Object> data = new HashMap<>();
        List<Invest> list = investService.findList1(member, null, null, null, coinType, null, null);
        data.put("list",list);
        data.put("invest",Math.random()*100);
        return Result.success(data);
    }

    @PostMapping("/moneyRule")
    public Result moneyRule(@CurrentUser Member member, HttpServletRequest request, Integer assetType) {
        Map<String, Object> data = new HashMap<>();
        if (member == null) {
            member = memberService.getCurrent(request);
        }
        if (member == null) {
            data.put("type",0);
            return Result.ok(0,null);
        }
        if(member.getIsAuth()==null||!member.getIsAuth()){
            return Result.ok(10,null);
        }
        if(StringUtils.isBlank(member.getEncodedPassword1())){
            return Result.ok(20,null);
        }
        return Result.success(null);
    }

}
