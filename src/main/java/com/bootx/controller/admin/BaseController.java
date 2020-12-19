/*
 * Copyright 2008-2018 shopxx.net. All rights reserved.
 * Support: localhost
 * License: localhost/license
 * FileId: a9iY+NSWQm+xSi61iz90x/KwOxs9FHPv
 */
package com.bootx.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Controller - 基类
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public class BaseController {

	/**
	 * "验证结果"属性名称
	 */
	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	@Autowired
	private Validator validator;

	/**
	 * 数据验证
	 * 
	 * @param target
	 *            验证对象
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Object target, Class<?>... groups) {
		Assert.notNull(target, "[Assertion failed] - target is required; it must not be null");

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		}
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
		return false;
	}

	/**
	 * 数据验证
	 * 
	 * @param targets
	 *            验证对象
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Collection<Object> targets, Class<?>... groups) {
		Assert.notEmpty(targets, "[Assertion failed] - targets must not be empty: it must contain at least 1 element");

		for (Object target : targets) {
			if (!isValid(target, groups)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 数据验证
	 * 
	 * @param type
	 *            类型
	 * @param property
	 *            属性
	 * @param value
	 *            值
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
		Assert.hasText(property, "[Assertion failed] - property must have text; it must not be null, empty, or blank");

		Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		}
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
		return false;
	}

	/**
	 * 数据验证
	 * 
	 * @param type
	 *            类型
	 * @param properties
	 *            属性
	 * @param groups
	 *            验证组
	 * @return 验证结果
	 */
	protected boolean isValid(Class<?> type, Map<String, Object> properties, Class<?>... groups) {
		Assert.notNull(type, "[Assertion failed] - type is required; it must not be null");
		Assert.notEmpty(properties, "[Assertion failed] - properties must not be empty: it must contain at least 1 element");

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (!isValid(type, entry.getKey(), entry.getValue(), groups)) {
				return false;
			}
		}
		return true;
	}

	protected BigDecimal setScale(BigDecimal amount){
		if (amount != null) {
			return amount.setScale(2, RoundingMode.FLOOR);
		}
		return amount;
	}
}