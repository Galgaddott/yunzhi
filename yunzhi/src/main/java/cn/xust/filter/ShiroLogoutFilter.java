package cn.xust.filter;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;

import cn.xust.utils.ErrorMessage;

/**
 * 
 *�Զ���ǳ�������
 *�ǳ�ǰ��մ��û���session��Ϣ
 * @author ��ε
 *
 */
public class ShiroLogoutFilter  extends LogoutFilter{

	 @Override
	    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		 
		 //����options����
	        if (request instanceof HttpServletRequest) {
	            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
	                return true;
	            }
	        }
	        //���HTTPSession���û���Ϣ
	        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
	        HttpSession session = httpServletRequest.getSession();
	        session.invalidate();

	        return super.preHandle(httpServletRequest, response);
	    }
	 
	 //�ǳ����������Ϣ
	 @Override
	 protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
		 //��ֹ���룬�����ڴ���JSON����
         ((HttpServletResponse) response).setHeader("Content-Type","application/json;charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println(JSONObject.toJSON(new ErrorMessage(1,"�ǳ��ɹ���")));
         out.flush();
         out.close();
     
	    }
	
	
}
