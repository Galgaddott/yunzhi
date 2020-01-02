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
	public User login(String account, String password) {
		User user = null;
		//进行md5加密
		password  = EncryptKit.MD5(password);
		     user = userMapper.selectSingle(account, password);
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
		 //先根据学号/工号 和邮箱进行查重
		if( (userMapper.selectPassword(user.getAccount(), user.getEmail())!=null) || user.getMajor()<0 )
		  return null;
		else
		{
			//正式进行注册
			if (userMapper.addSingle(user)>0)
				return userMapper.selectSingle(user.getAccount(), user.getPassword());
			else
				return null;
			
			
		}
		
		
	}


	/*用于Realm查询密码是否存在 若存在返回 不存在返回null
	 * 
	 * */
	
	@Override
	public String selectPassword(String account,String email) {
		
		return userMapper.selectPassword(account,email);
	}

	/**
	 * 根据专业名字查询专业的id
	 * 			查询不到返回-1
	 */
	
	@Override
	public int selectMajorId(String majorName) {
		return userMapper.selectMajorId(majorName);
	}

	
//	
//	/**
//	 * 用户上传头像 
//	 * 成功返回true
//	 * 失败返回false
//	 */
//	@Override
//	public boolean uploadAVATARUR(User user) {
//		
//		int flag = userMapper.uploadAVATARURL(user);
//		
//		return flag>0?true:false;
//		
//	}
//	/**
//	 * 修改用户信息
//	 * 成功返回true
//	 * 失败返回false
//	 */
//	@Override
//	public boolean updateUser(User user) {
//		
//		int flag = userMapper.updateUser(user);
//		
//		
//		return flag>0?true:false;
//	}
	
	

}
