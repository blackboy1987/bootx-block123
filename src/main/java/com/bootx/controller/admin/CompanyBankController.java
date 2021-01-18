package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.common.Pageable;
import com.bootx.entity.CompanyBank;
import com.bootx.service.CompanyBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminCompanyBankController")
@RequestMapping("/block/admin/company_bank")
public class CompanyBankController extends BaseController {

    @Autowired
    private CompanyBankService companyBankService;

    @PostMapping("/page")
    public Message page(Pageable pageable){
        return Message.success(companyBankService.findPage(pageable));
    }

    @PostMapping("/save")
    public Message save(CompanyBank companyBank){
        companyBank.setIsEnabled(true);
        companyBankService.save(companyBank);
        return Message.success("ok");
    }

    @PostMapping("/enabled")
    public Message enabled(Long id){
        CompanyBank companyBank = companyBankService.find(id);
        if(companyBank!=null){
            companyBank.setIsEnabled(true);
            companyBankService.update(companyBank);
            return Message.success("操作成功");
        }
        return Message.error("参数错误");
    }

    @PostMapping("/disabled")
    public Message disabled(Long id){
        CompanyBank companyBank = companyBankService.find(id);
        if(companyBank!=null){
            companyBank.setIsEnabled(false);
            companyBankService.update(companyBank);
            return Message.success("操作成功");
        }
        return Message.error("参数错误");
    }
}
