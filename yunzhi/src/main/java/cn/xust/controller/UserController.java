package cn.xust.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import cn.xust.pojo.User;
import cn.xust.serviceimpl.UserServiceImpl;
import cn.xust.utils.Code;
import cn.xust.utils.EncryptKit;
import cn.xust.utils.Message;
import cn.xust.utils.UploadUtils;
import cn.xust.utils.Verification;

/**
 *   用户控制器
 * add by galgaddott
 * last time 2020/1/1
 *
 */
	@Controller
	@RequestMapping("/user")
	public class UserController {
	    
		@Autowired
	    private UserServiceImpl userService;
		//在spring中配置的邮件发送的bean
		@Autowired
		private JavaMailSender javaMailSender;

		@RequestMapping(value="login",method= {RequestMethod.POST,RequestMethod.GET})
		@ResponseBody
		 public Map<String ,Object> Login(@RequestBody Map<String ,Object> str,HttpServletResponse response) {

			Map<String ,Object> map =new HashMap<String ,Object>();
			//获取主体
			 Subject subject = SecurityUtils.getSubject();
			 if(!subject.isAuthenticated() && !subject.isRemembered()) {
				 System.out.println("正在进入登录区");
					//封装账户信息 对密码先进行md5加密
						UsernamePasswordToken token = new UsernamePasswordToken((String)str.get("account"),EncryptKit.MD5((String)str.get("password")));
						
						try {
							//设置记住我
							token.setRememberMe(true);
							
							subject.login(token); //进入realm判断
							
							User user = userService.login((String)str.get("account"),(String)str.get("passWord"));
							if(user != null) {
								
								//存入session 供服务端使用 Session - user
								subject.getSession().setAttribute(subject.getSession().getId().toString(), user);
								map.put("code",Code.Login_SUCCESS.getCode());
								map.put("message",user);
								map.put("session",subject.getSession().getId());
								
							}else
							{
								 map.put("code",Code.Login_FAIL.getCode());
						         map.put("message",Code.Login_FAIL.getMsg());
							}
							
						}
						catch(AuthenticationException e) {
							 map.put("code",Code.Login_FAIL.getCode());
					         map.put("message",Code.Login_FAIL.getMsg());
						}
					 
				 }
			 else {
				 
				    map.put("code",Code.Login_SUCCESS.getCode());
					map.put("message","你仍在登录有效期内!请勿重复登录!");
			 }

			return map;
		}
		
		

		@RequestMapping(value="register",method= {RequestMethod.POST,RequestMethod.GET})
		@ResponseBody
		@Transactional
		public Map<String,Object> Register( @RequestBody User user) {
			Map<String ,Object> map =new HashMap<String ,Object>();
			//先对接受前端的user对象进行md5加密
			user.setPassword(EncryptKit.MD5(user.getPassword()));
			//根据专业名字查询专业的id
			user.setMajor(userService.selectMajorId(user.getMajorName()));
			
			//定义返回对象
			User userr = null;
			
			//根据角色信息填充不同字段
			int status = user.getStatus();
			if (status == 0) {
				user.setPosition(-1);
			}else {
				user.setNickName(user.getRealName());
			}
			
			//进行注册
			userr = userService.register(user);
			
			if(userr == null)
			{
				map.put("code",Code.Register_FAIL.getCode());
				map.put("message",Code.Register_FAIL.getMsg());
				
			}else
			{
			
			map.put("code",Code.Register_SUCCESS.getCode());
			map.put("message",userr);
			}
			return map;
		}
//		
//		
//		
//		
//		
//
		@RequestMapping(value="email",method= {RequestMethod.POST,RequestMethod.GET})
		@ResponseBody
	    public Map<String,Object> sendMail03(@RequestBody User user){
			//定义返回对象
			System.out.println("正在进入email");
			Map<String,Object> map = new HashMap<>();
			System.out.println("邮箱为"+user.getEmail());
	        MimeMessage mMessage=javaMailSender.createMimeMessage();//创建邮件对象
	        MimeMessageHelper mMessageHelper;
	        Properties prop = new Properties();
	        String from;
	        //获取验证码
	        String text = Verification.getCode();
	        try {
	            //从配置文件中拿到发件人邮箱地址
	            prop.load(this.getClass().getResourceAsStream("/Properties/mail.properties"));
	            from = prop.get("mail.smtp.username")+"";
	            mMessageHelper=new MimeMessageHelper(mMessage,true);
	            
	            mMessageHelper.setFrom(from);//发件人邮箱
	            
	            mMessageHelper.setTo(user.getEmail());//收件人邮箱
	            
	            mMessageHelper.setSubject("云智舒心驿站验证码");//邮件的主题
	          
	            mMessageHelper.setText(text);//邮件的文本内容
	            
	            javaMailSender.send(mMessage);//发送邮件
	       
	        	map.put("code",Code.EMAIL_SUCCESS.getCode());
	        	map.put("message",Code.EMAIL_SUCCESS.getMsg());
	            map.put("email",text);
	            
	            
	        }  catch (Exception e) {
	        	map.put("code",Code.EMAIL_FAIL.getCode());
	        	map.put("message",Code.EMAIL_FAIL.getMsg());
	            e.printStackTrace();
	        }
	  
	        return map;
	    }
//
//		
//		
//		
//		
//		/**
//		 * 根据Cookie获取userName
//		 * 
//		 */
//		@RequestMapping("getUsername")
//		@CrossOrigin
//		@ResponseBody
//		public Object getUsername(HttpServletRequest request) {
//			
//					User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
//					return new Message(1,user.getUserName());
//					
//		}
//		 
//		/**
//		 * 根据Cookie获取AvatarUrl
//		 * 
//		 */
//		@RequestMapping("getAvatarUrl")
//		@CrossOrigin
//		@ResponseBody
//		public Object getAvatarUrl(HttpServletRequest request) {
//			
//			User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
//			return new Message(1,user.getAvatarUrl());
//			
//		}
//		
//		
//		
//		
//		/**
//		 * 用户上传头像
//		 *
//		 */
//		
//		
//		 @RequestMapping("uploadAVATAR")
//		 @CrossOrigin
//		 @ResponseBody
//		 
//		 public Object uploadPicture(HttpServletRequest request,HttpServletResponse response) {
//			 
//			
//			 
//			 //获取文件解析器
//			 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
//			 
//			 //判断request里是否有文件
//			 
//			 if(multipartResolver.isMultipart(request)) {
//				 
//				 //转化request
//				 MultipartHttpServletRequest multrequest = (MultipartHttpServletRequest) request;
//				
//				 
//		     //上传文件
//				 String AVATAR_URL = UploadUtils.upload(multrequest, "/AVATAR_URL","file");
//				 
//				 if(AVATAR_URL==null) {
//					 
//					 return new Message(Code.UPLOAD_FAIL.getCode(),Code.UPLOAD_FAIL.getMsg());
//				 }else {
//					 
//					 //获取user
//					 User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
//					 //添加url
//					 user.setAvatarUrl(AVATAR_URL);     
//					 
//					 //把头像路径传入数据库
//					 boolean flag =userService.uploadAVATARUR(user);
//					 if(flag == true) {
//						 return new Message(Code.UPLOAD_SUCCESS.getCode(),AVATAR_URL);
//					 }else {
//						 return new Message(Code.UPLOAD_FAIL.getCode(),Code.UPLOAD_FAIL.getMsg());
//					 }
//					 
//				 }
//				 
//			 }
//			 else {
//				 
//				return new Message(Code.UPLOAD_FAIL.getCode(),Code.UPLOAD_FAIL.getMsg());
//				 
//			 }
//
//			
//			 
//		 }
//		 
//
//		/*
//		 * 返回当前用户的全部信息
//		 * 
//		 */
//		 @RequestMapping("getUserProfile")
//		 @CrossOrigin
//		 @ResponseBody
//		 public Object getUserProfile() {
//			 
//			 
//			 User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
//			 
//			 return user;
//		 }
//		
//		 /*
//			 * 更改信息
//			 * 
//			 */
//		 @RequestMapping("updateUserProfile")
//		 @CrossOrigin
//		 @ResponseBody
//		 public Object updateUserProfile(@RequestBody User userr) {
//			 
//			 //获得uuid
//			 User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
//			 
//			 //给userr填充uuid
//			 userr.setUuid(user.getUuid());
//			 
//			 //存入数据库
//			 boolean flag = userService.updateUser(userr);
//			 
//			 //
//			 Message message = new Message();
//			 if(flag == true) {
//				 message.setCode(Code.UPDATE_SUCCESS.getCode());
//				 message.setMessage(Code.UPDATE_SUCCESS.getMsg());
//			 }else {
//				 message.setCode(Code.UPDATE_FAIL.getCode());
//				 message.setMessage(Code.UPDATE_FAIL.getMsg());
//			 }
//			 
//			 return message;
//		 }
//		
		
		
}