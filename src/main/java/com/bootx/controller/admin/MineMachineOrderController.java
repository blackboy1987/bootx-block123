package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.common.Pageable;
import com.bootx.entity.BaseEntity;
import com.bootx.service.MineMachineOrderService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminMineMachineOrderController")
@RequestMapping("/block/admin/mine_machine_order")
public class MineMachineOrderController extends BaseController {

    @Autowired
    private MineMachineOrderService mineMachineOrderService;

    @PostMapping("/page")
    @JsonView(BaseEntity.PageView.class)
    public Message page(Pageable pageable){
        return Message.success(mineMachineOrderService.findPage(pageable));
    }

}
