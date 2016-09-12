package br.com.m4u.sms.api.domain.results;

public enum ResultTypeEnum {
	
	SUCCESS(1, "Success"),
	VALIDATION_ERROR(2, "Validation Error"),
	INTERNAL_SERVER_ERROR(3, "Internal Server Error");
	
	private int code;
	private String message;
	
	private ResultTypeEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}
}
