package com.bootx.common;

import com.bootx.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * 消息
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
public class Result {

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private Integer code;

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private Integer type;

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private String content;

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private String message;

	@JsonView({BaseEntity.ViewView.class, BaseEntity.ListView.class, BaseEntity.EditView.class, BaseEntity.BaseView.class})
	private Object date;


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getDate() {
		return date;
	}

	public void setDate(Object date) {
		this.date = date;
	}

	/**
	 * 构造方法
	 */
	public Result() {
	}

	public Result(Integer type,Integer code, String message,String content, Object date) {
		this.code = code;
		this.type = type;
		this.message = message;
		this.content = content;
		this.date = date;
	}

	public static Result success(String message, Object data) {
		return new Result(200,0, message,message, data);
	}

	public static Result ok (Integer type,Integer code, String message,String content, Object date) {
		return new Result(type,code,message,content,date);
	}
	public static Result ok (Integer type, String message,String content, Object date) {
		return new Result(type,200,message,content,date);
	}
	public static Result ok (Integer type, Integer code) {
		return new Result(type,code,null,null,null);
	}

	public static Result success(Object data) {
		return new Result(200,0, "请求成功","请求成功", data);
	}


	public static Result error(String message) {
		return new Result(400,null, message,message, null);
	}
}