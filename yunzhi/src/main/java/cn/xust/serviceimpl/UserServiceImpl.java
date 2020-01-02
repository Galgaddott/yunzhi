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
	public User login(String account, String password) {
		User user = null;
		//����md5����
		password  = EncryptKit.MD5(password);
		     user = userMapper.selectSingle(account, password);
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
		 //�ȸ���ѧ��/���� ��������в���
		if( (userMapper.selectPassword(user.getAccount(), user.getEmail())!=null) || user.getMajor()<0 )
		  return null;
		else
		{
			//��ʽ����ע��
			if (userMapper.addSingle(user)>0)
				return userMapper.selectSingle(user.getAccount(), user.getPassword());
			else
				return null;
			
			
		}
		
		
	}


	/*����Realm��ѯ�����Ƿ���� �����ڷ��� �����ڷ���null
	 * 
	 * */
	
	@Override
	public String selectPassword(String account,String email) {
		
		return userMapper.selectPassword(account,email);
	}

	/**
	 * ����רҵ���ֲ�ѯרҵ��id
	 * 			��ѯ��������-1
	 */
	
	@Override
	public int selectMajorId(String majorName) {
		return userMapper.selectMajorId(majorName);
	}

	
//	
//	/**
//	 * �û��ϴ�ͷ�� 
//	 * �ɹ�����true
//	 * ʧ�ܷ���false
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
//	 * �޸��û���Ϣ
//	 * �ɹ�����true
//	 * ʧ�ܷ���false
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
