package com.bootx.config;

import com.bootx.audit.AuditLogMethodArgumentResolver;
import com.bootx.entity.Admin;
import com.bootx.entity.Member;
import com.bootx.interceptor.CorsInterceptor;
import com.bootx.interceptor.LoginInterceptor;
import com.bootx.security.CurrentUserHandlerInterceptor;
import com.bootx.security.CurrentUserMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public CorsInterceptor corsInterceptor() {
        CorsInterceptor corsInterceptor = new CorsInterceptor();
        return corsInterceptor;
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        return loginInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor())
                .addPathPatterns("/**");

       registry.addInterceptor(loginInterceptor())
                .addPathPatterns("/member/api/**")
                .excludePathPatterns("/member/api/login","/member/api/logout","/member/api/book","/member/api/book/**","/member/api/project","/member/api/project/**","/member/api/tool","/member/api/tool/**","/member/api/resource/**","/member/api/course/**");

        registry.addInterceptor(currentUserHandlerInterceptor1())
                .addPathPatterns("/member/api/**");
        registry.addInterceptor(currentUserHandlerInterceptor())
                .addPathPatterns("/block/admin/**");

    }


    @Bean
    public FixedLocaleResolver localeResolver(){
        FixedLocaleResolver localeResolver = new FixedLocaleResolver();
        return localeResolver;
    }

    @Bean
    public CurrentUserHandlerInterceptor currentUserHandlerInterceptor1() {
        CurrentUserHandlerInterceptor currentUserHandlerInterceptor = new CurrentUserHandlerInterceptor();
        currentUserHandlerInterceptor.setUserClass(Member.class);
        return currentUserHandlerInterceptor;
    }

    @Bean
    public CurrentUserHandlerInterceptor currentUserHandlerInterceptor() {
        CurrentUserHandlerInterceptor currentUserHandlerInterceptor = new CurrentUserHandlerInterceptor();
        currentUserHandlerInterceptor.setUserClass(Admin.class);
        return currentUserHandlerInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers = new ArrayList<>();
        handlerMethodArgumentResolvers.add(currentUserMethodArgumentResolver());
        handlerMethodArgumentResolvers.add(auditLogMethodArgumentResolver());
        resolvers.addAll(handlerMethodArgumentResolvers);
    }

    @Bean
    public CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver(){

        return new CurrentUserMethodArgumentResolver();
    }

    @Bean
    public AuditLogMethodArgumentResolver auditLogMethodArgumentResolver(){

        return new AuditLogMethodArgumentResolver();
    }

}
