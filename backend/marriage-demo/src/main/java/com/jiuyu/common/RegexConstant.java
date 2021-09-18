package com.jiuyu.common;

/**
 * <p>Title: RegexConstant</p>
 * <p>Description: </p>
 * @author he_jiebing@jiuyv.com
   @date   2020年8月27日 下午12:47:19
 */

public class RegexConstant {
	
	
	private RegexConstant() {
		throw new AssertionError("No java.nio.charset.StandardCharsets instances for you!");
	}

	/**
	 * 中国手机正则表达式
	 */
	public static final String CHINA_PHONE_REGEX = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
	/**
	 * 邮箱正则表达式
	 */
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
	/**
	 * 图片类型
	 */
	public static final String IMG_TYPE = "jpg,jpeg,png,bmp";
	/**
	 * 用户名正则表达式 :英文字母，至少包含一个英文字母，长度6到25位
	 */
	public static final String NICKNAME_REGEX = "^(?=.*[A-Za-z])[\\da-zA-Z]{6,25}$";
	/**
	 * 用户密码正则表达式:至少包含数字，大小写字母，特殊字符，长度 8到16位
	 */
	public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,16}";
	/**
	 * 短信验证码表达式
	 */
	public static final String SMS_CODE_REGEX = "^\\d{6}$";
	
	/**
	 * 文档类型
	 */
	public static final String DOC_TYPE = "doc,docx,pdf";
	
	/**
	 * 智能合约压缩包类型
	 */
	public static final String CONTRACT_TYPE = "zip";

	/**
	 * 接口返回时不打印消息体（消息体太大，会占满log窗口）的方法
	 */
	public static final String LOG_FILTER_METHOD_REGEX = "AddressInfoController.getResponseAddressInfo.*";
}
