package com.bootx.controller.api.app.user;

import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.Sn;
import com.bootx.entity.TransactionRecord;
import com.bootx.security.CurrentUser;
import com.bootx.service.SnService;
import com.bootx.service.TransactionRecordService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController("appBuyController")
@RequestMapping("/app/user/buy")
public class BuyController extends BaseController {

    @Resource
    private TransactionRecordService transactionRecordService;
    @Resource
    private SnService snService;


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
    public Result submit(@CurrentUser Member member, Long id,String image){
        TransactionRecord transactionRecord = transactionRecordService.find(id);
        if(transactionRecord==null){
            return Result.error("挂单不存在，购买失败");
        }
        if(transactionRecord.getType()!=0){
            return Result.error("挂单类型不是购买，购买失败");
        }
        if(transactionRecord.getStatus()!=0){
            return Result.error("挂单状态不是待交易中，购买失败");
        }
        if(transactionRecord.getSeller()==member){
           // return Result.error("不能购买自己的卖出单");
        }
        if(StringUtils.isBlank(image)){
            return Result.error("请上传交易凭证");
        }
        transactionRecord.setSn(snService.generate(Sn.Type.ORDER));
        transactionRecord.setBuyDate(new Date());
        transactionRecord.setBuyer(member);
        transactionRecord.setStatus(2);
        transactionRecord.setImage(image);
        transactionRecordService.update(transactionRecord);
        return Result.success("等待系统审核");
    }

    @PostMapping("/record")
    @JsonView(BaseEntity.ListView.class)
    public Result record(@CurrentUser Member member, Integer page, Integer limit, Integer type, HttpServletRequest request){
        Pageable pageable = new Pageable(page,limit);
        Page<TransactionRecord> page1 = transactionRecordService.findPage(null,member,0,null,null,null,pageable);
        return Result.success(page1);
    }

}
