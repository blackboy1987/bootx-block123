package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.common.Pageable;
import com.bootx.entity.AccountLog;
import com.bootx.service.AccountLogService;
import com.bootx.service.BitCoinAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminAccountLogController")
@RequestMapping("/block/admin/account_log")
public class AccountLogController extends BaseController {

    @Autowired
    private AccountLogService accountLogService;
    @Autowired
    private BitCoinAccountService bitCoinAccountService;

    @PostMapping("/page")
    public Message page(Pageable pageable){
        return Message.success(accountLogService.findPage(pageable));
    }
    @PostMapping("/pass")
    public Message pass(Long id){
        AccountLog accountLog = accountLogService.find(id);
        if(accountLog!=null&&accountLog.getState()==0){
            accountLog.setState(1);
            accountLogService.update(accountLog);
            bitCoinAccountService.addMoney(accountLog);
            return Message.success("操作成功");
        }
        return Message.error("信息记录错误");
    }
    @PostMapping("/reject")
    public Message reject(Long id){
        AccountLog accountLog = accountLogService.find(id);
        if(accountLog!=null&&accountLog.getState()==0){
            accountLog.setState(2);
            accountLogService.update(accountLog);
            return Message.success("操作成功");
        }
        return Message.error("信息记录错误");
    }
}
