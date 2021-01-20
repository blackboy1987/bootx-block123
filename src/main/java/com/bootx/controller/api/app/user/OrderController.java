package com.bootx.controller.api.app.user;

import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.entity.MineMachineOrder;
import com.bootx.service.MemberService;
import com.bootx.service.MineMachineOrderService;
import com.bootx.service.MineMachineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController("appUserOrderController")
@RequestMapping("/app/user/order")
public class OrderController {

    @Autowired
    private MineMachineOrderService mineMachineOrderService;
    @Autowired
    private MineMachineService mineMachineService;
    @Autowired
    private MemberService memberService;

   @PostMapping("/create")
    public Result create(Long productId, Integer quantity, Integer day, HttpServletRequest request){
       Member member = memberService.getCurrent(request);
       if(member==null){
           return Result.error("登录信息已过期");
       }
       MineMachine mineMachine = mineMachineService.find(productId);

       MineMachineOrder mineMachineOrder = mineMachineOrderService.create(member,mineMachine,quantity,day,0,null,"矿机购买");
       Map<String,Object> map = new HashMap<>();
       map.put("id",mineMachineOrder.getId());
       map.put("sn",mineMachineOrder.getSn());
       return Result.success(map);
    }

    @PostMapping("/detail")
    public Result detail(Long orderId, HttpServletRequest request){
        Member member = memberService.getCurrent(request);
        if(member==null){
            return Result.error("登录信息已过期");
        }
        MineMachineOrder mineMachineOrder = mineMachineOrderService.find(orderId);
        return Result.success(mineMachineOrder);
    }
}
