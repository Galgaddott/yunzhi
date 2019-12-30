package cn.xust.utils;

/**
 * 异常信息显示类
 * @author 周蔚
 *
 */
public class ErrorMessage {

	private int code;
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ErrorMessage(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public ErrorMessage() {
	
	}
	
	
}
