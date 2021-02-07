package com.bootx.controller.api.app.user;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.*;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController("appSellController")
@RequestMapping("/app/user/sell")
@CrossOrigin
public class SellController extends BaseController {

    @Resource
    private TransactionRecordService transactionRecordService;
    @Resource
    private BitCoinAccountService bitCoinAccountService;
    @Resource
    private BitCoinAccountMoneyService bitCoinAccountMoneyService;
    @Resource
    private SnService snService;
    @Resource
    private ReceiptAccountService receiptAccountService;

    @PostMapping("/list")
    @JsonView(BaseEntity.ListView.class)
    public Result list(@CurrentUser Member member, Integer page, Integer limit, Integer type, HttpServletRequest request){
        Pageable pageable = new Pageable(page,limit);
        Page<TransactionRecord> page1 = transactionRecordService.findPage(null,null,0,0,null,null,pageable);
        return Result.success(page1);
    }

    @PostMapping("/info")
    @JsonView(BaseEntity.ViewView.class)
    public Result info(@CurrentUser Member member, Long id){
        return Result.success(transactionRecordService.find(id));
    }

    @PostMapping("/submit")
    @JsonView(BaseEntity.ViewView.class)
    public Result submit(@CurrentUser Member member, BigDecimal rmbAmount,BigDecimal jlbCount,Long payInfoId){
        if(jlbCount.compareTo(BigDecimal.ZERO)<0){
            return Result.error("数量填写不正确，挂单失败");
        }

        ReceiptAccount receiptAccount = receiptAccountService.find(payInfoId);
        if(receiptAccount==null || receiptAccount.getUserId().compareTo(member.getId())!=0){
            return Result.error("支付方式选择不合法，挂单失败");
        }

        BitCoinAccount bitCoinAccount = bitCoinAccountService.findByUserIdAndAssetType(member.getId(), 6);
        BitCoinAccountMoney bitCoinAccountMoney =  bitCoinAccountMoneyService.findByBitCoinAccountIdAndUserId(bitCoinAccount, member);
        // 可用币的金额
        BigDecimal subtract = bitCoinAccountMoney.getMoney().subtract(bitCoinAccountMoney.getFrozenMoney());
        BigDecimal realJlbCount = jlbCount.divide(new BigDecimal(0.92),10,BigDecimal.ROUND_DOWN);
        if(subtract.compareTo(realJlbCount)<0){
            return Result.error("账户余额不足，挂单失败");
        }
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setSn(snService.generate(Sn.Type.ORDER));
        transactionRecord.setType(0);
        transactionRecord.setStatus(1);
        transactionRecord.setHandlingFeesRate(new BigDecimal(0.08));
        transactionRecord.setHandlingFees(realJlbCount.multiply(transactionRecord.getHandlingFeesRate()));
        transactionRecord.setRmbAmount(rmbAmount);
        transactionRecord.setSeller(member);
        transactionRecord.setJlbCount(jlbCount);

        transactionRecord.setBankCard(receiptAccount.getBankCard());
        transactionRecord.setTheirBank(receiptAccount.getTheirBank());
        transactionRecord.setArea( receiptAccount.getArea());
        transactionRecord.setName(receiptAccount.getName());
        transactionRecord.setPayType(receiptAccount.getType());



        transactionRecordService.save(transactionRecord);
        // 冻结
        if(bitCoinAccountMoney.getLockMoney()==null){
            bitCoinAccountMoney.setLockMoney(BigDecimal.ZERO);
        }
        bitCoinAccountMoney.setLockMoney(bitCoinAccountMoney.getLockMoney().add(realJlbCount));
        bitCoinAccountMoneyService.update(bitCoinAccountMoney);
        return Result.success("等待系统审核");
    }

    @PostMapping("/record")
    @JsonView(BaseEntity.ListView.class)
    public Result record(@CurrentUser Member member, Integer page, Integer limit, Integer type, HttpServletRequest request){
        Pageable pageable = new Pageable(page,limit);
        Page<TransactionRecord> page1 = transactionRecordService.findPage(member,null,0,null,null,null,pageable);
        return Result.success(page1);
    }

}
