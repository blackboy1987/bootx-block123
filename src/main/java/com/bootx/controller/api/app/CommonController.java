package com.bootx.controller.api.app;


import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.bootx.StsServiceSample;
import com.bootx.common.Result;
import com.bootx.entity.BaseEntity;
import com.bootx.entity.Member;
import com.bootx.entity.Notice;
import com.bootx.entity.NoticeReadLog;
import com.bootx.security.CurrentUser;
import com.bootx.service.AdversiteService;
import com.bootx.service.MemberService;
import com.bootx.service.NoticeReadLogService;
import com.bootx.service.NoticeService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("iconApiCommonController")
@RequestMapping("/app/common")
public class CommonController {

    @Autowired
    private AdversiteService adversiteService;
    /**
     * CacheManager
     */
    private static final Ehcache cache = CacheManager.create().getCache("smsCode");

    @Autowired
    private MemberService memberService;

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeReadLogService noticeReadLogService;

    /**
     * 资讯列表
     * @param member
     * @param request
     * @param page
     * @param userId
     * @param excision
     * @return
     */
    @RequestMapping("/news-pages")
    public Result newsPages(@CurrentUser Member member,HttpServletRequest request,Integer page,Long userId,Integer excision){
        System.out.println("news-readNum:"+userId);
        return Result.success(0);
    }


    @RequestMapping("/news-readNum")
    public Result newsReadNum(Long userId){
        System.out.println("news-readNum:"+userId);
        return Result.success(0);
    }


    /**
     * 一键阅读所有资讯
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/oneKeyRead")
    public Result oneKeyRead(@CurrentUser Member member, HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        List<Notice> notices = noticeService.findAll();
        NoticeReadLog noticeReadLog = noticeReadLogService.findByUserId(member.getId());
        if(noticeReadLog==null){
            noticeReadLog = noticeReadLogService.create(member.getId());
        }
        List<Long> noticeIds = notices.stream().map(BaseEntity::getId).collect(Collectors.toList());
        noticeReadLog.setNoticeIds(noticeIds);
        noticeReadLogService.update(noticeReadLog);
        return Result.success("");

    }

    @PostMapping("/news-read")
    public Result newsRead(@CurrentUser Member member, HttpServletRequest request,Long userId,Long id){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录失效，请重新登录");
        }
        NoticeReadLog noticeReadLog = noticeReadLogService.findByUserId(member.getId());
        if(noticeReadLog==null){
            noticeReadLog = noticeReadLogService.create(member.getId());
        }
        if(!noticeReadLog.getNoticeIds().contains(id)){
            List<Long> noticeIds = noticeReadLog.getNoticeIds();
            noticeIds.add(id);
            noticeReadLog.setNoticeIds(noticeIds);
            noticeReadLogService.update(noticeReadLog);
        }
        return Result.success("");

    }





    @RequestMapping("/adversite")
    public Result adversite(){
        return Result.success(adversiteService.findAll());
    }

    @PostMapping("/code/phone/add_user")
    public Result phoneAddUser(String tel){
        boolean b = memberService.mobileExists(tel);
        if(b){
            return Result.error("手机号码已注册，获取验证码失败");
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


    @PostMapping("/code/phone/forget_pass")
    public Result phoneForgetPass(String tel){
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

    @PostMapping("/online")
    public Result online(Long userId, Boolean online, HttpServletRequest request){
       Member member = memberService.getCurrent(request);
       if(member==null){
           member = memberService.find(userId);
       }
       if(member==null){
           return Result.error("请先登录");
       }

        return Result.success("离线");
    }

    @PostMapping("/oss/sts/token")
    public Result ossStsToken(){
        // data:{"type":200,"content":"","date":
        // {"accessid":"LTAIlRXU0scvCf3d",
        // "policy":"eyJleHBpcmF0aW9uIjoiMjAyMC0xMi0xNVQwODo1NzoyNy43OTlaIiwiY29uZGl0aW9ucyI6W1siY29udGVudC1sZW5ndGgtcmFuZ2UiLDAsMTA0ODU3NjAwMF0sWyJzdGFydHMtd2l0aCIsIiRrZXkiLCJ1cGxvYWQvYXV0aC8yMDIwMTIxNS8iXV19",
        // "signature":"D1OTzU+zBchntkVxM9e/ByveZ9I=",
        // "dir":"upload/auth/20201215/",
        // "host":"https://hashbox.oss-cn-hangzhou.aliyuncs.com",
        // "expire":1608022647
        // },"code":null,"message":null}
        /**
         * Expiration: 2020-12-15T09:00:51Z
         * Access Key Id: STS.NU7hk2RxpLni2fu8mxbwS4u7t
         * Access Key Secret: 2abax6Crat8PT6QSfGCGrVJShsWEFupSrDH1VfbRNpio
         * Security Token: CAIShwJ1q6Ft5B2yfSjIr5aCI9GGv6dR+6yCMEDE3G0tbvh/27Ccljz2IHxIfHNrBe0YvvQwlG5T6vwflqdBS559TE3vScxt4q9L6lsjimQENYrng4YfgbiJREKhaXeirvKwDsz9SNTCAIDPD3nPii50x5bjaDymRCbLGJaViJlhHLt1Mw6jdmgEfpQ0QDFvs8gHL3DcGO+wOxrx+ArqAVFvpxB3hBEYi8G2ydbO7QHF3h+oiL1XhfyoesH+NZI1bMwgCI7vgLwmTMebjn4MsSot3bxtkalJ9Q3AutygGFRL632ESbGIroU1d1MkOPhqRP4a96SmyOcatO3XmpnvzxFEMfHV8JMPxXsuqRqAAYyhjvSu5fFgmFpoma7uUKt78dxNFfmR7IsGEu+5skic+EbOL9/g6FSdALeJSE5w3AHAf91NMwiBKZIKcWG4oCGUZMqbvb69rp+EIqzBXEiehK8jqxYNw4uk802buMydcdwiLAOuE5IaHZ+28yfxL01VBfVtfWn2qPln7+pPTclD
         * RequestId: 71A86A7F-7A5E-415E-AD04-6487EF03243A
         */

