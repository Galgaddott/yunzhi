package cn.xust.service;

import cn.xust.pojo.User;

public interface UserService {
	//�û���¼
	User login (String userName,String passWord,String email);
	
	//�û�ע��
	User register(User user);
	
	//Realm�����û����������ѯ����
	
	String selectPassword(String userName,String email);
	
	//�ϴ��û�ͷ��
	
	boolean uploadAVATARUR(User user);
	
	//�޸��û���Ϣ
	boolean updateUser(User user);
}
