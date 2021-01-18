package com.bootx.controller.admin;

import com.bootx.common.Message;
import com.bootx.common.Pageable;
import com.bootx.entity.BitCoinType;
import com.bootx.service.BitCoinTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("adminBitCoinTypeController")
@RequestMapping("/block/admin/bit_coin_type")
public class BitCoinTypeController extends BaseController {

    @Autowired
    private BitCoinTypeService bitCoinTypeService;

    @PostMapping("/page")
    public Message page(Pageable pageable){
        return Message.success(bitCoinTypeService.findPage(pageable));
    }

    @PostMapping("/save")
    public Message save(BitCoinType bitCoinType){
        bitCoinType.setEnabled(true);
        bitCoinTypeService.save(bitCoinType);
        return Message.success("ok");
    }

    @PostMapping("/enabled")
    public Message enabled(Long id){
        BitCoinType bitCoinType = bitCoinTypeService.find(id);
        if(bitCoinType!=null){
            bitCoinType.setEnabled(true);
            bitCoinTypeService.update(bitCoinType);
            return Message.success("操作成功");
        }
        return Message.error("参数错误");
    }

    @PostMapping("/disabled")
    public Message disabled(Long id){
        BitCoinType bitCoinType = bitCoinTypeService.find(id);
        if(bitCoinType!=null){
            bitCoinType.setEnabled(false);
            bitCoinTypeService.update(bitCoinType);
            return Message.success("操作成功");
        }
        return Message.error("参数错误");
    }
}
