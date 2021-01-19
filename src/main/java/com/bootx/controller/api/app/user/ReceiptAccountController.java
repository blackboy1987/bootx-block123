package com.bootx.controller.api.app.user;

import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.security.CurrentUser;
import com.bootx.service.ReceiptAccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/app/user/receiptAccount")
public class ReceiptAccountController extends BaseController {

    @Resource
    private ReceiptAccountService receiptAccountService;

    @PostMapping("/list")
    public Result list(@CurrentUser Member member){
        return Result.success(receiptAccountService.findListByUserId(member.getId()));
    }

}
