package com.bootx.controller.api;


import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.service.CacheService;
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

import java.util.*;

@RestController("iconCommonController")
@RequestMapping("/common")
public class Common1Controller {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CacheService cacheServicea;
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

    @PostMapping("/verifier/phone/code")
    public Result verifierPhoneCode(String phone,String phonecode){
        Member member = memberService.findByMobile(phone);
        if(member==null){
            return Result.error("该手机号暂未注册！");
        }
        if(!cacheServicea.smsCodeCacheValidate(phone,phonecode)){
            return Result.error("短信验证码错误或已失效，请重新输入");
        }
        return Result.success("");
    }

    @PostMapping("payData")
    public Result payData(){
        /**
         * {"id":6,"payId":6,"productId":null,"open":null,"name":"alipay","type":5,"remark":"支付宝","zh_name":null}
         */
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
        return Result.success(list);
    }

}
