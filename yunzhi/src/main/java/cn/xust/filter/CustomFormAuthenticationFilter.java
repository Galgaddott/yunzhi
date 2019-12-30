package cn.xust.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.xust.utils.ErrorMessage;


public class CustomFormAuthenticationFilter extends FormAuthenticationFilter{
	
	
	
	 @Override
	    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		   //处理options请求
	        if (request instanceof HttpServletRequest) {
	            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
	                return true;
	            }
	        }
	        //如果不是option请求则调用父类方法处理
	        return super.isAccessAllowed(request, response, mappedValue);
	    }
	    
	    @Override
	    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
	        throws Exception {
	    	  //防止乱码，适用于传输JSON数据
	         ((HttpServletResponse) response).setHeader("Content-Type","application/json;charset=UTF-8");
	         PrintWriter out = response.getWriter();
	         out.println(JSONObject.toJSON(new ErrorMessage(0,"您还没有通过认证")));
	         out.flush();
	         out.close();
	         //不跳转到Login
	         return false;
	    }
}
