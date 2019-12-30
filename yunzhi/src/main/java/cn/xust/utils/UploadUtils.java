package cn.xust.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadUtils {

	/**
	 * 
	 * @param request 
	 * @param path 上传文件夹名
	 * @return
	 */
	public static String upload(MultipartHttpServletRequest request,String pathh,String fileName) {
		
		
		//获取文件
		MultipartFile file = request.getFile(fileName);
		
		if(file==null) {
			return null;
		}
		//获取文件夹得服务器路径
				String path = request.getServletContext().getRealPath(pathh);
			
		//获取文件原始的名字
				String oldName = file.getOriginalFilename();
		//截取后缀
				String behind = oldName.substring(oldName.lastIndexOf("."));
				
		//改写文件名字以防止中文乱码（时间戳加后缀）
				String newName = System.currentTimeMillis()+behind;
				
		//获取文件输入流
				try {
					InputStream in = file.getInputStream();
					//获取文件输出流
					OutputStream out =new FileOutputStream(new File(path,newName)); 
					
					byte[] buffer = new byte[2048];
					int length = 0;
					while( (length = in.read(buffer) ) != -1 )
					{
						out.write(buffer, 0, length);
					}
					
					out.flush();
					
					 if(out != null)
						 out.close();
					 if(in != null)
						 in.close();
					
					
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
				
		//返回文件在网上的路径
				
		return "http://49.232.138.118:8080/plarform"+pathh+"/"+newName;
	}
	
	
	
	
}
