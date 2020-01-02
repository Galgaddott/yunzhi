package cn.xust.pojo;

/**
 * 用户实体类
 * add by galgaddott
 * last time 2019/12/30
 *
 */
public class User {
	private int uuid;
	private String account;
	private String password;
	private String nickName;
	private String realName;
	private int major;
	private String majorName;//为了方便映射补加字段 add by galgaddott
	private String email;
	private String avatarUrl;
	private int position;
	private int status;
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
	}
	public String getMajorName() {
		return majorName;
	}
	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public User(int uuid, String account, String password, String nickName, String realName, int major,
			String majorName, String email, String avatarUrl, int position, int status) {
		super();
		this.uuid = uuid;
		this.account = account;
		this.password = password;
		this.nickName = nickName;
		this.realName = realName;
		this.major = major;
		this.majorName = majorName;
		this.email = email;
		this.avatarUrl = avatarUrl;
		this.position = position;
		this.status = status;
	}
	
	
	public User() {
	
	}
	
	
}
