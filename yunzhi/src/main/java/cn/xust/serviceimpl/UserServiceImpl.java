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
    


	/*�ȼ�����ƥ��
	 * �ɹ�����User ʧ�ܷ���null 
	 * */
	@Override
	public User login(String userName, String passWord,String email) {
		User user = null;
		//����md5����
		passWord  = EncryptKit.MD5(passWord);
		     user = userMapper.selectSingle(userName, passWord,email);
		return user;
	}
	
	/*
	 *�û�ע��
	 *�ɹ�����user
	 *ʧ�ܷ���null 
	 * 
	 * */
	@Override
	public User register(User user) {
		 //�Ȳ���
		if( userMapper.selectPassword(user.getAccount(), user.getEmail())!=null )
		  return null;
		else
		{
			//��ʽ����ע��
			if (userMapper.addSingle(user)>0)
				return userMapper.selectSingle(user.getAccount(), user.getPassword(),user.getEmail());
			else
				return null;
			
			
		}
		
		
	}


	/*����Realm��ѯ�����Ƿ���� �����ڷ��� �����ڷ���null
	 * 
	 * */
	
	@Override
	public String selectPassword(String userName, String email) {
		
		return userMapper.selectPassword(userName, email);
	}

	
	
	/**
	 * �û��ϴ�ͷ�� 
	 * �ɹ�����true
	 * ʧ�ܷ���false
	 */
	@Override
	public boolean uploadAVATARUR(User user) {
		
		int flag = userMapper.uploadAVATARURL(user);
		
		return flag>0?true:false;
		
	}
	/**
	 * �޸��û���Ϣ
	 * �ɹ�����true
	 * ʧ�ܷ���false
	 */
	@Override
	public boolean updateUser(User user) {
		
		int flag = userMapper.updateUser(user);
		
		
		return flag>0?true:false;
	}
	
	

}
