package com.bootx.controller.api.app.user.v2;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.bootx.common.Page;
import com.bootx.common.Pageable;
import com.bootx.common.Result;
import com.bootx.controller.admin.BaseController;
import com.bootx.entity.Member;
import com.bootx.entity.MemberRank;
import com.bootx.entity.MineMachineOrder;
import com.bootx.security.CurrentUser;
import com.bootx.service.BitCoinAccountService;
import com.bootx.service.MemberRankService;
import com.bootx.service.MemberService;
import com.bootx.service.MineMachineOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController("1")
@RequestMapping("/app/user/v2/order")
public class OrderController extends BaseController {

    @Resource
    private MemberService memberService;
    @Resource
    private MineMachineOrderService mineMachineOrderService;
    @Resource
    private BitCoinAccountService bitCoinAccountService;
    @Resource
    private MemberRankService memberRankService;


    @PostMapping("/page")
    public Result orderPage(HttpServletRequest request, @CurrentUser Member member, Long parsentUserId, Integer excision,Integer page){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        Member current = memberService.find(parsentUserId);
        if(current==null|| current.getParent()!=member){
            return Result.error("信息错误");
        }
        Map<String,Object> data = new HashMap<>();
        Pageable pageable = new Pageable(page,10);
        Page<MineMachineOrder> page1 = mineMachineOrderService.findPage(pageable);
        data.put("list",page1.getContent());
        data.put("page",page1.getPageNumber());
        data.put("excision",excision);
        return Result.success(data);
    }

    @PostMapping("/myPage")
    public Result myPage(HttpServletRequest request, @CurrentUser Member member, Integer excision, Integer page, String orderType, String coinType){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        Map<String,Object> data = new HashMap<>();
        Pageable pageable = new Pageable(page,10);
        Page<MineMachineOrder> page1 = mineMachineOrderService.findPage(pageable,member,excision,orderType,coinType);
        data.put("list",page1.getContent());
        data.put("page",page1.getPageNumber());
        data.put("excision",excision);
        return Result.success(data);
    }

    /**
     * {
     * "id",1,
     * "largess",5,
     * "largessBtc",true,
     * "largessFil",false,
     * "largessHpt",true,
     * "btcPrice",19213,
     * "hptPrice",0.1,
     * "ethPrice",594.35,
     * "filPrice",29.84,
     * "investRate",0,
     * "exchangeRate",6.63,
     * "hbtExchangeRate",10,
     * "enableEthElectricBuy",false,
     * "popusStatus",true,
     * "ipfsActivityStatus",true,
     * "filBlockStatus",true
     * }
     * @param request
     * @param member
     * @param excision
     * @param page
     * @param orderType
     * @param coinType
     * @return
     */
    @PostMapping("/rule")
    public Result rule(HttpServletRequest request, @CurrentUser Member member, Integer excision, Integer page, String orderType, String coinType){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",1);
        data.put("largess",5);
        data.put( "largessBtc",true);
        data.put( "largessFil",false);
        data.put( "largessHpt",true);
        data.put( "btcPrice",19213);
        data.put( "hptPrice",0.1);
        data.put( "ethPrice",594.35);
        data.put( "filPrice",29.84);
        data.put("investRate",0);
        data.put("exchangeRate",6.63);
        data.put("hbtExchangeRate",10);
        data.put("enableEthElectricBuy",false);
        data.put( "popusStatus",true);
        data.put( "ipfsActivityStatus",true);
        data.put("filBlockStatus",true);
        return Result.success(data);
    }

    /**
     * "exchangeRate":6.63,
     * "hbtExchangeRate":10,
     * "orderPrice":600,
     * "orderRmbPrice":1349.81,
     * "electricPrice":43.99,
     * "saleType":0,
     * "productType":99,
     * "type":3
     * @return
     */
    @PostMapping("/payPriceCompute")
    public Result payPriceCompute(BigDecimal exchangeRate, BigDecimal hbtExchangeRate, BigDecimal orderPrice, BigDecimal orderRmbPrice, BigDecimal electricPrice, Integer saleType,Integer productType,Integer type){
        return Result.success(setScale(orderRmbPrice));
    }

