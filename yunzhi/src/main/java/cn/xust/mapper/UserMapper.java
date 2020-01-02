package cn.xust.mapper;

import org.apache.ibatis.annotations.Param;

import cn.xust.pojo.User;

public interface UserMapper {
	//多参数入参
	User selectSingle(@Param("account") String account,@Param("password") String password);
	
	int  addSingle(User user);
	
	String selectPassword(@Param("account") String account,@Param("email") String email);
	
    int uploadAVATARURL(User user);
    
    int updateUser(User user);
    
    int selectMajorId(@Param("majorName") String majorName);
}
