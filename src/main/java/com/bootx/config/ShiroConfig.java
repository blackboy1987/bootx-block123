package com.bootx.config;

import com.bootx.entity.Admin;
import com.bootx.entity.Member;
import com.bootx.security.AuthenticationFilter;
import com.bootx.security.AuthorizingRealm;
import com.bootx.security.LogoutFilter;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    private EhCacheManagerFactoryBean ehCacheManagerFactoryBean;


    @Bean
    public ShiroFilterFactoryBean shiroFilter(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter > filters = new HashMap<>();

        filters.put("logout",new LogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(authorizingRealm());
        defaultWebSecurityManager.setCacheManager(shiroCacheManager());
        return defaultWebSecurityManager;
    }

    @Bean
    public AuthorizingRealm authorizingRealm(){
        AuthorizingRealm authorizingRealm = new AuthorizingRealm();
        authorizingRealm.setAuthenticationCacheName("authorization");
        return authorizingRealm;
    }

    @Bean
    public EhCacheManager shiroCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(ehCacheManagerFactoryBean.getObject());
        return ehCacheManager;
    }

    @Bean
    public AuthenticationFilter memberAuthc(){
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setUserClass(Member.class);
        authenticationFilter.setLoginUrl("/member/login");
        authenticationFilter.setSuccessUrl("/member/index");
        return authenticationFilter;
    }


    @Bean
    public AuthenticationFilter adminAuthc(){
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setUserClass(Admin.class);
        authenticationFilter.setLoginUrl("/admin/login");
        authenticationFilter.setSuccessUrl("/admin/index");
        return authenticationFilter;
    }

    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean (){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        methodInvokingFactoryBean.setArguments(securityManager());

        return methodInvokingFactoryBean;
    }
}