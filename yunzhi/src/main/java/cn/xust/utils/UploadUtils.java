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
	 * @param path �ϴ��ļ�����
	 * @return
	 */
	public static String upload(MultipartHttpServletRequest request,String pathh,String fileName) {
		
		
		//��ȡ�ļ�
		MultipartFile file = request.getFile(fileName);
		
		if(file==null) {
			return null;
		}
		//��ȡ�ļ��е÷�����·��
				String path = request.getServletContext().getRealPath(pathh);
			
		//��ȡ�ļ�ԭʼ������
				String oldName = file.getOriginalFilename();
		//��ȡ��׺
				String behind = oldName.substring(oldName.lastIndexOf("."));
				
		//��д�ļ������Է�ֹ�������루ʱ����Ӻ�׺��
				String newName = System.currentTimeMillis()+behind;
				
		//��ȡ�ļ�������
				try {
					InputStream in = file.getInputStream();
					//��ȡ�ļ������
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
				
		//�����ļ������ϵ�·��
				
		return "http://49.232.138.118:8080/plarform"+pathh+"/"+newName;
	}
	
	
	
	
}
