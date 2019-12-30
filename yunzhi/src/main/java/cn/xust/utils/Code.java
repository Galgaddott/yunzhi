package cn.xust.utils;

/**
 * ״̬����
 * @author ��ε
 *
 */
public enum Code {

	
	Login_SUCCESS(1,"��½�ɹ�!"),
    Login_FAIL(0,"��¼ʧ�ܣ�"),
    Register_SUCCESS(1,"ע��ɹ���"),
    Register_FAIL(0,"ע��ʧ�ܣ�"),
    Upload_SUCCESS(1,"�ϴ��ɹ���"),
    Upload_FAIL(0,"�ϴ�ʧ�ܣ�"),
    EMAIL_SUCCESS(1,"�����ʼ��ɹ���"),
    EMAIL_FAIL(0,"�����ʼ�ʧ�ܣ�"),
    UPLOAD_SUCCESS(1,"�ϴ��ļ��ɹ�!"),
    UPLOAD_FAIL(0,"�ϴ��ļ�ʧ��"),
    UPDATE_SUCCESS(1,"���³ɹ�"),
    UPDATE_FAIL(0,"����ʧ��"),
    CREATE_SUCCESS(1,"�����ɹ�"),
    CREATE_FAIL(0,"����ʧ��"),
    Error_code(500,"�������ڲ�����"),;
  

	private Integer code;
    private String msg;
    
	private Code(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	 
    public Integer getCode() {
		return code;
	}


	public String getMsg() {
		return msg;
	}

	
	
	
}
