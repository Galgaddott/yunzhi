package cn.xust.utils;

import java.util.Random;

public class Verification {
    
	
	private static final char[] code = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H',
			'I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private static final Random random = new Random();
	
	public static String getCode() {
		 StringBuffer sb = new StringBuffer();
		for( int i = 1 ; i<=4 ;i++) {
			sb.append(code[random.nextInt(37)]);
		}
		
		
		return sb.toString();
	}
	
	

	
	
}
