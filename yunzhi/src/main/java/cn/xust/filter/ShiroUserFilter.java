package cn.xust.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import cn.xust.utils.ErrorMessage;
public class ShiroUserFilter extends UserFilter {

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
	         out.println(JSONObject.toJSON(new ErrorMessage(0,"����û��ͨ����֤���¼ƾ֤�ѹ���")));
	         out.flush();
	         out.close();
	         //����ת��Login
	         return false;
	    }

	
}
