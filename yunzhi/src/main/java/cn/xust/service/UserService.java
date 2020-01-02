package cn.xust.service;

import cn.xust.pojo.User;

public interface UserService {
//	//用户登录
	User login (String account,String password);
//	
//	//用户注册
	User register(User user);
	
	//Realm根据用户名或邮箱查询密码
	
	String selectPassword(String account,String email);
//	
//	//上传用户头像
//	
//	boolean uploadAVATARUR(User user);
//	
//	//修改用户信息
//	boolean updateUser(User user);
	
	//根据专业名字查询专业的id
	int selectMajorId(String majorName);
}
