package cn.xust.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xust.mapper.UserMapper;
import cn.xust.pojo.User;
import cn.xust.service.UserService;
import cn.xust.utils.EncryptKit;
@Transactional
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	
	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
    


	/*先加密在匹配
	 * 成功返回User 失败返回null 
	 * */
	@Override
	public User login(String userName, String passWord,String email) {
		User user = null;
		//进行md5加密
		passWord  = EncryptKit.MD5(passWord);
		     user = userMapper.selectSingle(userName, passWord,email);
		return user;
	}
	
	/*
	 *用户注册
	 *成功返回user
	 *失败返回null 
	 * 
	 * */
	@Override
	public User register(User user) {
		 //先查重
		if( userMapper.selectPassword(user.getAccount(), user.getEmail())!=null )
		  return null;
		else
		{
			//正式进行注册
			if (userMapper.addSingle(user)>0)
				return userMapper.selectSingle(user.getAccount(), user.getPassword(),user.getEmail());
			else
				return null;
			
			
		}
		
		
	}


	/*用于Realm查询密码是否存在 若存在返回 不存在返回null
	 * 
	 * */
	
	@Override
	public String selectPassword(String userName, String email) {
		
		return userMapper.selectPassword(userName, email);
	}

	
	
	/**
	 * 用户上传头像 
	 * 成功返回true
	 * 失败返回false
	 */
	@Override
	public boolean uploadAVATARUR(User user) {
		
		int flag = userMapper.uploadAVATARURL(user);
		
		return flag>0?true:false;
		
	}
	/**
	 * 修改用户信息
	 * 成功返回true
	 * 失败返回false
	 */
	@Override
	public boolean updateUser(User user) {
		
		int flag = userMapper.updateUser(user);
		
		
		return flag>0?true:false;
	}
	
	

}
