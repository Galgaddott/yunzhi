package cn.xust.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AnonymousFilter;

public class AnonFilter  extends AnonymousFilter{
	
	//处理完这个才进入realm
	 @Override
	    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
	        // Always return true since we allow access to anyone
		 System.out.println("将进入annon");
		 //获取主体
		  Subject subject = SecurityUtils.getSubject();
		 Cookie cookie = new Cookie("flag",subject.getSession().getId().toString());
		 cookie.setPath("/");
		 ((HttpServletResponse) response).addCookie(cookie);
	        return true;
	    }
	
	

}