    @PostMapping("/payment")
    public Result payment(Long orderId, Integer payType,String coupon){
        MineMachineOrder mineMachineOrder = mineMachineOrderService.find(orderId);

        String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhVrABhPxV2NysbKJ3H8OqjWvREjRTwvqVI75/TkLs78GgBn9euEGwy5N+l9cWwKjHW6CPEVJZV+r0rjj1zsbvV/LB6H7TvVwZUzHanB5/o8kKODcR6ef6k7tIR6wX/sBA89sE/Mfgy1su5jJSvee85ZAxQqoWdJuaBD4uOIeqE1OHClMAYiRAIn7HgJliU/vj2DtSuKDhgy7k3Xqw6S0D3Vjs2nRsHi6oDrrzUGecnG7jgSHBlBfAM/vk0gP+YXyVWKVvfDIkSew05ou1s/Iqf6YuMj1t9xaa8sDjSKKYFLdIwWiEBJHjq7OEeN0J97vibKivmRTmnf6mEq6gW917QIDAQAB";
        String APP_PRIVATE_KEY="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFWsAGE/FXY3Kxsoncfw6qNa9ESNFPC+pUjvn9OQuzvwaAGf164QbDLk36X1xbAqMdboI8RUllX6vSuOPXOxu9X8sHoftO9XBlTMdqcHn+jyQo4NxHp5/qTu0hHrBf+wEDz2wT8x+DLWy7mMlK957zlkDFCqhZ0m5oEPi44h6oTU4cKUwBiJEAifseAmWJT++PYO1K4oOGDLuTderDpLQPdWOzadGweLqgOuvNQZ5ycbuOBIcGUF8Az++TSA/5hfJVYpW98MiRJ7DTmi7Wz8ip/pi4yPW33FprywONIopgUt0jBaIQEkeOrs4R43Qn3u+JsqK+ZFOad/qYSrqBb3XtAgMBAAECggEAEs47aiAB1ssFb52350pbrREpCD4kljsd7pLDGjyjM9PqfMIthtiY9YlgIcC063ixvALLhuhWc19MgcjlCIMjZE9P+GGVaQsfsIllTdNSwN06wjEDn286E/bRZexH/yEMgZcMrLuKZBtRzBq9uD63Qa6cr6zTFOeh/jb2fEwW+rDGONF8V7ZMluVBBbSQFXw9wYLmzhorIhPSBwVlW0iuuFN77fps68pzd4jwOscdEv8NIve7rBV+KQlf3cVrBsdqNzGKKF6UQvJbS79gpeJkqkDPflnd6Xb7dr6CyvtuXUfXoQFxVH4c4PA6Pz728HoTrBBVW5UdopzTGO32FjDswQKBgQDwURVjSFZTtCCG+G1BvpqrrAO1TE8kkE/qf6l0dy1oq91NHgXBDAJOFu2X/q9jAzFhlys12DQWfaYzTuOddcvn0/mP4BDbAJ8kU3F4sOHhky4wwIqYs4w8qaLkXCOoFMx12hA0HUN8u2jEA4i6o7fHmz3C3nUhlRqtlLtONgtyNQKBgQCODqxHwWCn2oPv06iXPhiBnBo7dO/aflNjlcgFCzRfP9sdPxGpURql3/2dRjJI5L/up8UiUUG59JQFh1WiNTa3nIHcGSGrYCcYRSPhfackgk2dkGNAgTapidD92uYjevDpOmoXTkmTEIO4ojyjQt1N/KN7N3O7ayzx/+4SfPHr2QKBgQDhA8r272CFd3+NYUdL6+B/Jc1DEHR6U/XAKFDOkFigTWwlLRtOKqRt4eIOE5a9Ktwt+JGpFQuQk43vNMrDQOzv4Pcn1DF0Hhzksozo6RZXVKxHUcF0QYnpsxymne91DiRzpP7PDSeHvyLVhkmcGHeYGq+1qv4Zt7cHGOBY1xnYkQKBgHruNaXusP7weOYsKqOe58QZWxYCrqFMWks+TZYXyR13+9yGPHliJwZOHIFiQpeT5IfZBR4aZR9PiFx0beVt2UjxFetqkBlmns5A3rj+bIqKfDISxeEHME9sZL+ZEzi25g1eMHq0k7TBQMuhEYpSjYI0UyCIrw0LxRwyVkoHd66ZAoGBAMW5MieZk3wYj7l4GdnZnp3elk4Kukjtfj01d9wi2d6EqPzR9HFwOX0pJBx5Nw5I278pjoNv0W24hNfAlNqgrqzsl1V8b9MYKbjlEBRpsHUVAEIJU6B826lTR+nDQbQl8ZhzH9NxS9n0xUaGzQggzlKF5ic0ZlxqnXMNj/w4bRtN";
        String APP_ID="2021001194618423";
        String CHARSET="UTF-8";

        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("我是测试数据");
        model.setSubject("App支付测试Java");
        model.setOutTradeNo(mineMachineOrder.getSn());
        model.setTimeoutExpress("30m");
        model.setTotalAmount("0.01");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl("http://jlb.i-gomall.com/app/user/v2/order/callback");
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
            return Result.success("支付成功",response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return Result.error("参数错误");

    }

    @GetMapping("/callback")
    public Result callback(Long orderId, Integer payType,String coupon){
        return Result.error("参数错误");

    }



    @PostMapping("/pay")
    public Result pay(HttpServletRequest request, @CurrentUser Member member,Long orderId, Integer payType,Integer coinType,String pass,String coupon){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        MineMachineOrder mineMachineOrder = mineMachineOrderService.find(orderId);
        if(mineMachineOrder==null||mineMachineOrder.getUserId().compareTo(member.getId())!=0){
            return Result.error("订单信息不正确");
        }
        if(!member.isValidCredentials1(pass)){
            return Result.error("资金密码输入不正确");
        }

        BigDecimal amount = bitCoinAccountService.getAmount(member.getId(),coinType);
        if(amount.compareTo(mineMachineOrder.getAmount())<0){
            return Result.error("账户余额不足，支付失败");
        }
        mineMachineOrder.setState(2);
        if(payType==null){
            payType = coinType;
        }
        mineMachineOrder.setPayType(payType);
        mineMachineOrderService.update(mineMachineOrder);
        // 更新推荐人的会员等级
        Member parent = member.getParent();
        if(parent!=null){
            Long orderCount = mineMachineOrderService.countTeam(parent);
            MemberRank memberRank = memberRankService.getLastest(orderCount);
            if(memberRank!=null){
                parent.setMemberRank(memberRank);
                memberService.update(member);
            }
        }


        return Result.success("ok");
    }

    @PostMapping("/detail")
    public Result detail(Long orderId, HttpServletRequest request, @CurrentUser Member member){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期");
        }
        MineMachineOrder mineMachineOrder = mineMachineOrderService.find(orderId);
        return Result.success(mineMachineOrder);
    }
}
