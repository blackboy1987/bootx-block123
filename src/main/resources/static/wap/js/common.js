var Common = {
	version: 1,
	siteName: '算力蜂',
   // basePath: "http://47.104.242.11",
    basePath:"http://api.suanlifeng.com",
   // basePath: "http://47.104.109.58:8910",
   //  basePath: "http://api.test.suanlifeng.com",
    // muipost提交
    muipost: function (url, params, callback, errCallBack) {
   		mui.ajax(Common.basePath + url, {
            data: params,
            dataType: 'json', //服务器返回json格式数据
            type: 'POST',
            timeout: 10000, //超时时间设置为10秒；
            success: function (data) {
            	try {
                	mui.hideLoading();
                } catch(err) {
                }
				if (data.type == 200) {
            		callback(data);
            	} else if (data.type == 0) {
            		//登录过期
            		plus.runtime.restart();
            	} else if (data.type == 400) {
            		// 400 error
            		mui.toast(data.content);
            	} else {
            		// 500 warn
            		errCallBack(data);
            	}
            },
            error: function (xhr, type, errorThrown) {
                //异常处理；
            	try {
                	mui.hideLoading();
                } catch(err) {
                }
                mui.toast("网络异常，无法访问");
            }
        });
    },
    // 判断是否为空
    isNull: function(str) {
    	if (str == null || str == '') {
    		return true;
    	} else {
    		return false;
    	}
    },
    //判断是否相同
    isEqual:function(str1,str2){
    	if (str1 != str2) {
    		return true;
    	} else {
    		return false;
    	}
    },
    // 手机号码验证
    isPhoneAvailable: function(phone) {
    	var reg = /^1[3-9]\d{9}$/;
        if (reg.test(phone)) {
            return false;
        } else {
            return true;
        }
    },
    // 邮箱验证
    isEmail: function(obj) {
    	var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
    	if(!reg.test(obj)){
    		return true;
    	} else {
    		return false;
    	}
    },
    // 密码验证：支持数字，英文字母和下划线
    numletter_: function(obj) {
    	var reg = "^[0-9a-zA-Z\_]+$";
    	if(!reg.test(obj)){
    		return true;
    	} else {
    		return false;
    	}
    },
    // 密码验证：支持数字，英文字母
    numletter: function(obj) {
    	var reg = "^[0-9a-zA-Z]+$";
    	if(!reg.test(obj)){
    		return true;
    	} else {
    		return false;
    	}
    },
    //资金密码验证：6位数字组合
    isCash:function(obj) {
    	var reg = /^\d{6}$/;
    	if(!reg.test(obj)){
    		return true;
    	} else {
    		return false;
    	}
    },
    // 密码校验
    passWord: function(obj) {
    	var reg = /^[a-zA-Z\d_]{8,}$/;
    	if(!reg.test(obj)){
    		return true;
    	} else {
    		return false;
    	}
    },
    // 只能输入字母
    english: function(obj) {
    	var reg = "[a-zA-Z]";
    	if(!reg.test(obj)){
    		return true;
    	} else {
    		return false;
    	}
    },
    // 只能输中文
	china: function(obj) {
		var reg = "^[\u0391-\uFFE5]+$";
		if(!reg.test(obj)){
    		return true;
    	} else {
    		return false;
    	}
	},
	// 金额：格式定义为带小数的正数，小数点最多有三位
	money: function(obj) {
		var reg = "^[0-9]+[\.][0-9]{0,3}$";
		if(!reg.test(obj)){
    		return true;
    	} else {
    		return false;
    	}
	},
	//货币：格式定义为保留小数点后二位
	moneyFormat:function(number) {
		// 默认2位小数
		return Common.moneyFormatByDecimals(number, 2);
	},
	moneyFormatByDecimals:function(number, decimals) {
		// var decimals = 6;
		var dec_point = ".";
		var thousands_sep = ",";
		/*
		 * 参数说明：
		 * number：要格式化的数字
		 * decimals：保留几位小数
		 * dec_point：小数点符号
		 * thousands_sep：千分位符号
		 * */
		number = (number + '').replace(/[^0-9+-Ee.]/g, '');
		var n = !isFinite(+number) ? 0 : +number;
	
		var prec = !isFinite(+decimals) ? 0 : Math.abs(decimals);
		var s = '';
		var toFixedFix = function(n, prec) {
			var k = Math.pow(10, prec);
			var temp = parseFloat(n * k).toFixed(2);
			return '' + temp / k;
		};
		
		s = (prec ? toFixedFix(n, prec) : '' + Math.floor(n)).split('.');
		
		var re = /(-?\d+)(\d{3})/;
		while (re.test(s[0])) {
			s[0] = s[0].replace(re, "$1" + thousands_sep + "$2");
		}
	
		if ((s[1] || '').length < prec) {
			s[1] = s[1] || '';
			s[1] += new Array(prec - s[1].length + 1).join('0');
		}
		return s.join(dec_point);
		
	},
	// 时间格式化
	dateFtt: function(fmt, date) {
		var o = {   
		    "M+" : date.getMonth()+1,                 //月份   
		    "d+" : date.getDate(),                    //日   
		    "h+" : date.getHours(),                   //小时   
		    "m+" : date.getMinutes(),                 //分   
		    "s+" : date.getSeconds(),                 //秒   
			"q+" : Math.floor((date.getMonth()+3)/3), //季度   
		    "S"  : date.getMilliseconds()             //毫秒   
	  	};
  		if(/(y+)/.test(fmt)) {   
    		fmt = fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
    	}
  		
  		for(var k in o) {
    		if(new RegExp("("+ k +")").test(fmt)) {  
  				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
			} 
		} 
		return fmt;   
	},
	// 时间加减
	dateAdd: function(days, date) {
		if (days == undefined || days == '') {
            days = 1;
        }
		var date = new Date(date);
        date.setDate(date.getDate() + days);
		return date;
	}
};

