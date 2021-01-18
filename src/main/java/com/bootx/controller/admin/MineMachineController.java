package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.common.Pageable;
import com.bootx.entity.BaseEntity;
import com.bootx.service.MineMachineService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminMineMachineController")
@RequestMapping("/block/admin/mine_machine")
public class MineMachineController extends BaseController {

    @Autowired
    private MineMachineService mineMachineService;

    @PostMapping("/page")
    @JsonView(BaseEntity.PageView.class)
    public Message page(Pageable pageable){
        return Message.success(mineMachineService.findPage(pageable));
    }

}
