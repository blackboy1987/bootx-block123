package com.bootx.controller.api.app;

import com.bootx.common.Result;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.security.UserAuthenticationToken;
import com.bootx.service.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController("iconApiAuthController")
@RequestMapping("/app/auth")
public class AuthController {

    @Resource
    private MemberService memberService;
    @Resource
    private UserService userService;
    @Resource
    private MemberRankService memberRankService;
    @Resource
    private BitCoinAccountService bitCoinAccountService;
    @Resource
    private MineMachineService mineMachineService;
    @Resource
    private MineMachineOrderService mineMachineOrderService;
    @Resource
    private CacheService cacheService;

    /**
     * 登录
     * @param phone
     * @param password
     * @param umeng
     * @param mobile
     * @param uuid
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result login (String phone, String password, String umeng, String mobile, String uuid, HttpServletRequest request){
        System.out.println(phone);
        Member member = memberService.findByMobile(phone);
        if(member==null){
            return Result.error("手机号输入错误");
        }
        if(!member.isValidCredentials(password)){
            return Result.error("密码错误");
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",member.getId());
        data.put("name",member.getName());
        data.put("userName",member.getUsername());
        data.put("code",member.getId()+"");
        data.put("addPass",member.getEncodedPassword1()==null);
        data.put("activation",true);
        data.put("isAuth",member.getIsAuth());
        //成功： {"type":200,"content":"请求成功","data":{"code":"10051","addPass":true,"id":10051,"userName":"blackboy","activation":true}}
        //失败： {"type":400,"content":"手机号输入错误","date":null,"code":null,"message":null}
        userService.login(new UserAuthenticationToken(Member.class, member.getUsername(), password, false, request.getRemoteAddr()));
        return Result.success(data);
    }



    /**
     * 注册
     * @param enrollVo
     * @return
     */
    @PostMapping("/enroll")
    public Result enroll (EnrollVo enrollVo, HttpServletRequest request){
        boolean mobileExist = memberService.mobileExists(enrollVo.getPhone());
        if(mobileExist){
            return Result.error("手机号存在，注册失败");
        }
        if(!cacheService.smsCodeCacheValidate(enrollVo.getPhone(),enrollVo.getPhonecode())){
            return Result.error("验证码校验失败，注册失败");
        }
        Member member = new Member();
        member.init();
        if(StringUtils.isNotBlank(enrollVo.getExtendCode())){
            Member parent = memberService.findByExtendCode(enrollVo.getExtendCode());
            if(parent==null){
                return Result.error("邀请码不存在，注册失败");
            }else{
                member.setParent(parent);
            }
        }

        member.setExtendCode(memberService.createExtendCode());
        member.setUsername("jlb_"+enrollVo.getPhone());
        member.setName(enrollVo.getName());
        member.setMobile(enrollVo.getPhone());
        member.setPhone(enrollVo.getPhone());
        member.setPassword(enrollVo.password);
        member.setLastLoginIp(request.getRemoteAddr());
        member.setEmail(member.getUsername()+"@qq.com");
        member.setMemberRank(memberRankService.findDefault());
        userService.register(member);
        userService.login(new UserAuthenticationToken(Member.class, member.getUsername(), enrollVo.getPassword(), false, request.getRemoteAddr()));
        /**
         * 创建JLB账户
         */
        ExecutorService singleThreadPool = Executors.newFixedThreadPool(1);
        singleThreadPool.execute(()-> {
            // 初始化各个币的账户
            bitCoinAccountService.initAccount(member);
        });
        singleThreadPool.shutdown();

        // 赠送免费的矿机
        MineMachine mineMachine = mineMachineService.findDefault();
        if(mineMachine!=null){
            mineMachineOrderService.create(member,mineMachine,1,30,null,null,"系统赠送");
        }

        return Result.success("");
    }


    /**
     * 注册
     * @param passVo
     * @return
     */
    @PostMapping("/pass")
    public Result pass (PassVo passVo){
        Member member = memberService.findByMobile(passVo.getPhone());
        if(member==null){
            return Result.error("该手机号暂未注册！");
        }

        if(!cacheService.smsCodeCacheValidate(passVo.getPhone(),passVo.getPhonecode())){
            return Result.error("验证码校验失败");
        }
        member.setPassword(passVo.getPassword());
        memberService.update(member);
        return Result.success("");
    }


    /**
     * 退出
     * @return
     */
    @PostMapping("/out")
    public Result out (){
        userService.logout();
        return Result.success("");
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EnrollVo implements Serializable {

        private String name;
        private String phone;
        private String password;
        private String repassword;
        private String phonecode;
        private String extendCode;
        private String umeng;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRepassword() {
            return repassword;
        }

        public void setRepassword(String repassword) {
            this.repassword = repassword;
        }

        public String getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(String phonecode) {
            this.phonecode = phonecode;
        }

        public String getExtendCode() {
            return extendCode;
        }

        public void setExtendCode(String extendCode) {
            this.extendCode = extendCode;
        }

        public String getUmeng() {
            return umeng;
        }

        public void setUmeng(String umeng) {
            this.umeng = umeng;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PassVo implements Serializable {
        private String phone;
        private String password;
        private String repassword;
        private String phonecode;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRepassword() {
            return repassword;
        }

        public void setRepassword(String repassword) {
            this.repassword = repassword;
        }

        public String getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(String phonecode) {
            this.phonecode = phonecode;
        }
    }
}