        AssumeRoleResponse.Credentials credentials = StsServiceSample.getStsToken();
        if(credentials==null){
            return Result.error("认证失败");
        }
        Map<String,Object> result = new HashMap<>();
        result.put("accessid",credentials.getAccessKeyId());
        result.put("policy","policy");
        result.put("signature",credentials.getSecurityToken());
        result.put("dir","dir");
        result.put("host","http://localhost:80/common/upload");
        result.put("expire",credentials.getExpiration());
        return Result.success(result);
    }

    @PostMapping("/payData")
    public Result payData(Long productId){
        // data:{"type":200,"content":"列表","date":
        // [{"id":4,"payId":4,"productId":null,"open":null,"name":"USDT","type":1,"remark":"USDT","zh_name":null},
        // {"id":5,"payId":5,"productId":null,"open":null,"name":"CNY","type":10,"remark":"CNY","zh_name":null},
        // {"id":6,"payId":6,"productId":null,"open":null,"name":"alipay","type":5,"remark":"支付宝","zh_name":null}
        // ],"code":null,"message":null}
        return Result.success("");
    }


    /**
     * 忘记资金密码的短信发送
     * @return
     */
    @PostMapping("/code/phone/forget_money_pass")
    public Result forgetMoneyPass(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期！");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(member.getMobile(),"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){
            // 发送成功写入到缓存
            cache.put(new Element(member.getMobile(),code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }

    /**
     * 更换绑定的手机号
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/code/phone/up_phone")
    public Result upPhone(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期！");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(member.getMobile(),"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){
            // 发送成功写入到缓存
            cache.put(new Element(member.getMobile(),code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }

    /**
     * 更换绑定的手机号
     * @param member
     * @param request
     * @return
     */
    @PostMapping("/obtain/phone/code")
    public Result phoneCode(@CurrentUser Member member,HttpServletRequest request){
        if(member==null){
            member = memberService.getCurrent(request);
        }
        if(member==null){
            return Result.error("登录信息已过期！");
        }
        String code = CodeUtils.create(6);
        System.out.println(code);
        String result = SmsUtils.send(member.getMobile(),"【聚力国际】 您本次操作的验证码为："+code+"。请再10分钟之内按页面提示提交验证码，如非本人操作，请忽略。！");
        if(StringUtils.equalsIgnoreCase("0",result)){
            // 发送成功写入到缓存
            cache.put(new Element(member.getMobile(),code));
            return Result.success("");
        }else{
            return Result.error("验证码发送失败！");
        }
    }

    @PostMapping("/version")
    public Result version(){

        /**
         * {
         *         "id": 1,
         *         "version": 591,
         *         "versionName": "5.9.1",
         *         "androidUrl": "https://hashbox.oss-cn-hangzhou.aliyuncs.com/download/H5AB7809E.wgt",
         *         "iosUrl": "https://hashbox.oss-cn-hangzhou.aliyuncs.com/download/H5AB7809E.wgt",
         *         "updateAddress": "https://testflight.apple.com/join/61lUFPtO",
         *         "forcedUpdate": true,
         *         "downUpdate": false,
         *         "updateMemo": "",
         *         "createDate": "2018-11-17 20:04:25",
         *         "downUrl":
         *     }
         */
        Map<String,Object> data = new HashMap<>();
        data.put("id",1);
        data.put("version",682);
        data.put("versionName","6.8.2");
        data.put("androidUrl","https://boox-mall.oss-cn-hangzhou.aliyuncs.com/H5B95E076.wgt");
        data.put("iosUrl","https://boox-mall.oss-cn-hangzhou.aliyuncs.com/H5B95E076.wgt");
        data.put("updateAddress","https://boox-mall.oss-cn-hangzhou.aliyuncs.com/H5B95E076.wgt");
        data.put("forcedUpdate",true);
        data.put("downUpdate",false);
        data.put("updateMemo","");
        data.put("createDate",new Date());
        data.put("downUrl","https://boox-mall.oss-cn-hangzhou.aliyuncs.com/H5B95E076.wgt");
        return Result.success(data);
    }

}
