package com.bootx.controller.api.common;


import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.service.MemberService;
import com.bootx.util.CodeUtils;
import com.bootx.util.SmsUtils;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("iconCommonController")
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private MemberService memberService;

    /**
     * CacheManager
     */
    private static final Ehcache cache = CacheManager.create().getCache("smsCode");

    /**
     * 不在常用设备上登录 获取验证码
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/obtain/phone/code")
    public Result phoneCode(String tel){
        Member member = memberService.findByMobile(tel);
        if(member==null){
            return Result.error("该手机号暂未注册！");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(tel,"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){
            // 发送成功写入到缓存
            cache.put(new Element(tel,code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }



    @PostMapping("payData")
    public Result payData(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",6);
        map.put("payId",6);
        map.put("productId",null);
        map.put("open",null);
        map.put("name","alipay");
        map.put("type",5);
        map.put("remark","支付宝");
        map.put("zh_name",null);
        list.add(map);

        Map<String,Object> map1 = new HashMap<>();
        map1.put("id",5);
        map1.put("payId",5);
        map1.put("productId",null);
        map1.put("open",null);
        map1.put("name","CNY");
        map1.put("type",10);
        map1.put("remark","CNY");
        map1.put("zh_name",null);
        list.add(map1);


        return Result.success(list);
    }

    @PostMapping("/oss/sts/auth")
    public Result auth(String key){
        return Result.success(key);
    }
}
