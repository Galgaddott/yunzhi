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
		   //����options����
	        if (request instanceof HttpServletRequest) {
	            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
	                return true;
	            }
	        }
	        //�������option��������ø��෽������
	        return super.isAccessAllowed(request, response, mappedValue);
	    }
	    
	    @Override
	    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
	        throws Exception {
	    	  //��ֹ���룬�����ڴ���JSON����
	         ((HttpServletResponse) response).setHeader("Content-Type","application/json;charset=UTF-8");
	         PrintWriter out = response.getWriter();
	         out.println(JSONObject.toJSON(new ErrorMessage(0,"����û��ͨ����֤")));
	         out.flush();
	         out.close();
	         //����ת��Login
	         return false;
	    }
}
