/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: SGfaoZr/OkitVhxevlMbfsWQAGr+gNVV
 */
package com.bootx.security;

import com.bootx.entity.User;
import com.bootx.event.UserLoggedOutEvent;
import com.bootx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * Security - 注销过滤器
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	@Autowired
	private UserService userService;

	/**
	 * 请求前处理
	 * 
	 * @param servletRequest
	 *            ServletRequest
	 * @param servletResponse
	 *            ServletResponse
	 * @return 是否继续执行
	 */
	@Override
	protected boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		User currentUser = userService.getCurrent();
		applicationEventPublisher.publishEvent(new UserLoggedOutEvent(this, currentUser));

		return super.preHandle(servletRequest, servletResponse);
	}

}