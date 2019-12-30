package cn.xust.utils;

import java.security.MessageDigest;
/**
 * 账户信息md5加密类
 * @author 周蔚
 *
 */
public class EncryptKit {
	
	private static String MD5 = "MD5";
	/**
	 * MD5加密
	 * @param string
	 * @return
	 */
	public static String MD5(String string) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] older = string.getBytes();
			MessageDigest md5 = MessageDigest.getInstance(MD5);
			md5.update(older);
			byte[] newer = md5.digest();
			int j = newer.length;
			char[] chars = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = newer[i];
				chars[k++] = hexDigits[b >>> 4 & 0xf];
				chars[k++] = hexDigits[b & 0xf];
			}
			return new String(chars);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
