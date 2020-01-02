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
 * shiro认证授权处理类
 * last time 2020/1/2
 * add by galgaddott
 *
 */
public class accountRealm extends AuthorizingRealm {
	   //注入Service层
		@Autowired
     private UserServiceImpl userService;
	
	//授权
	//当用户进入权限页面时就会调用此方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		/*
		 * 1.从PrincipalCollection获取用户信息
		 * 2.利用用户的信息来查询角色或权限
		 * 3.创建SimpleAuthenticationInfo对象，并设置其roles属性
		 * 4.返回此对象
		 * 
		 * */
		System.out.println("正在进行授权判断");
	
		//从Session中获取role
		
		User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
		
		
		Set<String> roles = new HashSet<>();
		//1为老师 0为普通用户
		
		if(1==user.getStatus()) {
			
			roles.add("teacher");
		}else {
			
			roles.add("student");
			
		}
		
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		
		return info;
	}

	//进行用户验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//获取账号
		String account = (String) token.getPrincipal();
		
		//进行数据查询密码
		String passWord = userService.selectPassword(account,null);
	
		System.out.println("查出来的密码是:  "+passWord);
		
		//返回认证信息   
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(account,passWord,this.getName());
			

				return simpleAuthenticationInfo;

	}

}
