package cn.xust.service;

import cn.xust.pojo.User;

public interface UserService {
//	//�û���¼
	User login (String account,String password);
//	
//	//�û�ע��
	User register(User user);
	
	//Realm�����û����������ѯ����
	
	String selectPassword(String account,String email);
//	
//	//�ϴ��û�ͷ��
//	
//	boolean uploadAVATARUR(User user);
//	
//	//�޸��û���Ϣ
//	boolean updateUser(User user);
	
	//����רҵ���ֲ�ѯרҵ��id
	int selectMajorId(String majorName);
}