// JQuery方法定制
(function ($) {
    $.ajaxSetup({
        type: 'POST',
        async: true,
        dataType: "json",
        timeout: 30000
    });

    /**
     * 获取form表单数据
     */
    $.fn.getFormData = function (isValid) {
        var fieldElem = $(this).find('input,select,textarea'); //获取所有表单域
        var data = {};
        layui.each(fieldElem, function (index, item) {
            if (!item.name) return;
            if (/^checkbox|radio$/.test(item.type) && !item.checked) return;
            var value = item.value;
            if (item.type == "checkbox") {//如果多选
                if (data[item.name]) {
                    value = data[item.name] + "," + value;
                }
            }
            if (isValid) {
                //如果为true,只需要处理有数据的值
                if (!$.isEmpty(value)) {
                    data[item.name] = value;
                }
            }
            else {
                data[item.name] = value;
            }
        });
        return data;
    };

    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(
            function () {
                if (serializeObj[this.name]) {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    } else {
                        serializeObj[this.name] = [
                            serializeObj[this.name], this.value];
                    }
                } else {
                    serializeObj[this.name] = this.value;
                }
            });
        return serializeObj;
    };


    $.extend({
        //非空判断
        isEmpty: function (value) {
            if (value === null || value == undefined || value === '') {
                return true;
            }
            return false;
        },
        //获取对象指
        result: function (object, path, defaultValue) {
            var value = "";
            if (!$.isEmpty(object) && $.isObject(object) && !$.isEmpty(path)) {
                var paths = path.split('.');
                var length = paths.length;
                $.each(paths, function (i, v) {
                    object = object[v];
                    if (length - 1 == i) {
                        value = object;
                    }
                    if (!$.isObject(object)) {
                        return false;
                    }
                })

            }

            if ($.isEmpty(value) && !$.isEmpty(defaultValue)) {
                value = defaultValue;
            }
            return value;
        },
        //判断是否obj对象
        isObject: function (value) {
            var type = typeof value;
            return value != null && (type == 'object' || type == 'function');
        },
        //是否以某个字符开头
        startsWith: function (value, target) {
            return value.indexOf(target) == 0;
        },
        //设置sessionStorage
        setSessionStorage: function (key, data) {
            sessionStorage.setItem(key, data);
        },
        //获取sessionStorage
        getSessionStorage: function (key) {
            return sessionStorage.getItem(key) == null ? "" : sessionStorage.getItem(key);
        },
        //删除sessionStorage
        removeSessionStorage: function (key) {
            sessionStorage.removeItem(key);
        },
        //清除sessionStorage
        clearSessionStorage: function () {
            sessionStorage.clear();
        },
        uuid: function () {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        },
        getUrlParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
    });

}(jQuery));