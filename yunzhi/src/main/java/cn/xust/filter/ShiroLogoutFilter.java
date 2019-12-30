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
 *自定义登出过滤器
 *登出前清空此用户的session信息
 * @author 周蔚
 *
 */
public class ShiroLogoutFilter  extends LogoutFilter{

	 @Override
	    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		 
		 //处理options请求
	        if (request instanceof HttpServletRequest) {
	            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
	                return true;
	            }
	        }
	        //清除HTTPSession的用户信息
	        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
	        HttpSession session = httpServletRequest.getSession();
	        session.invalidate();

	        return super.preHandle(httpServletRequest, response);
	    }
	 
	 //登出返回相关信息
	 @Override
	 protected void issueRedirect(ServletRequest request, ServletResponse response, String redirectUrl) throws Exception {
		 //防止乱码，适用于传输JSON数据
         ((HttpServletResponse) response).setHeader("Content-Type","application/json;charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.println(JSONObject.toJSON(new ErrorMessage(1,"登出成功！")));
         out.flush();
         out.close();
     
	    }
	
	
}
