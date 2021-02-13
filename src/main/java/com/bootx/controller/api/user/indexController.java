package com.bootx.controller.api.user;

import com.bootx.common.Result;
import com.bootx.entity.ArticleCategory;
import com.bootx.entity.Member;
import com.bootx.entity.MineMachine;
import com.bootx.security.CurrentUser;
import com.bootx.service.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController("iconUserController")
@RequestMapping("/user")
public class indexController {

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

    @RequestMapping("/electric/page")
    public Result moneyList(@CurrentUser Member member, HttpServletRequest request){
        return Result.success("没有更多数据了");
    }

    /**
     * 注册
     * @param enrollVo
     * @return
     */
    @PostMapping("/registerData")
    public Result registerData (EnrollVo enrollVo){
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
                member.setGrade(parent.getGrade()+1);
            }
        }else{
            member.setGrade(0);
        }

        member.setExtendCode(memberService.createExtendCode());
        member.setUsername("jlb_"+enrollVo.getPhone());
        member.setName(enrollVo.getName());
        member.setMobile(enrollVo.getPhone());
        member.setPhone(enrollVo.getPhone());
        member.setPassword(enrollVo.password);
        member.setLastLoginIp("0:0:0:1");
        member.setEmail(member.getUsername()+"@qq.com");
        member.setMemberRank(memberRankService.findDefault());
        member.setAdClickCount(0L);
        setValue(member);
        userService.register(member);
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

    private void setValue(Member member) {
        Member parent = member.getParent();
        if (parent != null) {
            member.setTreePath(parent.getTreePath() + parent.getId() + ArticleCategory.TREE_PATH_SEPARATOR);
        } else {
            member.setTreePath(ArticleCategory.TREE_PATH_SEPARATOR);
        }
        member.setGrade(member.getParentIds().length);
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

}
