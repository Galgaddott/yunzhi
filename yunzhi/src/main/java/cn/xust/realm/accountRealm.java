package cn.xust.realm;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.xust.pojo.User;
import cn.xust.serviceimpl.UserServiceImpl;
import cn.xust.utils.EncryptKit;
/**
 * shiro��֤��Ȩ������
 * last time 2020/1/2
 * add by galgaddott
 *
 */
public class accountRealm extends AuthorizingRealm {
	   //ע��Service��
		@Autowired
     private UserServiceImpl userService;
	
	//��Ȩ
	//���û�����Ȩ��ҳ��ʱ�ͻ���ô˷���
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		/*
		 * 1.��PrincipalCollection��ȡ�û���Ϣ
		 * 2.�����û�����Ϣ����ѯ��ɫ��Ȩ��
		 * 3.����SimpleAuthenticationInfo���󣬲�������roles����
		 * 4.���ش˶���
		 * 
		 * */
		System.out.println("���ڽ�����Ȩ�ж�");
	
		//��Session�л�ȡrole
		
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
		
		
		Set<String> roles = new HashSet<>();
		//1Ϊ��ʦ 0Ϊ��ͨ�û�
		
		if(1==user.getStatus()) {
			
			roles.add("teacher");
		}else {
			
			roles.add("student");
			
		}
		
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		
		return info;
	}

	//�����û���֤
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//��ȡ�˺�
		String account = (String) token.getPrincipal();
		
		//�������ݲ�ѯ����
		String passWord = userService.selectPassword(account,null);
	
		System.out.println("�������������:  "+passWord);
		
		//������֤��Ϣ   
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(account,passWord,this.getName());
			

				return simpleAuthenticationInfo;

	}

}
