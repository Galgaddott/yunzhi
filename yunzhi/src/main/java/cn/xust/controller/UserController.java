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
 *   �û�������
 * add by galgaddott
 * last time 2020/1/1
 *
 */
	@Controller
	@RequestMapping("/user")
	public class UserController {
	    
		@Autowired
	    private UserServiceImpl userService;
		//��spring�����õ��ʼ����͵�bean
		@Autowired
		private JavaMailSender javaMailSender;

		@RequestMapping(value="login",method= {RequestMethod.POST,RequestMethod.GET})
		@ResponseBody
		 public Map<String ,Object> Login(@RequestBody Map<String ,Object> str,HttpServletResponse response) {

			Map<String ,Object> map =new HashMap<String ,Object>();
			//��ȡ����
			 Subject subject = SecurityUtils.getSubject();
			 if(!subject.isAuthenticated() && !subject.isRemembered()) {
				 System.out.println("���ڽ����¼��");
					//��װ�˻���Ϣ �������Ƚ���md5����
						UsernamePasswordToken token = new UsernamePasswordToken((String)str.get("account"),EncryptKit.MD5((String)str.get("password")));
						
						try {
							//���ü�ס��
							token.setRememberMe(true);
							
							subject.login(token); //����realm�ж�
							
							User user = userService.login((String)str.get("account"),(String)str.get("passWord"));
							if(user != null) {
								
								//����session �������ʹ�� Session - user
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
					map.put("message","�����ڵ�¼��Ч����!�����ظ���¼!");
			 }

			return map;
		}
		
		

		@RequestMapping(value="register",method= {RequestMethod.POST,RequestMethod.GET})
		@ResponseBody
		@Transactional
		public Map<String,Object> Register( @RequestBody User user) {
			Map<String ,Object> map =new HashMap<String ,Object>();
			//�ȶԽ���ǰ�˵�user�������md5����
			user.setPassword(EncryptKit.MD5(user.getPassword()));
			//����רҵ���ֲ�ѯרҵ��id
			user.setMajor(userService.selectMajorId(user.getMajorName()));
			
			//���巵�ض���
			User userr = null;
			
			//���ݽ�ɫ��Ϣ��䲻ͬ�ֶ�
			int status = user.getStatus();
			if (status == 0) {
				user.setPosition(-1);
			}else {
				user.setNickName(user.getRealName());
			}
			
			//����ע��
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
			//���巵�ض���
			System.out.println("���ڽ���email");
			Map<String,Object> map = new HashMap<>();
			System.out.println("����Ϊ"+user.getEmail());
	        MimeMessage mMessage=javaMailSender.createMimeMessage();//�����ʼ�����
	        MimeMessageHelper mMessageHelper;
	        Properties prop = new Properties();
	        String from;
	        //��ȡ��֤��
	        String text = Verification.getCode();
	        try {
	            //�������ļ����õ������������ַ
	            prop.load(this.getClass().getResourceAsStream("/Properties/mail.properties"));
	            from = prop.get("mail.smtp.username")+"";
	            mMessageHelper=new MimeMessageHelper(mMessage,true);
	            
	            mMessageHelper.setFrom(from);//����������
	            
	            mMessageHelper.setTo(user.getEmail());//�ռ�������
	            
	            mMessageHelper.setSubject("����������վ��֤��");//�ʼ�������
	          
	            mMessageHelper.setText(text);//�ʼ����ı�����
	            
	            javaMailSender.send(mMessage);//�����ʼ�
	       
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
//		 * ����Cookie��ȡuserName
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
//		 * ����Cookie��ȡAvatarUrl
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
//		 * �û��ϴ�ͷ��
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
//			 //��ȡ�ļ�������
//			 CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getServletContext());
//			 
//			 //�ж�request���Ƿ����ļ�
//			 
//			 if(multipartResolver.isMultipart(request)) {
//				 
//				 //ת��request
//				 MultipartHttpServletRequest multrequest = (MultipartHttpServletRequest) request;
//				
//				 
//		     //�ϴ��ļ�
//				 String AVATAR_URL = UploadUtils.upload(multrequest, "/AVATAR_URL","file");
//				 
//				 if(AVATAR_URL==null) {
//					 
//					 return new Message(Code.UPLOAD_FAIL.getCode(),Code.UPLOAD_FAIL.getMsg());
//				 }else {
//					 
//					 //��ȡuser
//					 User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
//					 //���url
//					 user.setAvatarUrl(AVATAR_URL);     
//					 
//					 //��ͷ��·���������ݿ�
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
//		 * ���ص�ǰ�û���ȫ����Ϣ
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
//			 * ������Ϣ
//			 * 
//			 */
//		 @RequestMapping("updateUserProfile")
//		 @CrossOrigin
//		 @ResponseBody
//		 public Object updateUserProfile(@RequestBody User userr) {
//			 
//			 //���uuid
//			 User user = (User) SecurityUtils.getSubject().getSession().getAttribute(SecurityUtils.getSubject().getSession().getId().toString());
//			 
//			 //��userr���uuid
//			 userr.setUuid(user.getUuid());
//			 
//			 //�������ݿ�
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