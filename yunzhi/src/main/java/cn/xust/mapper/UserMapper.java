package cn.xust.mapper;

import org.apache.ibatis.annotations.Param;

import cn.xust.pojo.User;

public interface UserMapper {
	//多参数入参
	User selectSingle(@Param("userName") String userName,@Param("passWord") String passWord,@Param("email") String email);
	
	int  addSingle(User user);
	
	String selectPassword(@Param("userName") String userName,@Param("email") String email);
	
    int uploadAVATARURL(User user);
    
    int updateUser(User user);
}
