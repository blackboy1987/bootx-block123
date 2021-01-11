package com.bootx.controller.api.app;

import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.AnnouncementService;
import com.bootx.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/app/announcement")
public class AnnouncementController {

    @Autowired
    private MemberService memberService;
    @Resource
    private AnnouncementService announcementService;

    @GetMapping("/temporary")
    public Result temporary(@CurrentUser Member member, HttpServletRequest request){
        if (member == null) {
            member = memberService.getCurrent(request);
        }
        if (member == null) {
            return Result.error("登录失效，请重新登录");
        }
        return Result.success(announcementService.findAll());
    }
}
